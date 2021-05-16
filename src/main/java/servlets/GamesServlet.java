package servlets;

import models.Game;
import app.ServiceLayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/games")
public class GamesServlet extends HttpServlet {
    private final ServiceLayer<Game> serviceLayer = new ServiceLayer<>(Game.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var data = serviceLayer.read();
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/games.jsp").forward(req, resp);
    }
}
