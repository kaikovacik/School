(* if you use this function to compare two strings (returns true if the same
   string), then you avoid some warning regarding polymorphic comparison.  *)

fun same_string(s1 : string, s2 : string) =
    s1 = s2
    
(* Write a function all_except_option, which takes a string and a string list. Return
NONE if the string is not in the list, else return SOME list where lst is identical to the argument
list except the string is not in it. You may assume the string is in the list at most once. Use
same_string, provided to you, to compare strings. Sample solution is around 8 lines. *)
fun all_except_option(s : string, ls : string list) =
    let 
        fun all_except_option(s : string, ls1 : string list, ls2 : string list) =
            case ls1 of
                [] => NONE
            |   head::rest =>
                    if same_string(head, s) then SOME (ls2 @ rest)
                    else all_except_option(s, rest, ls2 @ [head])
    in 
        all_except_option(s, ls, [])
    end

(* Write a function get_substitutions1, which takes a string list list (a list of list of
strings, the substitutions) and a string s and returns a string list. The result has all the
strings that are in some list in substitutions that also has s, but s itself should not be in the result. *)
fun get_substitutions1(ls : string list list, s : string) = 
    case ls of
        [] => []
    |   head::rest =>
            case all_except_option(s, head) of
                SOME tmp => tmp @ get_substitutions1(rest, s)
            |   NONE => get_substitutions1(rest, s)

(* Write a function get_substitutions2, which is like get_substitutions1 except it uses a
rest-recursive local helper function. *)
fun get_substitutions2(ls : string list list, s : string) =
    let 
        fun get_substitutions2(ls1 : string list list, s : string, ls2 : string list) =
            case ls1 of
                [] => ls2
            |   head::rest => 
                    case all_except_option(s, head) of
                        SOME tmp => get_substitutions2(rest, s, ls2 @ tmp) 
                    |   NONE => get_substitutions2(rest, s, ls2)
    in
	    get_substitutions2(ls, s, [])
    end

(* Write a function similar_names, which takes a string list list of substitutions (as in Parts 2 and 3)
and a full name of type {first:string,middle:string,last:string } and returns a list
of full names (type {first:string,middle:string,last:string} list). The result
is all the full names you can produce by substituting for the first name (and only the first name) using
substitutions and Parts 2 or 3. The answer should begin with the original name (then have 0 or more
other names). *)
type full_name = { first  : string, 
                   middle : string, 
                   last   : string }

fun similar_names(ls : string list list, name : full_name) = 
    let 
        val {first=first, middle=middle, last=last} = name
        fun get_names(ls : string list, name : full_name) =
            case ls of
                [] => []
            |   head::rest => 
                    {first=head, middle=(middle), last=(last)}::get_names(rest, name)
    in
        name::get_names(get_substitutions1(ls, first), name)
    end

(************************************************************************)
(* Game  *)

(* you may assume that Num is always used with valid values 2, 3, ..., 10 *)

datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw


exception IllegalMove

(* Write a function card_color, which takes a card and returns its color (spades and clubs are black,
diamonds and hearts are red). Note: One case-expression is enough. *)
fun card_color(suit, _) =
    case suit of
        (Diamonds | Hearts) => Red
    |   _ => Black

(* Write a function card_value, which takes a card and returns its value (numbered cards have their
number as the value, aces are 11, everything else is 10). Note: One case-expression is enough. *)
fun card_value(_, rank) =
    case rank of
        Ace => 11
    |   (King | Queen | Jack) => 10
    |   Num n => n

(* Write a function remove_card, which takes a list of cards cs, a card c, and an exception e. It returns
a list that has all the elements of cs except c. If c is in the list more than once, remove only the first
one. If c is not in the list, raise the exception e. You can compare cards with =. *)
fun remove_card(cs : card list, c : card, e) = 
    case cs of 
        [] => raise e
    |   head::rest => 
            if head = c then rest
            else head::remove_card(rest, c, e)

(* Write a function all_same_color, which takes a list of cards and returns true if all the cards in
the list are the same color. The color of empty list is true. Hint: An elegant solution is very similar to
one of the functions using nested pattern-matching in the lectures. *)
fun all_same_color (cs : card list) = 
    case cs of
	    [] => true
    |   head::[] => true
    |   head::(next::rest) => (card_color(head) = card_color(next)) andalso all_same_color(next::rest)

(* Write a function sum_cards, which takes a list of cards and returns the sum of their values. Use a
locally defined helper function that is tail recursive. *)
fun sum_cards(cs : card list) =
    let
        fun sum_cards(cs : card list, sum : int) =
            case cs of
                [] => sum
            |   head::rest =>
                    sum_cards(rest, sum + card_value(head))   
    in
        sum_cards(cs, 0)
    end

(* Write a function score, which takes a card list (the held-cards) and an int (the goal) and computes
the score as described above. 

The objective is to end the game with a low score (0 is best). Scoring works as follows: Let sum be
the sum of the values of the held-cards. If sum is greater than goal, the preliminary score is two times
(sum − goal), else the preliminary score is (goal − sum). The score is the preliminary score unless all the
held-cards are the same color, in which case the score is the preliminary score divided by 2 (and rounded
down as usual with integer division; use ML’s div operator). *)
fun score(cs : card list, goal : int) =
    let
        val sum = sum_cards cs 
        val pre_score =
            if sum > goal then 2 * (sum - goal)
            else goal - sum
    in
        if all_same_color cs then pre_score div 2
        else pre_score
    end

