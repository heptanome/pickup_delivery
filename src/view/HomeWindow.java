package view;

import javax.swing.*;

import controller.Application;
import model.CityMap;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The main class used in the View (MVC model), will showcase a window
 * with different buttons to interact with (load a map, load requests, ...)
 */
public class HomeWindow extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 3L;
	/**
	 * The width of the window
	 */
	protected final static int WIDTH = 1420;
	/**
	 * The height of the window
	 */
	protected final static int HEIGHT = 850;
	
	/**
	 * Used to store the currently loaded map, or null
	 */
	protected CityMap loadedMap;
	protected SetOfRequests loadedSOR;
	
	private JButton btnLoadMap = new JButton("Load a map");
	private JButton btnLoadRequest = new JButton("Load a set of requests");
	private JButton btnAddRequest = new JButton("Add a request");
	private JButton btnDeleteRequest = new JButton("Delete a request");
	private JButton btnComputeTour = new JButton("Compute a Tour");
	private JPanel textualContainer;
	private JPanel graphicalContainer;
	private PropertyChangeSupport support;

	public GraphicalView gv;
	public TextualView tv;

	//Variables used to add a new request
	private Request newRequest = new Request(" ", " ", 0, 0);
	private String precedingPickup = null;
	private String preceedingDelivery = null;

	/**
	 * Will build the window following a specific layout together with specific buttons
	 * @param name the name required for a window to be created
	 */
	public HomeWindow(String name) {
		super(name);
		support = new PropertyChangeSupport(this);
		
		//Layout
		setLayout(null);

		// Graphical container
		graphicalContainer = new JPanel();
		graphicalContainer.setLayout(null);
		graphicalContainer.setBounds(0, 0, 820, HEIGHT-30);

		//Textual container
		textualContainer = new JPanel();
		textualContainer.setLayout(null);
		textualContainer.setBounds(820, 0, 400, HEIGHT-30);
		textualContainer.setBackground(new Color(188, 188, 188));

		//Buttons container
		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setBounds(1220, 0, 200, HEIGHT-30);
		buttonsContainer.setBackground(new Color(5, 132, 243));
		//buttonsContainer.setLayout(new FlowLayout(5));

		BoxLayout boxLayout1 = new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS);
		buttonsContainer.setLayout(boxLayout1);
		buttonsContainer.add(Box.createVerticalStrut(30));

		// Buttons
		btnLoadMap.addActionListener(new LoadMapListener());
		btnLoadMap.setUI(new StyledButtonUI());
		btnLoadMap.setEnabled(true);
		//buttonsContainer.add(btnLoadMap, BorderLayout.SOUTH);
		btnLoadMap.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLoadMap.setAlignmentY(5);
		buttonsContainer.add(btnLoadMap);
		buttonsContainer.add(Box.createVerticalStrut(10));
		
		btnLoadRequest.addActionListener(new LoadRequestListener());
		btnLoadRequest.setUI(new StyledButtonUI());
		btnLoadRequest.setEnabled(false);
		//buttonsContainer.add(btnLoadRequest, BorderLayout.SOUTH);
		btnLoadRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnLoadRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnAddRequest.addActionListener(new AddRequestListener());
		btnAddRequest.setUI(new StyledButtonUI());
		btnAddRequest.setEnabled(false);
		//buttonsContainer.add(btnAddRequest, BorderLayout.SOUTH);
		btnAddRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnAddRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnDeleteRequest.addActionListener(new DeleteRequestListener());
		btnDeleteRequest.setUI(new StyledButtonUI());
		btnDeleteRequest.setEnabled(false);
		//buttonsContainer.add(btnDeleteRequest, BorderLayout.SOUTH);
		btnDeleteRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnDeleteRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnComputeTour.addActionListener(new ComputeTourListener());
		btnComputeTour.setUI(new StyledButtonUI());
		btnComputeTour.setEnabled(false);
		//buttonsContainer.add(btnComputeTour, BorderLayout.SOUTH);
		btnComputeTour.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnComputeTour);
		

		// Add containers
		add(graphicalContainer);
		add(textualContainer);
		add(buttonsContainer);

		setSize(WIDTH, HEIGHT);
		setLocation(0, 0);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Refreshing the View (graphical) with a newly loaded map
	 * @param map The map that was received from the Model after parsing it
	 */
	public void setMap(CityMap map) {
		this.loadedMap = map;

		// Graphical view
		graphicalContainer.removeAll();
		if(graphicalContainer.getMouseListeners().length>0){
			graphicalContainer.removeMouseListener(graphicalContainer.getMouseListeners()[0]);
		}
		graphicalContainer.repaint();
		gv = new GraphicalView(this.loadedMap);
		graphicalContainer.add(gv);
		
		// Buttons enabling
		btnLoadRequest.setEnabled(true);
		btnAddRequest.setEnabled(false);
		btnDeleteRequest.setEnabled(false);
		btnComputeTour.setEnabled(false);
		
	}

	/**
	 * Passing the set of requests down to the graphical and textual containers
	 * @param sor Set of requests parsed from the Model
	 */
	public void setRequests(SetOfRequests sor) {
		this.loadedSOR = sor;
		
		// graphical view of a set of requests
		gv.displayRequests(this.loadedSOR);
		
		// textual view TODO Paul
		textualContainer.removeAll();
		textualContainer.repaint();
		tv = new TextualView(this.loadedMap);
		tv.setBounds(0, 0, 400, 800);
		textualContainer.add(tv);
		tv.displayRequests(this.loadedSOR);
		
		// Buttons enabling
		btnAddRequest.setEnabled(true);
		btnDeleteRequest.setEnabled(true);
		btnComputeTour.setEnabled(true);

		//Add mouse listner
		//addMouseOnMapListener();
	}
	
	/**
	 * Passing the segments to the graphical container
	 * @param segments An ordered (linked) list of segments the cyclist will have
	 * to follow
	 */
	public void tourComputed(List<Segment> segments) {
		gv.displayTour(segments);
		tv.displayTour(this.loadedSOR, segments);
		//TODO textual container & road map (file)
	}

	public Request getNewRequest(){
		return newRequest;
	}

	public void setPreceedingPickup (String s){
		precedingPickup = s;
	}

	public void setPreceedingDelivery (String s){
		preceedingDelivery = s;
	}

	public class LoadRequestListener implements ActionListener {
		
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

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*
			System.out.println("Select a new pickup point on the map (a white point)");
			gv.unselect();

			String pickupId = null;
			SingleMouseClickOnMapListener m = new SingleMouseClickOnMapListener();
			
			String pickupId = null;
			MouseOnMapListener m = new MouseOnMapListener();
			gv.addMouseListener(m);
			while(pickupId==null){
				pickupId = gv.getSelectedPointId();

			}
			gv.removeMouseListener(m);

			System.out.println("Select a preceeding point on the map (a colored point)");
			gv.unselect();
			String preceedingPickupId = null;
			while(preceedingPickupId==null){
				preceedingPickupId = gv.getSelectedPointId();
			}

			String value = JOptionPane.showInputDialog("Enter the pickup duration (in minutes)","");
			int pickupDuration = Integer.parseInt(value);

			System.out.println("Select a new deliverypoint on the map (a white point)");
			gv.unselect();
			String deliveryId = null;
			while(deliveryId==null){
				deliveryId = gv.getSelectedPointId();
			}

			System.out.println("Select a preceeding point on the map (a colored point)");
			gv.unselect();
			String preceedingDeliveryId = null;
			while(preceedingDeliveryId==null){
				preceedingDeliveryId = gv.getSelectedPointId();
			}

			value = JOptionPane.showInputDialog("Enter the delivery duration (in minutes)","");
			int deliveryDuration = Integer.parseInt(value);
			System.out.println(pickupId + "   " + preceedingPickupId  +"   "  + pickupDuration);
			System.out.println(deliveryId + "   " + preceedingDeliveryId  +"   "  + deliveryDuration);
			*/

			// Application.addRequest();
			support.firePropertyChange("addRequest", null, null);
		}

	}

	public class DeleteRequestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Application.deleteRequest();
			support.firePropertyChange("deleteRequest", null, null);
		}

	}

	public class ComputeTourListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Application.computeTour();
			support.firePropertyChange("computeTour", null, null);
		}

	}

	public class MouseOnMapListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {
			//Only works if there is a map loaded
			if(loadedMap!=null){
				String selectedPointId = gv.mapClickedResponse(e.getX(), e.getY());
				System.out.println(selectedPointId);
				//TODO : implement a metohd in textual view that highlights the request that has this id 
			}
		}
	}

	public void addMouseOnMapListener(){
		graphicalContainer.addMouseListener(new MouseOnMapListener());
	}

	public class SingleMouseClickOnMapListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {
			int i = 0;
			//Only works if there is a map loaded
			if(loadedMap!=null){
				String selectedPointId = gv.mapClickedResponse(e.getX(), e.getY());
				support.firePropertyChange("pointClicked", null, selectedPointId);
				
				
			}
		}

	}

	public void addSingleMouseClickOnMapListener(){
		graphicalContainer.addMouseListener(new SingleMouseClickOnMapListener());
	}

	public void removeAllMouseListeners(){
		for(int i = 0; i<graphicalContainer.getMouseListeners().length; i++){
			graphicalContainer.removeMouseListener(graphicalContainer.getMouseListeners()[0]);
		}
		System.out.println(graphicalContainer.getMouseListeners().length);
	}


	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * Following good practice, the Model communicates with the View using
	 * listeners
	 * @param evt the event the View is listening for, from the Model
	 */
	@SuppressWarnings("unchecked")
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
			this.tourComputed((List<Segment>)evt.getNewValue());
			break;
		default:
			break;
		}
	}

	

}
