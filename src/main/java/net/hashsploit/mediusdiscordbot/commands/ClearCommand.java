package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;

public class ClearCommand extends Command {

	public static final String COMMAND = "clear";
	public static final String DESCRIPTION = "Clears the chat with specified parameters.";
	
	private boolean isWorking;
	
	public ClearCommand() {
		super(COMMAND, DESCRIPTION, true);
		isWorking = false;
	}

	@Override
	public void onFire(CommandEvent event) {
		
	}
	
	private void clear() {
		
	}
	
}
