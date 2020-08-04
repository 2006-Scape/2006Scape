echo This is meant to be run by the live server admin! You probably don\'t want to do this!
for i in {0..50}
do
    cp target/file_server-1.0-jar-with-dependencies.jar ./fserver.jar
    java -jar fserver.jar
done
