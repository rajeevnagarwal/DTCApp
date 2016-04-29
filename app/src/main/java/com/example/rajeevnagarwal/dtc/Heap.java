package com.example.rajeevnagarwal.dtc;

import java.util.ArrayList;
import java.util.Collections;

public class Heap<T extends Comparable<T>> {
	private ArrayList<T> heap;
	public Heap()
	{
		this.heap = new ArrayList<T>();
	}
	private void PercolateUp(int i)
	{
		if(i==0)
			return ;
		else if(heap.get(i).compareTo(heap.get((i-1)/2))==1)
		{
			return ;
		}
		else
		{
			Collections.swap(heap,i,(i-1)/2);
			PercolateUp((i-1)/2);
		}
		
	}
	private void PercolateDown(int i)
	{
		int largest = i;
		if(2*i+1<heap.size()&&heap.get(i).compareTo(heap.get(2*i+1))==1)
		{
			largest = 2*i+1;
		}
		else if(2*i+2<heap.size()&&heap.get(i).compareTo(heap.get(2*i+2))==1){
			largest = 2*i+2;
		}
		if(largest!=i)
		{
			Collections.swap(heap,i,largest);
			PercolateDown(largest);
		}
		
	}
	public void add(T n)
	{
		heap.add(n);
		PercolateUp(heap.size()-1);
		
	}
	public void remove(T n)
	{
		for(int i=0;i<heap.size();i++)
			if(heap.get(i).equals(n))
				heap.remove(i);
		BuildHeap();
	}
	public void remove(int i)
	{
		heap.remove(i);
		BuildHeap();
	}
	public void BuildHeap()
	{
		for(int i=(heap.size()-1)/2;i>=0;i--)
		{
			PercolateDown(i);
		}
	}
	public T extractMin()
	{
		T n = heap.get(0);
		heap.remove(0);
		return n;		
	}
	public void Update(T n)
	{
		for(int i=0;i<heap.size();i++)
			if(heap.get(i).equals(n))
				heap.remove(i);
		heap.add(n);
		BuildHeap();
	}
	public void print()
	{
		for(int i=0;i<heap.size();i++)
			System.out.println(heap.get(i));
	}
	public boolean isEmpty()
	{
		if(heap.size()!=0)
			return false;
		else 
			return true;
	}
	

}
