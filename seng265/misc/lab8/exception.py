import sys

class MyException(Exception):
    pass 

try:
    raise MyException("MyException has occurred!")
    raise TypeError()
    print("No problems here!")
except MyException as e:
    print("This exception only runs if the error is type MyException: ", e, file=sys.stderr)
except:
    print("This catches all other errors.", file=sys.stderr)
    sys.exit(-1) 
finally:
    print("After everything in the try or exceptions has run, then this runs.")
    
print("I'm the rest of the program.")
