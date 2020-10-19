import java.lang.Math;

public class GraphicalPoint {

    private int xPixel;
    private int yPixel;

    public GraphicalPoint(float lat, float longi){
    
        float x = (float) ((lat - 4.8) * 10000);
        float y = (float) ((longi - 45.7) * 10000);
        xPixel = Math.round(x);
        yPixel = Math.round(y);
    }

    public int getXPixel(){
        return xPixel;
    }

    public int getYPixel(){
        return yPixel;
    }
        
    
}