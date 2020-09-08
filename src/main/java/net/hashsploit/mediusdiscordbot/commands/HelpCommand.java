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

public class HelpCommand extends Command {
	
	private static final String COMMAND = "help";
	private static final String DESCRIPTION = "Show a list of commands";
	
	public HelpCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {
		
		String url = null;
		String title = "Title";
		String description = "Description";
		EmbedType type = EmbedType.RICH;
		OffsetDateTime timestamp = OffsetDateTime.now();
        int color = 0x880000;
        Thumbnail thumbnail = null;
        Provider siteProvider = null;
        AuthorInfo author = new AuthorInfo("authorName", null, null, null);
        VideoInfo videoInfo = null;
        Footer footer = new Footer("footer", null, null);
        ImageInfo image = null;
        List<Field> fields = new ArrayList<Field>();
		
		MessageEmbed embed = new MessageEmbed(url, title, description, type, timestamp, color, thumbnail, siteProvider, author, videoInfo, footer, image, fields);
		
		
		event.reply(embed);
	}
	
}
