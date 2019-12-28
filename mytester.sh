#!/bin/sh
i=1
#make clean
#make

INFILE="$1"

if [ -z "$INFILE" ]
then
  for filename in tests/tema3/*.cl
  do
    echo $filename
    make ARGS=$filename run | tail -n +2 > myouts/"$i.s"
    spim -exception_file trap.handler.nogc -file myouts/"$i.s" > myouts/"$i.out"
    echo $i
    i=$((i+1))
  done
else
  echo $INFILE
  make ARGS=$INFILE run | tail -n +2 > myouts/"test.s"
  spim -exception_file trap.handler.nogc -lstack 10000000 -file myouts/"test.s"
fi
