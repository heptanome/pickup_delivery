public class GraphicalPoint {
    private int xPixel;
    private int yPixel;

    public GraphicalPoint(float lat, float longi){
        xPixel = (int)(lat-45)*800;
        yPixel = (int)(longi-4)*800;
    }

    public int getXPixel(){
        return xPixel;
    }

    public int getYPixel(){
        return yPixel;
    }
        
    
}