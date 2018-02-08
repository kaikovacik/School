/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: January 20, 2018
 * Filename: Stock.java
 * Details: CSC 225 - Assignment 1
 */

import java.util.Scanner;
import java.io.File;
import java.util.Queue;
import java.util.LinkedList;

public class Stock
{ 

	public static int[] calculateSpan(int[] p)
	{
		LinkedList<Integer> rel_max_spans = new LinkedList<>();
		LinkedList<Integer> rel_max_prices = new LinkedList<>();
		int[] s = new int[p.length];

		s[0] = 1;
		int span = 1;
		for(int i = 1; i < p.length; i++)
		{
			// Increment span by default
			span++;
			// If downward trend, stack relative max values and reset span
			if(p[i] < p[i-1])
			{
				rel_max_prices.push(p[i-1]);
				rel_max_spans.push(span-1);
				span = 1;
			}
			// If new relative max, increment by all previous smaller relative max values
			else while(!rel_max_prices.isEmpty() && p[i] >= rel_max_prices.peek())
			{
				span += rel_max_spans.pop();
				rel_max_prices.pop();
			}
			// After finding appropriate span, insert into s array
			s[i] = span;
		}
		return s;
	}

	public static int[] readInput(Scanner s)
    	{
		Queue<Integer> q = new LinkedList<Integer>();

		int n=0;
		if(!s.hasNextInt())
			return null;
		int temp = s.nextInt();
		while(temp>=0)
        {
			q.offer(temp);
			temp = s.nextInt();
			n++;
		}
		int[] inp = new int[q.size()];
		for(int i=0;i<n;i++)
			inp[i]= q.poll();
		return inp;
	}

	public static void main(String[] args)
    	{
		Scanner s;
        Stock m = new Stock();

        if (args.length > 0)
        {
        	try
            {
        		s = new Scanner(new File(args[0]));
        	} 
            catch(java.io.FileNotFoundException e)
            {
        		System.out.printf("Unable to open %s\n",args[0]);
        		return;
        	}
        	System.out.printf("Reading input values from %s.\n", args[0]);
        }
        else
        {
        	s = new Scanner(System.in);
        	System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
        }     

        int[] price = m.readInput(s);
        System.out.println("The stock prices are:");
        for(int i=0;i<price.length;i++)
        	System.out.print(price[i]+ (((i+1)==price.length)? ".": ", "));
        
        if(price!=null)
        {
        	int[] span = m.calculateSpan(price);
        	if(span!=null)
            {
        		System.out.println("\nThe spans are:");
        		for(int i=0;i<span.length;i++)
        	       System.out.print(span[i]+ (((i+1)==span.length)? ".": ", "));
        	}
        }
    }
}
