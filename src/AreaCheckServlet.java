import com.presto.lab7.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "AreaCheckServlet", urlPatterns = "/check")
public class AreaCheckServlet extends HttpServlet {
    public final static String POINTS_ATTRIBUTE = "checkedPoints";
    public final static String X_PARAM = "x";
    public final static String Y_PARAM = "y";
    public final static String R_PARAM = "r";
    public final static String IN_AREA = "да";
    public final static String NOT_IN_AREA = "нет";
    public final static String INCORRECT_PARAMS = "Неверные параметры!";

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        double x, y, r;

        String xParam = req.getParameter(X_PARAM);
        String yParam = req.getParameter(Y_PARAM);
        String rParam = req.getParameter(R_PARAM);
        String checkResult;

        try {
            x = Double.parseDouble( strip(xParam) );
            y = Double.parseDouble( strip(yParam) );
            r = Double.parseDouble( strip(rParam) );

            if( x < -3 || x > 5
                || y < -4 || y > 4
                || r < 1 || r > 3)
                throw new NumberFormatException();

            checkResult = pointCheck(x, y, r) ? IN_AREA : NOT_IN_AREA;

        } catch(NumberFormatException|NullPointerException e) {
            checkResult = INCORRECT_PARAMS;
        }

        ArrayList<Point> points = (ArrayList<Point>) getServletContext()
                .getAttribute(POINTS_ATTRIBUTE);
        if(points == null)
            points = new ArrayList<>();

        points.add( new Point(xParam, yParam, rParam, checkResult) );
        getServletContext().setAttribute(POINTS_ATTRIBUTE, points);

        req.getRequestDispatcher("/pages/index.jsp").forward(req, res);
    }

    private String strip(String numStr) {
        if(numStr == null)
            return null;

        return numStr.replaceFirst("[.]00+", ".0");
    }

    private boolean pointCheck(double x, double y, double r) {
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

    private boolean checkFirstRegion(double x, double y, double r) {
        return false;
    }

    private boolean checkSecondRegion(double x, double y, double r) {
        return x <= 0 && y >= 0
                && x >= -r && y <= r;
    }

    private boolean checkThirdRegion(double x, double y, double r) {
        return x <= 0 && y <= 0
                && x >= -r && y >= (-x - r);
    }

    private boolean checkFourthRegion(double x, double y, double r) {
        return x >= 0 && y <= 0
                && (x*x + y*y <= r*r/4);
    }
}

