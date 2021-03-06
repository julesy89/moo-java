package com.msu.moo.operators.crossover;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.moo.operators.crossover.permutation.PMXCrossover;

public class PMXCrossoverTest {

	private class PMXCrossoverMock extends PMXCrossover<Integer> {

		public List<Integer> crossover_(List<Integer> p1, List<Integer> p2, int lb, int ub) {
			return super.crossover_(p1, p2, lb, ub);
		}

	}

	PMXCrossoverMock c = new PMXCrossoverMock();


	
	@Test
	public void testPMXCrossover2() {
		List<Integer> a = new ArrayList<>(Arrays.asList(8,4,7,3,6,2,5,1,9,0));
		List<Integer> b = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
		assertEquals(new ArrayList<Integer>(Arrays.asList(0,7, 4, 3, 6, 2, 5, 1, 8, 9)), c.crossover_(a, b, 3, 7));
		assertEquals(new ArrayList<Integer>(Arrays.asList(8,2,1,3,4,5,6,7,9,0)), c.crossover_(b, a, 3, 7));
	}


}
