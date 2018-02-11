import java.util.Scanner;

// =====================================================================================================================

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

// =====================================================================================================================

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
		heap[size] = x;
		bubbleup(size);
		size++;
	}
	
	public void bubbleup(int index)
	{
		int parentIndex = (index-1)/2;

		// Basecase when index is root or inappropriate to swap.
		if(heap[index] >= heap[parentIndex])
			return;
		else
		{
			exchange(index, parentIndex);
			bubbleup(parentIndex);
		}
	}

	public void exchange(int index1, int index2)
	{
		int temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	public void bubbledown(int index)
	{
		int leftIndex = 2*index+1;
		int rightIndex = 2*index+2;

		// Basecase when no daughters or when both daughters are greater.
		if(leftIndex > size-1 || (heap[index] < heap[leftIndex] && heap[index] < heap[rightIndex])) 
			return;
		else if((rightIndex > size-1 && heap[index] > heap[leftIndex])  || heap[leftIndex] <= heap[rightIndex])
		{
			exchange(index, leftIndex);
			bubbledown(leftIndex);
		}
		else if(rightIndex < size-1)
		{
			exchange(index, rightIndex);
			bubbledown(rightIndex);
		}
	}

	public int peek()
	{
		return (size != 0)? heap[0] : -1;
	}
	
	public int removeMin()
	{
		exchange(0, size-1);
		int temp = heap[size-1];
		heap[size-1] = 0;
		size--;
		bubbledown(0);
		return temp;
	}
}

// =====================================================================================================================

class maxHeap
{
	private int[] heap;
	private int size;
	
	public maxHeap()
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
		heap[size] = x;
		bubbleup(size);
		size++;
	}
	
	public void bubbleup(int index)
	{
		int parentIndex = (index-1)/2;

		// Basecase when index is root or inappropriate to swap.
		if(heap[index] <= heap[parentIndex])
			return;
		else
		{
			exchange(index, parentIndex);
			bubbleup(parentIndex);
		}
	}

	public void exchange(int index1, int index2)
	{
		int temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	public void bubbledown(int index)
	{
		int leftIndex = 2*index+1;
		int rightIndex = 2*index+2;

		// Basecase when no daughters or when both daughters are greater.
		if(leftIndex > size-1 || (heap[index] > heap[leftIndex] && heap[index] > heap[rightIndex])) 
			return;
		else if((rightIndex > size-1 && heap[index] < heap[leftIndex])  || heap[leftIndex] >= heap[rightIndex])
		{
			exchange(index, leftIndex);
			bubbledown(leftIndex);
		}
		else if(rightIndex < size-1)
		{
			exchange(index, rightIndex);
			bubbledown(rightIndex);
		}
	}

	public int peek()
	{
		return (size != 0)? heap[0] : -1;
	}
	
	public int removeMin()
	{
		exchange(0, size-1);
		int temp = heap[size-1];
		heap[size-1] = 0;
		size--;
		bubbledown(0);
		return temp;
	}
}

// =====================================================================================================================