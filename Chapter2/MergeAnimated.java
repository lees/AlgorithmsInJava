
public class MergeAnimated
{

	

	public static void merge(Double[] a, int lo, int mid, int hi)
	{
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi ; k++)
		 	aux[k] = a[k];
		
		for (int k = lo; k <= hi; k++) 
		{
			if (i > mid)       				a[k] = aux[j++];
			else if (j > hi)   				a[k] = aux[i++];
			else if (less(aux[j],aux[i]))   a[k] = aux[j++];
			else							a[k] = aux[i++];
			Animate.drawDoubleArray(a);
 		}
	}

	private static Double[] aux;

	public static void sort(Double[] a)
	{
		aux = new Double[a.length];
		sort(a,0,a.length-1);
	}

	public static void sort(Double[] a, int lo, int hi)
	{
		if (hi <= lo) return;
		int mid = lo + (hi-lo)/2;
		sort(a,lo,mid);
		sort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}

	private static boolean less(Double v, Double w)
	{
		return v.compareTo(w) < 0;
	}

	private static void exch(Double[] a, int i, int j)
	{
		Double t = a[i]; a[i] = a[j]; a[j] = t;
	}

	private static void show(Double[] a)
	{
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i]+" ");
		StdOut.println();
	}

	public static boolean isSorted(Double[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	public static void main(String[] args)
	{
		Double[] a = new Double[100];
		for (int i=0; i < a.length ; i++) 
		{
			a[i] = Math.random();
		}
		sort(a);
		assert isSorted(a);
		show(a);
	} 
}