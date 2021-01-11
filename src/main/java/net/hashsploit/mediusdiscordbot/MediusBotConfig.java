package net.hashsploit.mediusdiscordbot;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import net.hashsploit.mediusdiscordbot.MediusInformationClient;

public class MediusBotConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(MediusBotConfig.class);
	
	private JSONObject json;
	private Level logLevel;
	private String discordToken;
	private String prefix;
	private ArrayList<Long> operators;
	private int defaultColor;
	private JSONObject commandSettings;
	private MediusInformationClient MISClient;

	private HashSet<String> faqWords; 
	
	public MediusBotConfig(JSONObject json) {
		this.json = json;

		this.logLevel = Level.valueOf(json.getString("log_level"));
		this.discordToken = json.getString("discord_token");
		this.prefix = json.getString("prefix");
		this.defaultColor = json.getInt("default_color");

		// Load operators
		this.operators = new ArrayList<Long>();
		final JSONArray jsonOperatorArray = json.getJSONArray("operators");
		final Iterator<Object> jsonOperatorIterator = jsonOperatorArray.iterator();
		while (jsonOperatorIterator.hasNext()) {
			final long id = Long.parseLong(jsonOperatorIterator.next().toString());
			operators.add(id);
		}

		// Load command settings
		this.commandSettings = json.getJSONObject("command_settings");

		// Load medius information client settings
		final String name = json.getString("name");
		final String description = json.getString("description");
		final String address = json.getString("address");
		final int port = json.getInt("port");
		final String MISToken = json.getString("mis_token");

		this.MISClient = new MediusInformationClient(name, description, address, port, MISToken);

		// Load faqWords
		this.faqWords = new HashSet<String>();
		final JSONArray jsonFaqWordsArray = json.getJSONArray("faq_words");
		final Iterator<Object> jsonFaqWordsIterator = jsonFaqWordsArray.iterator();
		while (jsonFaqWordsIterator.hasNext()) {
			final String faqWord = jsonFaqWordsIterator.next().toString();
			faqWords.add(faqWord);
		}

		logger.info("Configuration loaded.");
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	public String getDiscordToken() {
		return discordToken;
	}

	public void setDiscordToken(String discordToken) {
		this.discordToken = discordToken;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getDefaultColor(){
		return defaultColor;
	}

	public void setDefaultColor(int defaultColor){
		this.defaultColor = defaultColor;
	}

	public ArrayList<Long> getOperators() {
		return operators;
	}

	public void setOperators(ArrayList<Long> operators) {
		this.operators = operators;
	}


	public HashSet<String> getFaqWords(){ 
		return this.faqWords; 
	}

	public void setFaqWords(HashSet<String> faqWords) { 
		this.faqWords = faqWords; 
	}

	public MediusInformationClient getMISClient() {
		return this.MISClient;
	}

	public void setMISClient(MediusInformationClient MISClient) {
		this.MISClient = MISClient;
	}

	public JSONObject getCommandSettings(){
		return commandSettings;
	}
	
}
