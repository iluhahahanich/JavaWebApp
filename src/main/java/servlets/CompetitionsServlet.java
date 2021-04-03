package servlets;

import models.sportEvents.Competition;
import models.sportEvents.Game;
import service.ServiceLayer;

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
        var data = serviceLayer.read();
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/competitions.jsp").forward(req, resp);
    }
}
