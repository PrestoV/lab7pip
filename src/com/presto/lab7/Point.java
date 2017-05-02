package com.presto.lab7;


public class Point {
    public final static String IN_AREA = "да";
    public final static String NOT_IN_AREA = "нет";
    public final static String INCORRECT_PARAMS = "Неверные параметры!";

    private String x;
    private String y;
    private String r;
    private Boolean checkResult;

    public Point(String x, String y, String r, Boolean checkResult) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.checkResult = checkResult;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public boolean getCheckResult() {
        return checkResult;
    }

    public String getCheckString() {
        if(checkResult == null)
            return INCORRECT_PARAMS;

        return checkResult ? IN_AREA : NOT_IN_AREA;
    }

}
