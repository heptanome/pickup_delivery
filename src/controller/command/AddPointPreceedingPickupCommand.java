package controller.command;

import model.Intersection;
import view.HomeWindow;

public class AddPointPreceedingPickupCommand implements Command {
	private Intersection interClicked;
	private HomeWindow hw;

	/**
	 * Create the command which adds the point preceding the pickup when adding a
	 * new request
	 * 
	 * @param i  the intersection preceding the new pickup
	 * @param hw the HomeWindow
	 */
	public AddPointPreceedingPickupCommand(Intersection i, HomeWindow hw) {
		this.interClicked = i;
		this.hw = hw;
	}

	@Override
	public void doCommand() {
		try {
			hw.setPreceedingPickup(interClicked);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undoCommand() {
		try {
			hw.setPreceedingPickup(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}