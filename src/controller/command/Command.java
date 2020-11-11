package controller.command;

public interface Command {

	/**
	 * Execute the command this
	 * 
	 * @throws Exception some commands do throw exceptions
	 */
	void doCommand() throws Exception;

	/**
	 * Self-explanatory, will undo the command
	 */
	void undoCommand();
}