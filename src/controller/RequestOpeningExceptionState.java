/**
 * 
 */
package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.xml.sax.SAXException;

import view.HomeWindow;

/**
 * @author Arthur Batel
 *
 */
public class RequestOpeningExceptionState implements State {

	@Override
	public void handleException(Application a, Exception e, HomeWindow hw,State previousState) {
		String dialogTitle = "Loading requests error";
		String dialogMessage;
		
		if(e instanceof IllegalArgumentException) {
			dialogMessage = "The requests file path argument is null. Unable to load the requests.\nIllegalArgumentException";
		}else if(e instanceof IOException) {
			dialogMessage = "An IO error occured. Unable to load the requests.";
		}else if(e instanceof SAXException) {
			dialogMessage = "Unable to parse the document. Please check if you selected a proper requests file."; 
		}else {
			e.printStackTrace();
			dialogMessage = "An exception has been raised. Unable to load the requests.\nPlease check if you selected the proper requests file.\nPotential problem : the requests intersections do not correspond to the map loaded.";
		}
		
		JOptionPane.showMessageDialog(hw,dialogMessage,dialogTitle,JOptionPane.PLAIN_MESSAGE);
		
		//Transition vers l'état précédent
		a.setCurrentState(previousState);
	}
	
	/**
	 * Method called by the state to display a message with specific information about the state
	 * 
	 * @param hw the HomeWindow
	 */
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Something went wrong while loading the request. Try again.");
		System.out.println("apa");
    }

}
