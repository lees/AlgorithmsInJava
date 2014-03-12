
import java.util.LinkedList;

public class KdTree {

    private Node root;
    private int size;

    private static class Node
    {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;
        private boolean isHorisontal;

        public Node()
        {}

        public boolean isSmaller(Point2D par)
        {
            if (isHorisontal)
                return (par.x() < p.x());
            else
                return (par.y() < p.x());
        }

        public RectHV leftRect()
        {
            if (isHorisontal)
                return new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),p.y());
            else
                return new RectHV(rect.xmin(),rect.ymin(),p.x(),rect.ymax());
        }

        public RectHV rightRect()
        {
            if (isHorisontal)
                return new RectHV(rect.xmin(),p.y(),rect.xmax(),rect.ymax());
            else
                return new RectHV(p.x(),rect.ymin(),rect.xmax(),rect.ymax());
        }


    }


    public KdTree()                               // construct an empty set of points
    {
        root = null;
        size = 0;
    }

    public boolean isEmpty()                        // is the set empty?
    {
        return (size == 0);
    }

    public int size()                               // number of points in the set
    {
        return size;
    }

    private Node insertToNode(Node node, Point2D p)
    {
        if (node.p.equals(p)) return node;

        Node newNode = new Node();
        newNode.p = p;
        newNode.isHorisontal = !node.isHorisontal;

        if (node.isSmaller(p))
        {
            if (node.left != null)
                return insertToNode(node.left, p);

            newNode.rect = node.leftRect();
            node.left = newNode;
            return newNode;
        }
        else
        {
            if (node.right != null)
                return insertToNode(node.right, p);

            newNode.rect = node.leftRect();
            node.right = newNode;
            return newNode;

        }
    }

    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {

        size++;
        if (root != null)
        {
            insertToNode(root, p);
            return;
        }

        root = new Node();
        root.isHorisontal = false;
        root.rect = new RectHV(0,0,1,1);

    }

    private boolean subtreeContains(Node subtree, Point2D p)
    {
        if (subtree == null) return false;

        if (subtree.p.equals(p)) return true;

        if (subtree.isSmaller(p))
            return subtreeContains(subtree.left, p);
        else
            return subtreeContains(subtree.right, p);
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return subtreeContains(root, p);
    }

    private void drawSubtree(Node node)
    {
        if (node == null) return;
        node.p.draw();
        drawSubtree(node.left);
        drawSubtree(node.right);
    }

    public void draw()                              // draw all of the points to standard draw
    {
        drawSubtree(root);
    }

    private Iterable<Point2D> rangeInSubtree(Node subtree, RectHV rect)
    {
        LinkedList<Point2D> result = new LinkedList<Point2D>();
        if (subtree == null) return result;
        if (rect.contains(subtree.p)) result.addFirst(subtree.p);
        if (subtree.left != null && rect.intersects(subtree.leftRect())) 
        	for (Point2D p: rangeInSubtree(subtree.left, rect))
        		result.addLast(p);
        if (subtree.right != null && rect.intersects(subtree.rightRect()))
        	for (Point2D p: rangeInSubtree(subtree.right, rect))
        		result.addLast(p);

        return result;
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        return rangeInSubtree(root, rect);
    }

    private Point2D nearestInSubtree(Node subtree, Point2D searchPoint)
    {
        Point2D result = subtree.p;
        Point2D resultInSubtree;
        RectHV secondRect;

        if (subtree.left == null && subtree.right == null) return result;
        if (subtree.left == null || subtree.right == null)
        {
            if (subtree.left == null)
                resultInSubtree = nearestInSubtree(subtree.right, searchPoint);
            else
                resultInSubtree = nearestInSubtree(subtree.left, searchPoint);

            if (searchPoint.distanceSquaredTo(subtree.p) < searchPoint.distanceSquaredTo(resultInSubtree))
                return subtree.p;
            else
                return resultInSubtree;
        }

        boolean isSmaller = subtree.isSmaller(searchPoint);
        if (isSmaller)
        {
            resultInSubtree = nearestInSubtree(subtree.left, searchPoint);
            secondRect = subtree.rightRect();
        }
        else
        {
            resultInSubtree = nearestInSubtree(subtree.right, searchPoint);
            secondRect = subtree.leftRect();
        }

        if (searchPoint.distanceSquaredTo(subtree.p) > searchPoint.distanceSquaredTo(resultInSubtree))
            result = resultInSubtree;

        if (searchPoint.distanceSquaredTo(result) < secondRect.distanceSquaredTo(searchPoint))
            return result;

        if (isSmaller)
            resultInSubtree = nearestInSubtree(subtree.right, searchPoint);
        else
            resultInSubtree = nearestInSubtree(subtree.left, searchPoint);

        if (searchPoint.distanceSquaredTo(result) > searchPoint.distanceSquaredTo(resultInSubtree))
            result = resultInSubtree;

        return result;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        if (root == null) return null;

        return nearestInSubtree(root, p);
    }


}