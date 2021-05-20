package servlets;

import models.Competition;
import app.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/competitions")
public class CompetitionsServlet extends HttpServlet {
    private final ServiceLayer<Competition> serviceLayer = new ServiceLayer<>(Competition.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("data", serviceLayer.readAll());
        req.getRequestDispatcher("views/competitions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("deleteId") != null) {
            var id = req.getParameter("deleteId");
            serviceLayer.delete(id);
            this.doGet(req, resp);
        }
    }
}
