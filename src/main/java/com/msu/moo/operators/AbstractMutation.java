package com.msu.moo.operators;

import com.msu.moo.model.Evaluator;
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

	@SuppressWarnings("unchecked")
	public void mutate(Object obj) {
		T element = null;
		
		try {
			element = (T) obj;
	    } catch (ClassCastException e) {
	    	throw new RuntimeException("This Mutation is not allowed for this variable!");
	    }
		
		mutate_(element);
	}
	
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

	public <V extends IVariable, P extends IProblem<V, P>> Solution mutate(Evaluator<V, P> eval, Solution a) {
		IVariable var = mutate(a.getVariable());
		return eval.run(var);
	}
	
	
	abstract protected void mutate_(T element);


}
