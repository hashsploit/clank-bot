package net.hashsploit.mediusdiscordbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		// Log PM's and exit
		if (event.isFromType(ChannelType.PRIVATE)) {
			logger.info(String.format("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay()));
			return;
		}
		
		// Do not fire on our own messages
		if (event.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
			return;
		}
		
		// Parse by @mention
		if (event.getMessage().getContentDisplay().startsWith("@" + event.getJDA().getSelfUser().getName())) {
			final String message = event.getMessage().getContentDisplay().split("@" + event.getJDA().getSelfUser().getName())[1].trim();
			final String[] parts = message.split(" ");
			final String command = parts[0];
			final String[] args = Arrays.copyOfRange(parts, 1, parts.length);
			
			handleCommand(event, command, args);
		}
		
		// Parse by prefix
		if (event.getMessage().getContentRaw().startsWith(MediusBot.getInstance().getConfig().getPrefix())) {
			final String message = event.getMessage().getContentDisplay().split(MediusBot.getInstance().getConfig().getPrefix())[1];
			final String[] parts = message.split(" ");
			final String command = parts[0];
			final String[] args = Arrays.copyOfRange(parts, 1, parts.length);
			
			handleCommand(event, command, args);
		}
		
	}
	
	/**
	 * Handle incoming commands
	 * @param event
	 * @param command
	 * @param args
	 */
	private void handleCommand(final MessageReceivedEvent event, final String command, final String[] args) {
		logger.info(String.format("[%s] [#%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(), event.getMember().getEffectiveName(), event.getMessage().getContentDisplay()));
		
		// Find if this is a valid command
		for (final Command cmd : MediusBot.getInstance().getCommands()) {
			if (cmd.getName().equals(command)) {
				
				// Check permissions
				if (cmd.isOperatorCommand() && !isOperator(event.getAuthor().getIdLong())) {
					return;
				}
				
				
				final List<String> arguments = new ArrayList<String>();
				
				for (final String s : args) {
					arguments.add(s);
				}
				
				// Fire the command
				CommandEvent cmdEvent = new CommandEvent(event.getAuthor(), event.getChannel(), cmd, arguments);
				cmd.onFire(cmdEvent);
			}
		}
	}
	
	/**
	 * Check if a Discord Client Id is an operator.
	 * @param discordId
	 * @return
	 */
	private boolean isOperator(long discordId) {
		for (long id : MediusBot.getInstance().getConfig().getOperators()) {
			if (id == discordId) {
				return true;
			}
		}
		return false;
	}

}
