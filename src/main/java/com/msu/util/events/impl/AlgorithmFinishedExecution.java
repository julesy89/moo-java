package com.msu.util.events.impl;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.util.events.IEvent;

public class AlgorithmFinishedExecution implements IEvent {

	protected AExperiment experiment;

	protected IProblem problem;

	protected IAlgorithm algorithm;

	public AlgorithmFinishedExecution(AExperiment experiment, IProblem problem, IAlgorithm algorithm) {
		super();
		this.experiment = experiment;
		this.problem = problem;
		this.algorithm = algorithm;
	}

	public AExperiment getExperiment() {
		return experiment;
	}

	public IProblem getProblem() {
		return problem;
	}

	public IAlgorithm getAlgorithm() {
		return algorithm;
	}
	
	

}