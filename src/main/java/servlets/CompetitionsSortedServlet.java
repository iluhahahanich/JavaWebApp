package servlets;

import models.AgeGroup;
import models.Competition;
import app.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/competitions_sorted")
public class CompetitionsSortedServlet extends HttpServlet {
    private final ServiceLayer<Competition> serviceLayer = new ServiceLayer<>(Competition.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var data = serviceLayer.readAll();
        data = (List<Competition>) serviceLayer.getSortedByAgeGroupAttendance(data, AgeGroup.ADULT);
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/competitions_sorted.jsp").forward(req, resp);
    }
}