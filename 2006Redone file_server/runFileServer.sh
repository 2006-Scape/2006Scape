rm -rf org/ 
cp -r ../CompiledServer/production/2006rebotted/org .
java -cp ".:./libs/*" org.apollo.jagcached.FileServer
