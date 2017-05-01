package com.presto;

public class Point
{
    private String x;
    private String y;
    private String r;
    private boolean inArea;

    public Point(String x, String y, String r, boolean inArea)
    {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
    }

    public String getX()
    {
        return x;
    }

    public String getY()
    {
        return y;
    }

    public String getR()
    {
        return r;
    }

    public boolean isInArea()
    {
        return inArea;
    }

}
