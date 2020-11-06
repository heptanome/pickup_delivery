package view;

import javax.swing.*;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main class used in the View (MVC model), will showcase a window with
 * different buttons to interact with (load a map, load requests, ...)
 */
public class HomeWindow extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 3L;
	protected final static int WIDTH = 1420;
	protected final static int HEIGHT = 850;

	/**
	 * Used to store the currently loaded map, or null
	 */
	protected CityMap loadedMap;
	protected SetOfRequests loadedSOR;
	protected String helpText;
	
	private final JButton btnLoadMap = new JButton("Load a map");
	private final JButton btnLoadRequest = new JButton("Load a set of requests");
	private final JButton btnAddRequest = new JButton("Add a request");
	private final JButton btnDeleteRequest = new JButton("Delete a request");

	private final JButton btnComputeTour = new JButton("Compute a tour");
	private final JButton btnRoadMap = new JButton("Display the road map");
	private final JButton btnSaveRoadMap = new JButton("Save the Road Map");
	private final JButton btnHelp = new JButton("SOS");

	private final JLabel lblHelp = new JLabel();

	private final JPanel textualContainer;
	private final JPanel graphicalContainer;
	private final JPanel buttonsContainer;
	private final PropertyChangeSupport support;

	public GraphicalView gv;
	public TextualView tv;

	/* 
	 *Variables used to add a new request
	 */
	private Request newRequest = null;
	private Intersection precedingPickup = null;
	private Intersection preceedingDelivery = null;

	/**
	 * Will build the window following a specific layout together with specific
	 * buttons
	 * 
	 * @param name the name required for a window to be created
	 */
	public HomeWindow(final String name) {
		super(name);
		this.support = new PropertyChangeSupport(this);
		this.helpText = "Please load a map";

		// Layout
		setLayout(null);

		// Graphical container
		graphicalContainer = new JPanel();
		graphicalContainer.setLayout(null);
		graphicalContainer.setBounds(0, 0, 820, HEIGHT - 30);
		graphicalContainer.addMouseListener(new MouseOnMapListener());

		// Textual container
		textualContainer = new JPanel();
		textualContainer.setLayout(null);
		textualContainer.setBounds(820, 0, 400, HEIGHT - 30);
		textualContainer.setBackground(new Color(188, 188, 188));

		// Buttons container
		buttonsContainer = new JPanel();
		buttonsContainer.setBounds(1220, 0, 200, HEIGHT - 30);
		buttonsContainer.setBackground(new Color(5, 132, 243));

		final BoxLayout boxLayout1 = new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS);
		buttonsContainer.setLayout(boxLayout1);
		buttonsContainer.add(Box.createVerticalStrut(30));

		// Buttons
		this.setButton(btnLoadMap, new LoadMapListener());
		btnLoadMap.setEnabled(true);

		// Jlabel
		// buttonsContainer.add(btnLoadMap, BorderLayout.SOUTH);
		btnLoadMap.setAlignmentX(Component.CENTER_ALIGNMENT);
		// btnLoadMap.setAlignmentY(5);
		buttonsContainer.add(btnLoadMap);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnLoadRequest.addActionListener(new LoadRequestListener());
		btnLoadRequest.setUI(new StyledButtonUI());
		btnLoadRequest.setEnabled(false);
		// buttonsContainer.add(btnLoadRequest, BorderLayout.SOUTH);
		btnLoadRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnLoadRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnAddRequest.addActionListener(new AddRequestListener());
		btnAddRequest.setUI(new StyledButtonUI());
		btnAddRequest.setEnabled(false);
		// buttonsContainer.add(btnAddRequest, BorderLayout.SOUTH);
		btnAddRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnAddRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnDeleteRequest.addActionListener(new DeleteRequestListener());
		btnDeleteRequest.setUI(new StyledButtonUI());
		btnDeleteRequest.setEnabled(false);
		// buttonsContainer.add(btnDeleteRequest, BorderLayout.SOUTH);
		btnDeleteRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnDeleteRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnComputeTour.addActionListener(new ComputeTourListener());
		btnComputeTour.setUI(new StyledButtonUI());
		btnComputeTour.setEnabled(false);
		// buttonsContainer.add(btnComputeTour, BorderLayout.SOUTH);
		btnComputeTour.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnComputeTour);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnHelp.addActionListener(new HelpListener());
		btnHelp.setUI(new StyledButtonUI());
		btnHelp.setEnabled(true);
		// btnHelp.setFont(new Font("Arial", Font.BOLD, 30));
		btnHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnHelp);
		buttonsContainer.add(Box.createVerticalStrut(10));

		lblHelp.setAlignmentX(Component.CENTER_ALIGNMENT);

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

	
	private void setButton(JButton newButton, ActionListener listener) {
		newButton.addActionListener(listener);
		newButton.setUI(new StyledButtonUI());
		newButton.setEnabled(false);
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(newButton);
		buttonsContainer.add(Box.createVerticalStrut(10));
	}
	
	/**
	 * Refreshing the View (graphical) with a newly loaded map
	 * 
	 * @param map The map that was received from the Model after parsing it
	 */
	public void setMap(final CityMap map) {
		this.loadedMap = map;
		this.helpText = "<html>The map has been loaded. <br> Please load a requests file now.</html>";

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
	 * 
	 * @param sor Set of requests parsed from the Model
	 */
	public void setRequests(final SetOfRequests sor) {
		this.loadedSOR = sor;
		this.helpText = "<html>The map and the set of <br>requests have been loaded. <br> Now is the time to compute!</html>";

		// graphical view of a set of requests
		gv.displayRequests(this.loadedSOR);

		// textual view
		textualContainer.removeAll();
		textualContainer.repaint();
		tv = new TextualView(this.loadedMap);
		tv.setBounds(0, 0, 400, 800);
		textualContainer.add(tv);
		tv.displayRequests(this.loadedSOR);

		// Buttons enabling
		btnComputeTour.setEnabled(true);

		// event listening (Home listens to TextualView)
		tv.addPropertyChangeListener(this);
	}
	
	public void selectCell(Intersection inter) {
		gv.selectPoint(inter);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * Passing the segments to the graphical container
	 * 
	 * @param segments An ordered (linked) list of segments the cyclist will have to
	 *                 follow
	 */
	public void tourComputed(List<Segment> segments) {
		this.helpText = "Your tour has been computed. Feel free to add or delete a point.";
		gv.displayTour(segments);
		tv.displayTour(this.loadedSOR, segments);
		// TODO textual container & road map (file)
	}

	
	public Request getNewRequest(){
		return newRequest;
	}

	public void setNewRequest(final Request r){
		newRequest = r;
	}

	public void setPreceedingPickup (final Intersection i){
		precedingPickup = i;
	}

	public void setPreceedingDelivery (final Intersection i){
		preceedingDelivery = i;
	}

	public Intersection getPreceedingPickup (){
		return precedingPickup;
	}

	public Intersection getPreceedingDelivery (){
		return preceedingDelivery;
	}
	
	public Request getRequestFromIntersection (Intersection i) {
		return loadedSOR.getRequestFromIntersection(i);
	}

	

	public class LoadRequestListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			File currentDirectory = null;
			try {
				currentDirectory = new File(".").getCanonicalFile();
				System.out.println("Current directory : " + currentDirectory);
			} catch (final IOException err) {
			}
			final JFileChooser dialogue = new JFileChooser(currentDirectory);
			dialogue.showOpenDialog(null);
			final String requestPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Selected File : " + requestPath);

			support.firePropertyChange("loadRequests", "", requestPath);
		}

	}

	public class LoadMapListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			File currentDirectory= null;
			try {
				currentDirectory = new File(".").getCanonicalFile();
				System.out.println("Current directory : " + currentDirectory);
			} catch (final IOException err) {
				
			}
			final JFileChooser dialogue = new JFileChooser(currentDirectory);
			dialogue.showOpenDialog(null);
			final String mapPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Selected File : " + mapPath);
			support.firePropertyChange("loadMap", "", mapPath);
			System.out.println("fired loadmap");
		}

	}

	public class AddRequestListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			support.firePropertyChange("addRequest", null, null);
		}

	}

	public class DeleteRequestListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			support.firePropertyChange("deleteRequest", null, null);
		}

	}

	public class ComputeTourListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			support.firePropertyChange("computeTour", null, null);
			
			// Buttons Enabling
			btnRoadMap.setEnabled(true);
			btnAddRequest.setEnabled(true);
			btnDeleteRequest.setEnabled(true);
		}

	}
	
	public class RoadMapListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			if (btnRoadMap.getText() == "Return to the Map") {
				this.removeRoadMap();
				btnRoadMap.setText("Display the road map");
				this.enableButtons(true);
			}
			else {
				this.displayRoadMap();
				btnRoadMap.setText("Return to the Map");
				this.enableButtons(false);
			}
		}
		
		public void displayRoadMap() {
			graphicalContainer.removeAll();
			graphicalContainer.repaint();
			final JPanel roadMapView = new RoadMapView();
			graphicalContainer.add(roadMapView);
		}
		
		public void removeRoadMap() {
			graphicalContainer.removeAll();
			graphicalContainer.repaint();
			graphicalContainer.add(gv);
		}
		
		private void enableButtons(final boolean state) {
			btnSaveRoadMap.setEnabled(!state);
			btnLoadMap.setEnabled(state);
			btnLoadRequest.setEnabled(state);
			btnComputeTour.setEnabled(state);
			btnAddRequest.setEnabled(state);
			btnDeleteRequest.setEnabled(state);
		}

	}
	
	public class SaveRoadMapListener implements ActionListener {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			System.out.println("Saving the road map");
		}

	}

	public class HelpListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			lblHelp.setText(helpText);
			buttonsContainer.add(lblHelp);
			buttonsContainer.updateUI();

			TimerTask task = new TimerTask() {
		        public void run() {
					buttonsContainer.remove(lblHelp);
					buttonsContainer.updateUI();
				}
			};
			Timer timer = new Timer("Timer");
			long delay = 5000L;
			timer.schedule(task, delay);
		}

	}

	/*
	 * Mouse listener to use in the states where the map is displayed (click on map or table)
	 *
	 */
	public class MouseOnMapListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// Only works if there is a map loaded
			if (loadedMap != null) {
				Intersection selectedPoint = gv.mapClickedResponse(e.getX(), e.getY());
				if(selectedPoint != null) {
					System.out.println(selectedPoint);
					tv.selectCell(selectedPoint);
				}else {
					System.out.println("no selected point");
				}
			}
		}
	}

	public void addMouseOnMapListener(){
		graphicalContainer.addMouseListener(new MouseOnMapListener());
	}

	/*
	 * Mouse listener that fires an property change after one click (for the addRequest + delete) when 
	 * we need the point clicked to be a special point (pickup, delivery, departure)
	 * 
	 */
	public class SingleMouseClickOnSpecialPointListener implements MouseListener {

		@Override
		public void mousePressed(final MouseEvent e) {}

		@Override
		public void mouseReleased(final MouseEvent e) {}

		@Override
		public void mouseEntered(final MouseEvent e) {}

		@Override
		public void mouseExited(final MouseEvent e) {}

		@Override
		public void mouseClicked(final MouseEvent e) {
			Intersection selectedPoint = gv.mapClickedResponse(e.getX(), e.getY(), true);
			if(selectedPoint != null) {
				System.out.println("special : " + selectedPoint.getNumber());
				support.firePropertyChange("pointClicked", null, selectedPoint);
			}
		}

	}

	public void addSingleMouseClickOnSpecialPointListener(){
		graphicalContainer.addMouseListener(new SingleMouseClickOnSpecialPointListener());
	}

	/*
	 * Mouse listener that fires an property change after one click (for the addRequest + delete) when 
	 * the point clicked can be special or random
	 */
	public class SingleMouseClickOnAnyPointListener implements MouseListener {

		@Override
		public void mousePressed(final MouseEvent e) {}

		@Override
		public void mouseReleased(final MouseEvent e) {}

		@Override
		public void mouseEntered(final MouseEvent e) {}

		@Override
		public void mouseExited(final MouseEvent e) {}

		@Override
		public void mouseClicked(final MouseEvent e) {
			Intersection selectedPoint = gv.mapClickedResponse(e.getX(), e.getY());
			if(selectedPoint != null) {
				System.out.println("special or random : " + selectedPoint.getNumber());
				support.firePropertyChange("pointClicked", null, selectedPoint);
			}
		}

	}

	public void addSingleMouseClickOnAnyPointListener(){
		graphicalContainer.addMouseListener(new SingleMouseClickOnAnyPointListener());
		System.out.println(graphicalContainer.getMouseListeners().length);
	}


	public void removeAllMouseListeners(){
		for(int i = 0; i<graphicalContainer.getMouseListeners().length; i++){
			graphicalContainer.removeMouseListener(graphicalContainer.getMouseListeners()[i]);
		}
		System.out.println(graphicalContainer.getMouseListeners().length);
	}


	

	/**
	 * Following good practice, the Model communicates with the View using listeners
	 * 
	 * @param evt the event the View is listening for, from the Model
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		final String propName = evt.getPropertyName();

		switch (propName) {
		case "updateMap":
			this.setMap((CityMap) evt.getNewValue());
			break;
		case "updateRequests":
			this.setRequests((SetOfRequests) evt.getNewValue());
			break;
		case "tourComputed":
			this.tourComputed((List<Segment>) evt.getNewValue());
			break;
		case "selectCell":
			this.selectCell((Intersection) evt.getNewValue());
			break;
		default:
			break;
		}
	}

	

}
