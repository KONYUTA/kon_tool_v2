#!/bin/zsh
src_path[1]="./kon/lib/coord"
src_path+="./kon/lib/col"
src_path+="./kon/lib/debug"

space=" "
str="javac"

#mainのコンパイル
set -x
javac ./Main.java

#main以外のコンパイル
for dir in $src_path;do
    for f in $(find $dir -type f | grep .java);do
        javac $f
    done
done
set +x
echo "終わりましたよ🥺"
