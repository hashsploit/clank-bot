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

		// can be used for showing last cache value
		// OffsetDateTime timestamp = OffsetDateTime.now();

		for (final Command c : MediusBot.getInstance().getCommands()) {
			if (!c.isOperatorCommand()) {
				embed.addField('`' + c.getName() + '`', c.getDescription(), false);
			}
		}
		if (MediusBot.getInstance().isOperator(event.getIssuer().getIdLong())){
			for (final Command c : MediusBot.getInstance().getCommands()) {
				if (c.isOperatorCommand()) {
					embed.addField('`' + c.getName() + '`', c.getDescription(), false);
				}
			}
		}
		embed.setTitle(COMMAND);
		embed.setDescription(DESCRIPTION );
		embed.setThumbnail(MediusBot.getInstance().getConfig().getDefaultCommandIcons().get(COMMAND));
		embed.setColor(MediusBot.getInstance().getConfig().getDefaultColor());
		embed.setAuthor​(MediusBot.NAME, null, MediusBot.ICON);
		
		event.reply(embed.build());
	}

}
