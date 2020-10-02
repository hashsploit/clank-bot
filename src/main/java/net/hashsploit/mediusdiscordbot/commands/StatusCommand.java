package net.hashsploit.mediusdiscordbot.commands;

import java.util.List;
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
import net.hashsploit.mediusdiscordbot.MediusJQMServer;
import net.hashsploit.mediusdiscordbot.util.TimedHashmap;
import net.hashsploit.mediusdiscordbot.util.HTTPRequestor;
import java.net.http.HttpResponse;

public class StatusCommand extends Command {

	public static final String COMMAND = "status";
	public static final String DESCRIPTION = "Gives users information about the current server status in either a pm if parsed or in channel if called with prefix.";

	public StatusCommand() {
		super(COMMAND, DESCRIPTION, false, new TimedHashmap<String,String>());
	}

	private CompletableFuture<String> getStatus(){
		CompletableFuture<String> res = new CompletableFuture<String>();
		if (!this.getJQMServerCache().containsKey("status")){
				final String body = new JSONObject().put("query", "status").toString();
				HTTPRequestor client = new HTTPRequestor("http://localhost:3000", body, new ArrayList<String>(), 30);
				client.makeRequest()
				.thenApply( (String serverRes) -> {
					this.getJQMServerCache().put("status", serverRes);
					res.complete(serverRes);
					return serverRes;
				});
		} else {
			res.complete(this.getJQMServerCache().get("status"));
		}
		return res;
	}
	
	private HashMap<String, Boolean> parseServerStatuses(JSONObject jsonServer){
		HashMap<String, Boolean> statuses = new HashMap<String, Boolean>();

		final Iterator<String> jsonRESTServerStatusesIter = jsonServer.keys();
		while(jsonRESTServerStatusesIter.hasNext()){
			final String MediusServerName = jsonRESTServerStatusesIter.next();
			statuses.put(MediusServerName, new Boolean(jsonServer.getBoolean(MediusServerName)));
		}

		return statuses;
	}

	@Override
	public void onFire(CommandEvent event) {
		EmbedBuilder embed = new EmbedBuilder();

		final String checkEmoji = "✅";
		final String xEmoji = "❌";
		List<String> args = event.getArguments();	//expect server names
		HashMap<String, MediusJQMServer> servers = MediusBot.getInstance().getConfig().getServers();
		// embed.setDescription(DESCRIPTION);
		getStatus()
		.thenAccept((String statusRes) -> {
			String thumbnail = MediusBot.getInstance().getConfig().getDefaultCommandIcons().get(COMMAND);
			int color = MediusBot.getInstance().getConfig().getDefaultColor();

			JSONObject jsonRESTServerStatusRes = new JSONObject(statusRes);

			// client queries for one particular server
			if (args.size() == 1 && servers.containsKey(args.get(0))){
				final MediusJQMServer server = servers.get(args.get(0));

				if (server.getCommandIcons().containsKey(COMMAND)){
					thumbnail = server.getCommandIcons().get(COMMAND);
				}

				color = server.getColor();

				//check first if we have a static status instead of a web res
				if (server.getStaticStatus() != null){
					embed.addField("**" + server.getName() + "**", server.getStaticStatus(), false);
				} else {
					HashMap<String, Boolean> statuses = parseServerStatuses(jsonRESTServerStatusRes.getJSONObject(server.getName()));

					for (String mediusServer : statuses.keySet()){
						embed.addField( (statuses.get(mediusServer).booleanValue() ? checkEmoji : xEmoji) + " **" + mediusServer + "**", "", false);
					}
				}
			} else {
				//client does an incorrect/ general query
				for (MediusJQMServer server : servers.values()){ 
					//check first if we have a static status instead of a web res
					if (server.getStaticStatus() != null){
						embed.addField("**" + server.getName() + "**", server.getStaticStatus(), false);
						continue;
					}

					HashMap<String, Boolean> statuses = parseServerStatuses(jsonRESTServerStatusRes.getJSONObject(server.getName()));
					boolean allHealthy = true;

					for (String mediusServer : statuses.keySet()){
						if (statuses.get(mediusServer).booleanValue() == false){
							allHealthy = false;
							break;
						}
					}

					embed.addField((allHealthy ? checkEmoji : xEmoji) + " **" + server.getName() + "**", "" , false);
				}

				embed.setDescription(DESCRIPTION);
				embed.addField("For detailed information, enter a specific server name.", "", false);
			}

			embed.setTitle("Servers Status", "https://status.uyaonline.com/");
			embed.setThumbnail(thumbnail);
			embed.setColor(color);
			event.reply(embed.build());
		});
	}
}