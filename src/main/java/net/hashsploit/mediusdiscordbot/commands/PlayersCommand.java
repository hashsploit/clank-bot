package net.hashsploit.mediusdiscordbot.commands;

import net.hashsploit.mediusdiscordbot.Command;
import net.hashsploit.mediusdiscordbot.CommandEvent;
import net.hashsploit.mediusdiscordbot.MediusBot;
import net.hashsploit.mediusdiscordbot.MediusBotConfig;
import net.hashsploit.mediusdiscordbot.MediusInformationClient;
import net.hashsploit.mediusdiscordbot.util.TimedHashmap;
import net.hashsploit.mediusdiscordbot.util.PagingUtil;
import net.hashsploit.mediusdiscordbot.util.EmbedUtil;

import net.hashsploit.mediusdiscordbot.proto.MediusStructures.PlayersReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.TableData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;

import org.json.JSONObject;

import gui.ava.html.image.generator.HtmlImageGenerator;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Dimension;

public class PlayersCommand extends Command {
    public static final String COMMAND = "players";
    public static final String DESCRIPTION = "Returns a list of online players for the specified game.";
    private final TimedHashmap<String, TableData> grpcResponseCache;
    private final int ENTRIES_PER_PAGE = 30;
    
    public PlayersCommand(){
        super(COMMAND, DESCRIPTION, false);
        this.grpcResponseCache = new TimedHashmap<String, TableData>();
    }

    private TableData _getServerPlayers(MediusInformationClient client){
        TableData serverPlayers = this.grpcResponseCache.get(client.getName());
        if (serverPlayers == null){
            PlayersReq req = PlayersReq.newBuilder().build();
            serverPlayers = client.GetPlayers(req);
            this.grpcResponseCache.put(client.getName(), serverPlayers);
        }

        return serverPlayers;
    }

    private MessageEmbed _createReplyEmbed(int selectedPage, int maxPage){
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(EmbedUtil.formatEmbedTitle(COMMAND));
		embed.setDescription("** **");
        embed.setThumbnail(MediusBot.getInstance().getConfig().getCommandSettings().getJSONObject(COMMAND).getString("icon"));
        embed.setColor(MediusBot.getInstance().getConfig().getDefaultColor());
        embed.setImage("attachment://result.png");
        embed.setFooter(String.format("Page %s of %s", selectedPage + 1, maxPage + 1));

        return embed.build();
    }

    private void _generateTableImage(TableData playersInfo, int selectedPage){
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

        ArrayList<String> headers = new ArrayList<String>(playersInfo.getHeaders().getValuesList());
        JSONObject playersEntryMappings = MediusBot.getInstance().getConfig().getCommandSettings().getJSONObject("players").getJSONObject("statuses");
        int nameCol = 0;
        int statusCol = 1;

        Comparator<ArrayList<String>> customComparator = new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> a, ArrayList<String> b) {
                int aP = playersEntryMappings.getJSONObject(a.get(statusCol)).getInt("priority");
                int bP = playersEntryMappings.getJSONObject(b.get(statusCol)).getInt("priority");
    
                if (aP < bP)    return -1;
                if (aP == bP)   return a.get(nameCol).toLowerCase().compareTo(b.get(nameCol).toLowerCase());
                
                return 1;
            }   
        };

        // Collections.sort(dataRows, customComparator);
        ArrayList<ArrayList<String>> dataRows = PagingUtil.getTableDataRowsRanged(playersInfo, ENTRIES_PER_PAGE, selectedPage, customComparator);

        HashMap<String, String> colors = new HashMap<String, String>();
        for(String key : playersEntryMappings.keySet()){
            colors.put(key, playersEntryMappings.getJSONObject(key).getString("color"));
        }

        String htmlTableString = PagingUtil.createHTMLTable(PagingUtil.createHeadSection(null), PagingUtil.createTableSection(colors, statusCol, headers, dataRows));
        imageGenerator.loadHtml(htmlTableString);
        imageGenerator.saveAsImage("hello-world.png");
    }

    @Override
	public void onFire(CommandEvent event) {
        TableData playersInfo = _getServerPlayers(MediusBot.getInstance().getConfig().getMISClient());

        int maxPage = PagingUtil.calculateMaxPage(ENTRIES_PER_PAGE, playersInfo.getRowsList().size());
        int selectedPage;
        try{
            selectedPage = Integer.parseInt(event.getArguments().get(0)) - 1;
            if (selectedPage < 1 || selectedPage > maxPage){
                throw new Exception("Selected page is larger than max possible page, or less than 0");
            }
        } catch (Exception e){
            selectedPage = 0; 
        }

        _generateTableImage(playersInfo, selectedPage);
 
        MessageEmbed embed = _createReplyEmbed(selectedPage, maxPage);

        event.replyWithImage(embed, "hello-world.png", "result.png");
    }
}