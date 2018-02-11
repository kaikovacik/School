public class minHeap
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
		if(heap[index] <= heap[parentIndex])
			return;
		else
		{
			System.out.println(peek());
			System.out.println(heap[index] + " {} " + heap[parentIndex]);
			exchange(index, parentIndex);
			System.out.println(peek());
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
		System.out.print(size + " " + (leftIndex > size-1) + "or" + (heap[index] >= heap[leftIndex] && heap[index] >= heap[rightIndex]) + " ");
		printHeap();

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
		minHeap heap = new minHeap();

		heap.insert(12);
		heap.insert(14);
		heap.insert(2);
		heap.insert(4);
		heap.insert(7);
		heap.insert(1);
		heap.insert(20);
		heap.insert(2);
		heap.insert(46);
		heap.insert(21);
		heap.insert(18);
		heap.insert(25);
		heap.insert(9);
		heap.insert(8);

		int n = heap.size();
		for(int i = 0; i < n; i++)
		{
			System.out.println(heap.size() + " [" + heap.peek() + " " + heap.removeMin() + "]");
			heap.printHeap();
		}
	}
}