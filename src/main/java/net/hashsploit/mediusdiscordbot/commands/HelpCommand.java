package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class HelpCommand extends Command {

	public static final String COMMAND = "help";
	public static final String DESCRIPTION = "Show a list of commands";

	public HelpCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {		
		EmbedBuilder embed = new EmbedBuilder();
		final String thumbnail = "https://i.imgur.com/uLdPtjc.png";

		// can be used for showing last cache value
		// OffsetDateTime timestamp = OffsetDateTime.now();

		embed.addBlankField(false);//line break after top meta info

		boolean issuerIsOperator = MediusBot.getInstance().isOperator(event.getIssuer().getIdLong());
		for (final Command c : MediusBot.getInstance().getCommands()) {
			if (!c.isOperatorCommand() || issuerIsOperator) {
				embed.addField('`' + c.getName() + '`', c.getDescription(), false);
			}
		}
		embed.setTitle(COMMAND);
		embed.setDescription(DESCRIPTION );
		embed.setColor(MediusBot.getInstance().getConfig().getDefaultColor());
		embed.setAuthor​(MediusBot.NAME, null, MediusBot.ICON);
		embed.setThumbnail(thumbnail);
		embed.setFooter​(MediusBot.NAME);

		embed.addBlankField(false);//line break before footer section
		embed.addField("", "[UYAOnline](https://uyaonline.com/)", true);
		
		event.reply(embed.build());
	}

}
