package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;

public class BroadcastCommand extends Command {

	private static final String COMMAND = "broadcast";
	private static final String DESCRIPTION = "Broadcast a message to all users on a specific server.";
	
	public BroadcastCommand() {
		super(COMMAND, DESCRIPTION, true);
	}

	@Override
	public void onFire(CommandEvent event) {
		
		
		
	}
	
	
}