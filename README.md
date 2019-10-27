# 2006rebotted - an open source, bottable remake server. Pull requests welcome!

## Discord Link: https://discord.gg/4zrA2Wy

## How to Play

### Client (non-bottable download): https://github.com/dginovker/2006rebotted/releases/
### Parabot client (recommended): https://www.parabot.org/community/
### Rune-Server project thread: [Project thread](https://www.rune-server.ee/runescape-development/rs2-server/projects/686444-2006rebotted-remake-server-will-allow-supply-creatable-bots.html)

# How to Develop for 2006rebotted

Step 1: Register a GitHub account

Step 2 (Windows users): Install Git Bash: https://git-scm.com/downloads

Step 3: Install IntelliJ Community Edition: https://www.jetbrains.com/idea/download/

Step 4: Install Java 8 from https://adoptopenjdk.net

Step 4: [Fork this repository](https://i.imgur.com/PoMTxZj.png)

Step 5: Open Git Bash and type `git clone <YOUR_FORK_URL>` ([Example](https://i.imgur.com/Hs1upNf.png)) 

Step 6: In Git Bash, type `cd 2006rebotted` ([Image](https://i.imgur.com/ePs3U2C.png))

Step 7: In Git Bash, type `git remote add upstream https://github.com/dginovker/2006rebotted` ([Image](https://i.imgur.com/4qYP9ve.png))

Step 8: In Git Bash, type `git checkout -b my-development` ([Image](https://i.imgur.com/4yHUsjc.png))

Step 9: Open IntelliJ and click "Open" on the Right-Hand panel ([Image](https://i.imgur.com/ApABBlm.png))

Step 10: Find where you "Cloned" the code to in Git Bash. If you can't find it, type `pwd` in Git Bash to help ([Image](https://i.imgur.com/YvVFtmW.png))

Step 11: Click this button if your code structure is not visible: https://i.imgur.com/bxXvoKv.png

Step 12: Click File -> Project Structure

Step 13: Set Project SDK to 1.8, Project Language Level to 8, & Project Compiler Output to any valid folder [Image](https://i.imgur.com/9PJDk0Q.png)

Step 14: Click "Modules" and make sure it looks like what I have highlighted. If it doesn't, join the Discord and I'll help you create one. ([Image](https://i.imgur.com/NBAnk0A.png))

Step 15: Click "Dependencies", then click the "Add" arrow and select "JARs or directories"([Image](https://i.imgur.com/tGI9QNI.png))

Step 16: Navigate to your 2006rebotted folder, open `2006Redone file_server` > `libs`. Holding "Shift" on your keyboard, select all the .jar files in the `libs` folder there, and hit OK. ([Image](https://i.imgur.com/Yv3SX1q.png))

Step 17: Hit OK in the project structure screen after all your JARs are imported: ([Image](https://i.imgur.com/Yv3SX1q.png))

Step 18: Navigate to the FileServer class (`2006Redone file_server/src/org/apollo/jagcached/FileServer.java`), Right Click -> Run. It will fail since we need to start it in the right directory: [Image](https://i.imgur.com/LFkr39U.png)

Step 19: To fix the directory issue, click FileServer and hit Edit Configurations at the top: [Image](https://i.imgur.com/lJdBPCs.png)

Step 20: Under Working Directory, add `2006Redone file_server` to the path (on Windows, you may need a \\ for paths instead of a /. Not sure): [Image](https://i.imgur.com/ANkbgBl.png)

Step 21: Navigate to the Server class (`2006Redone Server/src/redone/Server.java`), Right Click -> Run. It will fail since we need to start it in the right directory

Step 22: To fix the directory issue, click Server and hit Edit Configurations at the top

Step 23: Under Working Directory, add `2006Redone Server` to the path ([Image]("/home/dr_cookie/Projects/2006rebotted/2006Redone Client/src/Client.java"))

Step 24: Navigate to the Client class (`2006Redone Client/src/Client.java`), Right Click -> Run.


# Using Parabot with your local server:
- **1:** Download the latest `localhost_2006rebotted.jar` from [here](https://github.com/dginovker/2006rebotted/releases)
- **2:** Download the latest `Provider-version.jar` file from [here](http://v3.bdn.parabot.org/api/bot/download/default-provider?nightly=false)
- **3:** Create a file called `localhost.json` in `{user}\Documents\Parabot\servers`
- **4:** Put the following in the file
```json
{
    "name": "localhost",
    "author": "RedSparr0w",
    "version": 1.0,
    "client-class": "Game",
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

- `2006Redone_Client` and `2006Redone_Server` contain the compiled class code/outputs.
