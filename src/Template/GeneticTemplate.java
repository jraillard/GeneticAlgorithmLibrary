package Template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import Builder.IPopulationBuilder;
import Builder.PopulationBuilder;
import GeneticEnum.ReplaceEnum;
import GeneticEnum.SelectionEnum;
import Model.Individual;
import Replace.IReplaceStrategy;
import Replace.LowestReplaceStrategy;
import Replace.RandomReplaceStrategy;
import Selection.ISelectionStrategy;
import Selection.BestFitnessSelectionStrategy;
import Selection.RandomSelectionStrategy;

public abstract class GeneticTemplate {

	protected ISelectionStrategy _selectionStrategy;
	protected IReplaceStrategy _replaceStrategy;	
	private IPopulationBuilder _populationBuilder;	
	
	public GeneticTemplate()
	{
		_selectionStrategy = null;
		_replaceStrategy = null;
		_populationBuilder = new PopulationBuilder();
	}
	
	public Collection<Individual> Compute(
			Individual individualModel,
			int populationCount,
			SelectionEnum selectionStrategy,
			ReplaceEnum replaceStrategy,
			Function<Individual[], Individual[]> crossBeedingFunc,
			int stopIteDuration,
			int stopIteMax)
	{

		//1. Population init
		Collection<Individual> returnedPopulation = new ArrayList<>();
		
		//2. Strategy init
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
				return returnedPopulation;				
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
					return returnedPopulation;		
		}
		
		
		int iterationCounter = 0;
		Collection<Individual> selectedPop = null;
		while(iterationCounter < stopIteMax)
		{
			returnedPopulation = Evaluate(returnedPopulation);
			
			selectedPop = _selectionStrategy.Selection();
			
			/* CrossBeed, Mutation and Replace */
		}
		
		return returnedPopulation;
	}
	
		
	public abstract Collection<Individual> Evaluate(Collection<Individual> population);		
	public abstract Collection<Individual> CrossBeed(Collection<Individual> selectedPopulation);
	public abstract Collection<Individual> Mutate(Collection<Individual> selectedPopulation);
	
	public abstract void Replace();
}
