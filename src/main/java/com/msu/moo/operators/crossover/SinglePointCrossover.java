package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.model.ACrossover;
import com.msu.moo.model.variable.ListVariable;
import com.msu.moo.util.MyRandom;

/**
 * This is the single point crossover where a list with any type could but cut
 * in a half and recombined with another half.
 * 
 * [0,1,2,3,4] and [4,3,2,1,0] and point is one leads to
 * 
 * [0] + [3,2,1,0] = [0,3,2,1,0] and [4] + [1,2,3,4] = [4,1,2,3,4]
 * 
 */
public class SinglePointCrossover<T> extends ACrossover<List<T>, ListVariable<T>> {


	public List<List<T>> crossover_(List<T> a, List<T> b, int point) {

		// copy the both list and change values
		List<T> c1 = new ArrayList<T>(a);
		List<T> c2 = new ArrayList<T>(b);
		for (int i = point; i < a.size(); i++) {
			c2.set(i, a.get(i));
			c1.set(i, b.get(i));
		}
		return new ArrayList<>(Arrays.asList(c1, c2));
	}
	
	
	@Override
	public List<List<T>> crossover(List<T> a, List<T> b, MyRandom rand) {
		return crossover_(a, b, rand.nextInt(1, a.size() - 2));

	}



}
