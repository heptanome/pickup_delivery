package controller;

import model.Intersection;
import model.Request;
import view.HomeWindow;

public class AddDeliveryCommand implements Command{
    private Intersection interClicked;
    private int deliveryDuration;
    private HomeWindow hw;

    /**
	 * Create the command which adds a set of requests
	 */
	public AddDeliveryCommand(Intersection i, HomeWindow hw, int d){
        this.interClicked = i;
        this.deliveryDuration = d;
        this.hw = hw;
	}

    @Override
    public void doCommand() {
        try{
            Request newR = hw.getNewRequest();
            newR.setDeliveryAddress(interClicked);
            newR.setDeliveryDuration(deliveryDuration);
            hw.setNewRequest(newR);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            Request newR = hw.getNewRequest();
            newR.setDeliveryAddress(null);
            newR.setDeliveryDuration(0);
            hw.setNewRequest(newR);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}