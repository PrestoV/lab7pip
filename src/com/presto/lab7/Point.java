package com.presto.lab7;


public class Point {
    private String x;
    private String y;
    private String r;
    private String checkResult;

    public Point(String x, String y, String r, String checkResult) {
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

    public String getCheckResult() {
        return checkResult;
    }

}
