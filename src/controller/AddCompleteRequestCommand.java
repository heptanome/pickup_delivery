package controller;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddCompleteRequestCommand implements Command{
    private Tour tour;
    private Request newRequest;
    private Intersection preceedingPickup;
    private Intersection preceedingDelivery;

    /**
	 * Create the command which adds a set of requests
	 * @param tour in which to load the map
	 */
	public AddCompleteRequestCommand(Tour t, Request newR, Intersection preceedingD, Intersection preceedingP){
        this.tour = t;
        this.newRequest = newR;
        this.preceedingPickup = preceedingP;
        this.preceedingDelivery = preceedingD;
	}

    @Override
    public void doCommand() {
        try{
            tour.addRequest(this.newRequest, this.preceedingDelivery, this.preceedingPickup);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            //TODO : check that it works when deleteRequest is implemented correctly
            tour.deleteRequest(this.newRequest);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}