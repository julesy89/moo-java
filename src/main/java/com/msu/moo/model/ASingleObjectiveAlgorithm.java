package com.msu.moo.model;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.ISingleObjectiveAlgorithm;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
