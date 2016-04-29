package com.example.rajeevnagarwal.dtc;

public class Link {
	private Node from;
	private Node to;
	private Distance dist;
	public Link(Node from, Node to,Distance dist)
	{
		this.from = from;
		this.to = to;
		this.dist = dist;
	}
	public Link(Node from, Node to,int val,String unit)
	{
		this.from = from;
		this.to = to;
		this.dist = new Distance(val,unit);
	}
	
	public Node getFrom()
	{
		return this.from;
	}
	public Node getTo()
	{
		return this.to;
	}
	public Distance getDist()
	{
		return this.dist;
	}

}
