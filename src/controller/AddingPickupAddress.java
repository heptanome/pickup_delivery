package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a Number"));
        System.out.println("pickup address " + i.getNumber() + " Duration :" + duration );
		Request newR = new Request(i, new Intersection("",0,0), duration ,0);
		hw.setNewRequest(newR);
    }
    
    @Override
	public State nextState() {
        return new AddingPointPreceedingPickup();
    }
    
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Select a pickup point on the map (white point) for the new request"); 
	}
}
