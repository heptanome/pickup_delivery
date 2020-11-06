package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        try {
	        int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a pickup duration (number of minutes)"));
            System.out.println("Pickup address " + i.getNumber() + " Duration :" + duration );
            Request newR = new Request(new Intersection("",0,0), new Intersection("",0,0), 0 ,0);
            newR.setPickupAddress(i);
            newR.setPickupDuration(duration);
            hw.setNewRequest(newR);
        } catch (NullPointerException e) {
	        JOptionPane.showMessageDialog(hw, "You did not chose a valid intersection.");
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
    
    @Override
	public State nextState() {
        return new AddingPointPreceedingPickup();
    }
    
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Select a pickup point on the map for the new request"); 
    }
    
    @Override
    public  void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
		hw.addSingleMouseClickOnAnyPointListener();
	}
}
