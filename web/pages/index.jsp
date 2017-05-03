<%@ page import="java.util.ArrayList,com.presto.lab7.*" %>
<%@ page import="com.presto.lab7.AreaCheckServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  ArrayList<Point> points = (ArrayList<Point>) application.getAttribute("checkedPoints");
%>

<html>

<head>

  <meta charset="utf-8">
  <title>Параметризированная область</title>

  <script type="text/javascript" src="${pageContext.request.contextPath}/pages/scripts.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style.css">

</head>

<body onload="<%
              if (points != null) {
                for(Point point : points){
                  if(point.getCheckResult() != null)
                    out.print("points.push(new Point("
                    + point.getXScaled() + ","
                    + point.getYScaled() + ","
                    + point.getR() + ","
                    + point.getCheckResult() +"));");
                }
                out.print("selectRValue('" + "r" + application.getAttribute(AreaCheckServlet.R_ATTRIBUTE) + "');");
              }
            %>plotDraw();">

<header class="header-area">
  <h1 align="center">
    Параметризированная область
  </h1>
  <h4 align="center">
    Постовалов Роман Дмитриевич P3202 №41200
  </h4>
</header>

<table align="center">
  <tr>
    <td rowspan="3">
      <canvas id="plot" height="440px" width="440px" onclick="plotClick(event)"></canvas>
    </td>

    <td>
      <fieldset class="field-value">
        <legend>Координаты точки</legend>

        <div class="value">
          <label>X
            <label id="error-x" class="error"></label>
          </label>
          <input type="text" name="value-x" placeholder="-3 ... 5">
        </div>

        <div class="value">
          <label>Y
            <label id="error-y" class="error"></label>
          </label>
          <input type="button" name="value-y" id="y-1" onClick="selectY(this.id)" value="-4">
          <input type="button" name="value-y" id="y-2" onClick="selectY(this.id)" value="-3">
          <input type="button" name="value-y" id="y-3" onClick="selectY(this.id)" value="-2">
          <input type="button" name="value-y" id="y-4" onClick="selectY(this.id)" value="-1">
          <input type="button" name="value-y" id="y-5" onClick="selectY(this.id)" value="0">
          <input type="button" name="value-y" id="y-6" onClick="selectY(this.id)" value="1">
          <input type="button" name="value-y" id="y-7" onClick="selectY(this.id)" value="2">
          <input type="button" name="value-y" id="y-8" onClick="selectY(this.id)" value="3">
          <input type="button" name="value-y" id="y-9" onClick="selectY(this.id)" value="4">
        </div>

      </fieldset>
    </td>
  </tr>

  <tr>
    <td>
      <fieldset class="field-value">
        <legend>Параметр</legend>

        <div class="value">
          <label>R
            <label id="error-r" class="error"></label>
          </label>
          <input type="button" name="value-r" id="r1.0" onClick="selectR(this.id)" value="1">
          <input type="button" name="value-r" id="r1.5" onClick="selectR(this.id)" value="1.5">
          <input type="button" name="value-r" id="r2.0" onClick="selectR(this.id)" value="2">
          <input type="button" name="value-r" id="r2.5" onClick="selectR(this.id)" value="2.5">
          <input type="button" name="value-r" id="r3.0" onClick="selectR(this.id)" value="3">
        </div>

      </fieldset>
    </td>
  </tr>

  <tr>
    <td>
      <input type="submit" name="check" onclick="checkValues()" value="Проверить">
    </td>
  </tr>

  <tr style="display: <%= (points == null) || (points.size() == 0) ? "none" : "table-row" %>;">
    <td colspan="2">

      <div id="result-card">
        <div id="result-title">
          Результаты проверок
        </div>

        <div id="scroll-div">
          <table id="result-table">
            <thead>
            <tr>
              <th width="7%">x</th>
              <th width="7%">y</th>
              <th width="7%">r</th>
              <th width="25%">принадлежит области</th>
            </tr>
            </thead>

            <tbody id="results">
            <%
              if (points != null) {
                for (int i = points.size() - 1; i >= 0; i--)
                  out.println("<tr><td>" + points.get(i).getX() +
                          "</td><td>" + points.get(i).getY() +
                          "</td><td>" + points.get(i).getR() +
                          "</td><td>" + points.get(i).getCheckString() + "</td></tr>");
              }
            %>
            </tbody>

          </table>
        </div>

      </div>
    </td>
  </tr>


</table>

</body>

</html>
