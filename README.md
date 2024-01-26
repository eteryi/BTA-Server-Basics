# Simply Basics

A Babric server-side mod made to support the classic serverside QoL functionalities from plugins such as Essentials.


## Commands

#### Player Commands
---
* /home <?name> -> If not provided a name, lists all the homes a user has, and if provided one teleports the user to the specified home;
* /sethome (name) -> Creates a new home on the player's current position;
* /delhome (name) -> Deletes a home;
* /tpa (player) -> Sends a TPA request to an online player;
* /tpaaccept <?player> -> If not provided a player name, accepts the most recent TPA request;
* /tpadeny <?player> -> Same thing as /tpaaccept, however denying the request;
* /warp <list/tp> <page/name> -> Lists or teleports to a specific warp, page must be a valid integer starting at 1;
#### Admin Commands
---
* /setwarp (name) -> Creates a new warp;
* /delwarp (name) -> Deletes a warp;

## Config
You can disable certain features using the config, that you can find on the /config/basics/ subdirectory on your server folder, the most important ones being:

| id | feature |
| --- | ------ |
| "modules:tpa" | Enables or disables the TPA part of the plugin |
| "modules:warp" | Enables or disables the Warp part of the plugin |
| "modules:home" | Enables or disables the Home part of the plugin |
| "modules:lua" | Experimental feature, not yet added |

To disable any of them just simply switch the "true" to "false", vice versa if you want to enable them.

#### Migration
You may also change the usernames on the /config/basics/players subdirectory if any player changes their username, so that their homes may be preserved.
