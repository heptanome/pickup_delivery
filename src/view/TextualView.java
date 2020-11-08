package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;

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
		
		displayCaption();
	}
	
	public void displayRequests(SetOfRequests sor) {
		
		JPanel conteneurTabRequest = new JPanel ();
		//conteneurTabRequest.setBackground(Color.red);
		conteneurTabRequest.setBounds(0, 50, 400, 200);
		conteneurTabRequest.setLayout(null);
		
		JPanel conteneurTabJTableRequest = new JPanel ();
		//conteneurTabJTableRequest.setBackground(Color.orange);
		conteneurTabJTableRequest.setBounds(0, 50, 400, 100);
		
		//creation du titre
		JLabel titreRequest = new JLabel("Request : ", JLabel.LEFT);
		titreRequest.setBounds(0, 0, 400, 50);
		titreRequest.setFont(fontTitle);
		
		String [][] donnees = new String [sor.getRequests().size()][5];
		String[] entetes = {"N°", "Pickup Adress", "Pickup duration", "Delivery Adress", "Delivery Duration"};
		
		int i = 0;
		for (Request r : sor.getRequests()) {
			String[] obj = {Integer.toString(i+1), r.getPickupAddress(), Integer.toString(r.getPickupDuration()), r.getDeliveryAddress(), Integer.toString(r.getDeliveryDuration())};
			donnees[i]= obj;
			i++;
		}
		
		//instance table model
		DefaultTableModel tableModel = new DefaultTableModel(donnees, entetes) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		uiTable = new JTable(donnees, entetes);
		uiTable.setModel(tableModel);
		uiTable.setBounds(10, 150, 300, 400);
		
		conteneurTabJTableRequest.add(uiTable.getTableHeader(), BorderLayout.NORTH);
		conteneurTabJTableRequest.add(uiTable, BorderLayout.CENTER);
		conteneurTabRequest.add(titreRequest);

		
		conteneurTabRequest.add(conteneurTabJTableRequest);
		add(conteneurTabRequest);
		
		uiTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		uiTable.getColumnModel().getColumn(1).setPreferredWidth(85);
		uiTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		uiTable.getColumnModel().getColumn(3).setPreferredWidth(90);
		uiTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		
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
		
		conteneurTabJTableRequest.updateUI();
		conteneurTabRequest.updateUI();
		updateUI();

	}
	
	public void displayTour(SetOfRequests sor, List<Segment> segments) {
		
		//creation du conteneur du tableau
		JPanel conteneurTabTour = new JPanel ();
		//conteneurTabTour.setBackground(Color.orange);
		conteneurTabTour.setBounds(0, 300, 400, 350);
		conteneurTabTour.setLayout(null);
		
		JPanel conteneurTabJTableTour = new JPanel ();
		//conteneurTabJTableTour.setBackground(Color.red);
		conteneurTabJTableTour.setBounds(0, 50, 400, 200);
		
		//creation du titre
		JLabel titreTour = new JLabel("Tour : ", JLabel.LEFT);
		titreTour.setBounds(0, 0, 400, 50);
		titreTour.setFont(fontTitle);
		
		//creation tab de donnees	
		//String [][] tabData = new String [4][5];
		String [][] tabData = {
								{"1", "Delivery", "A", "2s"},
								{"1", "Delivery", "A", "2s"},
								{"1", "Delivery", "A", "2s"},
								{"1", "Delivery", "A", "2s"}
							};
		String[] tadHeader = {"Order", "Type", "Adress", "Duration"};
		
		DefaultTableModel tableModel = new DefaultTableModel(tabData, tadHeader) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		JTable uiTableTour = new JTable(tabData, tadHeader);
		uiTableTour.setModel(tableModel);
		
		//uiTableTour.setBounds(100, 50, 0, 0);
		
		conteneurTabJTableTour.add(uiTableTour.getTableHeader(), BorderLayout.NORTH);
		conteneurTabJTableTour.add(uiTableTour, BorderLayout.CENTER);
		conteneurTabTour.add(titreTour);
			
		conteneurTabTour.add(conteneurTabJTableTour);
		add(conteneurTabTour);
		
		conteneurTabJTableTour.updateUI();
		conteneurTabTour.updateUI();
		updateUI();
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
		
		titleCaption.setBounds(50, 670, 300, 30);
		caption1.setBounds(50, 700, 300, 30);
		caption2.setBounds(50, 730, 300, 30);
		caption3.setBounds(50, 760, 300, 30);

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
	
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
