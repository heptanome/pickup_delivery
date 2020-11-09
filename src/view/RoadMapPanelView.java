package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RoadMapPanelView extends JPanel{

	private static final long serialVersionUID = 1L;
	private final int RM_WIDTH = 820;
	private final int RM_HEIGHT = 820;
	
	private final JPanel titleArea;
	private final JPanel roadArea;
	Font fontTitle = new Font("Arial", Font.BOLD, 40);

	public RoadMapPanelView () {
		
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0, 0, RM_WIDTH, RM_HEIGHT);
		this.setBackground(new Color(0xc1c3c6));
		
		//Title Area
		titleArea = new JPanel ();
		titleArea.setBounds(0, 0, 820, 100);
		add(titleArea);
		
		//RoadMap Area
		roadArea = new JPanel ();
		roadArea.setBounds(0, 100, 820, 720);
		add(roadArea);
		
		addTitle();
		addRoad();
		
	}
	
	public void addTitle () {
		
		JLabel title = new JLabel("Road Map", JLabel.CENTER);
		title.setForeground(Color.BLACK);
		title.setFont(fontTitle);
		titleArea.add(title);
		titleArea.updateUI();
		
		
	}
	
	public void addRoad () {
		
		JLabel texte = new JLabel("blabla", JLabel.CENTER);
		texte.setForeground(Color.BLACK);
		//title.setFont(fontTitle);
		roadArea.add(texte);
		roadArea.updateUI();
		
		
	}
}
