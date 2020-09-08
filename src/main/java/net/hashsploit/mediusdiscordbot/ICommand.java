package net.hashsploit.mediusdiscordbot;

public interface ICommand {
	
	/**
	 * Called on command execution.
	 * @return
	 */
	public void onFire(CommandEvent event);
	
}
