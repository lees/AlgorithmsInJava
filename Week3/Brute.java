import java.util.LinkedList;

public class Brute 
{

	Point[] points;

	private static class PointsLine
	{
		private Point[] points;

		public PointsLine(Point a, Point b, Point c, Point d)
		{
			points[0] = a;
			points[1] = b;
			points[2] = c;
			points[3] = d;
		}

		public String toString()
		{
			String result = "";
			for (int i = 0 ; i < 3; i++) 
			{
				result = result + points[i].toString() + " -> " + points[i+1].toString();
			}
			return result;
		}
	}

	private Brute(int size)
	{
		points = new Point[size];
	}

	private void setPoint(int position, int x,int y)
	{
		Point newPoint = new Point(x,y);
		points[position-1] = newPoint;
	}

	private static boolean areCollinear(Point p, Point q, Point r, Point s)
	{
		double slopePtoQ = p.slopeTo(q);
		double slopePtoR = p.slopeTo(r);
		if (slopePtoQ != slopePtoR) return false;

		double slopePtoS = p.slopeTo(s);
		if (slopePtoQ != slopePtoS) return false;

		return true;
	}

	private LinkedList<PointsLine> searchLines()
	{
		LinkedList<PointsLine> listOfLines = new LinkedList<PointsLine>();

		for (int i = 0; i < points.length; i++)
			for (int j = i + 1; j < points.length; j++)
				for (int k = j + 1; k < points.length; k++)
					for (int l = j + 1; l < points.length; l++)
						if (areCollinear(points[i],points[j],points[k],points[l]))
							listOfLines.add(new PointsLine(points[i],points[j],points[k],points[l]));

		return listOfLines;

	}


	public static void main(String[] args)
	{
		In input = new In(args[0]);

		int counter = input.readInt();
		Brute search = new Brute(counter);
		for (int i = counter; i>0; i--)
		{
			search.setPoint(i,input.readInt(),input.readInt());
		}

		StdOut.println(search.searchLines());

	}

}