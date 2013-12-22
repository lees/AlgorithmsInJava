
public class Animate 
{ 

	private static void drawDoubleArray_lines(Comparable[] a)
	{
		StdDraw.setPenColor(StdDraw.BLUE);
		for (int i = 0; i<a.length;i++)
		{
			StdDraw.line(i*0.01,0.0,i*0.01, (Double) a[i]);
		}
	}
	
	public static void drawDoubleArrayLess(Comparable[] a, int j,int k)
	{
		StdDraw.clear();
		StdDraw.setPenRadius(0.005);
		drawDoubleArray_lines(a);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.line(j*0.01,0.0,j*0.01, (Double) a[j]);
		StdDraw.line(k*0.01,0.0,k*0.01, (Double) a[k]);
		StdDraw.show(50);
	}

	public static void drawDoubleArrayExchange(Comparable[] a, int j,int k)
	{
		StdDraw.clear();
		StdDraw.setPenRadius(0.005);
		drawDoubleArray_lines(a);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.line(j*0.01,0.0,j*0.01, (Double) a[j]);
		StdDraw.line(k*0.01,0.0,k*0.01, (Double) a[k]);
		StdDraw.show(50);
	}


	public static void drawDoubleArray(Comparable[] a)
	{

		StdDraw.clear();
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.BLACK);
		drawDoubleArray_lines(a);
		StdDraw.show(50);

	}

	public static void main(String[] args) {
		StdDraw.square(.5, .5, .5);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.line(.5, .5, .9, .5);
		StdDraw.line(.9, .5, .5, .8);
		StdDraw.line(.5, .8, .5, .5);
		StdDraw.circle(.7, .65, .25);

		StdDraw.clear();
		StdDraw.setPenRadius(0.005);
		for (double i=0; i<1;i+=0.01)
		{
			//StdOut.printf("%f",i);
			StdDraw.line(i,0.0,i,i);
		}
		

	}
}