package Template;

import java.util.Collection;
import java.util.function.Function;

import GeneticEnum.ReplaceEnum;
import GeneticEnum.SelectionEnum;

import Replace.IReplaceStrategy;
import Replace.LowestReplaceStrategy;
import Replace.RandomReplaceStrategy;
import Selection.ISelectionStrategy;
import Selection.BestFitnessSelectionStrategy;
import Selection.RandomSelectionStrategy;

public abstract class GeneticTemplate {

	protected ISelectionStrategy _selectionStrategy;
	protected IReplaceStrategy _replaceStrategy;
	
	public Collection<?> Compute(Collection<?> population, 
			Function<?, ?> evalFunc,
			SelectionEnum selectionStrategy,
			ReplaceEnum replaceStrategy,
			int stopIteDuration,
			int stopIteMax)
	{
		
		// TODO : à voir s'il ne faut pas gérer les switch ailleurs...
		switch(selectionStrategy)
		{
			case BestFitness : 
				_selectionStrategy = new BestFitnessSelectionStrategy();
				break;
			case Random : 
				_selectionStrategy = new RandomSelectionStrategy();
				break;
			default : 
				return population;				
		}
		
		switch(replaceStrategy)
		{
			case Lowest : 
				_replaceStrategy = new LowestReplaceStrategy();
				break;
			case Random : 
				_replaceStrategy = new RandomReplaceStrategy();
				break;
			default :
					return population;		
		}
		
		int iterationCounter = 0;
		Collection<?> selectedPop = null;
		while(iterationCounter < stopIteMax)
		{
			population = Evaluation(population, evalFunc);
			
			selectedPop = Selection(population);
			/* CrossBeed, Mutation and Replace */
		}
		
		return population;
	}
	
	
	public abstract Collection<?> Evaluation(Collection<?> population, Function<?, ?> evalFunc);
	public abstract Collection<?> Selection(Collection<?> population);	
	public abstract void CrossBeed();
	public abstract void Replace();
}
