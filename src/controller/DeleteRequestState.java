package controller;

import model.Intersection;
import model.Request;
import model.Tour;
import view.HomeWindow;

public class DeleteRequestState implements State {
	
	
	public void deleteRequest() throws Exception {
		// specific behavior of the DeleteRequestState when deleting a request
		
		
	}
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        System.out.println("address " + i.getNumber() );
        System.out.println("Set of Requests before delete: "+ tour.setOfRequests.toString());
        System.out.println("The request "+ hw.getRequestFromIntersection(i).toString()+ " will be deleted.");
        
        hw.openPopUpWindow("The request "+ hw.getRequestFromIntersection(i).toString()+ " will be deleted.");
        tour.deleteRequest(hw.getRequestFromIntersection(i));
        System.out.println("Set of Requests after delete: "+ tour.setOfRequests.toString());

        
        // TODO: find corresponding pickup or delivery address respec.
		// TODO :  AddJOptionPane to ask for pickup duration and add it below
		// Request newR = new Request(i, new Intersection("",0,0), 0,0);
		// hw.setNewRequest(newR);
    }
	
	@Override
	public void describeState(HomeWindow hw) {
        System.out.println("Select a colored point on the map so that the corresponding request will "
        		+ "be deleted (pickup and delivery point)");
	}
	
	@Override
	public State nextState() {
		// TODO: better not instanciate a new instance
        return new DisplayingTourOnMapState();
    }
}
