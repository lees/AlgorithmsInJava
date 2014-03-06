import java.util.LinkedList;

public class Fast 
{

	private Point[] points;
	
	public Fast()
	{

	}

	private Fast(int size)
	{
		points = new Point[size];
	}

	private void setPoint(int position, int x, int y)
	{
		Point newPoint = new Point(x, y);
		points[position - 1] = newPoint;
		newPoint.draw();
	}

	private static void printAll(Point[] mas)
	{
		for (int i = 0; i < mas.length; i++) 
		{
			StdOut.println(mas[i].toString());
		}
		StdOut.println("");
	}

	private class Line
	{
		private LinkedList<Point> pointsList;

		public Line()
		{
			pointsList = new LinkedList<Point>();
		}

		public void add(Point point)
		{
			pointsList.add(point);
		}

		public int size()
		{ return pointsList.size();}

		public void end_addition()
		{
			
			if (pointsList.size() < 4) 
			{
				pointsList.clear();
				return;
			}

			pointsList.getFirst().drawTo(pointsList.getLast());

			while (pointsList.size() > 1)
			{
				StdOut.print(pointsList.removeLast().toString());
				StdOut.print(" -> ");
			}
			StdOut.print(pointsList.removeLast().toString());
			StdOut.println();

		}
	}

	private void searchLines()
	{

		java.util.Arrays.sort(points);

		for (int i = 0; i < points.length - 3; i++)
		{
			Point[] copy = java.util.Arrays.copyOfRange(points, i + 1, points.length);
			java.util.Arrays.sort(copy,points[i].SLOPE_ORDER);

			double slope = points[i].slopeTo(copy[0]);
			Line line = new Line();
			line.add(points[i]);
			line.add(copy[0]);
			
			for (int j = 1; j < copy.length; )
			{
				
				if (line.size() < 1)
				{
					line.add(points[i]);
					line.add(copy[j]);
					slope = points[i].slopeTo(copy[j]);
					j++;
					continue;
				}
				
				if (slope == points[i].slopeTo(copy[j]))
				{
					line.add(copy[j]);
					j++;
				}
				else
				{
					line.end_addition();
				}
				
			}
			line.end_addition();

		}

	}

	public static void main(String[] args)
	{

		In input = new In(args[0]);

		StdDraw.setXscale(0, 32768); 
		StdDraw.setYscale(0, 32768);

		int counter = input.readInt();
		Fast search = new Fast(counter);
		for (int i = counter; i > 0; i--)
			search.setPoint(i, input.readInt(), input.readInt());

		search.searchLines();
	
   }

}