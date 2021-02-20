package net.hashsploit.mediusdiscordbot;

import net.hashsploit.mediusdiscordbot.util.TimedHashmap;

public abstract class Command implements ICommand {
	
	private final String name;
	private final String description;
	private final boolean operatorCmd;
	// private final TimedHashmap<String, String> GRPCServerCache;
	
	public Command(final String name, final String description, final boolean operatorCmd) {
		this.name = name;
		this.description = description;
		this.operatorCmd = operatorCmd;
	}
	
	/**
	 * Get the command name.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the command description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns true if this command should only be issued by an operator.
	 * @return
	 */
	public boolean isOperatorCommand() {
		return operatorCmd;
	}
	
}
