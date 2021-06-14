#!/bin/sh

java -jar -Dfile.encoding="UTF-8" -Dspring.profiles.active=${PROFILE} ${packageFile}
