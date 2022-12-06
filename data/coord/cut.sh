#!/bin/zsh
find height|while read line;do
    cut -d , -f 1,2 $line > xy/$line
done
