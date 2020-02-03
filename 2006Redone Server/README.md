# 2006rebotted - Game Server

This is the Game Server component of our emulated Runescape environment.
It's responsibility is to provide the instructions for the in-game world, its entities, and respond to player interactions.
Contained within is an implementation of the Runescape network protocol roughly around version 508.
When run, this java application will listen on TCP port 43594.

### Building the project
 - `git clone https://github.com/dginovker/2006rebotted`
 - `cd 2006rebotted/2006Redone\ Server/`
 - `./gradlew build`
 - `java -jar ./build/libs/2006rebotted.jar`
 - You will also have to place a copy of the `data` folder in the same location as the jar.


