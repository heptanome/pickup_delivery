package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.xml.sax.SAXException;

import model.Intersection;
import model.Tour;
import view.HomeWindow;

/**
 * Main class of the controller. It implements the Tour (main class of the
 * Model) and HomeWindow (main class of the View). It is responsible for
 * transmitting the view events to the model.
 */
public class Application implements PropertyChangeListener {
	private final HomeWindow homeWindow;
	private final Tour tour;
	private final ListOfCommands listOfCommands;
	private State currentState;
	protected final HomeState homeState = new HomeState();
	protected final MapWithoutRequestsState mapWoRequestsState = new MapWithoutRequestsState();
	protected final MapWithRequestsState mapWithRequestsState = new MapWithRequestsState();
	protected final DisplayingTourOnMapState displayingTourState = new DisplayingTourOnMapState();
	protected final AddingPickupAddressState apa = new AddingPickupAddressState();
	protected final AddingPointPreceedingPickupState appp = new AddingPointPreceedingPickupState();
	protected final AddingDeliveryAddressState ada = new AddingDeliveryAddressState();
	protected final AddingPointPreceedingDeliveryState appd = new AddingPointPreceedingDeliveryState();
	protected final DeletingRequestState deleteRequestState = new DeletingRequestState();
	protected final MapOpeningExceptionState mapExceptionState = new MapOpeningExceptionState();
	protected final RequestOpeningExceptionState requestExceptionState = new RequestOpeningExceptionState();

	public static void main(final String[] args) {
		final Tour tour = new Tour();
		final HomeWindow homeWindow = new HomeWindow("Home Window");

		new Application(homeWindow, tour);
	}

	/**
	 * "Connects" the Model with the View and the View with the Controller by
	 * setting up the property listeners
	 * 
	 * @param hw    the View to work with
	 * @param t     the Model to work with
	 */
	public Application(final HomeWindow hw, final Tour t) {
		this.tour = t;
		this.homeWindow = hw;
		this.listOfCommands = new ListOfCommands();
		this.currentState = homeState;
		this.currentState.initiateState(this, this.homeWindow);
		// Window listens to Tour events
		this.tour.addPropertyChangeListener(this.homeWindow);
		// Application listens to Window events
		this.homeWindow.addPropertyChangeListener(this);
		
		this.currentState = homeState;
		this.currentState.initiateState(this, homeWindow);

	}

	/**
	 * Change the current state of the controller
	 * 
	 * @param state the new current state
	 */
	protected void setCurrentState(final State state) {
		currentState = state;
	}

	protected State getCurrentState() {
		return currentState;
	}

	/**
	 * Loads a map from a file path
	 * 
	 * @param fp The map's file path
	 * @throws IllegalArgumentException if the file path is null
	 * @throws IOException              if there is a I/O error
	 * @throws SAXException             if the xml file couldn't be parsed correctly
	 * @throws Exception                for any other exception
	 */
	public void loadMap(final String fp) {
		currentState.loadMap(this, this.homeWindow, fp, this.tour);
	}

	/**
	 * Loads a request from a file path
	 * 
	 * @param fp The set of requests' file path
	 * @throws IllegalArgumentException if the file path is null
	 * @throws IOException              if there is a I/O error
	 * @throws SAXException             if the xml file couldn't be parsed correctly
	 * @throws Exception                for any other exception
	 */
	public void loadRequests(final String fp) {
		currentState.loadRequests(this, homeWindow, fp, this.tour);
	}

	/**
	 * Starts the creation of a new request to add to the tour Pre condition : a set
	 * of requests has to be loaded
	 * 
	 */
	public void addRequest() {
		currentState.addRequests(this,homeWindow);
	}

	/**
	 * Method called when a point (an intersection) is selected on the map in one of
	 * the process of adding a request, or when deleting a request.
	 * 
	 * @param selectedPoint : the point that has been clicked
	 */
	public void pointClicked(Object selectedPoint) {
			currentState.pointClicked((Intersection)selectedPoint, homeWindow, tour, this);
	}

	/**
	 * Starts the process of deleting a request from the tour Pre condition : a set
	 * of requests has to be loaded //TODO : exception
	 * 
	 * @throws Exception
	 */
	public void deleteRequest() {
		currentState.deleteRequests(this, homeWindow);
	}

	/**
	 * Starts the process of computing a tour Pre condition : a set of requests has
	 * to be loaded
	 * 
	 */
	public void computeTour() throws Exception {
		currentState.computeTour(this, homeWindow, tour);
	}

	/**
	 * Getter for the listOfCommands attribute
	 * @return listeOfCommands
	 */
	public ListOfCommands getListOfCommands(){
		return this.listOfCommands;
	}

	/**
	 * Method called by window after a click on the button "Undo"
	 */
	public void undo(){
		currentState.undo(listOfCommands, this, homeWindow);
	}

	/**
	 * Method called by window after a click on the button "Undo"
	 */
	public void redo(){
		currentState.redo(listOfCommands, this, homeWindow);
	}
	
	public void displayHelp(){
		currentState.describeState(homeWindow);
	}

	/**
	 * We define different scenarios in this method, according to the events that
	 * can be brought up tp the controller.
	 * 
	 * "loadMap": triggers the controller into loading a map
	 * 
	 * "loadRequests": triggers the controller into loading a request
	 * 
	 * "addRequest" : triggers the controller into adding a request
	 * 
	 * "deleteRequest" : triggers the controller into its response to a clic during
	 * the adding and deleting processes
	 * 
	 * "deleteRequest" : triggers the controller into deleting a request
	 * 
	 * "computeTour": triggers the controller into computing the tour
	 * 
	 * "undo": triggers the controller into undoing the last command
	 * 
	 * "redo": triggers the controller into redoing the last undone command
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		final String propName = evt.getPropertyName();

		switch (propName) {
			case "loadMap":
				try {
					this.loadMap((String) evt.getNewValue());
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			case "loadRequests":
				try {
					this.loadRequests((String) evt.getNewValue());
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			case "addRequest":
				try {
					this.addRequest();
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			case "pointClicked":
				try {
					this.pointClicked(evt.getNewValue());
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			case "deleteRequest":
				try {
					this.deleteRequest();
				} catch (final Exception e) {
					e.printStackTrace();
				}
				break;
			case "computeTour":
				try {
					this.computeTour();
				} catch (final Exception e) {
				e.printStackTrace();
			}
			break;
			case "undo":
				try {
					this.undo();
				} catch (final Exception e) {
				e.printStackTrace();
			}
			break;
			case "redo":
				try {
					this.redo();
				} catch (final Exception e) {
				e.printStackTrace();
			}
			break;
			case "askHelp":
				try {
					this.displayHelp();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			break;
		default:
			break;
		}
		
	}
}
