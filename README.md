# 2006rebotted - an open source, bottable remake server. Pull requests welcome!

## Discord Link: https://discord.gg/4zrA2Wy

## Getting started

### Client (non-bottable download): https://github.com/dginovker/2006rebotted/releases/
### Parabot client (recommended): https://www.parabot.org/community/
### Rune-Server project thread: [Project thread](https://www.rune-server.ee/runescape-development/rs2-server/projects/686444-2006rebotted-remake-server-will-allow-supply-creatable-bots.html)

## Contributing

### Server source layout

- `2006Redone Server` contains all the server code; mark `src` as the Sources directory
- `2006Redone Client` contains all the client code; likewise mark `src`
  - If more than 2 arguments are passed in (can be anything), the client runs locally
- `2006Redone file_server` contains the file server code that is *required* to be running before a client can connect to a server. It must be running locally before a client can connect. `src` is the Sources directory

- `2006Redone_Client` and `2006Redone_Server` contain the compiled class code/outputs.

### ScriptFactory source

ScriptFactory's source code can be found in my Parabot scripting repository [here](https://github.com/dginovker/Parabot)

### Writing your own custom bot:

ScriptFactory is a script available on the [Parabot BDN](http://bdn.parabot.org/scripts/) that can help you create your own scripts. To use it, first register to Parabot, navigate to the BDN, and find ScriptFactory.

ScriptFactory example scripts: https://www.parabot.org/community/topic/18021-script-factory-13-create-your-own-scripts/

## Contributing Support (Server, Client, anything.)

Create an issue, message me on [Reddit](https://www.reddit.com/user/OsrsNeedsF2P/), [Rune-Server](https://www.rune-server.ee/members/before/), or Discord (Red Bracket#8151) to get in touch :)
