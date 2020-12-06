package net.hashsploit.mediusdiscordbot.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import org.json.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.MediusInformationClient;
import net.hashsploit.mediusdiscordbot.MediusJQMServer;
import net.hashsploit.mediusdiscordbot.util.TimedHashmap;
import net.hashsploit.mediusdiscordbot.util.HTTPRequestor;
import java.net.http.HttpResponse;

import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusListing;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusRes;

public class StatusCommand extends Command {

	public static final String COMMAND = "status";
	public static final String DESCRIPTION = "Gives users information about the current server status in either a pm if parsed or in channel if called with prefix.";
	private final TimedHashmap<String, StatusRes> grpcResponseCache;
	
	public StatusCommand() {
		super(COMMAND, DESCRIPTION, false, new TimedHashmap<String,String>());
		this.grpcResponseCache = new TimedHashmap<String, StatusRes>();
	}

	private StatusRes getServerStatus(MediusInformationClient client){
		StatusRes serverStatus = this.grpcResponseCache.get(client.getName());

		if (serverStatus == null){
			StatusReq req = StatusReq.newBuilder().setServerName(client.getName()).build();
			serverStatus = client.GetStatus(req);
			this.grpcResponseCache.put(client.getName(), serverStatus);
		}

		return serverStatus;
	}

	private HashMap<String, Boolean> parseServerStatuses(StatusRes serverStatuses){
		HashMap<String, Boolean> statuses = new HashMap<String, Boolean>();

		for(StatusListing status : serverStatuses.getServerStatusesList()){
			statuses.put(status.getServerName(), new Boolean(status.getServerActive()));
		}

		return statuses;
	}

	@Override
	public void onFire(CommandEvent event) {
		final String checkEmoji = "✅";
		final String xEmoji = "❌";
		EmbedBuilder embed = new EmbedBuilder();
		HashMap<String, MediusInformationClient> clients = MediusBot.getInstance().getConfig().getServers();
		ArrayList<String> targetServers = new ArrayList<String>();

		//parse args and figure out if server names are valid
		for (String arg : event.getArguments()){
			if (clients.containsKey(arg))	targetServers.add(arg);
		}

		int n = targetServers.size();
		
		// no server to find, try to help user
		if (n == 0){
			//shoot help status commanmd
			return;
		}

		// define thumbnail and colors
		int color = MediusBot.getInstance().getConfig().getDefaultColor();
		String thumbnail = MediusBot.getInstance().getConfig().getDefaultCommandIcons().get(COMMAND);
		if (n == 1){
			MediusInformationClient client = clients.get(targetServers.get(0));
			color = client.getColor();
			thumbnail = client.getCommandIcons().get(COMMAND);
		} 
		
		// populate embed with component statuses
		for (String targetServerName : targetServers){
			MediusInformationClient client = clients.get(targetServerName);

			if (client.getStaticStatus() != null){
				embed.addField(String.format("**%s**", client.getName()), client.getStaticStatus(), false);
				continue;
			}

			HashMap<String, Boolean> statuses = parseServerStatuses(getServerStatus(client));
			boolean allHealthy = true;
			for (String mediusComponent : statuses.keySet()){
				if (statuses.get(mediusComponent).booleanValue() == false){
					allHealthy = false;
					if (n == 1)	embed.addField( (statuses.get(mediusComponent).booleanValue() ? checkEmoji : xEmoji) + " **" + mediusComponent + "**", "", false);
				}
			}

			embed.addField(String.format("%s ** %s **", allHealthy ? checkEmoji : xEmoji, client.getName()), "", false);
		}

		embed.setTitle("Servers Status", "https://status.uyaonline.com/");
		embed.setThumbnail(thumbnail);
		embed.setColor(color);
		event.reply(embed.build());
	}
}