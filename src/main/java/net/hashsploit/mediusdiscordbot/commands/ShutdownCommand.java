package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.util.TimedHashmap;

public class ShutdownCommand extends Command {

	public static final String COMMAND = "shutdown";
	public static final String DESCRIPTION = "Shutdown the Bot";
	
	public ShutdownCommand() {
		super(COMMAND, DESCRIPTION, true);
	}
	
	@Override
	public void onFire(CommandEvent event) {
		event.reply("Shutting down.");
		MediusBot.getInstance().shutdown();
	}	
	
}
