
public class Animate 
{ 

	public static void drawDoubleArray(Double[] a)
	{

		StdDraw.clear();
		StdDraw.setPenRadius(0.005);
		for (int i = 0; i<a.length;i++)
		{
			StdDraw.line(i*0.01,0.0,i*0.01,a[i]);
		}
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