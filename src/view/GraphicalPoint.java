package view;
import java.lang.Math;

public class GraphicalPoint {

    private int xPixel;
    private int yPixel;
    private String intersectionId;


    public GraphicalPoint(Intersection i, float minLat, float minLongi,  float maxLat, float maxLongi){
        float xRange = maxLat - minLat;
        float yRange = maxLongi - minLongi;

        float x = (float) ((i.getLatitude() - minLat )*800/xRange);
        float y = (float) ((i.getLongitude() - minLongi)*800/yRange);
     
        xPixel = Math.round(x);
        yPixel = Math.round(y);
        intersectionId = i.getId();
    }

    public int getXPixel(){
        return xPixel;
    }

    public int getYPixel(){
        return yPixel;
    }

    public String getIntersectionId(){
        return intersectionId;
    }

        
    
}