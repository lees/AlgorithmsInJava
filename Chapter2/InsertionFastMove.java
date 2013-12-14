

public class InsertionFastMove extends SortingAlgorithm
{
	public static void sort(Double[] a)
	{
		int N = a.length;
		for (int i = 1; i < N; i++)
		{
			Double tmp = a[i];
			int j = i; 
			for (; j > 0 && less(tmp,a[j-1]);j--)
			{
				a[j] = a[j-1];
				//Animate.drawDoubleArray(a);
			}

			a[j]=tmp;
		}
	}

	public static void main(String[] args)
	{
		
		Double[] a = generateArrayOfDouble();
		sort(a);
		assert isSorted(a);
		show(a);
	} 
}