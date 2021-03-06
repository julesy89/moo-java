package com.msu.moo.experiment.impl;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT3;
import com.msu.moo.util.Range;

public class ZDTExperiment extends AExperiment<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT> {



	@Override
	protected void setAlgorithms(AbstractZDT problem,
			List<IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT>> algorithms) {
	
		Range<Double> range = problem.getRange();

		NSGAIIBuilder<DoubleListVariable, AbstractZDT> nsgaII = new NSGAIIBuilder<>();
		nsgaII
		.set("probMutation", 1.0)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new PolynomialMutation(range));
		
		algorithms.add(nsgaII.build());
		
		//algorithms.add(new RandomSearch<>(new DoubleListVariableFactory(range)));
	}
	

	
	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		problems.add(new ZDT3());
	}

	@Override
	protected void analyse(ExperimentCallback<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT> c) {
		Double hv = Hypervolume.calculate(c.result.getSolutions(), Arrays.asList(1.0, 1.0));
		System.out.println(String.format("%s,%f", c.algorithm, hv));
	}



}
