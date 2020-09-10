package net.hashsploit.mediusdiscordbot.commands;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.AuthorInfo;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.MessageEmbed.Footer;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.entities.MessageEmbed.Provider;
import net.dv8tion.jda.api.entities.MessageEmbed.Thumbnail;
import net.dv8tion.jda.api.entities.MessageEmbed.VideoInfo;
import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;

public class HelpCommand extends Command {

	private static final String COMMAND = "help";
	private static final String DESCRIPTION = "Show a list of commands";

	public HelpCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {
		
		StringBuilder commands = new StringBuilder();
		StringBuilder operatorCommands = new StringBuilder();

		for (Command c : MediusBot.getInstance().getCommands()) {
			if (c.isOperatorCommand() && MediusBot.getInstance().isOperator(event.getIssuer().getIdLong())) {
				operatorCommands.append("");
			} else {
				commands.append("");
			}
		}
		
		
		
		
		
		String url = null;
		String title = "Title";
		String description = "Description";
		EmbedType type = EmbedType.RICH;
		
		// can be used for showing last cache value
		OffsetDateTime timestamp = OffsetDateTime.now();
		
		int color = 0xFFAA00;
		Thumbnail thumbnail = null;
		Provider siteProvider = null;
		AuthorInfo author = null;
		VideoInfo videoInfo = null;
		
		
		Footer footer = new Footer("footer", null, null);
		ImageInfo image = null;
		List<Field> fields = new ArrayList<Field>();

		fields.add(new Field("name 1", "value 1", false));
		fields.add(new Field("name 2", "value 2", false));
		fields.add(new Field("name 3", "value 3", true));
		fields.add(new Field("name 4", "value 4", true));
		fields.add(new Field("name 5", "value 5", true));

		MessageEmbed embed = new MessageEmbed(url, title, description, type, timestamp, color, thumbnail, siteProvider, author, videoInfo, footer, image, fields);

		event.reply(embed);
	}

}
