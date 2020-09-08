package net.hashsploit.mediusdiscordbot;

public class MediusJQMServer {
	
	private final String name;
	private final String description;
	private final String address;
	private final int port;
	private final String token;
	private final int color;
	
	private int cachedTotalPlayers;
	private int cachedOnlinePlayers;
	
	public MediusJQMServer(final String name, final String description, final String address, final int port, final String token, final int color) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.port = port;
		this.token = token;
		this.color = color;
	}

	/**
	 * Get the total registered players.
	 * @return
	 */
	public int getTotalPlayers() {
		
		// TODO: send JQM
		
		
		// TODO: add cache
		return cachedTotalPlayers;
	}

	/**
	 * Get the number of players currently online.
	 * @return
	 */
	public int getOnlinePlayers() {
		
		// TODO: send JQM
		
		
		// TODO: add cache
		return cachedOnlinePlayers;
	}

	/**
	 * Get the Medius JQM server name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Medius JQM server description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the Medius JQM server address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Get the Medius JQM server port.
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get the Medius JQM server token.
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Get the server color.
	 * @return
	 */
	public int getColor() {
		return color;
	}
	
}
