#!/bin/sh

INFILE=$1
OUTFILE=$2

make ARGS=$INFILE run | tail -n +2 > $OUTFILE