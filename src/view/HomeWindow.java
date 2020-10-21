package view;

import javax.swing.*;

import controller.Application;
import model.CityMap;
import model.Segment;
import model.SetOfRequests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HomeWindow extends JFrame implements PropertyChangeListener {
	protected final static int WIDTH = 1400; // Largeur de la fenêtre
	protected final static int HEIGHT = 800; // Hauteur de la fenêtre
	protected CityMap loadedMap;

	private JButton btnLoadRequest = new JButton("Load a set of requests");
	private JButton btnLoadMap = new JButton("Load a map");
	private JButton btnAddRequest = new JButton("Add a request");
	private JButton btnDeleteRequest = new JButton("Delete a request");
	private JButton btnComputeTour = new JButton("Compute a Tour");
	private JPanel textualContainer;
	private JPanel graphicalContainer;
	private PropertyChangeSupport support;

	public GraphicalView gv;
	public TextualView tv;

	public HomeWindow(String nom) {
		super(nom);
		support = new PropertyChangeSupport(this);

		setSize(WIDTH, HEIGHT);
		setLocation(0, 0);
		setLayout(null);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(null);
		buttonsContainer.setBounds(1201, 0, 200, HEIGHT);
		buttonsContainer.setBackground(Color.red);

		// Buttons
		btnLoadRequest.setForeground(Color.red);
		btnLoadRequest.setBackground(Color.BLUE);
		btnLoadRequest.setBounds(25, 50, 150, 40);
		btnLoadRequest.addActionListener(new LoadRequestListener());
		buttonsContainer.add(btnLoadRequest, BorderLayout.SOUTH);

		btnLoadMap.setForeground(Color.red);
		btnLoadMap.setBackground(Color.BLUE);
		btnLoadMap.setBounds(25, 80, 150, 40);
		btnLoadMap.addActionListener(new LoadMapListener());
		buttonsContainer.add(btnLoadMap, BorderLayout.SOUTH);

		btnAddRequest.setForeground(Color.red);
		btnAddRequest.setBackground(Color.BLUE);
		btnAddRequest.setBounds(25, 110, 150, 40);
		btnAddRequest.addActionListener(new AddRequestListener());
		buttonsContainer.add(btnAddRequest, BorderLayout.SOUTH);

		btnDeleteRequest.setForeground(Color.red);
		btnDeleteRequest.setBackground(Color.BLUE);
		btnDeleteRequest.setBounds(25, 140, 150, 40);
		btnDeleteRequest.addActionListener(new DeleteRequestListener());
		buttonsContainer.add(btnDeleteRequest, BorderLayout.SOUTH);

		btnComputeTour.setForeground(Color.red);
		btnComputeTour.setBackground(Color.BLUE);
		btnComputeTour.setBounds(25, 170, 150, 40);
		btnComputeTour.addActionListener(new ComputeTourListener());
		buttonsContainer.add(btnComputeTour, BorderLayout.SOUTH);
		// Creation of main container
		graphicalContainer = new JPanel();
		graphicalContainer.setLayout(null);
		graphicalContainer.setBounds(0, 0, HEIGHT, HEIGHT);

		textualContainer = new JPanel();
		textualContainer.setLayout(null);
		textualContainer.setBounds(801, 0, 400, HEIGHT);
		textualContainer.setBackground(Color.green);
		
		//JLa

		// Ajout containers
		add(graphicalContainer);
		add(textualContainer);
		add(buttonsContainer);
	}

	public void setMap(CityMap map) {
		this.loadedMap = map;

		// Graphical view
		// JPanel graphicalView = new JPanel();
		// graphicalView.setBounds(0,0,HEIGHT,HEIGHT);
		// graphicalView.setBackground(Color.gray);
		graphicalContainer.removeAll();
		graphicalContainer.repaint();
		gv = new GraphicalView(loadedMap);
		graphicalContainer.add(gv);

		// TextualView
		
	}

	public void setRequests(SetOfRequests sor) {
		// graphical view of a set of requests
		gv.displayRequests(sor);
		// textusal view TODO Paul
		
		textualContainer.removeAll();
		textualContainer.repaint();
		tv = new TextualView(loadedMap);
		tv.setBounds(0, 0, 400, 800);
		textualContainer.add(tv);
		
		tv.displayRequests(sor);
	}
	
	public void tourComputed(LinkedList<Segment> segments) {
		segments.forEach(segment -> {
			System.out.println(segment);
		});
	}

	public class LoadRequestListener implements ActionListener {

		/**
		 * 
		 */
		public LoadRequestListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Un evenement a été detecté");
			Object source = e.getSource();

			if (source == btnLoadRequest) {
				File repertoireCourant = null;
				try {
					repertoireCourant = new File(".").getCanonicalFile();
					System.out.println("Répertoire courant : " + repertoireCourant);
				} catch (IOException err) {
				}
				JFileChooser dialogue = new JFileChooser(repertoireCourant);
				dialogue.showOpenDialog(null);
				String requestPath = dialogue.getSelectedFile().getAbsolutePath();
				System.out.println("Fichier choisi : " + requestPath);

				support.firePropertyChange("loadRequests", "", requestPath);
				// SetOfRequests sr = Application.loadRequest(requestPath);
			} else {
				System.out.println("Cet evenement n'a pas d'action associée");
			}
		}

	}

	public class LoadMapListener implements ActionListener {

		/**
		 * 
		 */
		public LoadMapListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
				System.out.println("Répertoire courant : " + repertoireCourant);
			} catch (IOException err) {
			}
			JFileChooser dialogue = new JFileChooser(repertoireCourant);
			dialogue.showOpenDialog(null);
			String mapPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Fichier choisi : " + mapPath);
			// Application.loadMap(mapPath);
			support.firePropertyChange("loadMap", "", mapPath);
			System.out.println("fired loadmap");
		}

	}

	public class AddRequestListener implements ActionListener {

		/**
		 * 
		 */
		public AddRequestListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Application.addRequest();
		}

	}

	public class DeleteRequestListener implements ActionListener {

		/**
		 * 
		 */
		public DeleteRequestListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Application.deleteRequest();
		}

	}

	public class ComputeTourListener implements ActionListener {

		/**
		 * 
		 */
		public ComputeTourListener() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Application.computeTour();
			support.firePropertyChange("computeTour", null, null);
		}

	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		switch (propName) {
		case "updateMap":
			this.setMap((CityMap) evt.getNewValue());
			break;
		case "updateRequests":
			this.setRequests((SetOfRequests) evt.getNewValue());
			break;
		case "tourComputed":
			this.tourComputed((LinkedList<Segment>)evt.getNewValue());
			break;
		default:
			break;
		}
	}

}
