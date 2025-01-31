package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.RoadMap;
import model.SetOfRequests;

/**
 * Used to display textual information about the requests and the tour.
 */
public class TextualView extends JPanel {

	/**
	 * Panel that will contain the textual representations of the requests and the
	 * tour
	 */
	private static final long serialVersionUID = 2L;
	private Font fontTitle;
	private JTable uiTable;
	private JTable uiTableTour;
	private PropertyChangeSupport support;
	Color back = new Color(0x2f394e);

	public TextualView(CityMap loadedMap) {
		this.support = new PropertyChangeSupport(this);

		setLayout(null);
		setBackground(back);

		fontTitle = new Font("Arial", Font.BOLD, 20);
	}

	/**
	 * Displays the requests in a table
	 * 
	 * @param sor the SetOfRequests containing the requests to display
	 */
	public void displayRequests(SetOfRequests sor) {

		JPanel conteneurTabRequest = new JPanel();
		conteneurTabRequest.setBackground(back);
		conteneurTabRequest.setBounds(0, 50, 400, 200);
		conteneurTabRequest.setLayout(null);
		JPanel conteneurTabJTableRequest = new JPanel();
		conteneurTabJTableRequest.setBackground(back);

		if (sor != null) {

			// conteneurTabJTableRequest.setBackground(Color.orange);
			conteneurTabJTableRequest.setBounds(0, 50, 400, 200);

			// creation du titre
			JLabel titreRequest = new JLabel("Request : ", JLabel.LEFT);
			titreRequest.setForeground(Color.WHITE);
			titreRequest.setBounds(0, 0, 400, 50);
			titreRequest.setFont(fontTitle);

			// conteneur scrollable
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 50, 400, 150);
			scrollPane.setBorder(null);
			conteneurTabJTableRequest.setLayout(new BoxLayout(conteneurTabJTableRequest, BoxLayout.Y_AXIS));

			String[][] donnees = new String[sor.getNbRequests()][5];
			String[] entetes = { "N°", "Pickup Adress", "Pickup duration", "Delivery Adress", "Delivery Duration" };

			int i = 0;
			for (Request r : sor.getRequests()) {
				String[] obj = { Integer.toString(r.getNumero()), r.getPickupAddress(),
						Integer.toString(r.getPickupDuration()), r.getDeliveryAddress(),
						Integer.toString(r.getDeliveryDuration()) };
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

			scrollPane.setViewportView(conteneurTabJTableRequest);
			conteneurTabRequest.add(scrollPane);
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
						if (col == 1) {
							support.firePropertyChange("selectCell", null, sor.getRequests().get(row).getPickup());
						} else if (col == 3) {
							support.firePropertyChange("selectCell", null, sor.getRequests().get(row).getDelivery());
						}
					}
				}
			});

			conteneurTabJTableRequest.updateUI();
			conteneurTabRequest.updateUI();
			updateUI();

			displayCaption();
		}

	}

	/**
	 * Displays the path of the computed Tour in a table
	 * 
	 * @param roadMap the RoadMap containing the path to display
	 * @param sor     the SetOfRequest use to compute the Tour
	 */
	public void displayTour(RoadMap roadMap, SetOfRequests sor) {

		// creation du conteneur du tableau
		JPanel conteneurTabTour = new JPanel();
		conteneurTabTour.setBackground(back);
		conteneurTabTour.setBounds(0, 250, 400, 350);
		conteneurTabTour.setLayout(null);

		JPanel conteneurTabJTableTour = new JPanel();
		conteneurTabJTableTour.setBackground(back);
		conteneurTabJTableTour.setBounds(0, 50, 400, 200);
		conteneurTabJTableTour.setLayout(new BoxLayout(conteneurTabJTableTour, BoxLayout.Y_AXIS));

		// conteneur scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 50, 400, 300);
		scrollPane.setBorder(null);

		// creation du titre
		JLabel titreTour = new JLabel("Tour : ", JLabel.LEFT);
		titreTour.setForeground(Color.white);
		titreTour.setBounds(0, 0, 400, 50);
		titreTour.setFont(fontTitle);

		// recuperation donnees
		HashMap<Intersection, List<Request>> mapPickupAddressToRequest = roadMap.getMapPickupAddressToRequest();
		HashMap<Intersection, List<Request>> mapDeliveryAddressToRequest = roadMap.getMapDeliveryAddressToRequest();

		LinkedList<Intersection> orderedAddresses = roadMap.getOrderedAddresses();
		String[][] tabData = new String[orderedAddresses.size()][4];

		int i = 0;
		boolean depart = false;
		String duration = "-1";
		for (Intersection inter : orderedAddresses) {

			List<Request> listRequest = new LinkedList<Request>();
			String type = "";
			boolean typeRequest = false;

			if (mapPickupAddressToRequest.containsKey(inter)) {
				listRequest = mapPickupAddressToRequest.get(inter);

			} else if (mapDeliveryAddressToRequest.containsKey(inter)) {
				listRequest = mapDeliveryAddressToRequest.get(inter);
				typeRequest = true;
			}

			String numeroReq = "";
			if (listRequest.isEmpty() == false) {
				for (Request r : listRequest) {
					type = "not init";
					if (r != null) {
						if (typeRequest) {
							type = "Delivery";
							duration = Integer.toString(r.getDeliveryDuration());
						} else {
							type = "Pickup";
							duration = Integer.toString(r.getPickupDuration());
						}
						numeroReq = Integer.toString(r.getNumero());
					}
				}
			} else {
				if (depart == false) {
					type = "Start";
					numeroReq = "NC";
					duration = "NC";
					depart = true;
				} else {
					type = "End";
					numeroReq = "NC";
					duration = "NC";
				}
			}
			String[] obj = { numeroReq, type, inter.getNumber(), duration };
			tabData[i] = obj;
			i++;
		}

		// creation tab de donnees
		String[] tadHeader = { "Request N°", "Type", "Adress", "Duration" };

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
				int row = uiTableTour.rowAtPoint(evt.getPoint());
				int col = uiTableTour.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					// selected a row
					support.firePropertyChange("selectCell", null, orderedAddresses.get(row));
				}
			}
		});

		conteneurTabJTableTour.add(uiTableTour.getTableHeader(), BorderLayout.NORTH);
		conteneurTabJTableTour.add(uiTableTour, BorderLayout.CENTER);
		conteneurTabTour.add(titreTour);

		conteneurTabTour.add(scrollPane);
		scrollPane.setViewportView(conteneurTabJTableTour);
		add(conteneurTabTour);

		conteneurTabJTableTour.updateUI();
		conteneurTabTour.updateUI();
		updateUI();
	}

	/**
	 * Displays a caption of the map (graphical view)
	 */
	public void displayCaption() {

		Font fontCaption = new Font("Arial", Font.BOLD, 15);

		JLabel titleCaption = new JLabel("Caption", JLabel.LEFT);
		JLabel caption1 = new JLabel("⏺ Yellow: departure", JLabel.LEFT);
		JLabel caption2 = new JLabel("⏺ Blue: pickup", JLabel.LEFT);
		JLabel caption3 = new JLabel("⏺ Magenta: delivery", JLabel.LEFT);
		JLabel caption4 = new JLabel("---- Beginning of the tour", JLabel.LEFT);
		JLabel caption5 = new JLabel("---- End of the tour", JLabel.LEFT);

		titleCaption.setFont(fontTitle);
		caption1.setFont(fontCaption);
		caption2.setFont(fontCaption);
		caption3.setFont(fontCaption);
		caption4.setFont(fontCaption);
		caption5.setFont(fontCaption);

		titleCaption.setForeground(Color.white);
		caption1.setForeground(Color.yellow);
		caption2.setForeground(Color.blue);
		caption3.setForeground(Color.magenta);
		caption4.setForeground(new Color(0, 100, 100));
		caption5.setForeground(new Color(255, 100, 100));

		titleCaption.setBackground(Color.gray);
		caption1.setBackground(Color.gray);
		caption2.setBackground(Color.gray);
		caption3.setBackground(Color.gray);
		caption4.setBackground(Color.gray);
		caption5.setBackground(Color.gray);

		titleCaption.setBounds(200, 620, 300, 30);
		caption1.setBounds(200, 650, 300, 30);
		caption2.setBounds(200, 680, 300, 30);
		caption3.setBounds(200, 710, 300, 30);
		caption4.setBounds(200, 740, 300, 30);
		caption5.setBounds(200, 770, 300, 30);

		add(titleCaption);
		add(caption1);
		add(caption2);
		add(caption3);
		add(caption4);
		add(caption5);

		titleCaption.setOpaque(true);
		caption1.setOpaque(true);
		caption2.setOpaque(true);
		caption3.setOpaque(true);
		caption4.setOpaque(true);
		caption5.setOpaque(true);
	}

	// this is only a POC, not finished
	/**
	 * Selects the row containing a specific Intersection
	 * 
	 * @param inter an Intersection.
	 */
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
