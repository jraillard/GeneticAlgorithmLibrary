package Template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	
	public List<Individual> Compute(
			Individual individualModel,
			int populationCount,
			int childrenToGenerate,
			SelectionEnum selectionStrategy,
			ReplaceEnum replaceStrategy,
			Function<Individual[], Individual[]> crossBeedingFunc,
			int stopIteDuration,
			int stopIteMax)
	{

		//1. Population init
		List<Individual> population = _populationBuilder.BuildPopulation(individualModel, populationCount);
		
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
		List<Individual> selectedPop = null;
		while(iterationCounter < stopIteMax)
		{
			population = Evaluate(population);
			
			selectedPop = _selectionStrategy.Selection(population, childrenToGenerate+1);
			
			/* CrossBeed, Mutation and Replace */
		}
		
		return population;
	}
	
		
	public abstract List<Individual> Evaluate(List<Individual> population);		
	public abstract List<Individual> CrossBeed(List<Individual> selectedPopulation);
	public abstract List<Individual> Mutate(List<Individual> selectedPopulation);	
	public abstract List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population);
}
