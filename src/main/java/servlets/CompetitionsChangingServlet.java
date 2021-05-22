package servlets;

import app.ServiceLayer;
import models.Competition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@WebServlet("/competitions_changing")
public class CompetitionsChangingServlet extends HttpServlet {
    private final ServiceLayer<Competition> serviceLayer = new ServiceLayer<>(Competition.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("data", serviceLayer.readAll());
        req.getRequestDispatcher("views/competitions_changing.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("changeId") != null){
            String id = req.getParameter("changeId");
            req.setAttribute("changing", id);
            this.doGet(req, resp);
        }
        else if (req.getParameter("changingCancel") != null) {
            resp.sendRedirect("competitions");
        }
        else if (req.getParameter("new") != null){
            var newCompetition = new Competition();
            if (!ServiceLayer.daoType.equals("mongo") && !ServiceLayer.daoType.equals("postgre")) {
                String id = UUID.randomUUID().toString();
                newCompetition.setId(id);
            }
            serviceLayer.create(newCompetition);
            req.setAttribute("changing", newCompetition.getId());
            this.doGet(req, resp);
        }
        else if (req.getParameter("okId") != null) {
            var params = req.getParameterNames();
            var curCompetition = serviceLayer.read(req.getParameter("okId"));
            for (String param; params.hasMoreElements(); ) {
                param = params.nextElement();
                switch (param) {
                    case "title" -> curCompetition.setTitle(req.getParameter(param));
                    case "place" -> curCompetition.setPlace(req.getParameter(param));
                    case "date" -> {
                        try {
                            var paramStr= req.getParameter(param);
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(paramStr);
                            curCompetition.setDate(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    case "children" -> curCompetition.getAttendance().setChildren(Integer.parseInt(req.getParameter(param)));
                    case "adults" -> curCompetition.getAttendance().setAdults(Integer.parseInt(req.getParameter(param)));
                    case "elderly" -> curCompetition.getAttendance().setElderly(Integer.parseInt(req.getParameter(param)));
                    case "gold" -> curCompetition.getPedestal().setGold(req.getParameter(param));
                    case "silver" -> curCompetition.getPedestal().setSilver(req.getParameter(param));
                    case "bronze" -> curCompetition.getPedestal().setBronze(req.getParameter(param));
                }
            }
            serviceLayer.update(curCompetition);
            resp.sendRedirect("competitions");
        }
    }
}
