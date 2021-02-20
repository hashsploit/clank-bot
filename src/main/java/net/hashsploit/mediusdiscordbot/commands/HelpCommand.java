package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.util.EmbedUtil;

import net.dv8tion.jda.api.EmbedBuilder;

public class HelpCommand extends Command {

	public static final String COMMAND = "help";
	public static final String DESCRIPTION = "Show a list of commands";

	public HelpCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {		
		
		// can be used for showing last cache value
		// OffsetDateTime timestamp = OffsetDateTime.now();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(EmbedUtil.formatEmbedTitle(COMMAND));
		embed.setDescription(DESCRIPTION);
		embed.setThumbnail(MediusBot.getInstance().getConfig().getCommandSettings().getJSONObject(COMMAND).getString("icon"));
		embed.setColor(MediusBot.getInstance().getConfig().getDefaultColor());

		for (final Command c : MediusBot.getInstance().getCommands()) {
			if (!c.isOperatorCommand()) {
				embed.addField(String.format("`%s`", c.getName()), "- " + c.getDescription() ,false);
			}
		}

		event.reply(embed.build());
	}
}
