def is_isogram(string):
	chars = list(string.lower())
	unique_chars = {c for c in chars}
	return len(unique_chars) == len(chars)

print(is_isogram(input()))
