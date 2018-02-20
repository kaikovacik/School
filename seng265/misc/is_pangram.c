#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int is_pangram(char *sentence)
{
	int flag_letter[26];
	for(int i = 0; i < 26; i++)
		flag_letter[i] = 0;

	int n = strlen(sentence);
	for(int i = 0; i < n; i++)
	{
		char c = tolower(*sentence);
		if((int) c >= 97 && (int) c <= 122)
		{
			flag_letter[(int) c-97] = 1;
		}
		sentence++;
	}

	for(int i = 0; i < 26; i++)
	{
		if(flag_letter[i] == 0)
			return 0;
	}

	return 1;
}

int main(int argc, char const *argv[])
{
	char sentence[100];

	if(argc < 2 || strlen(argv[1]) > sizeof(sentence))
	{
		printf("Something went wrong!"); 
		return -1;
	}

	strcpy(sentence, argv[1]);
	printf("\"%s\", %s pangram.\n", sentence, (is_pangram(sentence))? "is a" : "is not a");
	return 0;
}