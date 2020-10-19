import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 */

/**
 * @author Arthur Batel
 *
 */
public class LoadMapBtn implements ActionListener {

	/**
	 * 
	 */
	public LoadMapBtn() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Application.loadRequest("chemin"); //TODO: Implémenter la récupération du chemin
	}

}
