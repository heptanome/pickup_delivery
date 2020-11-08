package controller;

import java.io.IOException;

import org.xml.sax.SAXException;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a tour on home page (when opening the application)
 */
public class HomeState implements State {
	
	@Override
	public void loadMap(Application a, HomeWindow homeWindow, String fp, Tour tour, ListOfCommands l) {
		try {
			tour.setMap(fp);
			l.add(new LoadMapCommand(tour,fp));
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().setButtons(homeWindow,l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
    public void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(true, false, false, false, false, false, false, false, l.redoPossible(), false);
	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw){
		l.redo();
		a.setCurrentState(a.mapWoRequestsState);
		a.getCurrentState().setButtons(hw,l);
	}

}
