package view;

import java.awt.*;

public class GraphicalSegment {
    private int xOriginPixel;
    private int yOriginPixel;
    private int xDestPixel;
    private int yDestPixel;
    private Color color;
    /* Color code:
        white: random segment
        blue : segment used by the tour
    */

    public GraphicalSegment(int xo, int yo, int xd, int yd){
        //+4 so that the line starts from the center of the point
        xOriginPixel = xo + 4;
        yOriginPixel = yo + 4;
        xDestPixel = xd + 4;
        yDestPixel = yd + 4;
        color = Color.white;

    }

    public int getXOriginPixel(){
        return xOriginPixel;
    }

    public int getYOriginPixel(){
        return yOriginPixel;
    }

    public int getXDestPixel(){
        return xDestPixel;
    }

    public int geYDestPixel(){
        return yDestPixel;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color c){
        color = c;
    }

}