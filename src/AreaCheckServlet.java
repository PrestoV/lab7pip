import com.presto.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AreaCheckServlet", urlPatterns = "/check")
public class AreaCheckServlet extends HttpServlet
{
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        double x, y, r;
        String resRow;

        //res.setContentType("text/html;charset=UTF-8");

        String xParam = req.getParameter("x");
        String yParam = req.getParameter("y");
        String rParam = req.getParameter("r");
        boolean inArea;

        try
        {
            x = Double.parseDouble( strip(xParam) );
            y = Double.parseDouble( strip(yParam) );
            r = Double.parseDouble( strip(rParam) );

            if( x < -3 || x > 5
                || y < -4 || y > 4
                || r < 1 || r > 3)
                throw new NumberFormatException();

            inArea = pointCheck(x, y, r);

            ArrayList<Point> points = (ArrayList<Point>) getServletContext().getAttribute("checkedPoints");

            if(points == null)
                points = new ArrayList<>();

            points.add( new Point(xParam, yParam, rParam, inArea) );

            getServletContext().setAttribute("checkedPoints", points);

            /*
            resRow = "<tr><td>" + xParam +
                    "</td><td>" + yParam +
                    "</td><td>" + rParam +
                    "</td><td>" + (inArea?"да":"нет") + "</td></tr>";
                    */
        }
        catch(NumberFormatException|NullPointerException e)
        {
            /*
            resRow = "<tr><td>" + xParam +
                "</td><td>" + yParam +
                "</td><td>" + rParam +
                "</td><td>" + "Incorrect parameters!" + "</td></tr>"; */
        }

/*
        PrintWriter out = res.getWriter();
        out.println("<div id=\"result-card\" style=\"width: 75%\">" +
                        "<div id=\"result-title\">" +
                            "Результат проверки" +
                        "</div>" +
                        "<div id=\"scroll-div\">" +
                            "<table id=\"result-table\">" +
                                "<thead>" +
                                    "<tr>" +
                                        "<th width=\"7%\">x</th>" +
                                        "<th width=\"7%\">y</th>" +
                                        "<th width=\"7%\">r</th>" +
                                        "<th width=\"25%\">принадлежит области</th>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody id=\"results\">" + resRow +
                                "</tbody>" +
                            "</table>" +
                        "</div>" +
                "</div>" +
                "<input type=\"submit\" name=\"check\" style=\"width: 25%\" " +
                "onclick=\"location.href='/lab7Pip/app'\" value=\"Новый запрос\">"
        );
        */

        req.getRequestDispatcher("/pages/index.jsp").forward(req, res);
    }

    private String strip(String numStr)
    {
        if(numStr == null)
            return null;

        int dot_pos = numStr.indexOf(".");
        if(dot_pos == -1)
            return numStr;

        int len = numStr.length() - dot_pos - 2;
        dot_pos++;

        while (len-- > 0 && numStr.charAt(dot_pos + 1) == '0')
        {
            numStr = numStr.replaceFirst(".0", ".");
        }

        return numStr;
    }

    private boolean pointCheck(double x, double y, double r)
    {
        if(x > 0)
        {
            if(y > 0)
                return checkFirstRegion(x, y, r);
            else if(y < 0)
                return checkFourthRegion(x, y, r);
            else
                return checkFirstRegion(x, y, r) || checkFourthRegion(x, y, r);
        }
        else if (x < 0)
        {
            if(y > 0)
                return checkSecondRegion(x, y, r);
            else if(y < 0)
                return checkThirdRegion(x, y, r);
            else
                return checkSecondRegion(x, y, r) || checkThirdRegion(x, y, r);
        }
        else
        {
            if(y > 0)
                return checkFirstRegion(x, y, r) || checkSecondRegion(x, y, r);
            else if(y < 0)
                return checkThirdRegion(x, y, r) || checkFourthRegion(x, y, r);
            else
                return checkFirstRegion(x, y, r) || checkSecondRegion(x, y, r)
                        || checkThirdRegion(x, y, r) || checkFourthRegion(x, y, r);
        }
    }

    private boolean checkFirstRegion(double x, double y, double r)
    {
        return false;
    }

    private boolean checkSecondRegion(double x, double y, double r)
    {
        return x <= 0 && y >= 0
                && x >= -r && y <= r;
    }

    private boolean checkThirdRegion(double x, double y, double r)
    {
        return x <= 0 && y <= 0
                && x >= -r && y >= (-x - r);
    }

    private boolean checkFourthRegion(double x, double y, double r)
    {
        return x >= 0 && y <= 0
                && (x*x + y*y <= r*r/4);
    }
}

