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
	public void loadMap(Application a, HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
			a.setCurrentState(a.mapWoRequestsState);
			setButtons(homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("map file path argument is null");
		} catch (IOException e) {
			System.out.println("An IO error occured");
		} catch (SAXException e) {
			System.out.println("Unable to parse the document");
		} catch (Exception e) {}
		
	}

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, false, false, false, false, false, false, false);
	}

}
