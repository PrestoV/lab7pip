package com.presto.lab7;


public class Point {
    public final static String IN_AREA = "да";
    public final static String NOT_IN_AREA = "нет";
    public final static String INCORRECT_PARAMS = "Неверные параметры!";

    private String x;
    private String y;
    private String r;
    private Boolean checkResult;
    private Double rValue;
    private Double xScaled;
    private Double yScaled;

    public Point(String x, String y, String r, Boolean checkResult, Double xScaled, Double yScaled, Double rValue) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.checkResult = checkResult;
        this.rValue = rValue;
        this.xScaled = xScaled;
        this.yScaled = yScaled;
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

    public Boolean getCheckResult() {
        return checkResult;
    }

    public String getCheckString() {
        if(checkResult == null)
            return INCORRECT_PARAMS;

        return checkResult ? IN_AREA : NOT_IN_AREA;
    }

    public double getXScaled(){
        return xScaled;
    }

    public double getYScaled(){
        return yScaled;
    }

    public double getRValue(){
        return rValue;
    }

    public void setScale(double scale){
        if(xScaled != null && yScaled != null){
            xScaled *= scale;
            yScaled *= scale;
        }
    }

    public void newCheckResult(Boolean checkResult){
        this.checkResult = checkResult;
    }

}
