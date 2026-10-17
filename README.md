# 2006Scape Project - an open source, actively developed emulation server. Pull requests welcome! ![Gameplay Image](https://i.imgur.com/WHnQz2W.png)

## Discord Link: https://discord.gg/hZ6VfWG
## Resources

- **Client/Launcher Download:** [2006Scape.org](https://2006Scape.org/)
- **Rune-Server Project Thread:** [Project Thread](https://www.rune-server.ee/runescape-development/rs2-server/projects/686444-2006rebotted-remake-server-will-allow-supply-creatable-bots.html)

## Prerequisites

Before getting started, ensure you have the following installed:

- **Java 8:** [Download Java 8 SDK](https://adoptopenjdk.net/?variant=openjdk8)
- **IntelliJ IDEA:** Recommended for development.
- **Maven:** For command-line builds.
- **Docker (Optional):** To run the client locally using Docker Compose. [Get Docker](https://docs.docker.com/get-started/get-docker/)

## Installation & Running (Developers)

### Setting Up the Project in IntelliJ

1. **Import the Project:**
   - Open IntelliJ IDEA and import the project from the repository.

2. **Configure the SDK:**
   - Navigate to **File > Project Structure** (or **Project Settings**) and set the SDK to Java 8.

3. **Running the Server:**
   - Go to `2006Scape Server/src/main/java/com.rs2`.
   - Right-click the `GameEngine` class and select **Run**.
   - ![Run GameEngine](https://i.imgur.com/Y0uT8OW.png)
   - *Alternative:* Run the server with the `-c` or `-config` argument. See details on the [Server Arguments Wiki](https://wiki.2006scape.org/books/getting-setup/page/server-arguments).

4. **Running the Client:**
   - Navigate to `2006Scape Client/src/main/java`.
   - Right-click the `Client` class and select **Run**.
   - ![Run Client](https://i.imgur.com/QTMwNyw.png)

### Building from the Command Line

- **Compile a Module:**
  ```bash
  mvn clean install

- **Build the Entire Project:**
   ```bash
   mvn -B clean install
   
## Advanced Topics
### Using Parabot with Your Local Server
1. **Download the Latest Parabot Client:**

2. **Launch Parabot:**

   - Run the following command:
   ```bash
   java -jar Parabot.jar -local

3. **Follow On-Screen Instructions:**
   - Complete any additional steps as prompted by the Parabot client.
### Server Source Layout
- **2006Scape Server:** Contains all server code. Mark the src folder as the sources directory.
- **2006Scape Client:** Contains all client code. Similarly, mark the src folder as the sources directory.
   - Note: When more than two arguments (any values) are passed in, the client runs in local mode.
### Playing Locally with Docker Compose
1. **Start Docker Compose:**
   Run:
   ```bash
   docker compose up -d
   ```
   *(Make sure Docker is installed and configured.)*
2. **Run the Client:**
   Execute:
   ```bash
   java -jar '2006Scape Client/target/client-1.0-jar-with-dependencies.jar'
   ```
   *(Replace / with \ on Windows.)*
