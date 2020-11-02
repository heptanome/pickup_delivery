package controller;

import view.HomeWindow;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(String s, HomeWindow hw) throws Exception{
        System.out.println("preceeding delivery address " + s );
    }

    @Override
	public State nextState() throws Exception{
        return new WorkingState();
	}
}