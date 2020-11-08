package controller;

import model.Tour;

public class LoadMapCommand implements Command{
    private Tour tour;
    private String filePath;

    /**
	 * Create the command which adds the shape s to the plan p
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public LoadMapCommand(Tour t, String fp){
        this.tour = t;
        this.filePath = fp;
	}

    @Override
    public void doCommand() {
        try{
            tour.setMap(filePath);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void undoCommand() {
        try{
            tour.resetTour();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}