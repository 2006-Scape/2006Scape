echo NOTE: This program is meant to be run by the live server admin only!
echo You probably don\'t want to run this, even if you\'re developing locally!
for i in {0..50}
do
    cp target/server-1.0-jar-with-dependencies.jar ./server.jar
    java -jar server.jar
done
