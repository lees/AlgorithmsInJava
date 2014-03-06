
public class Brute 
{

    private Point[] points;

    private static class PointsLine
    {
        private Point[] points;

        public PointsLine(Point a, Point b, Point c, Point d)
        {
            points = new Point[4];
            points[0] = d;
            points[1] = c;
            points[2] = b;
            points[3] = a;

        }

        public String toString()
        {
            String result = "";
            for (int i = 0; i < 3; i++) 
            {
                result = result + points[i].toString() + " -> ";
            }
            result = result + points[3].toString();
            return result;

        }
    }

    public Brute()
    {

    }

    private Brute(int size)
    {
        points = new Point[size];
    }

    private void setPoint(int position, int x, int y)
    {
        Point newPoint = new Point(x, y);
        points[position - 1] = newPoint;
        newPoint.draw();
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

    private void printPoint(int i, String s)
    {
        //StdOut.println("");
        //StdOut.print(s); StdOut.print(i); StdOut.print(" ; "); StdOut.print(points[i]);
    }

    private void searchLines()
    {

        for (int i = 0; i < points.length; i++)
        {
            printPoint(i, "i = ");

            for (int j = 0; j < points.length; j++)
            {
                printPoint(j, "j = ");
                if (points[i].compareTo(points[j]) < 1) continue;
                double slopeToJ = points[i].slopeTo(points[j]);

                for (int k = 0; k < points.length; k++)
                {
                    printPoint(k, "k = ");
                    if (points[j].compareTo(points[k]) < 1) continue;
                    double slopeToK = points[i].slopeTo(points[k]);
                    if (slopeToJ != slopeToK) continue;

                    for (int l = 0; l < points.length; l++)
                    {
                        printPoint(l, "l = ");
                        if (points[k].compareTo(points[l]) < 1) continue;
                        double slopeToL = points[i].slopeTo(points[l]);
                        if (slopeToJ != slopeToL) continue;

                        PointsLine line = new PointsLine(points[i], points[j], points[k], points[l]);
                        StdOut.println(line);
                        points[i].drawTo(points[l]);

                    }
                        
                }
                    
            }   
        }

    }


    private void allSlopes()
    {
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i; j < points.length; j++) 
            {
                StdOut.println();
                StdOut.print(i);
                StdOut.print(" : ");
                StdOut.print(j);
                StdOut.print(" / ");
                StdOut.print(points[i].toString());
                StdOut.print(" : ");
                StdOut.print(points[j].toString());
                StdOut.print(" / ");
                StdOut.print(points[i].slopeTo(points[j]));
                StdOut.print(" : ");
                StdOut.print(points[j].slopeTo(points[i]));
                
            }
            
        }
        StdOut.println();
    }

    public static void main(String[] args)
    {
        In input = new In(args[0]);

        StdDraw.setXscale(0, 32768); 
        StdDraw.setYscale(0, 32768);

        int counter = input.readInt();
        Brute search = new Brute(counter);
        for (int i = counter; i > 0; i--)
            search.setPoint(i, input.readInt(), input.readInt());

        search.searchLines();

    }

}