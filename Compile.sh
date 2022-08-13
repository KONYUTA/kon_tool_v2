#!/bin/zsh
src_path[1]="Main.java"
src_path+="kon/lib/coord/*.java"
src_path+="kon/lib/col/*.java"
src_path+="kon/lib/debug/*.java"

space=" "
str="javac "

for i in $src_path;do
    $str$i
done
