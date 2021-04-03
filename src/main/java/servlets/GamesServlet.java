package servlets;

import models.sportEvents.Game;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/games")
public class GamesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            var data = Service.read(Game.class);
            req.setAttribute("data", data);
            req.getRequestDispatcher("views/games.jsp").forward(req, resp);
    }
}
