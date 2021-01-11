package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.util.TimedHashmap;

public class BroadcastCommand extends Command {

	public static final String COMMAND = "broadcast";
	public static final String DESCRIPTION = "Broadcast a message to all users on a specific server.";
	
	public BroadcastCommand() {
		super(COMMAND, DESCRIPTION, true);
	}

	@Override
	public void onFire(CommandEvent event) {
	}
}