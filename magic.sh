#!/bin/bash
if [ $1 = "--compile" ] || [ $1 = "-c" ]; then
    find . -iname "*.java" -print0 | xargs -0 javac
elif [ $1 = "--run" ] || [ $1 = "-r" ]; then
    java $(find . -name '*.java' -type f -exec grep -q "void main" {} \; -print | sed -E "s/\.\/(.+)\.java/\1/")
elif [ $1 = "--comprun" ] || [ $1 = "-cr" ]; then
    find . -iname "*.java" -print0 | xargs -0 javac
    java $(find . -name '*.java' -type f -exec grep -q "void main" {} \; -print | sed -E "s/\.\/(.+)\.java/\1/")
elif [ $1 = "--clean" ] || [ $1 = "-l" ]; then
    rm *.class
elif [ $1 = "--help" ] || [ $1 = "-h" ]; then
    echo -e "--compile, -c      compiles all .java files to .class"
    echo -e "--run,     -r      runs java program with main()"
    echo -e "--comprun, -cr     compiles and then immediately runs"
    echo -e "--clean,   -l      removes all .class files"
    echo -e "--help,    -h      displays what you are seeing now"
else
    echo "Invalid argument(s), try using \"--help\""
fi