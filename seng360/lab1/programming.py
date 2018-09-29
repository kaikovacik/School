# Kai Kovacik
# September 26, 2018

from Crypto.Cipher import AES

# Here's a one line answer for fun (it doesn't close the file though):
# print "The key that was used to encrypt is \"" + [(lambda s : ''.join([_ for _ in s if _ != '#']))(key) for key in ('{:#<16}'.format(_.strip()) for _ in open("words.txt", "r").readlines() if len(_) <=16) if (AES.new(key, AES.MODE_CBC, "aabbccddeeff00998877665544332211".decode('hex')).encrypt((lambda s: s + (16 - len(s) % 16) * chr(16 - len(s) % 16))("This is a top secret.")).encode('hex') == "764aa26b55a4da654df6b19e4bce00f4ed05e09346fb0e762583cb7da2ac93a2")][0] + "\""

pad = lambda s: s + (16 - len(s) % 16) * chr(16 - len(s) % 16)
unpad = lambda s : ''.join([_ for _ in s if _ != '#'])

def getPaddedKeysFromFile(file_name):
    f = open(file_name, "r")
    paddedKeys = ('{:#<16}'.format(_.strip()) for _ in f.readlines() if len(_) <=16)
    f.close()
    return paddedKeys

def crackCorrectKey(plaintext, ciphertext, iv, keys):
    return [unpad(key) for key in keys if (AES.new(key, AES.MODE_CBC, iv).encrypt(pad(plaintext)).encode('hex') == ciphertext.encode('hex'))][0]


def main():
    plaintext = "This is a top secret."
    ciphertext = "764aa26b55a4da654df6b19e4bce00f4ed05e09346fb0e762583cb7da2ac93a2".decode('hex')
    iv = "aabbccddeeff00998877665544332211".decode('hex')

    keys = getPaddedKeysFromFile("words.txt")
    key = crackCorrectKey(plaintext, ciphertext, iv, keys)

    print "The key that was used to encrypt is \"" + key + "\""
    
if __name__ == "__main__":
    main()