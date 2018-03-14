class CaesarCypher(object):
	def __init__(self, key):
		self.key = key

	def encrypt(self, text):
		encrypted = []
		for c in text:
			encrypted[(key)%len(text)] = c
			key+=1
		return encrypted

	def decrypt(self, encrypted_text):
		decrypted = []
		key = -key
		for c in encrypted_text:
			decrypted[(key)%len(encrypted_text)] = c
			key+=1
		return decrypted