import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;

public class TextualView extends JPanel {
	List<Intersection> instersections;
    List<Segment> segments;
    
    public TextualView(Map loadedMap){
        setLayout(null);
        setBackground(Color.red);
        
        Font font = new Font("Arial",Font.BOLD,20);
        
        JLabel label1 = new JLabel("Requests : ", JLabel.CENTER);
        JLabel label2 = new JLabel("Intersections : ", JLabel.CENTER);
        
        label1.setBounds(0, 0, 200, 200);
        label1.setFont(font);
        label2.setBounds(0, 100, 200, 200);
        label2.setFont(font);
        
        add(label1);
        add(label2);
    }
}
