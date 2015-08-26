package com.msu.moo.operators;

import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * This is an abstract Mutation of an object. This class which inherits from this one
 * could specify which type is expected to mutate. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractMutation<T> {


	public IVariable mutate(IVariable a) {

		try {
			
			IVariable result = a.copy();
			@SuppressWarnings("unchecked")
			T entry = (T) result.get();
			mutate_(entry);
			return result;
			
		} catch (Exception e){
			throw new RuntimeException("Mutation could not be performed. Wrong IVariable types!");
		}

	}

	public <V extends IVariable, P extends IProblem> Solution mutate(P problem, Solution a) {
		IVariable var = mutate(a.getVariable());
		return problem.evaluate(var);
	}
	
	
	abstract protected void mutate_(T element);


}
