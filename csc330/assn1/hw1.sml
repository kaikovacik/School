(*  Assignment #1 *)

type DATE = (int * int * int)
exception InvalidParameter

fun is_older(d1: DATE, d2: DATE): bool =
    if #1 d1 < #1 d2 then true
    else if #1 d1 = #1 d2 then
        if #2 d1 < #2 d2 then true
        else if #2 d1 = #2 d2 then
            if #3 d1 < #3 d2 then true
            else false
        else false
    else false

fun number_in_month(dates : DATE list, month : int) =
    if null dates then 0
    else if #2 (hd dates) = month then 1 + number_in_month(tl dates, month)
	else number_in_month(tl dates, month)

fun number_in_months(dates : DATE list, months : int list) =
    if null months then 0
    else if null (tl months) then number_in_month(dates, hd months)
    else number_in_month(dates, hd months) + number_in_months(dates, tl months)

fun dates_in_month(dates : DATE list, month : int) =
    if null dates then []
    else if #2 (hd dates) = month then (hd dates)::dates_in_month(tl dates, month)
	else dates_in_month(tl dates, month)

fun dates_in_months(dates : DATE list, months : int list) =
    if null months then []
    else if null (tl months) then dates_in_month(dates, hd months)
	else dates_in_month(dates, hd months) @ dates_in_months(dates, tl months)

fun get_nth(strings : string list, n : int) =
    if n < 1 orelse n > length strings then raise InvalidParameter
    else if n = 1 then hd strings
    else get_nth(tl strings, n-1)

fun date_to_string(date : DATE) =
    get_nth(["January", 
            "February", 
            "March", 
            "April", 
            "May", 
            "June", 
            "July",
            "August", 
            "September", 
            "October", 
            "November"], 
            #2 date) 
    ^ " " 
    ^ Int.toString(#3 date) 
    ^ ", " 
    ^ Int.toString(#1 date)

fun number_before_reaching_sum(sum : int, numbers : int list) =
    let
        fun for_each(count : int, curr : int, numbers : int list) =
            if (hd numbers + curr) >= sum
            then count
            else for_each(count + 1, curr + hd numbers, tl numbers)
    in
	    for_each(0, 0, numbers)
    end

fun what_month(day : int) =
    number_before_reaching_sum(day, [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]) + 1;

fun month_range(day1 : int, day2 : int) =
    if day1 > day2 then []
    else what_month(day1)::month_range(day1+1, day2)

fun oldest(dates : DATE list ) =
    if null dates then NONE
    else
        let
            fun is_oldest(dates : DATE list, date : DATE) =
                if null dates then true
                else 
                    if is_older(date, hd dates) then is_oldest(tl dates, date)
                    else false
            fun for_each(dates : DATE list) =
                if is_oldest(tl dates, hd dates) then hd dates
                else for_each(tl dates)
        in
            SOME (for_each(dates))
        end

fun reasonable_date(date : DATE) =
    let
        fun get_nth_int(numbers : int list, n : int) =
            if n = 1 then hd numbers
            else get_nth_int(tl numbers, n-1)
    in
        if #1 date > 0 andalso 
        #2 date > 0 andalso 
        #2 date < 13 andalso
        #3 date > 0 andalso 
        #3 date <= get_nth_int([31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31], #2 date) then 
            if #2 date = 2 then  
                if #3 date = 29 andalso 
                #1 date mod 4 = 0 andalso 
                (#1 date mod 100 <> 0 orelse #1 date mod 400 = 0) then true
                else false
            else true
        else false
    end