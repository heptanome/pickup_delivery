package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RoadMapPanelView extends JPanel{

	private static final long serialVersionUID = 1L;
	private final int RM_WIDTH = 820;
	private final int RM_HEIGHT = 820;
	
	private final JPanel titleArea;

	public RoadMapPanelView () {
		
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0, 0, RM_WIDTH, RM_HEIGHT);
		this.setBackground(new Color(0xc1c3c6));
		
		titleArea = new JPanel ();
		titleArea.setBackground(Color.RED);
		titleArea.setBounds(0, 0, 200, 200);
		
		add(titleArea);
		
	}
	
	public void addTitle () {
		
		JLabel title = new JLabel("Road Map");
		
		
	}
}
