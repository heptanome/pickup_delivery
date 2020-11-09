package controller.command;

import model.Tour;

public class LoadMapCommand implements Command {
	private Tour tour;
	private String filePath;

	/**
	 * Create the command which adds a map
	 * 
	 * @param t  in which to load the map
	 * @param fp the file path to the map
	 */
	public LoadMapCommand(Tour t, String fp) {
		this.tour = t;
		this.filePath = fp;
	}

	@Override
	public void doCommand() {
		try {
			tour.setMap(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undoCommand() {
		try {
			tour.resetMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}