package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RoadMapPanelView extends JPanel{

	private static final long serialVersionUID = 1L;
	private final int RM_WIDTH = 820;
	private final int RM_HEIGHT = 820;
	
	private final JPanel titleArea;
	private final JScrollPane roadArea;
	private JTextArea texte;
	Font fontTitle = new Font("Arial", Font.BOLD, 40);
	Color back = new Color(0xc1c3c6);

	public RoadMapPanelView () {
		
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0, 0, RM_WIDTH, RM_HEIGHT);
		this.setBackground(back);
		
		//Title Area
		titleArea = new JPanel ();
		titleArea.setBounds(0, 0, 820, 100);
		titleArea.setBackground(back);
		add(titleArea);
		
		//RoadMap Area
		roadArea = new JScrollPane ();
		roadArea.setBounds(160, 100, 500, 700);
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
		
		texte = new JTextArea("no tour initialised");
		texte.setForeground(Color.BLACK);
		texte.setEditable(false);
		//title.setFont(fontTitle);
		roadArea.setViewportView(texte);
		roadArea.updateUI();
		
		
	}
	
	public void updateRoad (String tourTxt) {
		texte.setText(tourTxt);
		
	}
}
