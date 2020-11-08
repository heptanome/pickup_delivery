package controller;

import java.io.IOException;

import org.xml.sax.SAXException;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a map without requests.
 */
public class MapWithoutRequestsState implements State {
	
	@Override
	public void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour, ListOfCommands l) {
		try {
			tour.setMap(fp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadRequests(Application a, HomeWindow hw,  String fp, Tour tour, ListOfCommands l) {
		try {
			tour.setRequests(fp);
			l.add(new LoadRequestsCommand(tour, fp));
			a.setCurrentState(a.mapWithRequestsState);
			a.getCurrentState().setButtons(hw,l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public  void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(true, true, false, false, false, false, false, true, l.redoPossible(), false);
	}

	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw){

		l.undo();
		a.setCurrentState(a.homeState);
		a.getCurrentState().setButtons(hw , l);

	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw){
		l.redo();
		a.setCurrentState(a.mapWithRequestsState);
		a.getCurrentState().setButtons(hw,l);
	}

}
