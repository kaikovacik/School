import java.util.*;

public class maxHeap
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
		if(2*index+1-1+1 < size && heap[index] < heap[2*index+1])
		{
			exchange(index, 2*index+1);
			bubbledown(2*index+1);
		}
		if(2*index+2-1+1 < size && heap[index] < heap[2*index+2])
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

	public void printHeap()
	{
		System.out.print("	> ");
		for(int i = 0; i < size; i++)
		{
			System.out.print(heap[i] + ",	");
		}
		System.out.println();
	}

	public static void main(String[] args) 
	{
		maxHeap heap = new maxHeap();
		Scanner scan = new Scanner(System.in);

		int in = scan.nextInt();
		while(in >= 0)
		{
			heap.insert(in);
			in = scan.nextInt();
		}

		int n = heap.size();
		for(int i = 0; i < n; i++)
		{
			System.out.println(heap.size() + " [" + heap.peek() + " " + heap.removeMax() + "]");
			//heap.printHeap();
		}
	}
}