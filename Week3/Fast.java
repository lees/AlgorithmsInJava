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

	private class LinesCollection
	{
		private int size;
		private Vector<Point> pointsCollection;
		private Vector<Double> slopeCollection;


		public LinesCollection()
		{
			size = 0;
			pointsCollection = new Vector<Point>();
			slopeCollection = new Vector<Double>();
		}

		public void addLine(double slope, Point point)
		{
			size++;
			pointsCollection.add(point);
			slopeCollection.add(slope);
		}

		public boolean hasLine(double slope, Point point)
		{
			for (int i = 0; i < size; i++)
			{
				if (slope != slopeCollection.get(i)) continue;
				if (point.slopeTo(pointsCollection.get(i)) != slope) continue;
				return true;
			}
			return false;
		}

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
				StdOut.print(pointsList.removeLast().toString());
				StdOut.print(" -> ");
			}
			StdOut.print(pointsList.removeLast().toString());
			StdOut.println();

		}

		public void clear()
		{
			pointsList.clear();
		}
	}

	private void searchLines()
	{

		java.util.Arrays.sort(points);
		LinesCollection linesCollection = new LinesCollection();

		for (int i = 0; i < points.length - 3; i++)
		{
			Point[] copy = java.util.Arrays.copyOfRange(points, i + 1, points.length);
			java.util.Arrays.sort(copy,points[i].SLOPE_ORDER);

			Point startPoint = points[i];
			double slope = startPoint.slopeTo(copy[0]);
			Line line = new Line();
			line.add(startPoint);
			line.add(copy[0]);
			
			for (int j = 1; j < copy.length; )
			{
				
				if (line.size() < 1)
				{
					line.add(startPoint);
					line.add(copy[j]);
					slope = startPoint.slopeTo(copy[j]);
					j++;
					continue;
				}
				
				if (slope == startPoint.slopeTo(copy[j]))
				{
					line.add(copy[j]);
					j++;
				}
				else
				{
					
					if (linesCollection.hasLine(slope, startPoint))
						line.clear();
					else
					{
						if (line.size() < 4) 
							line.clear();
						else
						{
							linesCollection.addLine(slope, startPoint);
							line.print();
						}
					}	
				}
			}
			
			if (line.size() >= 4 && !linesCollection.hasLine(slope, startPoint))
			{
				line.print();
				linesCollection.addLine(slope, startPoint);
			}
				

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