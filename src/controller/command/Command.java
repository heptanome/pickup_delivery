package controller.command;

public interface Command {

	/**
	 * Execute the command this
	 * @throws Exception 
	 */
	void doCommand() throws Exception;

	/**
	 * Self-explanatory, will undo the command
	 */
	void undoCommand();
}