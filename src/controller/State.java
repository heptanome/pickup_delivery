package controller;

import model.Tour;
import view.HomeWindow;
import model.Intersection;

public interface State {
	public default void loadMap(String fp, Tour tour) throws Exception {
		// default behavior of the states when loading a map
	}
	
	public default void loadRequests(String fp, Tour tour) throws Exception {
		// default behavior of the states when loading requests
	}
	
	/*
	 * TODO: are these two methods necessary?
	public default void addRequest() {
		// default behavior of the states when adding a request
		
	}
	
	public default void deleteRequest() throws Exception {
		// default behavior of the states when deleting a request
	}
	*/
	
	public default void computeTour(Tour tour) throws Exception {
		// default behavior of the states when computing a tour		
	}
	
	public default void cancel() {
		// default behavior of the states when canceling a running process
	}

	public default void pointClicked(Intersection i, HomeWindow hw, Tour tour)throws Exception{

	}

	public default State nextState(){
		return this;
	}

	public default void setMouseListener(HomeWindow hw) {

	}

	public default void describeState(HomeWindow hw) {

	}
}
