import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicalView  extends JPanel{
    List<Intersection> instersections;
    List<Segment> segments;
    
    public GraphicalView(Map loadedMap){
        setLayout(null);
        setBounds(0,0,800,800);
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(0,0,800,800);

        //Test
        g.setColor(Color.orange);
        g.fillOval(20,20,10,10);
    }

    
}


