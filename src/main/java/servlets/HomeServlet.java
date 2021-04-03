package servlets;

import clients.CsvDao;
import clients.Dao;
import clients.JsonDao;
import clients.XmlDao;
import handlers.LoggingProxyHandler;
import handlers.SportEventsHandler;
import models.AgeGroup;
import models.sportEvents.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.time.DayOfWeek;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Game> data = null;
        req.setAttribute("data", data);
        req.getRequestDispatcher("views/home.jsp").forward(req, resp);
    }
}
