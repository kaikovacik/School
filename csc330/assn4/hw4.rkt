#lang racket

(provide (all-defined-out)) ; so we can put tests in a second file

; these definitions are simply for the purpose of being able to run the tests
; you MUST replace them with your solutions

; Write a function sequence that takes 3 arguments low, high, and stride, all assumed to be numbers.
; Further, assume stride is positive. sequence produces a list of numbers from low to high
; (including low and possibly high) separated by stride and in sorted order.
(define (sequence low high stride) 
    (if (> low high)
        null
        (cons low (sequence (+ low stride) high stride))))

; Write a function string-append-map that takes a list of strings xs and a string suffix and returns
; a list of strings. Each element of the output should be the corresponding element of the input appended
; with suffix (with no extra space between the element and suffix).
(define (string-append-map xs suffix)
    (map (lambda(str)(string-append str suffix)) xs))

; Write a function list-nth-mod that takes a list xs and a number n. If the number is negative,
; terminate the computation with (error "list-nth-mod: negative number"). Else if the
; list is empty, terminate the computation with (error "list-nth-mod: empty list"). Else
; return the i-th element of the list where we count from zero and i is the remainder produced when
; dividing n by the list’s length. Library functions length, remainder, car, and list-tail are all
; useful —see the Racket documentation.
(define (list-nth-mod xs n)
    (cond   
        [(> 0 n) (error "list-nth-mod: negative number")]
        [(null? xs) (error "list-nth-mod: empty list")]
        [#t (let ([rem (remainder n (length xs))]) 
            (car (list-tail xs rem)))]))

;Write a function stream-for-n-steps that takes a stream s and a number n. It returns a list holding
;the first n values produced by s in order.
(define (stream-for-n-steps s n)
    (cond 
        [(= 0 n) null]
        [#t (cons (car (s)) (stream-for-n-steps (cdr (s)) (- n 1)))])) 

; Write a stream funny-number-stream that is like the stream of natural numbers (i.e., 1, 2, 3, ...)
; except numbers divisible by 5 are negated (i.e., 1, 2, 3, 4, -5, 6, 7, 8, 9, -10, 11, ...). Remember a
; stream is a thunk that when called produces a pair. Here the car of the pair will be a number and the
; cdr will be another stream.
(define funny-number-stream
    (letrec 
        ([f (lambda (x) 
            (if (= (remainder x 5) 0)
            (cons (- 0 x) (lambda () (f (+ x 1))))   
            (cons x (lambda () (f (+ x 1))))))])
    (lambda () (f 1))))
    
; Write a stream cat-then-dog, where the elements of the stream alternate between the strings
; "cat.jpg" and "dog.jpg" (starting with "cat.jpg"). More specifically, cat-then-dog should
; be a thunk that when called produces a pair of "cat.jpg" and a thunk that when called produces a
; pair of "dog.jpg" and a thunk that when called... etc.
(define cat-then-dog 
    (letrec
        ([f (lambda (x)
            (if (even? x)
            (cons "cat.jpg" (lambda () (f (+ x 1))))   
            (cons "dog.jpg" (lambda () (f (+ x 1))))))])
    (lambda () (f 0))))
    
; Write a function stream-add-zero that takes a stream s and returns another stream. If s would produce
; v for its i-th element, then (stream-add-zero s) would produce the pair (0 . v) for its
; i-th element. Sample solution: 4 lines. Hint: Use a thunk that when called uses s and recursion. Note:
; One of the provided tests uses (stream-add-zero cat-then-dog) with place-repeatedly.
; (define (stream-add-zero s)
(define (stream-add-zero s)
    (letrec 
        ([f (lambda (x)
            (lambda () (cons (cons 0 (car (x))) (f (cdr (x))))))])
    (f s)))      
    
; Write a function cycle-lists that takes two lists xs and ys and returns a stream. The lists may or
; may not be the same length, but assume they are both non-empty. The elements produced by the
; stream are pairs where the rst part is from xs and the second part is from ys. The stream cycles
; forever through the lists.
(define (cycle-lists xs ys)
    (letrec
        ([f (lambda (n)
            (lambda () (cons (cons (list-nth-mod xs n) (list-nth-mod ys n)) (f (+ n 1)))))])
    (f 0)))

; Write a function vector-assoc that takes a value v and a vector vec. It should behave like Racket’s
; assoc library function except:
;
;   (a) it processes a vector (Racket’s name for an array) instead of a list and
;   (b) it allows vector elements not to be pairs in which case it skips them.
;
; Process the vector elements in order starting from 0. Use library functions vector-length, vector-ref,
; and equal?. Return #f if no vector element is a pair with a car field equal to v, else return the first
; pair with an equal car field. Sample solution is 9 lines, using one local recursive helper function.
(define (vector-assoc v vec)
    (letrec
        ([len (vector-length vec)]
        [f (lambda (curr) 
            (cond 
                [(= curr len) #f]
                [(pair? (vector-ref vec curr)) (if (equal? (car (vector-ref vec curr)) v)
                    (vector-ref vec curr)
                    (f (+ curr 1)))]
                [#t (f (+ curr 1))]))])
    (f 0)))

; Write a function cached-assoc that takes a list xs and a number n and returns a function that takes
; one argument v and returns the same thing that (assoc v xs) would return. However, you should
; use an n-element cache of recent results to possibly make this function faster than just calling assoc
; (if xs is long and a few elements are returned often). The cache should be a vector of length n that is
; created by the call to cached-assoc and used-and-possibly-mutated each time the function returned
; by cached-assoc is called.
; The cache starts empty (all elements #f). When the function returned by cached-assoc is called,
; it first checks the cache for the answer. If it is not there, it uses assoc and xs to get the answer and
; if the result is not #f (i.e., xs has a pair that matches), it adds the pair to the cache before returning
; (using vector-set!). The cache slots are used in a round-robin fashion: the first time a pair is added
; to the cache it is put in position 0, the next pair is put in position 1, etc. up to position n - 1 and then
; back to position 0 (replacing the pair already there), then position 1, etc.
(define (cached-assoc xs n)
    (letrec
        ([cache (make-vector n)]
        [cache-slot 0]
        [find (lambda (x)
            (let
                ([v-from-cache (vector-assoc x cache)])
            (if v-from-cache
                v-from-cache
                (let 
                    ([v-from-xs (assoc x xs)])
                (if v-from-xs
                    (begin
                        (vector-set! cache cache-slot v-from-xs)
                        (set! cache-slot (remainder (+ cache-slot 1) n))
                        v-from-xs)
                    v-from-xs)))))])
    find))

; Define a macro that is used like (while-less e1 do e2) where e1 and e2 are expressions and
; while-less and do are syntax (keywords).
(define-syntax while-less
  (syntax-rules (do)
    [(while-lees e1 do e2)
     (let ([t1 e1])
       (letrec ([loop (lambda (t2)
                        (if (>= t2 t1)
                            #t
                            (loop e2)))])
         (loop e2)))]))