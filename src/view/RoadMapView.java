package view;

import javax.swing.*;

import model.Intersection;
import model.CityMap;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class RoadMapView extends JPanel {
	
	private final static long serialVersionUID = 3L;
	private final static int WIDTH = 820;
	private final static int HEIGHT = 820;
	private final static Color BACKGROUND_COLOR = new Color(188, 188, 188);
	private final static Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 50);
	
	protected CityMap loadedMap;
	protected SetOfRequests loadedSOR;

	public RoadMapView() {
		setLayout(null);
		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(BACKGROUND_COLOR);
    }
	
	public void paint(Graphics g) {
	    final int LINE_SPACING = 50;
	    int sizeText = 30;
	    
	    g.setColor(Color.BLACK);
	    g.setFont(DEFAULT_FONT);

	    String title = "Road Map";
	    int convertedLengthToPixel = title.length()*sizeText;
	    g.drawString(title, (WIDTH-convertedLengthToPixel)/2, LINE_SPACING+30);
	    g.setFont(new Font("Helvetica", Font.BOLD, 30));
	    
	}
	
}