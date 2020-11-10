/**
 * 
 */
package controller.state;

import javax.swing.JOptionPane;

import controller.Application;
import view.HomeWindow;

/**
 * @author Arthur Batel
 *
 */
public class ComputeTourExceptionState implements State {

	@Override
	public void handleException(Application a, Exception e, HomeWindow hw, State previousState) {
		String dialogTitle = "ComputeTour error";
		String dialogMessage = "An exception has been raised. Unable to compute a Tour.\nPlease check if you selected the proper requests file and map file.";
		e.printStackTrace();

		JOptionPane.showMessageDialog(hw, dialogMessage, dialogTitle, JOptionPane.PLAIN_MESSAGE);

		// Transition vers l'état précédent
		a.setCurrentState(previousState);
	}

	/**
	 * Method called by the state to display a message with specific information
	 * about the state
	 * 
	 * @param hw the HomeWindow
	 */
	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Something went wrong while computing a tour. Try again.");
	}

}
