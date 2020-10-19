package view;
import javax.swing.*;

import model.Intersection;
import model.Map;
import model.Segment;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class GraphicalView  extends JPanel{
    private List<Intersection> intersections;
    private List<Segment> segments;
    private float minLat;
    private float minLongi;
    private float maxLat;
    private float maxLongi;

    protected List<GraphicalPoint> graphicalPoints;
    
    public GraphicalView(Map loadedMap){
        setLayout(null);
        setBounds(0,0,800,800);
        intersections = loadedMap.getInstersections();
        segments = loadedMap.getSegments();

        graphicalPoints = new LinkedList<GraphicalPoint>();
        minLat = Float.POSITIVE_INFINITY;
        minLongi = Float.POSITIVE_INFINITY;
        maxLat = 0;
        maxLongi = 0;

        setExtemeCoordonates();
        System.out.println("Extremes : " + minLat + " " + maxLat + " " + minLongi + " " + maxLongi);
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0,0,800,800);

        //Draw intersections
        g.setColor(Color.white);
        for (Intersection i : intersections) {
            GraphicalPoint gp = new GraphicalPoint(i, minLat, minLongi, maxLat, maxLongi);
            graphicalPoints.add(gp);
            g.fillOval(gp.getXPixel(), gp.getYPixel(), 8, 8);
        }

        //Draw segments
        for(Segment s: segments){
            GraphicalSegment gs = createSegment(s.getIdOrigin(), s.getIdDestination());
            if (gs!=null){
                g.drawLine(gs.getXOriginPixel(), gs.getYOriginPixel(), gs.getXDestPixel(), gs.geYDestPixel());
            }
        }
       
        
    }

    public void setExtemeCoordonates(){
        for (Intersection i : intersections) {
            if(i.getLatitude() < minLat){
                minLat = i.getLatitude();
            } else if (i.getLatitude() > maxLat){
                maxLat = i.getLatitude();
            }

            if(i.getLongitude() < minLongi){
                minLongi = i.getLongitude();
            } else if (i.getLongitude() > maxLongi){
                maxLongi = i.getLongitude();
            }
        }
    }

    public GraphicalSegment createSegment(String idOrigin, String idDestination){
        //Go through the list to find the intersection
        int i = 0;
        GraphicalPoint origin = null;
        GraphicalPoint destination = null;
        while ((i<graphicalPoints.size()) && (origin == null || destination == null) ){
            if(idOrigin.equals(graphicalPoints.get(i).getIntersectionId())){
                origin = graphicalPoints.get(i);
            }
            if(idDestination.equals(graphicalPoints.get(i).getIntersectionId())){
                destination = graphicalPoints.get(i);
            }
            i++;
        }
        if(origin != null && destination != null){
            GraphicalSegment gs = new GraphicalSegment(origin.getXPixel(), origin.getYPixel(), destination.getXPixel(), destination.getYPixel());
            return gs;
        }
        return null;
    }


    
}


