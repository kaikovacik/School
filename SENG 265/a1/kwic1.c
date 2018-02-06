#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_WORD_LEN 		128
#define MAX_WORDS		32
#define MAX_EXCL_WORDS		32
#define MAX_KEYWORDS 		256
#define MAX_PHRASES 		128


// Finds information from file
void readFile(char filename[], char exclusion[][MAX_WORD_LEN], char phrases[][MAX_WORD_LEN], int *num_phrases, int *num_exclusions);
// Copies phrases as array of words
void toArrayOfWords(char phrase_words[][MAX_WORD_LEN], char phrases[]);
// Finds keywords from array of words and exclusion and copies to array of keywords
void toArrayOfKeyWords(char keywords[][MAX_WORD_LEN], char phrase_words[][MAX_WORD_LEN], char exclusion[][MAX_WORD_LEN]);
// Sorts inputted array using qsort
void insertionSort(char string_array[][MAX_WORD_LEN]);
// Outputs as specified
void output(char keywords[][MAX_WORD_LEN], char phrase_words[][MAX_WORD_LEN][MAX_WORD_LEN], int num_phrases);
// Read from standard input
void getInput(char exclusion[][MAX_WORD_LEN], char phrases[][MAX_WORD_LEN], int *num_exclusions, int *num_phrases);


int main(int argc, char const *argv[])
{
	// INPUT SPECIFIC MEMORY----------------------------------------------------
	char exclusion[MAX_EXCL_WORDS][MAX_WORD_LEN]; /*inputted exclusion words*/
	char phrases[MAX_PHRASES][MAX_WORD_LEN]; /*inputted phrases*/
	int num_phrases = 0;
	int num_exclusions = 0;
	// -------------------------------------------------------------------------

	// READ FROM STDIN----------------------------------------------------------
 	getInput(exclusion, phrases, &num_exclusions, &num_phrases);
  	// -------------------------------------------------------------------------

 	// COMPUTATIONAL MEMORY-----------------------------------------------------
	char phrase_words[num_phrases][MAX_WORD_LEN][MAX_WORD_LEN];
	char keywords[MAX_KEYWORDS][MAX_WORD_LEN];
	strcpy(keywords[0], ""); /*ensures clean memory overwrite*/
	// -------------------------------------------------------------------------

  	// EXECUTE------------------------------------------------------------------
	// Repeat for each phrase
	for(int i = 0; i < num_phrases; i++)
	{
		// Tokenize current phrase
		toArrayOfWords(phrase_words[i], phrases[i]);
		// Find the key words in current phrase
		toArrayOfKeyWords(keywords, phrase_words[i], exclusion);
	}
	// Sort the array of keywords alphabetically
	insertionSort(keywords);
	// Output to console all phrases containing the alphabetical keywords
	output(keywords, phrase_words, num_phrases);
	// -------------------------------------------------------------------------

	return 0;
}


void getInput(char exclusion[][MAX_WORD_LEN], char phrases[][MAX_WORD_LEN], int *num_exclusions, int *num_phrases)
{
	char line[MAX_WORD_LEN];

 	int block = 0;
 	while(fgets(line, sizeof(line), stdin))
 	{
 		if(line[0] != '\n') /*ignores blank lines*/
 		{
	 		if(strncmp(line, "::", 2) == 0)
	 			block++;
	 		else if(block == 1)
	 		{
	 			strcpy(exclusion[*num_exclusions], strtok(line, "\n"));
	 			strcpy(exclusion[*num_exclusions+1], "");
	 			(*num_exclusions)++;
	 		}
	 		else if(block == 2)
	 		{
	 			strcpy(phrases[*num_phrases], strtok(line, "\n"));
	 			strcpy(phrases[*num_phrases+1], "");
	 			(*num_phrases)++;
	 		}
	 	}
 	}
}


void toArrayOfWords(char phrase_words[][MAX_WORD_LEN], char phrase[])
{
	strcpy(phrase_words[0], strtok(phrase, " "));

	// Tokenize the words in the phrase 
	char *temp;
	for(int i = 1; (temp = strtok(NULL, " ")) != NULL ; i++)
		strncpy(phrase_words[i], temp, strlen(temp));
}


void toArrayOfKeyWords(char keywords[][MAX_WORD_LEN], char phrase_words[][MAX_WORD_LEN], char exclusion[][MAX_WORD_LEN])
{
	// Compare with exclusion words and write to keywords array
	int is_key;
	for(int i = 0; strlen(phrase_words[i]) != 0; i++)
	{
		is_key = 1;

		// Check exclusion
		for(int j = 0; strlen(exclusion[j]) != 0; j++)
		{
			if(strcmp(phrase_words[i], exclusion[j]) == 0)
			{
				is_key = 0;
				break;
			}
		}

		if(is_key)
		{
			char keyword[MAX_WORD_LEN];
			strcpy(keyword, phrase_words[i]);

			// Append to array if keyword not already present
			int array_end = 0;
			while(strlen(keywords[array_end]) != 0)
			{
				if(strcmp(keywords[array_end], keyword) == 0)
				{
					is_key = 0;
					break;
				}
				array_end++;
			}	
			if(is_key)
			{
				strcpy(keywords[array_end], keyword);
				strcpy(keywords[array_end+1], "");
			}
		}
	}
}


void insertionSort(char array[][MAX_WORD_LEN])
{
	for(int i = 1; strlen(array[i]) != 0; i++)
	{
		for(int j = i; j > 0; j--)
		{
			if(strcmp(array[j], array[j-1]) < 0)
			{
				char temp[MAX_WORD_LEN];
				// Swap
				strcpy(temp, array[j-1]);
				strcpy(array[j-1], array[j]);
				strcpy(array[j], temp);
			}
		}
	}
}

// Helper function for output
void printIndex(char phrase_words[][MAX_WORD_LEN], char keyword[])
{
	for(int i = 0; strlen(phrase_words[i]) != 0; i++)
	{
		if(strcmp(phrase_words[i], keyword) == 0)
		{
			char temp[strlen(keyword)];

			// Convert keyword to uppercase
			for(int j = 0; j < strlen(phrase_words[i]); j++)
				temp[j] = toupper(phrase_words[i][j]);
			temp[strlen(phrase_words[i])] = '\0'; /*ensures null terminator*/

			(i == 0)? printf("%s", temp): printf(" %s", temp); /*print keyword*/
		}
		else (i == 0)? printf("%s", phrase_words[i]): printf(" %s", phrase_words[i]); /*print all othr words*/
	}
}


void output(char keywords[][MAX_WORD_LEN], char phrase_words[][MAX_WORD_LEN][MAX_WORD_LEN], int n)
{
	// Find keywords in phrases in alphabetical order and print
	for(int i = 0; strlen(keywords[i]) != 0; i++)
	{
		for(int j = 0; j < n; j++)
		{
			for(int k = 0; strlen(phrase_words[j][k]) != 0; k++)
			{
				if(strcmp(phrase_words[j][k], keywords[i]) == 0)
				{
					printIndex(phrase_words[j], keywords[i]);
					printf("\n");
				}
			}
		}
	}
}
