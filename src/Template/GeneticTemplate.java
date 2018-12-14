package Template;

import java.util.List;
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

/***
 * Genetic template algorithm
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public abstract class GeneticTemplate {

	protected ISelectionStrategy _selectionStrategy;
	protected IReplaceStrategy _replaceStrategy;	
	private IPopulationBuilder _populationBuilder;	
	
	/***
	 * Constructor
	 */
	public GeneticTemplate()
	{
		_selectionStrategy = null;
		_replaceStrategy = null;
		_populationBuilder = new PopulationBuilder();
	}
	
	/***
	 * Compute Genetic Algorithm
	 * @param individualModel : individual model
	 * @param populationCount : number of individual in the population
	 * @param childrenToGenerate : number of children to generate at every iteration
	 * @param selectionStrategy : selection strategy 
	 * @param mutationProbability : probability to mutate (between 1% to 100%)
	 * @param replaceStrategy : replace strategy
	 * @param stopIteDuration : computing time
	 * @param stopIteMax : number of iteration to compute
	 * @return Optimised population 
	 */
	public List<Individual> Compute(
			Individual individualModel,
			int populationCount,
			int childrenToGenerate,
			SelectionEnum selectionStrategy,
			int mutationProbability,
			ReplaceEnum replaceStrategy,
			int stopIteDuration,
			int stopIteMax)
	{
		if(mutationProbability < 1 || mutationProbability > 100)
		{
			System.out.println("Please enter a mutation probability between 1 and 100");
			return null;
		}		
		
		//Population init
		List<Individual> population = _populationBuilder.BuildPopulation(individualModel, populationCount);
		
		//Strategy init
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
		List<Individual> tempPop = null;
		while(iterationCounter < stopIteMax)
		{
			//1. Evaluate
			population = Evaluate(population);
			//2. Selection
			tempPop = _selectionStrategy.Selection(population, childrenToGenerate+1);
			//3. CrossBeed/Mutation	on selectedPop	
			tempPop = CrossBeedAndMutate(tempPop, childrenToGenerate, mutationProbability);
			//4. Replacement
			population = _replaceStrategy.Replace(tempPop, population);
		}
		
		return population;
	}	

	/***
	 * Compute Genetic Algorithm with default mutation probability
	 * @param individualModel : individual model
	 * @param populationCount : number of individual in the population
	 * @param childrenToGenerate : number of children to generate at every iteration
	 * @param selectionStrategy : selection strategy 
	 * @param replaceStrategy : replace strategy
	 * @param stopIteDuration : computing time
	 * @param stopIteMax : number of iteration to compute
	 * @return Optimised population 
	 */
	public List<Individual> Compute(
				Individual individualModel,
				int populationCount,
				int childrenToGenerate,
				SelectionEnum selectionStrategy,
				ReplaceEnum replaceStrategy,
				int stopIteDuration,
				int stopIteMax)
	{
		return Compute(individualModel, populationCount, childrenToGenerate, selectionStrategy, 3, 
							replaceStrategy, stopIteDuration, stopIteMax);
	}
	
	/***
	 * Evaluate the population
	 * @param population
	 * @return
	 */
	protected abstract List<Individual> Evaluate(List<Individual> population);		
	
	/***
	 * Create children by crossbeeding or mutation in the selected population
	 * @param selectedPopulation : selected population
	 * @param nbChildrenToGenerate : number of children to generate
	 * @param mutationProbability : mutation probability
	 * @return Childrens list
	 */
	protected abstract List<Individual> CrossBeedAndMutate(List<Individual> selectedPopulation, int nbChildrenToGenerate, int mutationProbability);		
}
