package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ISolution;

/**
 * Abstracts solution class which implements the Solution interface. If an extra
 * information has to be stored simply inherit from this class.
 *
 * @param <V> variable type
 */
public abstract class ASolution<V> implements ISolution<V> {

	// ! objectives immutable
	protected List<Double> objective;

	// ! this list of doubles allows to safe constraint violations for a
	// solution
	protected List<Double> constraintViolations;

	// ! variable immutable
	protected V variable;

	/**
	 * Create a solution without constraints.
	 */
	public ASolution(V variable, List<Double> objectives) {
		this.variable = variable;
		this.objective = objectives;
		constraintViolations = new ArrayList<Double>();
	}

	/**
	 * Create a solution with constraints
	 */
	public ASolution(V variable, List<Double> objectives, List<Double> constraintViolations) {
		this(variable, objectives);
		this.constraintViolations = constraintViolations;
	}

	/**
	 * @return all objectives
	 */
	public List<Double> getObjectives() {
		return objective;
	}

	/**
	 * @return all variables
	 */
	public V getVariable() {
		return variable;
	}

	public List<Double> getConstraintViolations() {
		return constraintViolations;
	}

	@Override
	public Double getObjective(int n) {
		return objective.get(n);
	}

	@Override
	public int numOfObjectives() {
		return objective.size();
	}

	@Override
	public boolean hasConstrainViolations() {
		return getSumOfConstraintViolation() != 0;
	}

	@Override
	public double getSumOfConstraintViolation() {
		if (constraintViolations.isEmpty())
			return 0d;
		return constraintViolations.stream().mapToDouble(Double::doubleValue).sum();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objective == null) ? 0 : objective.hashCode());
		result = prime * result + ((constraintViolations == null) ? 0 : constraintViolations.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		ASolution<V> other = (ASolution<V>) obj;
		if (constraintViolations == null) {
			if (other.constraintViolations != null)
				return false;
		} else if (!constraintViolations.equals(other.constraintViolations))
			return false;
		if (objective == null) {
			if (other.objective != null)
				return false;
		} else if (!objective.equals(other.objective))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}

	/**
	 * Print only the objectives
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(objective);
		sb.append(",");
		sb.append(variable);
		return sb.toString();
	}

}
