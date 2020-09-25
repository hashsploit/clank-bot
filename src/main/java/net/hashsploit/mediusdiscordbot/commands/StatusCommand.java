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

	private String getStatus(MediusJQMServer server){
		if (server.getStaticStatus() != null){
			return server.getStaticStatus();
		}

		return "Server pinging under development!";
	}
	
	@Override
	public void onFire(CommandEvent event) {
		EmbedBuilder embed = new EmbedBuilder();

		List<String> args = event.getArguments();	//expect server names
		HashMap<String, MediusJQMServer> servers = MediusBot.getInstance().getConfig().getServers();

		String thumbnail = MediusBot.getInstance().getConfig().getDefaultCommandIcons().get(COMMAND);
		int color = MediusBot.getInstance().getConfig().getDefaultColor();
		if (args.size() == 1 && servers.containsKey(args.get(0))){
			final MediusJQMServer server = servers.get(args.get(0));
			if (server.getCommandIcons().containsKey(COMMAND)){
				thumbnail = server.getCommandIcons().get(COMMAND);
			}
			color = server.getColor();
			embed.addField('`' + server.getName() + '`', getStatus(server), false);
		} else {
			embed.setDescription(DESCRIPTION);
			for (MediusJQMServer server : servers.values()){
				embed.addField('`' + server.getName() + '`', getStatus(server), false);
			}
		}
		embed.setTitle("Servers Status", "https://status.uyaonline.com/");
		embed.setThumbnail(thumbnail);
		embed.setColor(color);
		embed.setAuthor​(MediusBot.NAME, null, MediusBot.ICON);

		event.reply(embed.build());
	}
}
