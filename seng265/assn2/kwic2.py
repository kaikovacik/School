def read():
	exclusion_words = []
	phrases = []

	block = 0
	s = input()
	while s is not "":
		if s == "::":
			block += 1
		elif block is 1:
			exclusion_words.append(s)
		elif block is 2:
			phrases.append(s)
		s = input()

	return exclusion_words, phrases


def findKeyWords(phrases, exclusion_words):
	key_words = []

	for phrase in phrases:
		phrase = phrase.split()
		for word in phrase:
			if word not in exclusion_words and word not in key_words:
					key_words.append(word)
	key_words.sort()

	return key_words


def highlightKeywords(phrases, key_words):
	output = []

	for key_word in key_words:
		for phrase in phrases:
			phrase = phrase.split()
			if key_word in phrase:
				line = []	
				for word in phrase:
					if word == key_word:
						line.append(word.upper())
					else:
						line.append(word)
				output.append(line)

	return output

def main():
	exclusion_words, phrases = read()
	key_words = findKeyWords(phrases, exclusion_words)
	output = highlightKeywords(phrases, key_words)
	
	print(output)
if __name__ == '__main__':
	main()