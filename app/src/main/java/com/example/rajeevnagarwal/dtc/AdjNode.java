package com.example.rajeevnagarwal.dtc;

public class AdjNode {
	private Node v;
	private Distance dist;
	public AdjNode(Node v,Distance dist)
	{
		this.v = v;
		this.dist = dist;
	}
	public AdjNode(Node v,int val,String unit)
	{
		this.v = v;
		this.dist = new Distance(val,unit);
	}
	public Node getV()
	{
		return this.v;
	}
	public Distance getDist()
	{
		return this.dist;
	}

}
