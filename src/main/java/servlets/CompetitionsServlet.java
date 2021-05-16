package servlets;

import models.Competition;
import app.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// TODO: fix Date displaying in in JSON

@WebServlet("/competitions")
public class CompetitionsServlet extends HttpServlet {
    private final ServiceLayer<Competition> serviceLayer = new ServiceLayer<>(Competition.class);
    private List<Competition> data;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        data = serviceLayer.read();
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/competitions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        data = serviceLayer.read();
        if (req.getParameter("deleteId") != null) {
            var id = req.getParameter("deleteId");
            data.removeIf(e -> e.getId().equals(id));
            serviceLayer.write(data);
            this.doGet(req, resp);
        }
    }
}
