package controller;

import model.Tour;
import view.HomeWindow;

public class DisplayingTourOnMapState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		// specific behavior of the working state when loading a map
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) throws Exception {
		// specific behavior of the working state when loading requests
		tour.setRequests(fp);
	}

	@Override
	public void computeTour(Tour tour) throws Exception {
		// specific behavior of the working state when computing a tour		
		tour.computeTour(); // <-- renvoie une liste de segment
	}

	@Override
	public void setMouseListener(HomeWindow hw)  {
		// specific behavior of the working state when computing a tour		
		hw.removeAllMouseListeners();
		hw.addMouseOnMapListener();
	}

}
