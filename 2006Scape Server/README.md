# 2006Scape - Game Server

This is the Game Server component of our emulated Runescape environment.
It's responsibility is to provide the instructions for the in-game world, its entities, and respond to player interactions.
Contained within is an implementation of the Runescape network protocol roughly around version 508.
When run, this java application will listen on TCP port 43594.

### Building the project
 - `git clone https://github.com/2006-Scape/2006Scape`
 - `cd 2006Scape/2006Scape\ Server/`
 - `mvn package`
 - `./runServer.sh`

