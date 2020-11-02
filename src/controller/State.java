package controller;

import model.Tour;

public interface State {
	public default void loadMap(String fp, Tour tour) throws Exception {
		// default behavior of the states when loading a map
	}
	
	public default void loadRequests(String fp, Tour tour) throws Exception {
		// default behavior of the states when loading requests
	}
	
	public default void addRequest() {
		// default behavior of the states when adding a request
		
	}

	public default void deleteRequests() {
		// default behavior of the states when deleting a request
	}
	
	public default void computeTour(Tour tour) throws Exception {
		// default behavior of the states when computing a tour		
	}
	
	public default void cancel() {
		// default behavior of the states when canceling a running process
	}
}
