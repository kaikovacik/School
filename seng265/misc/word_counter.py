# def totalWords(s):
# 	return len(s.split())

# def wordsOfLength(s, n):
# 	count = 0;
# 	for i in s.split() if len(i) is n:
# 			count += 1
# 	return count

# s = input()
# print(totalWords(s))
# n = int(input())
# print(wordsOfLength(s, n))

import sys
import string
	

def pretty_print():
	fmt = "{:>10} : {}"


def preprocess(data):
	remove_chars = string.punctuation + "\r\n"
	cleaner = str.maketrans(remove_chars, " "*len(remove_chars))
	return data.translate(cleaner)


def convert_to_list(data):
	return data.lower().split()


def count_words(words):
	unique_words = list(set(words))
	counted = {w : 0 for w in unique_words}
	for w in words:
		counted[w] += 1

def main():
	data = preprocess(sys.stdin.read())
	words = convert_to_list(data)
	count = count_words(words)

if __name__ == '__main__':
	main()