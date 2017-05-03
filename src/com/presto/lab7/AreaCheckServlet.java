package com.presto.lab7;

import com.presto.lab7.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "com.presto.lab7.AreaCheckServlet", urlPatterns = "/check")
public class AreaCheckServlet extends HttpServlet {
    public final static String POINTS_ATTRIBUTE = "checkedPoints";
    public final static String R_ATTRIBUTE = "rValue";
    public final static String X_PARAM = "x";
    public final static String Y_PARAM = "y";
    public final static String R_PARAM = "r";

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ArrayList<Point> points;
        Double x = null;
        Double y = null;
        Double r;
        Double oldR;

        String xParam = req.getParameter(X_PARAM);
        String yParam = req.getParameter(Y_PARAM);
        String rParam = req.getParameter(R_PARAM);
        Boolean checkResult;

        points = (ArrayList<Point>) getServletContext()
                .getAttribute(POINTS_ATTRIBUTE);
        if(points == null) {
            points = new ArrayList<>();
        }
        r = Double.parseDouble( strip(rParam) );
        oldR = (Double) getServletContext()
                .getAttribute(R_ATTRIBUTE);
        if(oldR == null){
            oldR = r;
        }
        scalePoints(points, oldR, r);
        getServletContext().setAttribute(R_ATTRIBUTE, r);

        if(req.getParameter("x") != null && req.getParameter("y") != null){
            try {
                x = Double.parseDouble( strip(xParam) );
                y = Double.parseDouble( strip(yParam) );

                if( x < -3 || x > 5
                        || y < -4 || y > 4
                        || r < 1 || r > 3)
                    throw new NumberFormatException();

                checkResult = pointCheck(x, y, r);
            } catch(NumberFormatException|NullPointerException e) {
                checkResult = null;
            }

            points.add( new Point(xParam, yParam, rParam, checkResult, x, y, r) );
            getServletContext().setAttribute(POINTS_ATTRIBUTE, points);
        }

        req.getRequestDispatcher("/pages/index.jsp").forward(req, res);
    }

    private static void scalePoints(ArrayList<Point> points, Double oldR, Double newR){
        if(newR != null && oldR != null && points != null && points.size() != 0){
            double scale = newR / oldR;
            for(Point point : points){
                point.setScale(scale);
                point.newCheckResult( pointCheck(point.getXScaled(), point.getYScaled(), point.getRValue()) );
            }
        }
    }

    private String strip(String numStr) {
        if(numStr == null)
            return null;

        return numStr.replaceFirst("[.]00+", ".0");
    }

    private static boolean pointCheck(double x, double y, double r) {
        if(x > 0) {
            if(y > 0)
                return checkFirstRegion(x, y, r);
            else if(y < 0)
                return checkFourthRegion(x, y, r);
            else
                return checkFirstRegion(x, y, r) || checkFourthRegion(x, y, r);
        }
        else if (x < 0) {
            if(y > 0)
                return checkSecondRegion(x, y, r);
            else if(y < 0)
                return checkThirdRegion(x, y, r);
            else
                return checkSecondRegion(x, y, r) || checkThirdRegion(x, y, r);
        }
        else {
            if(y > 0)
                return checkFirstRegion(x, y, r) || checkSecondRegion(x, y, r);
            else if(y < 0)
                return checkThirdRegion(x, y, r) || checkFourthRegion(x, y, r);
            else
                return checkFirstRegion(x, y, r) || checkSecondRegion(x, y, r)
                        || checkThirdRegion(x, y, r) || checkFourthRegion(x, y, r);
        }
    }

    private static boolean checkFirstRegion(double x, double y, double r) {
        return false;
    }

    private static boolean checkSecondRegion(double x, double y, double r) {
        return x <= 0 && y >= 0
                && x >= -r && y <= r;
    }

    private static boolean checkThirdRegion(double x, double y, double r) {
        return x <= 0 && y <= 0
                && x >= -r && y >= (-x - r);
    }

    private static boolean checkFourthRegion(double x, double y, double r) {
        return x >= 0 && y <= 0
                && (x*x + y*y <= r*r/4);
    }
}

