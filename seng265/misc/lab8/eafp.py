import timeit

#Few duplicate words, so exception 
tokens = "I am the very model of a modern major general Ive information vegetable animal and mineral".split(" ")
#tokens = ("foo "*30).split(" ")


def count1():
    counted = {}
    for word in tokens:
        #If word already exists, add 1
        if word in counted.keys():
            counted[word] += 1
        #Otherwise, set to 1
        else:
            counted[word] = 1
    
def count2():
    counted = {}
    for word in tokens:
        try: 
            counted[word] += 1
        except:
            counted[word] = 1

print("count with if/else", timeit.timeit(stmt=count1))
print("count with try/catch", timeit.timeit(stmt=count2))
