//Is this class useful?

public class GraphicalSegment {
    private int xOriginPixel;
    private int yOriginPixel;
    private int xDestPixel;
    private int yDestPixel;

    public GraphicalSegment(int xo, int yo, int xd, int yd){
        //+4 so that th line starts from the center of the point
        xOriginPixel = xo + 4;
        yOriginPixel = yo + 4;
        xDestPixel = xd + 4;
        yDestPixel = yd + 4;

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

}