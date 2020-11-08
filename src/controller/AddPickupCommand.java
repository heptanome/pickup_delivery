package controller;

import model.Intersection;
import model.Request;
import view.HomeWindow;

public class AddPickupCommand implements Command{
    private Intersection interClicked;
    private int pickupDuration;
    private HomeWindow hw;

    /**
	 * Create the command which adds a set of requests
	 */
	public AddPickupCommand(Intersection i, HomeWindow hw, int d){
        this.interClicked = i;
        this.pickupDuration = d;
        this.hw = hw;
	}

    @Override
    public void doCommand() {
        try{
            Request newR = new Request(new Intersection("",0,0), new Intersection("",0,0), 0 ,0);
            newR.setPickupAddress(interClicked);
            newR.setPickupDuration(pickupDuration);
            hw.setNewRequest(newR);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            hw.setNewRequest(null);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}