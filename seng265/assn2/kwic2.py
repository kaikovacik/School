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
		try:
			s = input()
		except Exception as e:
			break
		

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


def highlightKeyWords(phrases, key_words):
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


def formatAndPrint(line):
	# find key_word index
	kw_index = 0
	for word in line:
		if word.isupper():
			break
		else:
			kw_index += 1

	# find start index
	if kw_index > 1:
		start = kw_index
		total_len = 0
		for word in line[kw_index-1::-1]:
			total_len += len(" {}".format(word))
			if total_len > 20:
				break
			else:
				start -= 1
	else:
		start = 0

	if kw_index is len(line):
		start -= 1

	#################################################### FIX RIGHT FORMATTING!!
	# find end index
	if kw_index is not len(line):
		end = kw_index
		total_len = 0
		for word in line[kw_index::]:
			total_len += len(" {}".format(word))
			if total_len > 30:
				break
			else:
				end += 1
	else:
		end = kw_index+1

	# split phrase into three strings for output
	left = ""
	space_l = ""
	for word in line[start:kw_index:]:
		left = "{}{}{}".format(left, space, word)
		space = " "

	key_word = line[kw_index]

	right = ""
	space_r = ""
	for word in line[kw_index+1:end+1:]:
		right = "{}{}{}".format(right, space, word)
		space = " "

	print("{:>28s}{}{}{}{}".format(left, space_l, key_word, space_r, right))


def main():
	phrases, exclusion_words = read()
	key_words = findKeyWords(phrases, exclusion_words)
	output = highlightKeyWords(phrases, key_words)

	for line in output:
		formatAndPrint(line)

if __name__ == '__main__':
	main()