package controller.command;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddCompleteRequestCommand implements Command {
	private Tour tour;
	private Request newRequest;
	private Intersection preceedingPickup;
	private Intersection preceedingDelivery;

	/**
	 * Create the command which adds a complete request
	 * 
	 * @param t           in which to add the request
	 * @param newR        the Request to add
	 * @param preceedingD the intersection preceding the new delivery
	 * @param preceedingP the intersection preceding the new pickup
	 */
	public AddCompleteRequestCommand(Tour t, Request newR, Intersection preceedingD, Intersection preceedingP) {
		this.tour = t;
		this.newRequest = newR;
		this.preceedingPickup = preceedingP;
		this.preceedingDelivery = preceedingD;

	}

	@Override
	public void doCommand() {
		try {
			this.tour.addRequest(this.newRequest, this.preceedingDelivery, this.preceedingPickup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undoCommand() {
		try {
			System.out.println("undo add complete command");
			this.tour.deleteRequest(newRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}