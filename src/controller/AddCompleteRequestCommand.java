package controller;

import java.util.LinkedList;

import javax.swing.text.Segment;

import model.Intersection;
import model.Request;
import model.RoadMap;
import model.SetOfRequests;
import model.Tour;

public class AddCompleteRequestCommand implements Command{
    private Tour tour;
    private Request newRequest;
    private Intersection preceedingPickup;
    private Intersection preceedingDelivery;
/*
    private SetOfRequests oldSOR;
    private LinkedList<model.Segment> oldPath;
    private RoadMap oldRoadMap;*/

    /**
	 * Create the command which adds a set of requests
	 * @param tour in which to load the map
	 */
	public AddCompleteRequestCommand(Tour t, Request newR, Intersection preceedingD, Intersection preceedingP){
        this.tour = t;
        this.newRequest = newR;
        this.preceedingPickup = preceedingP;
        this.preceedingDelivery = preceedingD;
/*
        this.oldSOR = tour.getSOR();
        this.oldPath = tour.getPath();
        this.oldRoadMap = tour.getRoadMap();*/
	}

    @Override
    public void doCommand() {
        try{
            this.tour.addRequest(this.newRequest, this.preceedingDelivery, this.preceedingPickup);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            //this.tour.rollback(this.oldPath, this.oldSOR, this.oldRoadMap);
            //this.tour.deleteRequest(newRequest);
            //TODO
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}