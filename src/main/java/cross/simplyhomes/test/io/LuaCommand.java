package cross.simplyhomes.test.io;

import cross.simplyhomes.utils.SimpleCommand;

public class LuaCommand {
	public final String name;
	private SimpleCommand command;
	public LuaCommand(String name) {
		this.name = name;
	}
	public void addRunnable(SimpleCommand command) {
		this.command = command;
	}

	public SimpleCommand getCommand() {
		return this.command;
	}

}
