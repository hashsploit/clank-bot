package net.hashsploit.mediusdiscordbot;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class CommandEvent {

	private final User issuer;
	private final MessageChannel channel;
	private final Command command;
	private final List<String> arguments;
	
	public CommandEvent(final User issuer, final MessageChannel channel, final Command command, final List<String> arguments) {
		this.issuer = issuer;
		this.channel = channel;
		this.command = command;
		this.arguments = new ArrayList<String>();
	}

	public void reply(String message) {
		this.channel.sendMessage(message).complete();
	}
	
	public void reply(MessageEmbed embed) {
		this.channel.sendMessage(embed).complete();
	}
	
	/**
	 * Send a JSON Query Message  
	 * @param server
	 * @param payload
	 */
	public JSONObject sendJSONQueryMessage(String server, JSONObject payload) {
		
		
		return null;
	}

	/**
	 * Get the user/issuer of the command.
	 * @return
	 */
	public User getIssuer() {
		return issuer;
	}

	/**
	 * Get the current channel.
	 * @return
	 */
	public MessageChannel getChannel() {
		return channel;
	}

	/**
	 * Get the command object itself.
	 * @return
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * Get all string arguments.
	 * @return
	 */
	public List<String> getArguments() {
		return arguments;
	}
	
}
