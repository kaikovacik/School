import sys


def read():
	exclusion_words = []
	phrases = []

	block = 0
	for line in [s.replace('\n', '') for s in sys.stdin]:
		if line != "":
			if line == "::":
				block += 1
			elif block is 1:
				exclusion_words.append(line.lower())
			elif block is 2:
				phrases.append(line)		

	return phrases, exclusion_words


def findKeyWords(phrases, exclusion_words):
	key_words = []

	for phrase in phrases:
		phrase = phrase.split()
		for word in [s.lower() for s in phrase]:
			if word not in exclusion_words and word not in key_words:
					key_words.append(word)
	key_words.sort()

	return key_words


# I'll try to fix the tripple for loop for next assignment but it works fine
def preFormat(phrases, key_words):
	output = []

	for key_word in key_words:
		for phrase in phrases:
			phrase = phrase.split()
			if key_word in [s.lower() for s in phrase]:
				line = []
				line.append(key_word) # first value of line saves current key
				for word in phrase:
					if word.lower() == key_word:
						line.append(word.upper())
					else:
						line.append(word)
				output.append(line)

	return output


def formatAndPrint(line):
	# find key_word index
	kw_index = 1
	for word in line[1:]:
		if word.isupper() and word.lower() == line[0]:
			break
		else:
			kw_index += 1
	if kw_index > len(line)-1:
		kw_index = 1
		for word in line[1:]:
			if word.isdigit():
				break
			else:
				kw_index += 1

	# find start index
	if kw_index > 2:
		start = kw_index
		total_len = 0
		for word in line[kw_index-1:0:-1]:
			total_len += len(" {}".format(word))
			if total_len > 20:
				break
			else:
				start -= 1
	else:
		start = 1

	if kw_index is len(line):
		start -= 2

	# find end index
	if kw_index is not len(line):
		end = kw_index
		total_len = 0
		for word in line[kw_index::]:
			total_len += len(" {}".format(word))
			if total_len > 32:
				break
			else:
				end += 1
	else:
		end = kw_index

	# split phrase into three strings for output
	left = ""
	space_l = ""
	for word in line[start:kw_index:]:
		left = "{}{}{}".format(left, space_l, word)
		space_l = " "

	key_word = line[kw_index]

	right = ""
	space_r = ""
	for word in line[kw_index+1:end:]:
		right = "{}{}{}".format(right, space_r, word)
		space_r = " "

	print("{:>29s}{}{}".format((left + space_l), key_word, (space_r + right)))


def main():
	# GET INPUT-----------------------------------------------------------------
	phrases, exclusion_words = read()
	# --------------------------------------------------------------------------

	# DETERMINE KEYWORDS AND SORT ALPHABETICALLY--------------------------------
	key_words = findKeyWords(phrases, exclusion_words)
	# --------------------------------------------------------------------------

	# PREPARE OUTPUT FOR FORMATTING---------------------------------------------
	output = preFormat(phrases, key_words)
	# --------------------------------------------------------------------------

	# PRINT EACH LINE-----------------------------------------------------------
	for line in output:
		formatAndPrint(line)
	#---------------------------------------------------------------------------

if __name__ == '__main__':
	main()