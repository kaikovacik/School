def totalWords(s):
	return len(s.split())

def wordsOfLength(s, n):
	count = 0;
	for i in s.split():
		if len(i) is n:
			count += 1
	return count

s = input()
print(totalWords(s))
n = int(input())
print(wordsOfLength(s, n))