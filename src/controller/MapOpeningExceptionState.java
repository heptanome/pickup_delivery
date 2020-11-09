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
public class MapOpeningExceptionState implements State {
	
	@Override
	public void handleException(Application a, Exception e, HomeWindow hw,State previousState) {
		String dialogTitle = "Loading map error";
		String dialogMessage;
		
		if(e instanceof IllegalArgumentException) {
			dialogMessage = "The map file path argument is null. Unable to load a map.\nIllegalArgumentException";
		}else if(e instanceof IOException) {
			dialogMessage = "An IO error occured. Unable to load the map.\nIOException";
		}else if(e instanceof SAXException) {
			dialogMessage = "Unable to parse the document. Please check if you selected a proper Map file.\nSAXException"; 
		}else if(e instanceof IrrelevantFileException ){
			dialogMessage = "Unable to create a proper map. Please check if you selected a correct Map file\nIrrelevantFileException";
		}else {
			e.printStackTrace();
			dialogMessage = "An exception has been raised. Unable to load the Map.";
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
        JOptionPane.showMessageDialog(hw, "Something went wrong while loading the map. Try again.");
		System.out.println("apa");
    }

}
