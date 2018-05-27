# Very simple Makefile that creates two exectuables (one which uses provided debug.h file)
# To run:
#      $ make <filename without .c extension>
#      $ make example

CFLAGS =-Wall -g -std=c99
LIBS =-I.

% : %.c 
	gcc $< -o $@  $(CFLAGS) $(LIBS)

