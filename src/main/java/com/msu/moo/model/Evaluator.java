package com.msu.moo.model;

import java.util.List;

import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * Additionally, the class provides evaluation counting and returns hashed
 * results immediately, what ensures that no result is calculated twice.
 * 
 * This only works if you override the hash function for you variable correctly!
 * 
 */
public abstract class Evaluator<V extends IVariable, P extends IProblem<V,P>>  {
	
	//! the problem which the evaluator should evaluate
	protected P problem;
	
	//! number of evaluations so far
	protected long numOfEvaluations = 0;
	

	public Evaluator(P problem) {
		super();
		this.problem = problem;
	}


	public Solution run(IVariable variable) {
		// increase amount of evaluations
		++numOfEvaluations;
		
		try { 
			
			@SuppressWarnings("unchecked")
			V varToEvaluate =  (V) variable;
			
			// TODO: Hash the result value and return it directly if it fits!
			return new Solution(variable, evaluate(problem, varToEvaluate));
			
		} catch (Exception e) {
			throw new RuntimeException("Could not evaluate variable. Wrong Type!");
		}
			
	}
	

	public long count() {
		return numOfEvaluations;
	}

	
	abstract protected <T> List<Double> evaluate(P problem, V variable);



}
