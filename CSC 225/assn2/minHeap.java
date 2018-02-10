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
		if(parentIndex < 0)
			return;
		else if(heap[index] > heap[parentIndex])
			return;
		else
		{
			System.out.println(heap[index] + "(" + index + ")" + " ==? " + heap[parentIndex] + "(" + (index-1)/2 + ")");
			exchange(index, parentIndex);
			bubbleup(parentIndex);
		}
		
	}

	public void exchange(int index1, int index2)
	{
		int temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = heap[index1];
	}

	public void bubbledown(int index)
	{
		int leftIndex = 2*index+1;
		int rightIndex = 2*index+2;

		// Basecase when no daughters or when both daughters are greater.
		if(leftIndex > size-1)
			return;
		else if(heap[index] < heap[leftIndex] && heap[index] < heap[rightIndex])
			return;
		else if(rightIndex > size-1 || heap[leftIndex] < heap[rightIndex])
		{
			exchange(index, leftIndex);
			bubbledown(leftIndex);
		}
		else
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
		bubbledown(0);
		heap[size-1] = 0;
		size--;
		return -1;
	}

	public static void main(String[] args) 
	{
		minHeap heap = new minHeap();
		heap.insert(12);
		///System.out.println(heap.size() + " [" + heap.peek() + "]");
	}
}