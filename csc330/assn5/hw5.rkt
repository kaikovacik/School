;; Programming Languages, Homework 5 version 1.1
#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct ifgreater (e1 e2 e3 e4)    #:transparent) ;; if e1 > e2 then e3 else e4
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body)
(struct apair (e1 e2)     #:transparent) ;; make a new pair
(struct fst  (e)    #:transparent) ;; get first part of a pair
(struct snd  (e)    #:transparent) ;; get second part of a pair
(struct aunit ()    #:transparent) ;; unit value -- good for ending a list
(struct isaunit (e) #:transparent) ;; evaluate to 1 if e is unit else 0

;; a closure is not in "source" programs; it is what functions evaluate to
(struct closure (env fun) #:transparent)

;; Problem A

(define (mupllist->racketlist lst)
  (cond [(aunit? lst) '()]
        [(not (apair? lst)) lst]
        [#t (cons (mupllist->racketlist (apair-e1 lst)) (mupllist->racketlist (apair-e2 lst)))]))
        
(define (racketlist->mupllist lst)
  (cond [(equal? lst '()) (aunit)]
        [(not (pair? lst)) lst]
        [#t (apair (racketlist->mupllist (car lst)) (racketlist->mupllist (cdr lst)))]))

;; Problem B

;; lookup a variable in an environment
;; Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

;; Do NOT change the two cases given to you.
;; DO add more cases for other kinds of MUPL expressions.
;; We will test eval-under-env by calling it directly even though
;; "in real life" it would be a helper function of eval-exp.
(define (eval-under-env e env)
  (cond [(var? e)
          (envlookup env (var-string e))]

        [(int? e) e]

        [(add? e)
          (let ([v1 (eval-under-env (add-e1 e) env)]
                [v2 (eval-under-env (add-e2 e) env)])
          (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1)
                       (int-num v2)))
               (error "MUPL addition applied to non-number")))]

        [(ifgreater? e) 
          (let ([v1 (eval-under-env (ifgreater-e1 e) env)]
                [v2 (eval-under-env (ifgreater-e2 e) env)])
            (if (and (int? v1) (int? v2))
              (if (> (int-num v1) (int-num v2))
                (eval-under-env (ifgreater-e3 e) env)
                (eval-under-env (ifgreater-e4 e) env))
              (error "MUPL IfGreater applied to non-number")))]

        [(fun? e) 
          (closure env (fun (fun-nameopt e) (fun-formal e) (fun-body e)))]

        [(call? e) 
          (let ([e1 (eval-under-env (call-funexp e) env)]
                [e2 (eval-under-env (call-actual e) env)])
          (if (closure? e1)
            (eval-under-env (fun-body (closure-fun e1))
              (if (fun-nameopt(closure-fun e1))
                (append (list (cons (fun-nameopt (closure-fun e1)) e1) (cons (fun-formal (closure-fun e1)) e2)) (closure-env e1))
                (append (list (cons (fun-formal (closure-fun e1)) e2)) (closure-env e1))))
            (error "MUPL invalid function")))]

        [(mlet? e)
          (letrec ([v (eval-under-env (mlet-e e) env)]
                [env2 (cons (cons (mlet-var e) v) env)]) ; Add the var to the new environment
          (eval-under-env (mlet-body e) env2))]

        [(apair? e) 
          (apair (eval-under-env (apair-e1 e) env) (eval-under-env (apair-e2 e) env))]

        [(fst? e) 
          (let ([v (eval-under-env (fst-e e) env)])
          (if (apair? v)
            (apair-e1 v)
            (error "MUPL fst applied to non-pair")))]

        [(snd? e) 
          (let ([v (eval-under-env (snd-e e) env)])
          (if (apair? v)
            (apair-e2 v)
            (error "MUPL snd applied to non-pair")))]

        [(aunit? e) e]

        [(isaunit? e) 
          (let ([v (eval-under-env (isaunit-e e) env)])
          (if (aunit? v) 
            (int 1) 
            (int 0)))]

        [(closure? e) e]

        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))

;; Problem C

(define (ifaunit e1 e2 e3) 
  (if (aunit? (eval-exp e1))
    (eval-exp e2)
    (eval-exp e3)))

(define (mlet* lstlst e2)
  (if (null? (cdr lstlst))
    (mlet (car (car lstlst)) (cdr (car lstlst)) e2)
    (mlet (car (car lstlst)) (cdr (car lstlst)) (mlet* (cdr lstlst) e2))))

(define (ifeq e1 e2 e3 e4) 
  (ifgreater e1 e2 (ifgreater e2 e1 e3 e4) (ifgreater e2 e1 e4 e3)))
;; Problem D

(define mupl-map
  (fun #f "f"  
    (fun "loop" "xs" 
      (ifgreater 
        (isaunit (var "xs"))
        (int 0)
        (aunit)
        (apair 
          (call (var "f") (fst (var "xs")))
          (call (var "loop") (snd (var "xs"))))))))

(define mupl-mapAddN 
  (mlet "map" mupl-map
    (fun #f "i"
      (call (var "map") (fun #f "x" (add (var "x") (var "i")))))))