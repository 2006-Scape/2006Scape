# 2006Scape - an open source, actively developed emulation server. Pull requests welcome! ![Gameplay Image](https://i.imgur.com/WHnQz2W.png)

## Discord Link: https://discord.gg/hZ6VfWG

## How to Play

### Client/Launcher Download: https://2006Scape.org/
### Rune-Server project thread: [Project thread](https://www.rune-server.ee/runescape-development/rs2-server/projects/686444-2006rebotted-remake-server-will-allow-supply-creatable-bots.html)

# Installation + Running (Developers) with IntelliJ

1. Import Project in IntelliJ

2. Hit File > Project Settings > Set SDK to Java 8 (Download [Java 8 SDK](https://adoptopenjdk.net/?variant=openjdk8) if you don't have one already)

3. Navigate to `2006Scape Server` > `src` > `main` > `java` > `com.rs2`, right click GameEngine and hit Run [Image](https://i.imgur.com/HHooeVu.png)

   [(You Can Also Run The Server With The -c/-config Argument)](https://wiki.2006scape.org/books/getting-setup/page/server-arguments)
5. Navigate to `2006Scape Client` > `src` > `main` > `java`, right click Client and hit Run [Image](https://i.imgur.com/gSmqGLn.png)

# Installation + Running (Developers) with Terminal

1. Install *Java* (java) and *Maven* (mvn)

2. Run `setup.sh` (./setup.sh) or `mvn -B clean install`

3. Open another Terminal and `run_server.sh` (./run_server.sh)

4. Run `run_client.sh` (./run_client.sh)

## Using Parabot with your local server:
- **1:** Download the latest Parabot Client from [here](https://github.com/2006-Scape/Parabot/releases)
- **2:** Run the parabot client with the following arg:
```fix
java -jar Parabot.jar -local
```
- **3:** ???
- **4:** PROFIT

### Server source layout

- `2006Scape Server` contains all the server code; mark `src` as the Sources directory
- `2006Scape Client` contains all the client code; likewise mark `src`
  - If more than 2 arguments are passed in (can be anything), the client runs locally

## Building from command line

Run `mvn -B clean install`
