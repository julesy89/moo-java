package com.msu.moo.algorithms.nsgaII;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.AMultiObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.MyRandom;

/**
 * This algorithm is implemented in the base of NSGAII proposed by Professor Deb
 * in "A fast and elitist multiobjective genetic algorithm: NSGA-II".
 * 
 *
 */
public class NSGAII<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveEvolutionaryAlgorithm<V,P> {

	// ! rank for the whole population
	protected Map<Solution<V>, Integer> rank;

	// ! crowding distance for the whole population
	protected Map<Solution<V>, Double> crowding;
	
	

	// ! private constructor! use the builder!
	protected NSGAII() {
	}

	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator evaluator, MyRandom rand) {

		// initialize the population and calculate also rank and crowding
		initializePopulation(problem, evaluator, rand);
		
		
		while (evaluator.hasNext()) {

			// binary tournament selection for mating
			BinaryTournamentSelection<V> bts = new BinaryTournamentSelection<V>(population,
					new RankAndCrowdingComparator<V>(rank, crowding), rand);

			// create offspring population until size two times
			SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);
			while (offsprings.size() < populationSize) {
				// crossover
				List<V> off = crossover.crossover(bts.next().getVariable(), bts.next().getVariable(), rand);
				// mutation
				for (V offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						mutation.mutate(offspring, rand);
					}
					
					// evaluate directly or perform local optimization
					Solution<V> s = null;
					if (local != null && evaluator.hasNext()) {
						s = local.run(problem, evaluator, offspring);
					} else {
						s = evaluator.evaluate(problem, offspring);
					}
					
					offsprings.add(s);
				}
			}

			// merge population and offsprings
			population.addAll(offsprings);


			
			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet<V>(new HashSet<>(population));
			
			// survival of the best population
			calcRankAndCrowding(population);
			population.sort(new RankAndCrowdingComparator<V>(rank, crowding));
			Collections.reverse(population);
			population = new SolutionSet<V>(population.subList(0, Math.min(populationSize, population.size())));

			evaluator.notify(population);
			
		}

		NonDominatedSolutionSet<V> result = new NonDominatedSolutionSet<V>();
		result.addAll(population);
		return result;

	}

	protected void initializePopulation(P problem, IEvaluator evaluator, MyRandom rand) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet<V>(populationSize * 2);

		for (int i = 0; i < populationSize; i++) {
			population.add(evaluator.evaluate(problem, factory.next(rand)));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	protected void calcRankAndCrowding(SolutionSet<V> population) {
		NonDominatedRankIndicator rankInd = new NonDominatedRankIndicator();
		rank = rankInd.calculate(population);
		crowding = new HashMap<>();
		Collection<NonDominatedSolutionSet<V>> fronts = new NaiveNonDominatedSorting().run(population);
		for (NonDominatedSolutionSet<V> set : fronts) {
			crowding.putAll(new CrowdingIndicator().calculate(set.getSolutions()));
		}
	}


}
