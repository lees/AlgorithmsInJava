
import java.util.LinkedList;
import java.util.Vector;

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
		
		public void print()
		{
			pointsList.getFirst().drawTo(pointsList.getLast());

			while (pointsList.size() > 1)
			{
				StdOut.print(pointsList.removeFirst().toString());
				StdOut.print(" -> ");
			}
			StdOut.print(pointsList.removeFirst().toString());
			StdOut.println();

		}

		public void clear()
		{
			pointsList.clear();
		}
	}

	private void searchLines()
	{

		if (points.length < 4) return;
		
		java.util.Arrays.sort(points);

		for (int i = 0; i < points.length - 3; i++)
		{
			Point[] copy = java.util.Arrays.copyOfRange(points,0, points.length);
			java.util.Arrays.sort(copy,points[i].SLOPE_ORDER);

			Point startPoint = points[i];
			double slope = startPoint.slopeTo(copy[0]);
			Line line = new Line();
			boolean toSkip = (startPoint.compareTo(copy[0]) > 0);
			line.add(startPoint);
			line.add(copy[0]);
			
			for (int j = 1; j < copy.length; )
			{
				
				if (line.size() < 1)
				{
					line.add(startPoint);
					line.add(copy[j]);
					slope = startPoint.slopeTo(copy[j]);
					toSkip = (startPoint.compareTo(copy[j]) > 0);
					j++;
					continue;
				}
				
				if (slope == startPoint.slopeTo(copy[j]))
				{
					line.add(copy[j]);
					if (startPoint.compareTo(copy[j]) > 0)
						toSkip = true;
					j++;
				}
				else
				{
					if (line.size() < 4 || toSkip)
						line.clear();
					else
						line.print();
				}
			}
			
			if (line.size() >= 4 && !toSkip)
				line.print();
			else
				line.clear();

		}

		// StdOut.println("Lines total: "); 
		// StdOut.print(linesCollection.size());

	}

	public static void main(String[] args)
	{
		//StdDraw.show(0);
		StdDraw.setXscale(0, 32768); 
		StdDraw.setYscale(0, 32768);

		Fast search;

		if (args.length == 1)
		{
			In input = new In(args[0]);
			int counter = input.readInt();
			search = new Fast(counter);
			for (int i = counter; i > 0; i--)
				search.setPoint(i, input.readInt(), input.readInt());
		}
		else
		{
			int size = 128;
			search = new Fast(128 * 4);
			int pos = 1;
			for (int i = 128; i > 0; i--)
			{
				search.setPoint(pos++, i*100, 1000);
				search.setPoint(pos++, i*100, 2000);
				search.setPoint(pos++, i*100, 3000);
				search.setPoint(pos++, i*100, 4000);
			}

		}

		

		search.searchLines();
	
   }

}