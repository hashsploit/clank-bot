package net.hashsploit.mediusdiscordbot;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class MediusBotConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(MediusBotConfig.class);
	
	private JSONObject json;
	private Level logLevel;
	private String token;
	private String prefix;
	private HashSet<Long> operators;
	private int defaultColor;
	private HashMap<String, MediusJQMServer> servers;
	private HashMap<String, String> defaultCommandIcons;
	private HashSet<String> faqWords;
	
	public MediusBotConfig(JSONObject json) {
		this.json = json;
		
		this.logLevel = Level.valueOf(json.getString("log_level"));
		this.token = json.getString("token");
		this.prefix = json.getString("prefix");
		this.defaultColor = json.getInt("default_color");

		// Load operators
		this.operators = new HashSet<Long>();
		final JSONArray jsonOperatorArray = json.getJSONArray("operators");
		final Iterator<Object> jsonOperatorIterator = jsonOperatorArray.iterator();
		while (jsonOperatorIterator.hasNext()) {
			final long id = Long.parseLong(jsonOperatorIterator.next().toString());
			operators.add(id);
		}

		// Load faqWords
		this.faqWords = new HashSet<String>();
		final JSONArray jsonFaqWordsArray = json.getJSONArray("faq_words");
		final Iterator<Object> jsonFaqWordsIterator = jsonFaqWordsArray.iterator();
		while (jsonFaqWordsIterator.hasNext()) {
			final String faqWord = jsonFaqWordsIterator.next().toString();
			faqWords.add(faqWord);
		}

		//Load default command icons
		this.defaultCommandIcons = new HashMap<String, String>();
		final JSONObject jsonDefaultCommandIcons = json.getJSONObject("default_command_icons");
		final Iterator<String> jsonDefaultCommandIconsNameIter = jsonDefaultCommandIcons.keys();
			while (jsonDefaultCommandIconsNameIter.hasNext()){
				final String commandName = jsonDefaultCommandIconsNameIter.next();
				this.defaultCommandIcons.put(commandName, jsonDefaultCommandIcons.getString(commandName));
			}
		// Load Server
		this.servers = new HashMap<String, MediusJQMServer>();
		final JSONArray jsonServerArray = json.getJSONArray("servers");
		final Iterator<Object> jsonServerIterator = jsonServerArray.iterator();
		while (jsonServerIterator.hasNext()) {
			final JSONObject jsonServer = new JSONObject(jsonServerIterator.next().toString());
			final String name = jsonServer.getString("name");
			final String description = jsonServer.getString("description");
			final String address = jsonServer.getString("address");
			final int port = jsonServer.getInt("port");
			final String token = jsonServer.getString("token");
			final int color = jsonServer.getInt("color");
			final String staticStatus = jsonServer.has("static_status") ? jsonServer.getString("static_status") : null;

			//Load specified command icons for server
			final HashMap<String, String> commandIcons = new HashMap<String,String>();
			final JSONObject jsonCmdIcons = jsonServer.getJSONObject("command_icons");
			final Iterator<String> jsonCmdIconsNamesIter = jsonCmdIcons.keys();
			while (jsonCmdIconsNamesIter.hasNext()){
				final String commandName = jsonCmdIconsNamesIter.next();
				commandIcons.put(commandName, jsonCmdIcons.getString(commandName));
			}

			final MediusJQMServer server = new MediusJQMServer(name, description, address, port, token, color, staticStatus, commandIcons);
			servers.put(name, server);
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public HashSet<Long> getOperators() {
		return operators;
	}

	public void setOperators(HashSet<Long> operators) {
		this.operators = operators;
	}

	public HashMap<String,String> getDefaultCommandIcons(){
		return defaultCommandIcons;
	}
	
	public void setDefaultCommandIcons(HashMap<String, String> defaultCommandIcons){
		this.defaultCommandIcons = defaultCommandIcons;
	}

	public HashSet<String> getFaqWords(){ 
		return this.faqWords; 
	}

	public void setFaqWords(HashSet<String> faqWords) { this.faqWords = faqWords; }

	public HashMap<String, MediusJQMServer> getServers() {
		return servers;
	}

	public void setServers(HashMap<String, MediusJQMServer> servers) {
		this.servers = servers;
	}
	
}
