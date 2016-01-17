package com.msu.moo.model;


import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.exceptions.EvaluationException;

/**
 * The Evaluator class should be used for each algorithm to evaluate the result
 * of an object of type IVariable.
 * 
 * It is not forbidden to use the problem itself directly, but the evaluator is
 * counting the evaluations and also some other feature like hashing results
 * might be implemented.
 *
 */
public class Evaluator implements IEvaluator {

	// ! current amount of evaluations
	protected int evaluations = 0;	

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;
	
	// ! current amount of evaluations
	protected Evaluator father = null;

	
	public Evaluator(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	@SuppressWarnings("unchecked")
	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {
		
		if (evaluations >= (int) (maxEvaluations * 1.20)) 
			throw new EvaluationException("Evaluations expired. Check hasNext() first.");
		
		++evaluations;
		if (father != null) father.evaluations++;
		
		
		// cast the problem to the specific one
		IProblem<V> p = null;
		try {
			p = (IProblem<V>) problem;
		} catch (Exception e) {
			throw new EvaluationException("Problem does not accept the given variable!");
		}
		return p.evaluate(variable);
	}

	public int getEvaluations() {
		return evaluations;
	}


	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext() {
		if (maxEvaluations == null)
			return true;
		return evaluations <= maxEvaluations;
	}

	public Integer getMaxEvaluations() {
		return maxEvaluations;
	}

	
	public Evaluator createChildEvaluator(int maxEvaluations) {
		Evaluator eval = new Evaluator(maxEvaluations);
		eval.father = this;
		return eval;
	}
	
	public void increase() {
		++this.evaluations;
		if (getFather() != null) getFather().evaluations++;
	}

	public Evaluator getFather() {
		return father;
	}

	@Override
	public Integer numOfEvaluations() {
		return evaluations;
	}
	
	
	

}