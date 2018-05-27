#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>  

typedef struct node_t node_t;
typedef struct node_t { 
	node_t* next; 
	char* word;
	int length;
} node_t;

node_t head = {.length=0, .next=NULL};

void add_word(char* w)
{
    node_t* new_node = (node_t*)malloc(sizeof(node_t*));
    new_node->word = w;
    head.length++;
    head.next = new_node;
    printf("Added: %s (count=%d)\n", new_node->word, head.length);  
}

void print_list()
{
    node_t* cur = &head;
    while(cur->next != NULL)
    {
        printf("item: %s", cur->word);
        cur = cur->next;
    }
}

int main(int argc, char* argv[]){
    char* buffer;
    size_t buffer_size, char_read;
    char* delim = " ";

    while((char_read = getline(&buffer, &buffer_size, stdin)) != -1) {
        char* word = strtok(buffer, delim);
        do {
            printf("Read in %s\n", word);
            add_word(word);
            word = strtok(NULL, delim);
        } while(word != NULL);
    }

    print_list();
}