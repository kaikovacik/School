public class Test
{
	public static void main(String[] args) 
	{
		List<Medication> list= new MedListRefBased();
		Medication[] fill = new Medication[100];
		Medication[] data = new Medication[10];

		for(int i = 0; i < 100; i++)
		{
			fill[i] = new Medication("index", (i));
			list.add(fill[i], i);
		}

		for(int i = 0; i < 10; i++)
		{
			data[i] = new Medication("XXXX", 0);
		}
		System.out.println("	" + list);
		list.add(data[4], 99);
		System.out.println("	" + list);
	}
}