package controller;

import model.Tour;
import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;

/**
 * State interface of the controller. Implements the general description of a State.
 */
public interface State {
	/**
	 * Method called by the controller after a click on the button "Load a map" and the selection of 
	 * a xml file containing the map, in the file system.
	 * 
	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 * @param fp the file path to the map (xml file)
	 * @param tour the tour that will use the map
	 */
	public default void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour){
	}
	
   /**
	 * Method called by the controller after a click on the button "Load a set of requests" and the selection of 
	 * a xml file containing the set of requests, in the file system.
	 * 
	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 * @param fp the file path to the map (xml file)
	 * @param tour the tour that will use the map
	 */
	public default void loadRequests(Application a, HomeWindow hw, String fp, Tour tour) {
	}
	
   /**
	 * Method called by the controller after a click on the button "Add a request".
	 * 
	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 */
	public default void addRequests(Application a, HomeWindow hw) {
	}
	
   /**
	 * Method called by the controller when a point (an intersection) is selected on the map in one of
	 * the process of adding a request, or when deleting a request.
	 * 
	 * @param i the Intersection clicked
	 * @param hw the HomeWindow
	 * @param tour the Tour to which the intersection belongs
	 * @param a the current Application
	 */
	public default void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a){
	}
	
   /**
	 * Method called by the controller to delete a request from the tour 
	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 */
	public default void deleteRequests(Application a, HomeWindow hw)  {
	}
	
   /**
	 * Method called by the controller after a click on the button "Compute Tour"
	 * 
 	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 * @param tour the tour that will be calculate
	 */
	public default void computeTour(Application a, HomeWindow hw, Tour tour) {	
	}
	
   /**
	 * Method called by a State to initiate the next State
	 * 
 	 * @param a the controller of the application
	 * @param homeWindow the main window of the application
	 */
	public default void initiateState(Application a, HomeWindow hw) {	
	}
	
	/**
	 * Method called by the state to handle a raised Exception
	 * @param a the controller of the app
	 * @param e the Exception raised 
	 * @param hv the HomeWindow
	 * @param previousState the State in which the Exception has been raised
	 */
	public default void handleException(Application a, Exception e, HomeWindow hw, State previousState) {
	}

	/**
	 * Method called by the controller to cancel a running process
	 * 
	 */
	public default void cancel() {
	}
	
	/**
	 * Method called by the States to display a message about specific information of the current State
	 * 
	 * @param hw the HomeWindow
	 */
	public default void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Feel free to add or delete a request or go ahead and compute a tour.");
		System.out.println("apa");
    }

	/**
	 * Method called by the controller after a click on the button "Undo"
	 * @param l the current list of commands
	 * @param a the current Application
	 * @param hw the homeWindow displayed
	 */
	public default void undo(ListOfCommands l, Application a, HomeWindow hw){
	}

	/**
	 * Method called by the controller after a click on the button "Undo"
	 * @param l the current list of commands
	 * @param a the current Application
	 * @param hw the homeWindow displayed
	 */
	public default void redo(ListOfCommands l, Application a, HomeWindow hw){
	}


}
