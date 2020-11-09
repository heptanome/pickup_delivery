package controller.command;

import model.Tour;

public class LoadRequestsCommand implements Command {
	private Tour tour;
	private String filePath;

	/**
	 * Create the command which adds a set of requests
	 * 
	 * @param t  in which to load the set of requests
	 * @param fp the file path to the set of requests
	 */
	public LoadRequestsCommand(Tour t, String fp) {
		this.tour = t;
		this.filePath = fp;
	}

	@Override
	public void doCommand() {
		try {
			tour.setRequests(this.filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undoCommand() {
		try {
			tour.resetRequests();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}