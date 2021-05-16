package servlets;

import models.Match;
import app.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private final ServiceLayer<Match> serviceLayer = new ServiceLayer<>(Match.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var data = serviceLayer.read();
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/matches.jsp").forward(req, resp);
    }
}
