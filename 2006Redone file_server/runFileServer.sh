echo This is meant to be run by the live server admin! You probably don\'t want to do this!
rm -rf org/ 
cp -r ../CompiledServer/production/2006rebotted/org .
java -cp ".:./libs/*" org.apollo.jagcached.FileServer
