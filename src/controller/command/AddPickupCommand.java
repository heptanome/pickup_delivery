package controller.command;

import model.Intersection;
import model.Request;
import view.HomeWindow;

public class AddPickupCommand implements Command{
    private Intersection interClicked;
    private int pickupDuration;
    private HomeWindow hw;

    /**
	 * Create the command which adds the new pickup address (in the process of adding a request)
     * @param i the new pickupaddress
     * @param hw the home Window
     * @param d the pickup duration
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