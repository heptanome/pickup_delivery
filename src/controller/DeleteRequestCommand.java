package controller;

import model.Intersection;
import model.Request;
import model.Tour;

public class DeleteRequestCommand implements Command{
    private Request r;
    private Tour tour;
    private Intersection preceedingPickup;
    private Intersection preceedingDelivery;

    /**
	 * Create the command which deletes a request
     * @param r the request to delete
     * 
     * @param tour the Tour in which to delete it
     * @param pp the intersection preceeding the pickup of r
     * @param pd the intersection preceeding the delivery of r
     * 
	 */
	public DeleteRequestCommand(Request r, Tour tour, Intersection pp, Intersection pd ){
        this.r= r;
        this.tour = tour;
        this.preceedingPickup = pp;
        this.preceedingDelivery = pd;
	}

    @Override
    public void doCommand() {
        try{
            System.out.println("Redo delete command");

            //TODO : make this work
            tour.deleteRequest(r);
            tour.refreshColorsOfTour();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            System.out.println("undo delete command");
            tour.addRequest(r, preceedingPickup, preceedingDelivery);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}