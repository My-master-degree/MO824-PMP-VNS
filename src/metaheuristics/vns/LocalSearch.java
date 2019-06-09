package metaheuristics.vns;

import problems.Evaluator;

public abstract class LocalSearch<E, S> {

	public abstract S localOptimalSolution(Evaluator<E, S> eval, S solution);
	
	public abstract S randomSolution(Evaluator<E, S> eval, S solution);
	
}
