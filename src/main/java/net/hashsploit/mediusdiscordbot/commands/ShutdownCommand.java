package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;

public class ShutdownCommand extends Command {

	private static final String COMMAND = "shutdown";
	private static final String DESCRIPTION = "Shutdown the Bot";
	
	public ShutdownCommand() {
		super(COMMAND, DESCRIPTION, true);
	}
	
	@Override
	public void onFire(CommandEvent event) {
		event.reply("Shutting down.");
		MediusBot.getInstance().shutdown();
	}	
	
}
