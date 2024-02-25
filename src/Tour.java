public class Tour 
{
	public static void main(String[] args) {
		//define 4 points forming a square
		Point a = new Point(100.0, 100.0);
		Point b = new Point(500.0, 100.0);
		Point c = new Point(500.0, 500.0);
		Point d = new Point(100.0, 500.0);
		Tour squareTour = new Tour(a, b, c, d);
		squareTour.show();
		System.out.println(squareTour.distance());
		
		StdDraw.setXscale(0, 600);
		StdDraw.setYscale(0, 600);
		squareTour.draw();
	}
	private Node head;
	private int size;

	private class Node {
		Point p;
		Node next;
		
		public Node(Point p) {
			this.p = p;
		}
	}

	/** create an empty tour */
	public Tour()
	{
		head = null;
		size = 0;
	}
	
	/** create a four-point tour, for debugging */
	public Tour(Point a, Point b, Point c, Point d)
	{
		head = new Node(a);
		Node bNode = new Node(b);
		Node cNode = new Node(c);
		Node dNode = new Node(d);
		head.next = bNode;
		bNode.next = cNode;
		cNode.next = dNode;
		dNode.next = head;
		size = 4;
	}
	
	/** print tour (one point per line) to std output */
	public void show()
	{
		if (head == null) {
			return;
		}
		Node temp = head;
		System.out.println(temp.p);
		temp = temp.next;
		while (temp != head) {
			System.out.println(temp.p);
			temp = temp.next;
		}
	}
	
	/** draw the tour using StdDraw */
	public void draw()
	{
		if (head == null) {
			return;
		}
		Node temp = head;
		if (temp.next != null) {
			temp.p.drawTo(temp.next.p);
			temp = temp.next;
		}
		while (temp != head) {
			if (temp.next != null) {
				temp.p.drawTo(temp.next.p);
				temp = temp.next;
			}
		}
	}
	
	/** return number of nodes in the tour */
	public int size()
	{
		return size;
	}
	
	/** return the total distance "traveled", from start to all nodes and back to start */
	public double distance()
	{
		if (head == null) {
			return 0.0;
		}
		Node temp = head;
		double dist = 0.0;
		dist += temp.p.distanceTo(temp.next.p);
		temp = temp.next;
		while (temp != head) {
			dist += temp.p.distanceTo(temp.next.p);
			temp = temp.next;
		}
		return dist;
	}
	
	/** insert p using nearest neighbor heuristic */
    public void insertNearest(Point p) 
    {
		if (head == null) {
			head = new Node(p);
			head.next = head;
		}
		Node temp = head;
		double smallest = temp.p.distanceTo(p);
		Node nearest = head;
		temp = temp.next;
		while (temp != head) {
			double dist = temp.p.distanceTo(p);
			if (dist < smallest) {
				smallest = dist;
				nearest = temp;
			}
			temp = temp.next;
		}
		Node inserted = new Node(p);
		inserted.next = nearest.next;
		nearest.next = inserted;
		size++;
    }

    /** insert p using smallest increase heuristic */
    public void insertSmallest(Point p) 
    {
    	if (head == null) {
    		head = new Node(p);
    		head.next = head;
    	}
    	Node temp = head;
    	Node in = new Node(p);
    	in.next = temp.next;
    	temp.next = in;
    	double smallest = this.distance();
    	temp.next = in.next;
    	Node prev = head;
		temp = temp.next;
		while (temp != head) {
	    	in.next = temp.next;
	    	temp.next = in;
			double dist = this.distance();
	    	temp.next = in.next;
			if (dist < smallest) {
				smallest = dist;
				prev = temp;
			}
			temp = temp.next;
		}
		Node inserted = new Node(p);
		inserted.next = prev.next;
		prev.next = inserted;
		size++;
    }
}