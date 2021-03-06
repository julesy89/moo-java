package com.msu.moo.fonseca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.Factory;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionSet;

public class HypervolumeTest {

	
	
	@Test
	public void testHVNoRef() {
		Double d = Hypervolume.calculate(Factory.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}}));
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd() {
		SolutionSet<MockSolution>  s = Factory.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = Hypervolume.calculate(s, new ArrayList<Double>(Arrays.asList(1.0,1.0)));
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd2() {
		SolutionSet<MockSolution>  s = Factory.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = Hypervolume.calculate(s, new ArrayList<Double>(Arrays.asList(0.0,0.0)));
		assertTrue(d == null);
	}
	
	
	
	
}
