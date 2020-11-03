package view;

import javax.swing.*;

import controller.Application;
import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	protected String helpText;
	
	private JButton btnLoadMap = new JButton("Load a map");
	private JButton btnLoadRequest = new JButton("Load a set of requests");
	private JButton btnAddRequest = new JButton("Add a request");
	private JButton btnDeleteRequest = new JButton("Delete a request");
	private JButton btnComputeTour = new JButton("Compute a Tour");
	private JButton btnHelp = new JButton("🆘");
	private JLabel lblHelp = new JLabel();
	private JPanel textualContainer;
	private JPanel graphicalContainer;
	private JPanel buttonsContainer;
	private PropertyChangeSupport support;

	public GraphicalView gv;
	public TextualView tv;

	//Variables used to add a new request?
	private Request newRequest = null;
	private Intersection precedingPickup = null;
	private Intersection preceedingDelivery = null;

	/**
	 * Will build the window following a specific layout together with specific buttons
	 * @param name the name required for a window to be created
	 */
	public HomeWindow(String name) {
		super(name);
		this.support = new PropertyChangeSupport(this);
		this.helpText = "Please load a map";
		
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
		buttonsContainer = new JPanel();
		buttonsContainer.setBounds(1220, 0, 200, HEIGHT-30);
		buttonsContainer.setBackground(new Color(5, 132, 243));

		BoxLayout boxLayout1 = new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS);
		buttonsContainer.setLayout(boxLayout1);
		buttonsContainer.add(Box.createVerticalStrut(30));

		// Buttons
		btnLoadMap.addActionListener(new LoadMapListener());
		btnLoadMap.setUI(new StyledButtonUI());
		btnLoadMap.setEnabled(true);
		btnLoadMap.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLoadMap.setAlignmentY(5);
		buttonsContainer.add(btnLoadMap);
		buttonsContainer.add(Box.createVerticalStrut(10));
		
		btnLoadRequest.addActionListener(new LoadRequestListener());
		btnLoadRequest.setUI(new StyledButtonUI());
		btnLoadRequest.setEnabled(false);
		btnLoadRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnLoadRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnAddRequest.addActionListener(new AddRequestListener());
		btnAddRequest.setUI(new StyledButtonUI());
		btnAddRequest.setEnabled(false);
		btnAddRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnAddRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnDeleteRequest.addActionListener(new DeleteRequestListener());
		btnDeleteRequest.setUI(new StyledButtonUI());
		btnDeleteRequest.setEnabled(false);
		btnDeleteRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnDeleteRequest);
		buttonsContainer.add(Box.createVerticalStrut(10));

		btnComputeTour.addActionListener(new ComputeTourListener());
		btnComputeTour.setUI(new StyledButtonUI());
		btnComputeTour.setEnabled(false);
		btnComputeTour.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnComputeTour);
		buttonsContainer.add(Box.createVerticalStrut(10));
		
		btnHelp.addActionListener(new HelpListener());
		btnHelp.setUI(new StyledButtonUI());
		btnHelp.setEnabled(true);
		btnHelp.setFont(new Font("Arial", Font.BOLD, 30));
		btnComputeTour.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(btnHelp);
		
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
		this.helpText = "The map has been loaded. Please load a requests file now.";

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
		this.helpText = "The map and the set of requests have been loaded. Now is the time to compute!";
		
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
		btnAddRequest.setEnabled(true);
		btnDeleteRequest.setEnabled(true);
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
	 * @param segments An ordered (linked) list of segments the cyclist will have
	 * to follow
	 */
	public void tourComputed(List<Segment> segments) {
		this.helpText = "Your tour has been computed. Feel free to add or delete a point.";
		gv.displayTour(segments);
		tv.displayTour(this.loadedSOR, segments);
		//road map (file)
	}

	
	public Request getNewRequest(){
		return newRequest;
	}

	public void setNewRequest(Request r){
		newRequest = r;
	}

	public void setPreceedingPickup (Intersection i){
		precedingPickup = i;
	}

	public void setPreceedingDelivery (Intersection i){
		preceedingDelivery = i;
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
			support.firePropertyChange("addRequest", null, null);
		}

	}

	public class DeleteRequestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			support.firePropertyChange("deleteRequest", null, null);
		}

	}

	public class ComputeTourListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			support.firePropertyChange("computeTour", null, null);
		}

	}
	
	public class HelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblHelp.setText(helpText);
			buttonsContainer.add(lblHelp);
			buttonsContainer.updateUI();
			
			TimerTask task = new TimerTask() {
		        public void run() {
		        	System.out.println("retour de clique");
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
				Intersection selectedPoint = gv.mapClickedResponse(e.getX(), e.getY());
				System.out.println(selectedPoint.getNumber());
				tv.selectCell(selectedPoint);
				//TODO : implement a metohd in textual view that highlights the request that has this id 
			}
		}
	}

	public void addMouseOnMapListener(){
		graphicalContainer.addMouseListener(new MouseOnMapListener());
	}

	/*
	 * Mouse listener that fires an property change after one click (for the addRequest + maybe delete?)
	 *
	 */
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
			//Only works if there is a map loaded
			Intersection selectedPoint = gv.mapClickedResponse(e.getX(), e.getY());
			System.out.println(selectedPoint.getNumber());
			support.firePropertyChange("pointClicked", null, selectedPoint);
		}

	}

	public void addSingleMouseClickOnMapListener(){
		graphicalContainer.addMouseListener(new SingleMouseClickOnMapListener());
	}

	public void removeAllMouseListeners(){
		for(int i = 0; i<graphicalContainer.getMouseListeners().length; i++){
			graphicalContainer.removeMouseListener(graphicalContainer.getMouseListeners()[i]);
		}
		System.out.println(graphicalContainer.getMouseListeners().length);
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
		case "selectCell":
			this.selectCell((Intersection)evt.getNewValue());
			break;
		default:
			break;
		}
	}

	

}
