package view;

import javax.swing.*;

import controller.Application;
import model.CityMap;
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

	protected final static int WIDTH = 1420;
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
	private JButton btnComputeTour = new JButton("Compute a tour");
	private JButton btnRoadMap = new JButton("Display the road map");
	private JButton btnSaveRoadMap = new JButton("Save the Road Map");
	private JPanel textualContainer;
	private JPanel graphicalContainer;
	private PropertyChangeSupport support;

	public GraphicalView gv;
	public TextualView tv;

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
		graphicalContainer.addMouseListener(new MouseOnMapListener());

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
		this.setButton(btnLoadMap, new LoadMapListener(), buttonsContainer);
		btnLoadMap.setEnabled(true);
		btnLoadMap.setAlignmentY(5);
		this.setButton(btnLoadRequest, new LoadRequestListener(), buttonsContainer);
		this.setButton(btnComputeTour, new ComputeTourListener(), buttonsContainer);
		this.setButton(btnRoadMap, new RoadMapListener(), buttonsContainer);	
		this.setButton(btnAddRequest, new AddRequestListener(), buttonsContainer);
		this.setButton(btnDeleteRequest, new DeleteRequestListener(), buttonsContainer);
		this.setButton(btnSaveRoadMap, new SaveRoadMapListener(), buttonsContainer);

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

	
	private void setButton(JButton newButton, ActionListener listener, JPanel buttonsContainer) {
		newButton.addActionListener(listener);
		newButton.setUI(new StyledButtonUI());
		newButton.setEnabled(false);
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsContainer.add(newButton);
		buttonsContainer.add(Box.createVerticalStrut(10));
	}
	
	/**
	 * Refreshing the View (graphical) with a newly loaded map
	 * @param map The map that was received from the Model after parsing it
	 */
	public void setMap(CityMap map) {
		this.loadedMap = map;

		// Graphical view
		graphicalContainer.removeAll();
		graphicalContainer.repaint();
		gv = new GraphicalView(this.loadedMap);
		graphicalContainer.add(gv);
		
		// Buttons enabling
		btnLoadRequest.setEnabled(true);
		
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
		btnComputeTour.setEnabled(true);
	}
	
	/**
	 * Passing the segments to the graphical container
	 * @param segments An ordered (linked) list of segments the cyclist will have
	 * to follow
	 */
	public void tourComputed(List<Segment> segments) {
		gv.displayTour(segments);
		tv.displayTour(this.loadedSOR, segments);
		//TODO textual container
	}

	public class LoadRequestListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			File currentDirectory = null;
			try {
				currentDirectory = new File(".").getCanonicalFile();
				System.out.println("Current directory : " + currentDirectory);
			} catch (IOException err) {
			}
			JFileChooser dialogue = new JFileChooser(currentDirectory);
			dialogue.showOpenDialog(null);
			String requestPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Selected File : " + requestPath);

			support.firePropertyChange("loadRequests", "", requestPath);
		}

	}

	public class LoadMapListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File currentDirectory= null;
			try {
				currentDirectory = new File(".").getCanonicalFile();
				System.out.println("Current directory : " + currentDirectory);
			} catch (IOException err) {
				
			}
			JFileChooser dialogue = new JFileChooser(currentDirectory);
			dialogue.showOpenDialog(null);
			String mapPath = dialogue.getSelectedFile().getAbsolutePath();
			System.out.println("Selected File : " + mapPath);
			support.firePropertyChange("loadMap", "", mapPath);
			System.out.println("fired loadmap");
		}

	}

	public class AddRequestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Application.addRequest();
		}

	}

	public class DeleteRequestListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Application.deleteRequest();
		}

	}

	public class ComputeTourListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			support.firePropertyChange("computeTour", null, null);
			
			// Buttons Enabling
			btnRoadMap.setEnabled(true);
			btnAddRequest.setEnabled(true);
			btnDeleteRequest.setEnabled(true);
		}

	}
	
	public class RoadMapListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
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
			JPanel roadMapView = new RoadMapView();
			graphicalContainer.add(roadMapView);
		}
		
		public void removeRoadMap() {
			graphicalContainer.removeAll();
			graphicalContainer.repaint();
			graphicalContainer.add(gv);
		}
		
		private void enableButtons(boolean state) {
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
		public void actionPerformed(ActionEvent e) {
			System.out.println("Saving the road map");
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
