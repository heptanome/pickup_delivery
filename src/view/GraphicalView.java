package view;
import javax.swing.*;

import model.Intersection;
import model.Map;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class GraphicalView  extends JPanel {
    private List<Intersection> intersections;
    private List<Segment> segments;
    private float minLat;
    private float minLongi;
    private float maxLat;
    private float maxLongi;

    private List<GraphicalPoint> graphicalPoints;
    private List<GraphicalSegment> graphicalSegments;
    
    public GraphicalView(Map loadedMap){
        setLayout(null);
        setBounds(0,0,800,800);
        intersections = loadedMap.getInstersections();
        segments = loadedMap.getSegments();

        graphicalPoints = new LinkedList<GraphicalPoint>();
        graphicalSegments = new LinkedList<GraphicalSegment>();
        minLat = Float.POSITIVE_INFINITY;
        minLongi = Float.POSITIVE_INFINITY;
        maxLat = 0;
        maxLongi = 0;
        setExtemeCoordonates();

        displayMap();
        //System.out.println("Extremes : " + minLat + " " + maxLat + " " + minLongi + " " + maxLongi);
    }

    public void paint(Graphics g){
        //Background
        g.setColor(Color.gray);
        g.fillRect(0,0,800,800);

        //Draw intersections
        for (GraphicalPoint gp : graphicalPoints) {
            g.setColor(gp.getColor());
            g.fillOval(gp.getXPixel(), gp.getYPixel(), gp.getSize(), gp.getSize());
        }

        //Draw segments
        for(GraphicalSegment gs: graphicalSegments){
            if (gs!=null){
                g.setColor(gs.getColor());
                g.drawLine(gs.getXOriginPixel(), gs.getYOriginPixel(), gs.getXDestPixel(), gs.geYDestPixel());
            }
        }
       
        
    }

    public void displayMap(){
        //Graphical points
        for (Intersection i : intersections) {
            GraphicalPoint gp = new GraphicalPoint(i, minLat, minLongi, maxLat, maxLongi);
            graphicalPoints.add(gp);
        }

        //Graphical segments
        for(Segment s: segments){
            GraphicalSegment gs = createSegment(s.getIdOrigin(), s.getIdDestination());
            if (gs!=null){
                graphicalSegments.add(gs);
            }
        }
    }

    public void displayRequests(SetOfRequests sr){
        //Look for the departure point
        int i = 0;
        boolean found = false;
        while (i<graphicalPoints.size() && !found){
            if(sr.getDepot().equals(graphicalPoints.get(i).getIntersectionId())){
                graphicalPoints.get(i).setColor(Color.yellow);
                graphicalPoints.get(i).setSize(12);
                found = true;
            }
            i++;
        }
        

        //Change the color of pickup and delivery points
        for(Request r : sr.getRequests()){
            i = 0;
            boolean dFound = false;
            boolean pFound = false;
            while (i<graphicalPoints.size() && (!pFound || !dFound)){
                if(r.getPickupAddress().equals(graphicalPoints.get(i).getIntersectionId())){
                    graphicalPoints.get(i).setColor(Color.BLUE);
                    graphicalPoints.get(i).setSize(12);
                    pFound = true;
                } else if(r.getDeliveryAddress().equals(graphicalPoints.get(i).getIntersectionId())){
                    graphicalPoints.get(i).setColor(Color.MAGENTA);
                    graphicalPoints.get(i).setSize(12);
                    dFound = true;
                }
                i++;
            }
        }


        repaint();
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


