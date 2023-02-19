:: Runs The Server & FileServer
start cmd /k java -jar Server.jar -c ServerConfig.json -gui
:: Used To Wait 6 Seconds Before Running The Client
PING localhost -n 6 >NUL 
:: Starts The Client
start cmd /k java -jar Client.jar -s localhost