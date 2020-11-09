package controller.command;

public interface Command {

	/**
	 * Execute the command this
	 */
	void doCommand();

	/**
	 * Self-explanatory, will undo the command
	 */
	void undoCommand();
}