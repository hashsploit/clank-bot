package net.hashsploit.mediusdiscordbot.commands;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;

public class StatusCommand extends Command {

	public static final String COMMAND = "status";
	public static final String DESCRIPTION = "Gives users information about the current server status in either a pm if parsed or in channel if called with prefix";

	public StatusCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {
		List<String> args = event.getArguments();

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Servers Status", "https://status.uyaonline.com/");
		eb.addField("", "The servers are not public yet!", false);
		eb.addBlankField(false);
		eb.addField("DL Online Status", "In closed-beta right now and restricted to users with the #dl-beta-testing role only. Managed by @Dnawrkshp#8266 .", true);
		eb.addField("UYA Online Status", "In development and managed by @hashsploit#0001 .", true);

		if (args.get(0).equals("parsed")) {
			event.replyDM(eb.build());
		} else {
			event.reply(eb.build());
		}

	}

}
