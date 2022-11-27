#!/bin/zsh

cut -d , -f 1,2 $1 > tmp 
python3 calc_height.py tmp > height
paste -d , tmp height > result.in
rm tmp
rm height
head result.in
echo ".\n.\n.\n"
wc -l result.in
