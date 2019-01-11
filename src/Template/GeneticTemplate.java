package Template;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Builder.IPopulationBuilder;
import Builder.PopulationBuilder;
import Comparators.SortByFitnessAsc;
import GeneticEnum.ReplaceEnum;
import GeneticEnum.SelectionEnum;
import Model.Individual;
import Replace.IReplaceStrategy;
import Replace.LowestReplaceStrategy;
import Replace.RandomReplaceStrategy;
import Selection.ISelectionStrategy;
import Selection.BestFitnessSelectionStrategy;
import Selection.RandomSelectionStrategy;
import StopConditions.ChangeBestFitnessStopCondition;
import StopConditions.IteMaxStopCondition;
import StopConditions.PopulationEvolutionStopCondition;
import StopConditions.StopConditionManager;

/***
 * Genetic template algorithm
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public abstract class GeneticTemplate {

	private ISelectionStrategy _selectionStrategy;
	private IReplaceStrategy _replaceStrategy;	
	private IPopulationBuilder _populationBuilder;		
	private StopConditionManager _stopConditionManager;
	
	/***
	 * Constructor
	 */
	public GeneticTemplate()
	{
		_selectionStrategy = null;
		_replaceStrategy = null;
		_stopConditionManager = new StopConditionManager();
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
	 * @param stopIteMax : number of iteration to compute
 	 * @param stopIteEvolution : number of iteration to do if population don't evolve anymore
 	 * @param stopIteBestFitness : number of iteration to do if best fitness don't change
	 * @return Optimized population 
	 */
	public List<Individual> Compute(
			Individual individualModel,
			int populationCount,
			int childrenToGenerate,
			SelectionEnum selectionStrategy,
			int mutationProbability,
			ReplaceEnum replaceStrategy,
			int stopIteMax, 
			int stopIteEvolution,
			int stopIteBestFitnessChanged)
	{
		if(!CheckEntries(populationCount, childrenToGenerate, mutationProbability, stopIteBestFitnessChanged, stopIteEvolution, stopIteMax, 
				selectionStrategy, replaceStrategy)) { 
			System.out.println("Entries uncorrect");
			return null;
		}		
		
		//Population init
		List<Individual> population = _populationBuilder.BuildPopulation(individualModel, populationCount);
		List<Individual> tempPop = null;
		
		do{
			//1. Evaluate
			population = Evaluate(population);
			//2. Selection
			tempPop = _selectionStrategy.Selection(population, childrenToGenerate+1);
			//3. CrossBeed/Mutation	on selectedPop	
			tempPop = CrossAndMutate(tempPop, childrenToGenerate, mutationProbability);
			//4. Replacement
			population = _replaceStrategy.Replace(tempPop, population);			
			
		}while(_stopConditionManager.CheckConditions(population));
		
		return population;
	}	

	/***
	 * Compute Genetic Algorithm with default mutation probability
	 * @param individualModel : individual model
	 * @param populationCount : number of individual in the population
	 * @param childrenToGenerate : number of children to generate at every iteration
	 * @param selectionStrategy : selection strategy 
	 * @param replaceStrategy : replace strategy
	 * @param stopIteMax : number of iteration to compute
 	 * @param stopIteEvolution : number of iteration to do if population don't evolve anymore
 	 * @param stopIteBestFitness : number of iteration to do if best fitness don't change
	 * @return Optimized population 
	 */
	public List<Individual> Compute(
				Individual individualModel,
				int populationCount,
				int childrenToGenerate,
				SelectionEnum selectionStrategy,
				ReplaceEnum replaceStrategy,
				int stopIteMax,
				int stopIteEvolution,
				int stopIteBestFitness)
	{
		return Compute(individualModel, populationCount, childrenToGenerate, selectionStrategy, 3, replaceStrategy,  stopIteMax, stopIteEvolution, stopIteBestFitness);
	}
	
	/***
	 * Evaluate the population
	 * @param population
	 * @return
	 */
	protected abstract List<Individual> Evaluate(List<Individual> population);		
	
	/***
	 * Create children by crossing or mutation in the selected population
	 * @param selectedPopulation : selected population
	 * @param nbChildrenToGenerate : number of children to generate
	 * @param mutationProbability : mutation probability
	 * @return Children list
	 */
	private List<Individual> CrossAndMutate(List<Individual> selectedPopulation, int nbChildrenToGenerate, int mutationProbability)
	{
		List<Individual> newIndividuals = new ArrayList<>();
		Random rand = new Random();
		int tempRandValue = 0;
				
		for(int i=0; i< nbChildrenToGenerate; i++)
		{
			tempRandValue = rand.nextInt(100)+1;
			//Probability to get mutation
			if(1 >= tempRandValue && tempRandValue <= mutationProbability) 
				newIndividuals.add(selectedPopulation.get(i).Mutate());	
			else
				newIndividuals.add(selectedPopulation.get(i).Crossing(selectedPopulation.get(i+1)));
		}
		
		return newIndividuals;
	}
	
	
	/**
	 * Check the content of the compute method entries
	 * @param populationCount : number of individual in the population
	 * @param childrenToGenerate : number of children to generate at every iteration
	 * @param mutationProbability
	 * @param stopIteMax: number of iteration to compute
 	 * @param stopIteEvolution : number of iteration to do if population don't evolve anymore
	 * @param selectionStrategy : selection strategy 
	 * @param replaceStrategy : replace strategy
	 * @return Entries validity
	 */
	private boolean CheckEntries(int populationCount, int childrenToGenerate, int mutationProbability, int stopIteBestFitness, int stopIteEvolution, int stopIteMax, 
			SelectionEnum selectionStrategy, ReplaceEnum replaceStrategy) {

		// Check mutation probability value 
		if(mutationProbability < 1 || mutationProbability > 100) {
			System.out.println("Please enter a mutation probability between 1 and 100");
			return false;
		}	
		
		// Check children to generate number to be lower than individual number
		if(childrenToGenerate >= populationCount) {
			System.out.println("Please enter a number of children generated lower than the population");
			return false;
		}	
		
		// Check stopIteMax to be greater than 0
		if(stopIteMax <= 0){
			System.out.println("StopItem must be greater than 0");
			return false;
		}else
		{
			_stopConditionManager.AddStopCondition(new IteMaxStopCondition(_stopConditionManager, stopIteMax));
		}
		
		// Check stopIteEvolution to be lower than stopIteMax and positive
		if(stopIteEvolution >= stopIteMax) {
			System.out.println("StopIteEvolution must be lower than StopIteMax");
			return false;
		}else if(stopIteEvolution > 0)  { 
			_stopConditionManager.AddStopCondition(new PopulationEvolutionStopCondition(_stopConditionManager, stopIteEvolution));
		}
		
		// Check stopIteBestFitness to be lower than stopIteMax and positive 
		if(stopIteBestFitness >= stopIteMax) {
			System.out.println("StopIteBestFitness must be lower than StopIteMax");
			return false;
		}else if(stopIteBestFitness > 0)  { 
			_stopConditionManager.AddStopCondition(new ChangeBestFitnessStopCondition(_stopConditionManager, stopIteBestFitness));
		}
		
		// Strategy init
		switch(selectionStrategy)
		{
			case BestFitness : 
				_selectionStrategy = new BestFitnessSelectionStrategy();
				break;
			case Random : 
				_selectionStrategy = new RandomSelectionStrategy();
				break;
			default : 
				System.out.println("Please define an available selection strategy");
				return false;				
		}
		
		switch(replaceStrategy)
		{
			case Lowest : 
				_replaceStrategy = new LowestReplaceStrategy();
				break;
			case Random : 
				_replaceStrategy = new RandomReplaceStrategy();
				break;
			default: 
				System.out.println("Please define an available replace strategy");
				return false;	
		}
		
		return true;		
	}
}