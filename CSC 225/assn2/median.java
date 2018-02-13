import java.util.Scanner;

// MEDIAN===============================================================================================================

public class median
{
	static minHeap greater;
	static maxHeap lesser;
	
	public median()
	{
		greater = new minHeap();
		lesser = new maxHeap();
	}
	
	public static int calculateMedian(int x)
	{
		// Insert into minHeap if x > median, else insert into maxHeap
		if(x > greater.peek())
			greater.insert(x);
		else lesser.insert(x);

		// Balance heaps so median can be accessed
		while(greater.size()-lesser.size() > 1)
			lesser.insert(greater.removeMin());
		while(greater.size()-lesser.size() < 1)
			greater.insert(lesser.removeMax());

		// median stored at the top of minHeap
		return greater.peek();
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

// MINHEAP==============================================================================================================

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
		// Basecase when index is root or inappropriate to swap
		if(heap[index] < heap[(index-1)/2])
		{
			exchange(index, (index-1)/2);
			bubbleup((index-1)/2);
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
		// Basecase when index is leaf node or inappropriate to swap
		if(2*index+1 < size && heap[index] > heap[2*index+1])
		{
			exchange(index, 2*index+1);
			bubbledown(2*index+1);
		}

		if(2*index+2 < size && heap[index] > heap[2*index+2])
		{
			exchange(index, 2*index+2);
			bubbledown(2*index+2);
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

// MAXHEAP==============================================================================================================

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
		// Basecase when index is root or inappropriate to swap.
		if(heap[index] > heap[(index-1)/2])
		{
			exchange(index, (index-1)/2);
			bubbleup((index-1)/2);
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
		// Basecase when index is leaf node or inappropriate to swap
		if(2*index+1 < size && heap[index] < heap[2*index+1])
		{
			exchange(index, 2*index+1);
			bubbledown(2*index+1);
		}
		
		if(2*index+2 < size && heap[index] < heap[2*index+2])
		{
			exchange(index, 2*index+2);
			bubbledown(2*index+2);
		}
	}

	public int peek()
	{
		return (size != 0)? heap[0] : -1;
	}
	
	public int removeMax()
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