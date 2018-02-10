import java.util.Scanner;


public class median
{
	static minHeap min;
	static maxHeap max;
	
	public median()
	{
		min = new minHeap();
		max = new maxHeap();
	}
	
	public static int calculateMedian(int x)
	{
		//your code goes here
		return -1;
	}
	
	public static void main(String[] args)
	{
		median m = new median();
		
		System.out.println("Enter a list of non negative integers. To end enter a negative integers.");
		Scanner s = new Scanner(System.in);
		int current = s.nextInt();

		while(current >= 0)
		{
			System.out.println("current median:" + m.calculateMedian(current));
			current = s.nextInt();
			if(current < 0) break;
			m.calculateMedian(current);
			current = s.nextInt();
			
		}	
	}
}


class minHeap
{
	private int[] heap;
	private int size;
	
	public minHeap()
	{
		heap = new int[10000];
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	public int size()
	{
		return size;
	}
	
	public void insert(int x)
	{
		//Your code goes here
	}
	
	public void bubbleup(int index)
	{
		int parentIndex = (index-1)/2;

		// Basecase when index is root or inappropriate to swap.
		if(nullAt(parentIndex) || orderIs(index, parentIndex))
			return;
		else
		{
			exchange(index, parentIndex);
			bubbleup(parentIndex);
		}
		
	}
	public void exchange(int index1, int index2)
	{
		int temp = heap(index1);
		heap(index1) = heap(index2);
		heap(index2) = heap(index1);
	}
	public void bubbledown(int k)
	{
		//Your code goes here
	
	}
	public int peek()
	{
		//Your code goes here
		return -1;
	}
	
	public int removeMin()
	{
		//Your code goes here
		return -1;
	}
}


class maxHeap
{
	private int[] heap;
	private int size;
	
	public maxHeap()
	{
		heap=new int[10000];
		size=0;
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	public int size()
	{
		return size;
	}
	
	public void insert(int index)
	{
		//Your code goes here
	}
	
	public void bubbleup(int index)
	{
		
	}
	public void exchange(int i,int j)
	{
		//Your code goes here
	}
	public void bubbledown(int k)
	{
		//Your code goes here
		
	}
	public int peek()
	{
		//Your code goes here
		return -1;
	}
	
	public int removeMax()
	{
		//Your code goes here
		return -1;
	}
}