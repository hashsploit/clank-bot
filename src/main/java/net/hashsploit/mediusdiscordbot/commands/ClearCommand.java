package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;

public class ClearCommand extends Command {

	private static final String COMMAND = "shutdown";
	private static final String DESCRIPTION = "Shutdown the Bot";
	
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
