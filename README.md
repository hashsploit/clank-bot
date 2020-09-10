# Medius Discord Bot

A Discord bot built for community servers running Medius servers such as [Clank](https://github.com/hashsploit/clank).

Written for the [Ratchet & Clank Online Discord Community](https://discord.gg/mUQzqGu).

While you can use WebHooks to publish event data from a Medius stack to Discord,
Medius Discord Bot allows users of a Discord server to send queries to the Medius
stack directly in a standardized way.

## Features

- [x] Handle user chat commands.
- [ ] Send user chat messages.
- [ ] Send JSON query messages to a compatible MLS.
- [ ] JQM Players.
- [ ] JQM Games.
- [ ] JQM Rank (leaderboards).
- [ ] Caching.


## Configuration

**Base Configuration:**
| Name      | Type   | Description                                                                                                                                                           |
|-----------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| log_level | string | The log level to be used. Can be "TRACE, "DEBUG", "INFO", "WARNING", or "ERROR".                                                                                      |
| token     | string | The Discord App Token which can be gotten from https://discord.com/developers/applications/                                                                           |
| prefix    | string | Usually a character that this bot will listen to as a prefix for commands. This can be set to `null` to not listen to any prefix and instead listen to mentions only. |
| operators | array  | An array of integer values that represent Discord User Id's who have full control of the bot.                                                                         |
| servers   | array  | An array of Server Objects which have information regarding each Medius Server and component.                                                                         |

**Server Objects:**
| Name        | Type    | Description                                                     |
|-------------|---------|-----------------------------------------------------------------|
| name        | string  | A short string to represent this server, such as "dl" or "uya". |
| description | string  | A full string representation of the server name.                |
| address     | string  | The IP Address of the Medius Lobby Server JSON query API.       |
| port        | integer | The port of the Medius Lobby Server JSON query API.             |
| token       | string  | The pre-shared secret token used to access the JSON query API.  |
| color       | integer | The color to use for embedded messages.                         |


## Commands

Here is a list of common bot commands.
All optional parameters are defined with **[square brackets]**.
While all required parameters are defined with **<angle brackets>**.


#### Common user commands:
- `help [command]` - Show all commands, or show information about a specific command. Example: `!help players`.
- `servers` - Show a list of available Medius servers. Example: `!servers`.
- `status <server name>` - Show server status. Example: `!status dl`.
- `players <server name>` - Show all currently connected players on `server`. Example: `!players dl`.
- `games <server name>` - Show all game statuses. Example: `!games dl`.
- `rank <server name> [player]` - Show the current command issuer's username/nick's rank. If the username is not found or the issuer name is not a valid player in game the optional **[player]** parameter should be used. Example: `!rank dl Reconker`.


#### Operator commands:
- `shutdown` - Gracefully shutdown the bot.
- `clear <amount> [#channel] [@user]` - Clear chat messages of a specific amount in the current issuer's channel or of an optional channel of an optional user.
- `broadcast <server name> <message>` - Send an in-game broadcast to all players on a specific server (Medius System Message).
- `send <server name> <player> <msg>` - Send an in-game message to a specific player on a specific server (Medius System Message).
- `kick <server name> <player>` - Kick a player from the server.


## JSON Query Messages Protocol

The communication protocol between the Medius Lobby Server component and the bot use JSON query messages.
All response messages may include an optional `additional_message` field of type `string` which is appended to the chat message.


### Broadcast

Show a list of players and basic player information.

###### Request
```json
{
	"query": "broadcast",
	"message": "Server Restarting. We'll be right back!"
}
```

###### Response
```json
{
	"success": true
}
```


### Players

Show a list of players and basic player information.

###### Request
```json
{
	"query": "players"
}
```

###### Response

- `total` is the total numbers of users registered.

```json
{
	"total": 10,
	"players": [
		{
			"player_id": 1,
			"player_name": "hashsploit",
			"in_game": false
		},
		{
			"player_id": 2,
			"player_name": "Dnawrkshp",
			"in_game": true
		},
		{
			"player_id": 3,
			"player_name": "Badger41",
			"in_game": true
		}
	],
}
```

### Games

Show a list of active games staged or in-progress.

###### Request
```json
{
	"query": "games"
}
```

###### Response

- `extra_info` is an object that takes Key-Value pair string values for additional game information, this allows this bot to be used for other Medius titles as well.

```json
{
	"staging": [
		{
			"game_id": 302,
			"game_name": "Dnawrkshp's",
			"host": {
				"player_id": 2,
				"player_name": "Dnawrkshp"
			},
			"extra_info": {
				"time": "15:00 Minutes",
				"weapons": "Fusion Rifle, B6, Magma Cannon",
				"type": "King of The Hill",
				"map": "Maraxus Prison"
			},
		}
	],
	"in_progress": [
		{
			"game_id": 301,
			"game_name": "hashsploit's",
			"host": {
				"player_id": 1,
				"player_name": "hashsploit"
			},
			"extra_info": {
				"time": "Infinate",
				"weapons": "All",
				"type": "CTF",
				"map": "Sarathros"
			},
			"players": [
				{
					"player_id": 1,
					"player_name": "hashsploit",
					"host": true
				},
				{
					"player_id": 45,
					"player_name": "Reconker",
					"host": false
				},
			],
		}
	],
}
```





