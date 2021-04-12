package servlets;

import models.Competition;
import service.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/competitions_changing")
public class CompetitionsChangingServlet extends HttpServlet {
    private final ServiceLayer<Competition> serviceLayer = new ServiceLayer<>(Competition.class);
    private List<Competition> data;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        data = serviceLayer.read();
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/competitions_changing.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        data = serviceLayer.read();
        if (req.getParameter("changeId") != null){
            String id = req.getParameter("changeId");
            req.setAttribute("changing", id);
            this.doGet(req, resp);
        }
        else if (req.getParameter("changingCancel") != null) {
            resp.sendRedirect("competitions");
        }
        else if (req.getParameter("new") != null){
            String id = UUID.randomUUID().toString();
            var newCompetition = new Competition();
            newCompetition.setId(id);
            data.add(newCompetition);
            serviceLayer.write(data);
            req.setAttribute("changing", id);
            this.doGet(req, resp);
        }
        else if (req.getParameter("okId") != null) {
            var params = req.getParameterNames();
            var curCompetition = data.stream()
                    .filter(e -> e.getId().equals(req.getParameter("okId")))
                    .findFirst().get();
            for (String param; params.hasMoreElements(); ) {
                param = params.nextElement();
                switch (param) {
                    case "title" -> curCompetition.setTitle(req.getParameter(param));
                    case "place" -> curCompetition.setPlace(req.getParameter(param));
                    case "date" -> {
                        try {
                            curCompetition.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(req.getParameter(param)));
                        } catch (DatatypeConfigurationException e) {
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
            serviceLayer.write(data);
            resp.sendRedirect("competitions");
        }
    }
}
