package net.hashsploit.mediusdiscordbot.commands;

import java.util.HashMap;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.MediusInformationClient;

import net.hashsploit.mediusdiscordbot.util.TimedHashmap;
import net.hashsploit.mediusdiscordbot.util.EmbedUtil;

import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusListing;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusRes;

import org.json.JSONObject;

public class StatusCommand extends Command {

	public static final String COMMAND = "status";
	public static final String DESCRIPTION = "Gives users information about the current server status in either a pm if parsed or in channel if called with prefix.";
	private final TimedHashmap<String, StatusRes> grpcResponseCache;
	
	public StatusCommand() {
		super(COMMAND, DESCRIPTION, false);
		this.grpcResponseCache = new TimedHashmap<String, StatusRes>();
	}

	private StatusRes _getServerStatus(MediusInformationClient client){
		StatusRes serverStatus = this.grpcResponseCache.get(client.getName());
		if (serverStatus == null){
			StatusReq req = StatusReq.newBuilder().build();
			serverStatus = client.GetStatus(req);
			this.grpcResponseCache.put(client.getName(), serverStatus);
		}

		return serverStatus;
	}

	private MessageEmbed _createReplyEmbed(StatusRes serverStatuses){
		final String CHECK_EMOJI = "✅";
		final String X_EMOJI = "❌";
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(EmbedUtil.formatEmbedTitle(COMMAND));
		embed.setDescription("** **");
        embed.setThumbnail(MediusBot.getInstance().getConfig().getCommandSettings().getJSONObject(COMMAND).getString("icon"));
		embed.setColor(MediusBot.getInstance().getConfig().getDefaultColor());

		for(StatusListing status : serverStatuses.getServerStatusesList()){
			embed.addField(String.format("%s **%s**", status.getServerActive() ? CHECK_EMOJI : X_EMOJI, status.getServerName()), "", false);
		}

		return embed.build();
	}

	@Override
	public void onFire(CommandEvent event) {
		MediusInformationClient MISClient = MediusBot.getInstance().getConfig().getMISClient();

		MessageEmbed embed = _createReplyEmbed(_getServerStatus(MISClient));

		event.reply(embed);
	}
}