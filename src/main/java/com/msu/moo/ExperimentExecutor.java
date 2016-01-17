package com.msu.moo;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.ObjectFactory;

/**
 * This here is the central Experiment Executor. This avoids that each
 * experiment contains its own main method.
 * 
 * The experiment is factored by the given string. Furthermore, the iterations,
 * maxEvaluations and the random seed has to be set.
 * 
 * EXERIMENTS AVAILABLE
 * KursaweExperiment ZDTExperiment
 * 
 */
public class ExperimentExecutor {

	// ! experiment that should be executed
	protected final static String EXPERIMENT = "com.msu.moo.experiment.impl.KursaweExperiment";

	// ! number of iterations per experiment
	protected final static int ITERATIONS = 1;

	// ! max evaluations per run
	protected final static int MAX_EVALUATIONS = 50000;

	// ! random seed for experiment execution
	protected final static long SEED = 123456;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		BasicConfigurator.configure();
		AMultiObjectiveExperiment<IVariable, IProblem<IVariable>> experiment = ObjectFactory.create(AMultiObjectiveExperiment.class, EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
	}

}