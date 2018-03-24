# Kai Kovacik
# V00880027
# Mar 21, 2018

import re

class Kwic:
	START = 10
	CENTER = 30
	END = 60

	def __init__(self, excluded, lines):
		self.excluded = excluded
		self.lines = lines
		self.keywords = self.findKeyWords(self.lines, self.excluded)
		self.index = self.findIndex(self.lines, self.keywords)


	def findKeyWords(self, lines, excluded):
		keywords = []

		for line in lines:
			for word in [s.lower() for s in line.split()]:
				if word not in excluded and word not in keywords:
					keywords.append(word)
		keywords.sort()		
					
		return keywords


	def findIndex(self, lines, keywords):
		index = []

		for keyword in keywords:
			# describes properly-sized index line
			index_ptrn = re.compile(
									r"(\b.{," + str(self.CENTER-self.START-1) + r"} |^\b)" + 
									keyword + 
									r"\b(.{," + str(self.END-self.CENTER-len(keyword)+1) + r"}\b| $)"
									, re.IGNORECASE
									)
			for line in lines:
				index_found = index_ptrn.search(line)
				if index_found:
					index_line = index_found.group().strip()

					# describes keyword and finds keyword's location in index line
					key_ptrn = re.compile(r"\b" + keyword + r"\b", re.IGNORECASE)
					key_found = key_ptrn.search(index_line)
					indent_len = self.CENTER-key_found.start()-1

					# formats index with capitalized keyword
					s = "{}{}{}".format(index_line[:key_found.start()], keyword.upper(), index_line[key_found.end():])
					index.append("{:>{indent}}{}".format("", s, indent=indent_len))

		return index

	def output(self):
		return self.index