(* Write a function officiate, which “runs a game.” It takes a card list (the card-list), a move list
(what the player “does” at each point), and an int (the goal) and returns the score at the end of the
game after processing (some or all of) the moves in the move list in order. Use a locally defined
recursive helper function that takes several arguments that together represent the current state of the
game. As described above:
•   The game starts with the held-cards being the empty list.
•   The game ends if there are no more moves. (The player chose to stop since the move list is
    empty.)
•   If the player discards some card c, play continues (i.e., make a recursive call) with the held-cards
    not having c and the card-list unchanged. If c is not in the held-cards, raise the IllegalMove
    exception.
•   If the player draws but the card-list is (already) empty, the game is over. Else if drawing causes
    the sum of the held-cards to exceed the goal, the game is over (after drawing). Else play continues
    with a larger held-cards and a smaller card-list. Sample solution for is under 20 lines. *)
fun officiate (cards : card list, moves : move list, goal : int) = 
    let
        fun aux (cards : card list, held : card list, moves : move list, goal : int) =
            case (cards, held, moves, goal) of
            (_,     _, [],    _) => score (held, goal)
            | ([],    _, _,     _) => score (held, goal)
            | (c::cs, _, m::ms, _) => case m of
                        Discard d => aux (c::cs, remove_card (held, d, IllegalMove), ms, goal)
                        | Draw => case c::cs of
                            [] => score (held, goal)
                            | _ => 
                                let
                                val held' = c::held
                                val held_sum = sum_cards (held')
                                in
                                if (held_sum > goal)
                                then score (held', goal)
                                else aux (cs, held', ms, goal)
                                end
    in
	    aux (cards, [] , moves, goal) 
    end

fun officiate(cs : card list, moves : move list, goal : int) =
    let
        fun play(cs : card list, hand : card list, moves : move list, goal : int) =
            case (cs, hand, moves, goal) of
                (_, _, [], _) => score(hand, goal)
            |   ([], _, _, _) => score(hand, goal)
            |   (c_head::c_rest, _, m_head::m_rest, _) =>
                    case m_head of
                        Discard c => play(c_head::c_rest, remove_card(hand, c, IllegalMove), m_rest, goal)
                    |   Draw =>
                            case c_head::c_rest of
                                [] => score(hand, goal)
                            |   _  => 
                                    let
                                        val hand' = c_head::hand
                                        val sum = sum_cards(hand')
                                    in
                                        if sum > goal then score(hand', goal)
                                        else play(c_rest, hand', m_rest, goal)
                                    end

    in
        play(cs, [], moves, goal)
    end

(* I have provided a file with test bindings hw2-test.sml. Make sure that everyone of the test bindings
evaluates to true. Add at least one new test for each function. Your test should be named testN_0 where
N is the Part number. For example, your test for the first function all_except_option should be called
test1_0, and your test for sum_cards should be called test9_0
Add your tests to hw2.sml, not to hw2-test.sml. You will only sub. *)
val test1_0 = all_except_option("one", ["one", "two", "ONE", "TWO"]) = SOME (["two", "ONE", "TWO"]);
val test2_0 = get_substitutions1([["ardvark","deer","bear"], ["lion","bird"], ["lizard"]], "seal") = [];
val test3_0 = get_substitutions1([["who","what","when"], ["when","who"], ["who"]], "who") = ["what", "when", "when"];
val test4_0 = similar_names([["Sue", "Brad"], ["Stuart", "Brad","James"], ["Ryan", "Brooks"]], {first="Brad", middle = "Robin", last="Smith"}) =
            [{first="Brad", last="Smith", middle="Robin"},
             {first="Sue", last="Smith", middle="Robin"},
             {first="Stuart", last="Smith", middle="Robin"},
             {first="James", last="Smith", middle="Robin"}];
val test5_0 = card_color((Hearts, Ace)) = Red;
val test6_0 = card_value(Clubs, Num 6) = 6;
exception notFound
val test7_0 = remove_card([], (Clubs, Num 9), notFound) = [(Clubs, Num 9)] handle notFound => true;
val test8_0 = all_same_color([(Diamonds, Queen), (Hearts, Queen), (Diamonds, Num 9), (Clubs, Ace)]) = false;
val test9_0 = sum_cards([(Spades, Ace), (Clubs, Ace), (Hearts, Ace), (Diamonds, Ace)]) = 44;
val test10_0 = score([(Diamonds, Num 4)], 16) = 6;
val test11_0 = officiate([(Spades, Ace), (Hearts, Num 2)], [Draw, Draw, Draw], 15) = 2;
val all_tests_passed =  test1_0 andalso
                        test2_0 andalso
                        test3_0 andalso
                        test4_0 andalso
                        test5_0 andalso
                        test6_0 andalso
                        test7_0 andalso
                        test8_0 andalso
                        test9_0 andalso
                        test10_0 andalso
                        test11_0;