#!/bin/bash
if [ $1 = "--compile" ] || [ $1 = "-c" ]; then
    find ./ -iname "*.java" -print0 | xargs -0 javac
elif [ $1 = "--clean" ] || [ $1 = "-l" ]; then
    rm *.class
elif [ $1 = "--help" ] || [ $1 = "-h" ]; then
    echo -e "--compile, -c      compiles all .java files to .class"
    echo -e "--run,     -r      runs specified java program"
    echo -e "--clean,   -l      removes all .class files"
    echo -e "--help,    -h      displays what you are seeing now"
elif ([ $1 = "--run" ] || [ $1 = "-r" ]) && [ -n $2 ]; then
    java $2
else
    echo "Invalid argument(s), try using \"--help\""
fi