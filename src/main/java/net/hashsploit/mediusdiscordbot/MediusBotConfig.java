package net.hashsploit.mediusdiscordbot;

import java.util.HashSet;
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
	private HashSet<String> faqWords;
	private HashSet<MediusJQMServer> servers;
	
	public MediusBotConfig(JSONObject json) {
		this.json = json;
		
		this.logLevel = Level.valueOf(json.getString("log_level"));
		this.token = json.getString("token");
		this.prefix = json.getString("prefix");
		
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

		// Load Server
		this.servers = new HashSet<MediusJQMServer>();
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
			
			final MediusJQMServer server = new MediusJQMServer(name, description, address, port, token, color);
			servers.add(server);
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

	public HashSet<Long> getOperators() {
		return operators;
	}

	public void setOperators(HashSet<Long> operators) {
		this.operators = operators;
	}

	public HashSet<String> getFaqWords(){ return this.faqWords; }

	public void setFaqWords(HashSet<String> faqWords) { this.faqWords = faqWords; }

	public HashSet<MediusJQMServer> getServers() {
		return servers;
	}

	public void setServers(HashSet<MediusJQMServer> servers) {
		this.servers = servers;
	}
	
}
