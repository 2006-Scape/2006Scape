rm -rf redone/
cp -r ../CompiledServer/production/2006rebotted/redone .
java -cp ".:./libs/*" redone.Server
