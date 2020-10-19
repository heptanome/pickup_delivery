import javax.swing.*;
import java.awt.*;

public class GraphicalView  extends JPanel{
    
    public GraphicalView(){
        setLayout(null);
        setBounds(0,0,800,800);
    }

    public void paint(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0,0,800,800);
        g.setColor(Color.orange);
        g.fillOval(20,20,10,10);
    }
}


