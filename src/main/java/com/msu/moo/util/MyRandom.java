package com.msu.moo.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyRandom {

	// ! the current random object
	protected java.util.Random r;
	
	protected long seed = -1;
	
	
	public MyRandom() {
		r = new Random();
	}

	public MyRandom(long seed) {
		this.seed = seed;
		r = new Random();
		r.setSeed(this.seed);
	}

	/**
	 * Create an Integer without range
	 */
	public Integer nextInt(int max) {
		return r.nextInt(max);
	}

	/**
	 * Create an Integer in range
	 */
	public Integer nextInt(int min, int max) {
		return r.ints(min, max).findFirst().getAsInt();
	}

	/**
	 * Create a double value between 0 and 1.
	 */
	public Double nextDouble() {
		return r.nextDouble();
	}

	/**
	 * Create a double value in range
	 */
	public Double nextDouble(double min, double max) {
		return min + (max - min) * r.nextDouble();
	}

	public void shuffle(List<?> c) {
		Collections.shuffle(c, r);
	}

	public <T> T select(List<T> l) {
		return l.get(r.nextInt(l.size()));
	}

	public long getSeed() {
		return seed;
	}
	

}
