package controller.command;

import java.util.LinkedList;

public class ListOfCommands {
	private LinkedList<Command> list;
	private int currentIndex;

	public ListOfCommands() {
		currentIndex = -1;
		list = new LinkedList<Command>();
	}

	/**
	 * Add command c to this
	 * 
	 * @param c the command to add
	 * @throws Exception because of doCommand, as some commands can throw exceptions
	 */
	public void add(Command c) throws Exception {
		int i = currentIndex + 1;
		while (i < list.size()) {
			list.remove(i);
		}
		currentIndex++;
		list.add(currentIndex, c);
		c.doCommand();
	}

	/**
	 * Temporary remove the last added command (this command may be reinserted again
	 * with redo)
	 */
	public void undo() {
		if (currentIndex >= 0) {
			Command cde = list.get(currentIndex);
			currentIndex--;
			cde.undoCommand();
		}
	}

	/**
	 * Permanently remove the last added command (this command cannot be reinserted
	 * again with redo)
	 */
	public void cancel() {
		if (currentIndex >= 0) {
			Command cde = list.get(currentIndex);
			list.remove(currentIndex);
			currentIndex--;
			cde.undoCommand();
		}
	}

	/**
	 * Permanently remove the last added command from the list without calling
	 * undoCommand() (this command cannot be reinserted again with redo)
	 */
	public void removeWithOutUndoing() {
		if (currentIndex >= 0) {
			list.remove(currentIndex);
			currentIndex--;
		}
	}

	/**
	 * Reinsert the last command removed by undo
	 * 
	 * @throws Exception once again because of doCommand, it might throw exceptions
	 */
	public void redo() throws Exception {
		if (currentIndex < list.size() - 1) {
			currentIndex++;
			Command cde = list.get(currentIndex);
			cde.doCommand();
		}
	}

	/**
	 * Checks whether a "redo" action is possible
	 * 
	 * @return true if "redo" is possible
	 */
	public boolean redoPossible() {
		return (currentIndex < list.size() - 1);
	}

	/**
	 * Checks whether a "undo" action is possible
	 * 
	 * @return true if "redo" is possible
	 */
	public boolean undoPossible() {
		return (currentIndex > -1);
	}

	/**
	 * Permanently remove all commands from the list
	 */
	public void reset() {
		currentIndex = -1;
		list.clear();
	}
}