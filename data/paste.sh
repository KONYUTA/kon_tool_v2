#!/bin/zsh
#$1 x,y,geiod
#$2 height
#$3 output
cut -d " " -f 1,2 $1 > tmp
cut -d "," -f 3 $2 > tmp2
paste -d " " tmp tmp2 > tmp3
cut -d " " -f 3 $1 > tmp
paste -d " " tmp3 tmp > $3
rm tmp
rm tmp2
