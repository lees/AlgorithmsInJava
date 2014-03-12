
import java.util.TreeSet;
import java.util.LinkedList;

public class PointSET {

    private TreeSet<Point2D> tree;

    public PointSET()                               // construct an empty set of points
    {
       tree = new TreeSet<Point2D>();
    }

    public boolean isEmpty()                        // is the set empty?
    {
       return tree.isEmpty();
    }

    public int size()                               // number of points in the set
    {
       return tree.size();
    }


    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
        tree.add(p);
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return tree.contains(p);
    }

    public void draw()                              // draw all of the points to standard draw
    {
        for (Point2D p : tree)
            StdDraw.point(p.x(),p.y());
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        LinkedList<Point2D> queue = new LinkedList<Point2D>();
        for (Point2D p: tree)
            if (rect.contains(p))
                queue.addLast(p);
        return queue;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        Point2D closestPoint = tree.first();
        double closestDistance = p.distanceTo(closestPoint);
        for (Point2D point: tree)
        {
            double distance = point.distanceTo(p);
            if (distance < closestDistance)
            {
                closestPoint = point;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }
}