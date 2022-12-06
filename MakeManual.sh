#!/bin/zsh
src_path[1]="Main.java"
src_path+="kon/lib/coord/*.java"

space=" "
str="javadoc -d javadoc"

for i in $src_path;do
    str=$str$space$i
done

$str
