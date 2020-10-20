package view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;

import model.Intersection;
import model.Map;
import model.Request;
import model.Segment;
import model.SetOfRequests;

public class TextualView extends JPanel {

	JLabel label2;
	Font fontRequest;
	
	public TextualView(Map loadedMap) {
		setLayout(null);
		setBackground(Color.yellow);

		Font fontTitre = new Font("Arial", Font.BOLD, 20);
		fontRequest = new Font("Arial", Font.BOLD, 15);

		JLabel label1 = new JLabel("Requests : ", JLabel.CENTER);
		

		label1.setBounds(0, 0, 200, 200);
		label1.setFont(fontTitre);

		add(label1);
	}
	
	public void displayRequests(SetOfRequests sor) {
		//JTable est possible aussi
		int yStart = 30;
		System.out.println("displayRequest en textuel");
		for (Request r : sor.getRequests()) {
			System.out.println("Insertion JLabel");
			String s = r.toString();
			System.out.println(s);
			JLabel j = new JLabel(s, JLabel.LEFT);
			j.setFont(fontRequest);
			j.setBackground(Color.blue);
			j.setBounds(0, yStart, 400, 400);
			add(j);
			yStart = yStart +20;
		}
	}
}
