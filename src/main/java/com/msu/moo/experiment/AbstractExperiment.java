package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AbstractExperiment<P extends IProblem>  {

	static final Logger logger = Logger.getLogger(AbstractExperiment.class);

	// ! all algorithms that should be evaluated for this experiment
	protected abstract void setAlgorithms();

	// ! all problem instances that should be solved
	protected abstract void setProblem();

	
	
	// algorithm that should be compared in this experiment
	protected List<IAlgorithm<P>> algorithms = new ArrayList<>();

	// ! problem which should be solved
	protected P problem = null;


	
	public Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> run(long maxEvaluations, int iterations, long seed) {
		
		// initialize values
		setProblem();
		setAlgorithms();
		Random.getInstance().setSeed(seed);

		if (problem == null || algorithms == null || algorithms.size() == 0)
			throw new RuntimeException("Experiment could not be executed. Either problem or algorithms is null!");

		logger.info("Running the experiment.");
		logger.info("Execute Problem: " + problem.toString());

		logger.info("Following Algorithms are used and compared: " + algorithms.toString());

		NonDominatedSolutionSet trueFront = getTrueFront(problem);
		logger.info(String.format("True Front is known: %s", trueFront != null));

		Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> fronts = new HashMap<>();

		// calculate the result for each algorithm
		for (IAlgorithm<P> algorithm : algorithms) {

			logger.info(String.format("Startings runs for %s", algorithm));
			algorithm.setMaxEvaluations(maxEvaluations);

			List<NonDominatedSolutionSet> sets = new ArrayList<>();
			for (int i = 0; i < iterations; i++) {
				NonDominatedSolutionSet set = algorithm.run(problem);
				logger.info(String.format("[%s] Found %s non dominated solutions.", algorithm, set.size()));
				logger.debug(set.toString());
				sets.add(set);
			}
			fronts.put(algorithm, sets);
		}
		logger.info("All fronts were calculate and experiment is finished.");
		return fronts;
	}

	/**
	 * If the true front is known you have to override this method. DEFAULT:
	 * Front is not known and therefore this method returns null.
	 */
	public NonDominatedSolutionSet getTrueFront(P problem) {
		return null;
	}

	public List<IAlgorithm<P>> getAlgorithms() {
		return algorithms;
	}

	public P getProblem() {
		return problem;
	}
	
	
	

}