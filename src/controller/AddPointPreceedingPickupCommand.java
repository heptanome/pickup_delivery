package controller;

import model.Intersection;
import view.HomeWindow;

public class AddPointPreceedingPickupCommand implements Command{
    private Intersection interClicked;
    private HomeWindow hw;

    /**
	 * Create the command which adds the point preceeding the pickup when adding a new request
	 */
	public AddPointPreceedingPickupCommand (Intersection i, HomeWindow hw){
        this.interClicked = i;
        this.hw = hw;
	}

    @Override
    public void doCommand() {
        try{
            hw.setPreceedingPickup(interClicked);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            hw.setPreceedingPickup(null);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}