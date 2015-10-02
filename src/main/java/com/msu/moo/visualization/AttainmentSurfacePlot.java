package com.msu.moo.visualization;

import java.util.Collection;

import com.msu.moo.Configuration;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.ProblemFinishedEvent;
import com.msu.moo.util.plots.ScatterPlot;

public class AttainmentSurfacePlot extends AVisualize implements IListener<ProblemFinishedEvent>{

	
	public AttainmentSurfacePlot() {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
	}


	@Override
	public void update(ProblemFinishedEvent event) {
		IProblem problem = event.getProblem();
		AExperiment experiment = event.getExperiment();
		
		EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.PATH_TO_EAF);
		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			Collection<NonDominatedSolutionSet> set = experiment.getResult().get(problem, algorithm);
			NonDominatedSolutionSet median = eaf.calculate(set);
			experiment.getResult().add(problem, algorithm, median, "median");
			sp.add(median, algorithm.toString());
		}
		showOrPrint(sp, String.format("%s-eaf", problem));
		
	}




}