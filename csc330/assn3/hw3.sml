(* Assign 03 Provided Code *)

(*  Version 1.0 *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

(* Description of g:
	g accepts 
*)

fun g f1 f2 p =
    let
		val r = g f1 f2
    in
		case p of
			Wildcard          => f1 ()
		| 	Variable x        => f2 x
		| 	TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
		| 	ConstructorP(_,p) => r p
		| 	_                 => 0
    end


(**** put all your code after this line ****)

(* Write a function only_capitals that takes a string list and returns a string list that has
only the strings in the argument that start with an uppercase letter. Assume all strings have at least 1
character. Use List.filter, Char.isUpper, and String.sub to make a 1-2 line solution. *)
fun only_capitals(sl : string list) =
	List.filter (fn s => Char.isUpper(String.sub(s, 0))) (sl)


(* Write a function longest_string1 that takes a string list and returns the longest string in the
list. If the list is empty, return "". In the case of a tie, return the string closest to the beginning
of the list. Use foldl, String.size, and no recursion (other than the implementation of foldl is
recursive). *)
fun longest_string1(sl : string list) = 
	List.foldl (fn (s2, s1) => if String.size s1 >= String.size s2 then s1 else s2) ("") (sl)

(* Write a function longest_string2 that is exactly like longest_string1 except in the case of ties
it returns the string closest to the end of the list. Your solution should be almost an exact copy of
longest_string1. Still use foldl and String.size. *)
fun longest_string2(sl : string list) = 
	List.foldl (fn (s2, s1) => if String.size s1 > String.size s2 then s1 else s2) ("") (sl)

(* Write functions longest_string_helper, longest_string3, and longest_string4 such that:
	• 	longest_string3 has the same behavior as longest_string1 and longest_string4 has
		the same behavior as longest_string2.
	• 	longest_string_helper has type (int * int -> bool) -> string list -> string
		(notice the currying). This function will look a lot like longest_string1 and longest_string2
		but is more general because it takes a function as an argument.
	• 	longest_string3 an(d longest_string4 are defined with val-bindings and partial applications
		of longest_string_helper. *)
fun longest_string_helper f =
	List.foldl (fn (s2, s1) => if f(String.size s1, String.size s2) then s1 else s2) ("")
val longest_string3 = longest_string_helper (fn (x, y) => if x >= y then true else false)
val longest_string4 = longest_string_helper(fn (x, y) => if x > y then true else false)

(* Write a function longest_capitalized that takes a string list and returns the longest string
in the list that begins with an uppercase letter (or "" if there are no such strings). Use a val-binding
and the ML library’s o operator for composing functions. Resolve ties like in problem 2. *)
fun longest_capitalized(sl : string list) = 
	longest_string1(only_capitals(sl))

(* Write a function rev_string that takes a string and returns the string that is the same characters
in reverse order. Use ML’s o operator, the library function rev for reversing lists, and two library
functions in the String module (browse the module documentation to find the most useful functions.) *)
val rev_string = String.implode o List.rev o String.explode

(* Write a function first_answer that has type (’a -> ’b option) -> ’a list -> ’b. Notice
that the 2 arguments are curried. The first argument should be applied to elements of the second
argument in order, until the first time it returns SOME v for some v and then v is the result of the call
to first_answer. If the first argument returns NONE for all list elements, then first_answer should raise
the exception NoAnswer. Hints: Sample solution is 5 lines and does nothing fancy. *)
(* fun first_answer f list = 
	val x = List.map (f) (list) *)
(* 
val x = first_answer (fn x => if (x mod 2) = 0 then SOME x else NONE) [1,1,4,3]; *)
fun first_answer f list = 
	case list of
	 	[] => raise NoAnswer
	|	x::xs => 
			case f(x) of
				NONE => first_answer f xs
			|	SOME value => value

(* Write a function all_answers of type (’a -> ’b list option) -> ’a list -> ’b list
option (notice the 2 arguments are curried). The first argument should be applied to elements of the
second argument. If it returns NONE for any element, then the result for all_answers is NONE. Else the
calls to the first argument will have produced SOME lst1, SOME lst2, ... SOME lstn and
the result of all_answers is SOME lst where lst is lst1, lst2, ..., lstn appended together
(the order in the result list should be preserved). Hints: The sample solution is 8 lines. It uses a helper
function with an accumulator and uses @. Note all_answers f [] should evaluate to SOME []. *)
fun all_answers f list =
	let
		fun helper f list acc =
			case list of
				[] => SOME acc
			|	x::xs =>
					case f(x) of	
						NONE => NONE
					|	SOME value => helper f xs (acc @ value)
	in	
		helper f list []
	end

(* Use g to define a function count_wildcards that takes a pattern and returns how many
Wildcard patterns it contains. See the test cases to illustrate what it does. *)
val count_wildcards = g (fn _ => 1) (fn _ => 0)

(* Use g to define a function count_wild_and_variable_lengths that takes a pattern and
returns the number of Wildcard patterns it contains plus the sum of the string lengths of all the
variables in the variable patterns it contains. Use String.size. We care only about variable
names; the constructor names are not relevant. *)
val count_wild_and_variable_lengths = g (fn _ => 1) (fn x => String.size(x))

(* Use g to define a function count_some_var that takes a string and a pattern (as a pair) and
returns the number of times the string appears as a variable in the pattern. We care only about
variable names; the constructor names are not relevant. *)
fun count_some_var (s : string, p : pattern) =
	g (fn _ => 0) (fn str => if s = str then 1 else 0) p

(* Write a function check_pat that takes a pattern and returns true if and only if all the variables
appearing in the pattern are distinct from each other (i.e., use different strings). The constructor
names are not relevant. Hints: The sample solution uses two helper functions. The first takes a pattern
and returns a list of all the strings it uses for variables. Using foldl with a function that uses append
is useful in one case. The second takes a list of strings and decides if it has repeats. List.exists
may be useful. Sample solution is approximately 18 lines. These are hints, it is not rquired to use
foldl and List.exists, but they might make it easier. *)
fun check_pat p =
	let
		fun string_list_from p =
			case p of	
				(Variable x | ConstructorP (_,Variable x)) => [x]
			|	TupleP ps => List.foldl (fn (x2, x1) => string_list_from(x2) @ x1) [] ps
			|	_ => []

		fun all_values_unique_in sl =
			case sl of 
				[] => true
			|	x::xs => 
					if List.exists (fn y => x = y) xs then false
					else all_values_unique_in xs
	in
		all_values_unique_in(string_list_from p)
	end

val x = check_pat (TupleP [Wildcard,Variable "cat",
                         Variable "pp",TupleP[Variable "tt"],
                         Wildcard,ConstP 3,
                         ConstructorP("cony",Variable "pp")])

(* Write a function match that takes a valu * pattern and returns a (string * valu) list
option, namely NONE if the pattern does not match and SOME lst where lst is the list of bindings
if it does. Note that if the value matches but the pattern has no patterns of the form Variable
s, then the result is SOME []. Remember to look above for the rules for what patterns match
what values, and what bindings they produce. Hints: Sample solution has one case expression
with 7 branches. The branch for tuples uses all_answers and ListPair.zip. Sample solution is
approximately 20 lines. These are hints: We are not requiring all_answers and ListPair.zip
here, but they make it easier *)


(* Write a function first_match that takes a value and a list of patterns and returns a (string *
valu) list option, namely NONE if no pattern in the list matches or SOME lst where lst is
the list of bindings for the first pattern in the list that matches. Use first_answer and a handleexpression.
Notice that the 2 arguments are curried. Hints: Sample solution is 2 lines. *)
