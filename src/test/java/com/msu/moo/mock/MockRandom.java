package com.msu.moo.mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.util.MyRandom;

public class MockRandom extends MyRandom {

	
	Queue<Object> values = null;
	
	
	public MockRandom(List<Object> values) {
		super();
		this.values = new LinkedList<>(values);
	}

	@Override
	public Integer nextInt(int max) {
		return (Integer) values.poll();
	}
	

	@Override
	public Integer nextInt(int min, int max) {
		return (Integer) values.poll();
	}

	@Override
	public Double nextDouble() {
		return (Double) values.poll();
	}

	@Override
	public Double nextDouble(double min, double max) {
		return (Double) values.poll();
	}

	@Override
	public void shuffle(List<?> c) {
		super.shuffle(c);
	}

	@Override
	public <T> T select(List<T> l) {
		return l.get((Integer) values.poll());
	}
	
	

}
