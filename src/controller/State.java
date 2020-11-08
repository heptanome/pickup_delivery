package controller;

import model.Tour;
import view.HomeWindow;
import model.Intersection;

/**
 * State interface of the controller. Implements the general description of a State.
 */
public interface State {
	/**
	 * Method called by the controller after a click on the button "Load a map" and the selection of 
	 * a xml file containing the map, in the file system.
	 * 
	 * @param fp the file path to the map (xml file)
	 * @param tour the tour that will use the map
	 */
	public default void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour, ListOfCommands l){
	}
	
   /**
	 * Method called by the controller after a click on the button "Load a set of requests" and the selection of 
	 * a xml file containing the set of requests, in the file system.
	 * 
	 * @param fp the file path to the map (xml file)
	 * @param tour the tour that will use the map
	 */
	public default void loadRequests(Application a, HomeWindow hw, String fp, Tour tour, ListOfCommands l) {
	}
	
   /**
	 * Method called by the controller after a click on the button "Compute Tour"
	 * 
	 */
	public default void computeTour(Application a, HomeWindow hw, Tour tour, ListOfCommands l) {	
	}

	/**
	 * Method called by the controller to cancel a running process
	 * 
	 */
	public default void cancel() {
	}

   /**
	 * Method called by the controller after a click on the button "Load a set of requests" and the selection of 
	 * a xml file containing the set of requests, in the file system.
	 * 
	 * @param i the Intersection clicked
	 * @param hw the HomeWindow
	 * @param tour the Tour to which the intersection belongs
	 * @param a the current Application
	 */
	public default void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a){
	}

	/**
	 * Method called by the controller to change the mouse listeners of a HomeWindow
	 * according to the State
	 * 
	 * @param hw the HomeWindow
	 */
	public default void setMouseListener(HomeWindow hw) {
	}

	/**
	 * Method called by the controller to display a message about specific information of the current State
	 * 
	 * @param hw the HomeWindow
	 */
	public default void describeState(HomeWindow hw) {
	}

	/**
	 * Method called by the controller to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 */
	public default void setButtons(HomeWindow hw, ListOfCommands l){
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
