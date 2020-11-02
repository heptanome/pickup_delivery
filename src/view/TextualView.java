package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;
import java.util.List;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;
import view.HomeWindow.HelpListener;

public class TextualView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Font fontRequest;
	private Font fontTitle;
	private JTable uiTable;
	private PropertyChangeSupport support;
	
	
	public TextualView(CityMap loadedMap) {
		this.support = new PropertyChangeSupport(this);
		
		setLayout(null);
		setBackground(new Color(188, 188, 188));

		fontTitle = new Font("Arial", Font.BOLD, 20);
		fontRequest = new Font("Arial", Font.BOLD, 7);

		JLabel label1 = new JLabel("Requests : ", JLabel.CENTER);
		
		JLabel header1 = new JLabel("Numero");
		JLabel header2 = new JLabel("Pickup Adress");
		JLabel header3 = new JLabel("Pickup Duration");
		JLabel header4 = new JLabel("Delivery Adress");
		JLabel header5 = new JLabel("Delivery Duration");
		

		label1.setBounds(0, 20, 200, 200);
		label1.setFont(fontTitle);
		
		header1.setFont(fontRequest);
		header2.setFont(fontRequest);
		header3.setFont(fontRequest);
		header4.setFont(fontRequest);
		header5.setFont(fontRequest);
		
		header1.setBounds(20, 40, 200, 200);
		header2.setBounds(70, 40, 200, 200);
		header3.setBounds(140, 40, 200, 200);
		header4.setBounds(220, 40, 200, 200);
		header5.setBounds(300, 40, 200, 200);
		
		add(header1);
		add(header2);
		add(header3);
		add(header4);
		add(header5);

		add(label1);
		
		displayCaption();
	}
	
	public void displayRequests(SetOfRequests sor) {

		//En JTable
		String [][] donnees = new String [sor.getRequests().size()][5];
		String[] entetes = {"Numero", "Pickup Adress", "Pickup duration", "Delivery Adress", "Delivery Duration"};
		
		int i = 0;
		for (Request r : sor.getRequests()) {
			String[] obj = {Integer.toString(i+1), r.getPickupAddress(), Integer.toString(r.getPickupDuration()), r.getDeliveryAddress(), Integer.toString(r.getDeliveryDuration())};
			donnees[i]= obj;
			i++;
		}
		
		uiTable = new JTable(donnees, entetes);
		
		uiTable.setBounds(10, 150, 380, 150);
		add(uiTable.getTableHeader(), BorderLayout.NORTH);
		add(uiTable, BorderLayout.CENTER);
		
		uiTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		uiTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		uiTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		uiTable.getColumnModel().getColumn(3).setPreferredWidth(60);
		
		// https://stackoverflow.com/a/7351053
		uiTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = uiTable.rowAtPoint(evt.getPoint());
		        int col = uiTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	// selected a row
		        	support.firePropertyChange("selectCell", null, sor.getRequests().get(row).getDelivery());
		        }
		    }
		});

	}
	
	public void displayTour(SetOfRequests sor, List<Segment> segments) {
	}
	
	public void displayCaption () {
		
		Font fontCaption = new Font("Arial", Font.BOLD, 15);
		
		JLabel titleCaption = new JLabel("Caption", JLabel.LEFT);
		JLabel caption1 = new JLabel("Yellow point: departure", JLabel.LEFT);
		JLabel caption2 = new JLabel("Blue point: pickup address", JLabel.LEFT);
		JLabel caption3 = new JLabel("Magenta point: delivery address", JLabel.LEFT);
		
		titleCaption.setFont(fontTitle);
		caption1.setFont(fontCaption);
		caption2.setFont(fontCaption);
		caption3.setFont(fontCaption);
		
		titleCaption.setForeground(Color.white);
		caption1.setForeground(Color.yellow);
		caption2.setForeground(Color.blue);
		caption3.setForeground(Color.magenta);
		
		titleCaption.setBackground(Color.gray);
		caption1.setBackground(Color.gray);
		caption2.setBackground(Color.gray);
		caption3.setBackground(Color.gray);
		
		titleCaption.setBounds(50, 600, 300, 30);
		caption1.setBounds(50, 630, 300, 30);
		caption2.setBounds(50, 660, 300, 30);
		caption3.setBounds(50, 690, 300, 30);

		add(titleCaption);
		add(caption1);
		add(caption2);
		add(caption3);

		titleCaption.setOpaque(true);
		caption1.setOpaque(true);
		caption2.setOpaque(true);
		caption3.setOpaque(true);
		

	}
	
	// this is only a POC, not finished
	public void selectCell(Intersection inter) {
		int rows = uiTable.getRowCount();
		for(int i = 0; i < rows; i++) {
			String id1 = (String)uiTable.getValueAt(i, 1);
			String id2 = (String)uiTable.getValueAt(i, 3);
			
			if(id1.equals(inter.getNumber()) || id2.equals(inter.getNumber())) {
				uiTable.setRowSelectionInterval(i, i);
			}
		}
	}
	
	public boolean isCellEditable(int x, int y) {
		return false;
	}
	
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
