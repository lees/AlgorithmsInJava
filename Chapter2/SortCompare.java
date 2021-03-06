

public class SortCompare
{
	public static double time(String alg, Double[] a)
	{
		Stopwatch timer = new Stopwatch();
		if (alg.equals("Ins")) Insertion.sort(a);
		if (alg.equals("InsS")) InsertionSentinel.sort(a);
		//if (alg.equals("InsFm")) InsertionFastMove.sort(a);
		if (alg.equals("Sel")) Selection.sort(a);
		if (alg.equals("She")) Shell.sort(a);
		if (alg.equals("SheA")) ShellArr.sort(a);
		if (alg.equals("Mer")) Merge.sort(a);
		if (alg.equals("Quick")) Quick.sort(a);
		if (alg.equals("Heap")) Heap.sort(a);
		
		//if (alg.equals("Пирамидальная")) Heap.sort(a);
		
		double stime = timer.elapsedTime();
		StdOut.printf("%f | ",stime);
		return stime;

	}

	public static double timeRandomInput(String alg, int N, int T)
	{
		double total = 0.0;
		Double[] a = new Double[N];

		for (int t = 0; t < T; t++)
		{
			if (t % 10 == 0) StdOut.println();
			for (int i = 0; i < N; i++)
				a[i] = StdRandom.uniform();
			total += time(alg,a);
		}
		return total;
	}

	public static void main(String[] args)
	{
		String alg1 = args[0];
		String alg2 = args[1];
		int N = Integer.parseInt(args[2]);
		int T = Integer.parseInt(args[3]);
		double t1 = timeRandomInput(alg1,N,T);
		StdOut.printf("\n%s : %.1f \n",alg1,t1);
		double t2 = timeRandomInput(alg2,N,T);
		StdOut.printf("\n%s : %.1f \n",alg2,t2);
		StdOut.printf("For %d random Doubles\n %s %.1f times faster, than %s \n",N,alg1,(t2/t1),alg2);
	}

}