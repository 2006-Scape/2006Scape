#!/bin/bash
echo NOTE: This program is meant to be run by the live server admin only!
echo You probably don\'t want to run this, even if you\'re developing locally!
for 
rm -rf redone/
cp -r ../CompiledServer/production/2006rebotted/redone .
java -Xmx15G -cp ".:./libs/*" redone.Server
