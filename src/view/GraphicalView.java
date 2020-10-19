package view;
import javax.swing.*;

import model.Intersection;
import model.Map;
import model.Segment;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GraphicalView  extends JPanel{
    List<Intersection> intersections;
    List<Segment> segments;

    List<GraphicalPoint> graphicalPoints;
    
    public GraphicalView(Map loadedMap){
        setLayout(null);
        setBounds(0,0,800,800);
        intersections = loadedMap.getInstersections();
        segments = loadedMap.getSegments();
        graphicalPoints = new LinkedList<GraphicalPoint>();
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0,0,800,800);

        //Test
        g.setColor(Color.orange);
        g.fillOval(20,20,10,10);

        g.setColor(Color.black);
        /*
        for (Intersection i : intersections) {
            GraphicalPoint gp = new GraphicalPoint(i.getLatitude(), i.getLongitude());
            graphicalPoints.add(gp);
            g.fillOval(gp.getXPixel(), gp.getYPixel(), 10, 10);
            System.out.println(gp.getXPixel());
            System.out.println(gp.getYPixel());
        }
        System.out.println(graphicalPoints.size());*/
        g.fillOval(400, 400, 10, 10);
    }

    
}


