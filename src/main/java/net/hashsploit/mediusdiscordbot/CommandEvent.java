package net.hashsploit.mediusdiscordbot;

import java.util.List;
import java.io.InputStream;

import org.json.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.io.File;
import java.io.FileInputStream;

public class CommandEvent {

	private final User issuer;
	private final MessageChannel channel;
	private final Command command;
	private final List<String> arguments;
	
	public CommandEvent(final User issuer, final MessageChannel channel, final Command command, final List<String> arguments) {
		this.issuer = issuer;
		this.channel = channel;
		this.command = command;
		this.arguments = arguments;
	}

	/**
	 * Send a reply to the same channel as the issuer.
	 * @param message
	 */
	public void reply(String message) {
		this.channel.sendMessage(message).complete();
	}
	
	/**
	 * Send a fancy reply to the same channel as the issuer.
	 * @param embed
	 */
	public void reply(MessageEmbed embed) {
		this.channel.sendMessage(embed).complete();
	}

	/**
	 * Send a fancy reply to the same channel as issuer, adding an image attachment.
	 * @param embed
	 * @param fileName
	 * @param uploadName
	 */
	public void replyWithImage(MessageEmbed embed, String fileName, String uploadName){
		InputStream file;
        try{
            file = new FileInputStream(new File(fileName));
        } catch(Exception e){ 
            System.out.println(e.getMessage());
            file = null;
		}
		
		this.channel.sendFile(file, uploadName).embed(embed).complete();
	}

	/**
	 * Send a reply to the issuer via their Direct Messages.
	 * @param embed
	 */
	public void replyDM(String message) {
		issuer.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
	}

	/**
	 * Send a fancy reply to the issuer via their Direct Messages.
	 * @param embed
	 */
	public void replyDM(MessageEmbed embed) {
		// FIXME: Some users with DM's disabled throws this exception. Find a better way to handle.
		try {
			issuer.openPrivateChannel().queue(channel -> channel.sendMessage(embed).queue());
		} catch (ErrorResponseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send a JSON Query Message.
	 * @param server
	 * @param payload
	 */
	public JSONObject sendJSONQueryMessage(String server, JSONObject payload) {
		// FIXME: missing
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
