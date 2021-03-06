package com.presto.lab7;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "com.presto.lab7.ControllerServlet", urlPatterns = "/app")
public class ControllerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if(req.getParameter("r") != null) {
            req.getRequestDispatcher("check").forward(req, res);
        } else {
            req.getRequestDispatcher("/pages/index.jsp").forward(req, res);
        }

    }
}

