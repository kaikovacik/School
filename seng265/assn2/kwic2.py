def read():
	exclusion_words = []
	phrases = []

	block = 0
	s = input()
	while s is not "":
		if s != "\n":
			if s == "::":
				block += 1
			elif block is 1:
				exclusion_words.append(s.lower())
			elif block is 2:
				phrases.append(s)
		s = input()

	return phrases, exclusion_words


def findKeyWords(phrases, exclusion_words):
	key_words = []

	for phrase in phrases:
		phrase = phrase.split()
		for word in phrase:
			if word.lower() not in exclusion_words and word.lower() not in key_words:
					key_words.append(word.lower())
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
	phrases, exclusion_words = read()
	key_words = findKeyWords(phrases, exclusion_words)
	output = highlightKeywords(phrases, key_words)

	left = []
	index = ""
	right = []

	print(output[0])

	after_key_word = False
	for word in output[0]:
		if word.isupper() and word not in exclusion_words:
			after_key_word = True
			index = word
		else:
			if after_key_word:
				right.append(word)
			else:
				left.append(word)

	left_str = ""
	space = ""
	for word in left:
		left_str = left_str + space + word
		space = " "

	print(left, index, right)
	print(left_str)
	#print("{:>30s}{}{:<}".format('welcome my friend I love so much', key_words[0], "hey"))

if __name__ == '__main__':
	main()