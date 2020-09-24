package net.hashsploit.mediusdiscordbot.commands;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.EmbedBuilder;
import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.MediusJQMServer;

public class StatusCommand extends Command {

	public static final String COMMAND = "status";
	public static final String DESCRIPTION = "Gives users information about the current server status in either a pm if parsed or in channel if called with prefix";

	public StatusCommand() {
		super(COMMAND, DESCRIPTION, false);
	}

	@Override
	public void onFire(CommandEvent event) {
		EmbedBuilder embed = new EmbedBuilder();

		List<String> args = event.getArguments();	//expect server names
		HashMap<String, MediusJQMServer> servers = MediusBot.getInstance().getConfig().getServers();
		HashMap<String, String> serverStatus = new HashMap<String, String>();
		HashMap<String, String> thumbnails = new HashMap<String, String>();

		serverStatus.put("uya", "Currently in development.");
		thumbnails.put("uya", "https://i.imgur.com/nc2eE2s.png");

		serverStatus.put("dl", "Currently in beta phase.");
		thumbnails.put("dl", "https://i.imgur.com/N0AhWfe.png");


		String thumbnail = "https://i.imgur.com/Yo0Efh2.png";	//default
		int color = MediusBot.getInstance().getConfig().getDefaultColor();
		if (args.size() == 1 && serverStatus.containsKey(args.get(0)) && servers.containsKey(args.get(0))){
			thumbnail = thumbnails.get(args.get(0));
			color = servers.get(args.get(0)).getColor();
			embed.addField('`' + args.get(0) + '`', serverStatus.get(args.get(0)), false);
		} else {
			for (Map.Entry<String, String> entry : serverStatus.entrySet()){
				embed.addField('`' + entry.getKey() + '`', serverStatus.get(entry.getKey()), false);
			}
		}
		embed.setTitle("Servers Status", "https://status.uyaonline.com/");
		embed.setDescription(DESCRIPTION);
		embed.setColor(color);
		embed.setAuthor​(MediusBot.NAME, null, MediusBot.ICON);
		embed.setThumbnail(thumbnail);
		embed.setFooter​(MediusBot.NAME);

		embed.addField("", "[UYAOnline](https://uyaonline.com/)", true);
		
		event.reply(embed.build());
	}
}
