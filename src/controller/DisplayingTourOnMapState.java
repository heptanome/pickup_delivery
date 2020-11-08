package controller;

import java.io.IOException;

import org.xml.sax.SAXException;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a tour on the map
 */
public class DisplayingTourOnMapState implements State {
	
	@Override
	public void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour, ListOfCommands l) {
		try {
			tour.setMap(fp);
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().setButtons(homeWindow,l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadRequests(Application a, HomeWindow hw,  String fp, Tour tour, ListOfCommands l) {
		try {
			tour.setRequests(fp);
			a.setCurrentState(a.mapWithRequestsState);
			a.getCurrentState().setButtons(hw,l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void computeTour(Application a, HomeWindow hw, Tour tour, ListOfCommands l) {
		try {
			tour.computeTour(); // returns a list of segments
		}catch (Exception e) {
			
		}
	}

	@Override
	public void setMouseListener(HomeWindow hw)  {	
		hw.removeAllMouseListeners();
		hw.addMouseOnMapListener();
	}

	@Override
    public  void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(true, true, false, true, true, true, false , false, false, true);
	}

}
