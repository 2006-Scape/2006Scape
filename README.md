# 2006rebotted - an open source, actively developed emulation server. Pull requests welcome! ![Gameplay Image](https://i.imgur.com/WHnQz2W.png)

## Discord Link: https://discord.gg/hZ6VfWG

## How to Play

### Client (non-bottable download): https://github.com/dginovker/2006rebotted/releases/
### Parabot client (recommended): https://www.parabot.org/community/
### Rune-Server project thread: [Project thread](https://www.rune-server.ee/runescape-development/rs2-server/projects/686444-2006rebotted-remake-server-will-allow-supply-creatable-bots.html)

# Installation + Running (Developers)

1. Import Project in IntelliJ

2. Hit File > Project Settings > Set SDK to Java 8 (Download [Java 8 SDK](https://adoptopenjdk.net/?variant=openjdk8) if you don't have one already)

2. Navigate to `2006Redone file_server` > `src` > `main` > `java` > `org.apollo.jagcached`, right click FileServer and hit Run [Image](https://i.imgur.com/tsg9q1Z.png)

3. Navigate to `2006Redone Server` > `src` > `main` > `java` > `com.rebotted`, right click GameEngine and hit Run [Image](https://i.imgur.com/HHooeVu.png)

4. Navigate to `2006Redone Server` > `src` > `main` > `java`, right click Client and hit Run [Image](https://i.imgur.com/gSmqGLn.png)

*Advanced*

To compile any module from the command line, run `mvn clean install`

## How to get your code onto this project page

See this [forum post](https://rsrebotted.com/forums/viewthread.php?forum=5&id=78).

## Our 2.0 Client

We're working on a new client, rather than the one in the `Client/` directory. The reason for this is refactoring the client breaks Parabot, and the new client is much more refactored and in a better place to expand. You can find our new client repo [Here](https://github.com/dginovker/2006rebottedClient)!

## Using Parabot with your local server:
- **1:** Download the latest `localhost_2006rebotted.jar` from [here](https://github.com/dginovker/2006rebotted/releases) (or, if testing server changes, compile it yourself like [this](https://i.imgur.com/uDrF0gl.png))
- **2:** Download the latest `Provider-version.jar` file from [here](http://v3.bdn.parabot.org/api/bot/download/default-provider?nightly=false)
- **3:** Create a file called `localhost.json` in `{user}\Documents\Parabot\servers`
- **4:** Put the following in the file
```json
{
    "name": "localhost",
    "author": "RedSparr0w",
    "version": 1.0,
    "client-class": "LocalGame",
    "locations":{
        "provider": "pathToYourJar/Provider-1.21.5.jar",
        "server": "pathToYourJar/localhost_2006rebotted.jar",
        "hooks": "http://bdn.parabot.org/data/hooks/carmeuses/2006rebotted_hooks.xml"
    }
}
```
_(you will need to put the path to the jar files yourself)_
- 5: Run the parabot client with the following args:
```fix
java -jar Client-2.8.1.jar -login username password -loadlocal -v -clearcache
```
- **6:** ???
- **7:** PROFIT

### Server source layout

- `2006Redone Server` contains all the server code; mark `src` as the Sources directory
- `2006Redone Client` contains all the client code; likewise mark `src`
  - If more than 2 arguments are passed in (can be anything), the client runs locally
- `2006Redone file_server` contains the file server code that is *required* to be running before a client can connect to a server. It must be running locally before a client can connect. `src` is the Sources directory

- `CompiledServer/` contains the compiled class code and artifacts.
