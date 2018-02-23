def read():
	key_words = []
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


def main():
	exclusion_words, phrases = read()

	output = list()

	for exclude in exclusion_words:
		for phrase in phrases:
			spec = phrase.split()
			for word in spec:
				if word 

	for key in key_words:
		for phrase in phrases:
			spec = phrase.split()
			if key in phrase:
				for out in phrase:
					if out == key:
						print(out.upper(), end=" ")
					else:
						print(out, end=" ")

	# execute()
	# output()

if __name__ == '__main__':
	main()