package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.RoadMap;
import model.SetOfRequests;

public class TextualView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Font fontTitle;
	private JTable uiTable;
	private JTable uiTableTour;
	private PropertyChangeSupport support;

	public TextualView(CityMap loadedMap) {
		this.support = new PropertyChangeSupport(this);

		setLayout(null);
		setBackground(new Color(188, 188, 188));

		fontTitle = new Font("Arial", Font.BOLD, 20);
	}

	public void displayRequests(SetOfRequests sor) {

		JPanel conteneurTabRequest = new JPanel();
		// conteneurTabRequest.setBackground(Color.red);
		conteneurTabRequest.setBounds(0, 50, 400, 200);
		conteneurTabRequest.setLayout(null);
		JPanel conteneurTabJTableRequest = new JPanel();

		if (sor != null) {

			// conteneurTabJTableRequest.setBackground(Color.orange);
			conteneurTabJTableRequest.setBounds(0, 50, 400, 100);

			// creation du titre
			JLabel titreRequest = new JLabel("Request : ", JLabel.LEFT);
			titreRequest.setBounds(0, 0, 400, 50);
			titreRequest.setFont(fontTitle);

			String[][] donnees = new String[sor.getRequests().size()][5];
			String[] entetes = { "Numero", "Pickup Adress", "Pickup duration", "Delivery Adress", "Delivery Duration" };

			int i = 0;
			for (Request r : sor.getRequests()) {
				String[] obj = { Integer.toString(i + 1), r.getPickupAddress(), Integer.toString(r.getPickupDuration()),
						r.getDeliveryAddress(), Integer.toString(r.getDeliveryDuration()) };
				donnees[i] = obj;
				i++;
			}

			// instance table model
			DefaultTableModel tableModel = new DefaultTableModel(donnees, entetes) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
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

			uiTable.getColumnModel().getColumn(0).setPreferredWidth(25);
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

			displayCaption();
		}

	}

	public void displayTour(RoadMap roadMap) {

		System.out.println("DisplayTour avec RoadMap");
		// creation du conteneur du tableau
		JPanel conteneurTabTour = new JPanel();
		// conteneurTabTour.setBackground(Color.orange);
		conteneurTabTour.setBounds(0, 300, 400, 350);
		conteneurTabTour.setLayout(null);

		JPanel conteneurTabJTableTour = new JPanel();
		// conteneurTabJTableTour.setBackground(Color.red);
		conteneurTabJTableTour.setBounds(0, 50, 400, 200);

		// creation du titre
		JLabel titreTour = new JLabel("Tour : ", JLabel.LEFT);
		titreTour.setBounds(0, 0, 400, 50);
		titreTour.setFont(fontTitle);
		
		//recuperation donnees
		HashMap<Intersection, Request> mapAddressToRequest = roadMap.getMapAddressToRequest();
		
		LinkedList<Intersection> orderedAddresses = roadMap.getOrderedAddresses();
		String [][] tabData = new String [orderedAddresses.size()][4];
		
		int i = 0;
		boolean depart = false;
		int duration = -1;
		for (Intersection inter : orderedAddresses) {
			Request r = mapAddressToRequest.get(inter);
			String type = "not init";
			if (r != null) {
				if (inter.getNumber()==r.getPickupAddress()) {
					//c'est une recherche de colis
					type = "Pickup";
					duration = r.getPickupDuration();
				} else if (inter.getNumber()==r.getDeliveryAddress()) {
					//c'est une livraison
					type = "Delivery";
					duration = r.getDeliveryDuration();
				} else {
					//c'est autre chose
					type = "Other";
					duration = -2;
				}
			} else {
				if (depart == false) {
					type = "Start";
					duration = 0;
					depart = true;
				} else {
					type = "End";
					duration = 0;
				}
			}
			
			String [] obj = {Integer.toString(i + 1), type, inter.getNumber(), Integer.toString(duration)};
			tabData[i] = obj;
			i++;
		}
		
		// creation tab de donnees
		String[] tadHeader = { "Order", "Type", "Adress", "Duration" };

		DefaultTableModel tableModel = new DefaultTableModel(tabData, tadHeader) {
			private static final long serialVersionUID = 2L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		uiTableTour = new JTable(tabData, tadHeader);
		uiTableTour.setModel(tableModel);

		// https://stackoverflow.com/a/7351053
		uiTableTour.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = uiTable.rowAtPoint(evt.getPoint());
				int col = uiTable.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					// selected a row
					support.firePropertyChange("selectCell", null, null);
				}
			}
		});

		conteneurTabJTableTour.add(uiTableTour.getTableHeader(), BorderLayout.NORTH);
		conteneurTabJTableTour.add(uiTableTour, BorderLayout.CENTER);
		conteneurTabTour.add(titreTour);

		conteneurTabTour.add(conteneurTabJTableTour);
		add(conteneurTabTour);

		conteneurTabJTableTour.updateUI();
		conteneurTabTour.updateUI();
		updateUI();
	}

	public void displayCaption() {

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
		for (int i = 0; i < rows; i++) {
			String id1 = (String) uiTable.getValueAt(i, 1);
			String id2 = (String) uiTable.getValueAt(i, 3);

			if (id1.equals(inter.getNumber()) || id2.equals(inter.getNumber())) {
				uiTable.setRowSelectionInterval(i, i);
			} 
		}
		
		int rowsTour = uiTableTour.getRowCount();
		for (int i = 0; i < rowsTour; i++) {
			String id1 = (String) uiTableTour.getValueAt(i, 2);

			if (id1.equals(inter.getNumber())) {
				uiTableTour.setRowSelectionInterval(i, i);
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
