package Template;

import java.util.Arrays;
import java.util.List;

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

/***
 * Genetic template algorithm
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public abstract class GeneticTemplate {

	private ISelectionStrategy _selectionStrategy;
	private IReplaceStrategy _replaceStrategy;	
	private IPopulationBuilder _populationBuilder;	
	
	private boolean _stopIteEvolutionDefined;
	private boolean _stopIteBestFitnessDefined;
	private double[] _populationFitnessEvolution;
	private Individual[] _populationBestFitnessEvolution;
	
	/***
	 * Constructor
	 */
	public GeneticTemplate()
	{
		_selectionStrategy = null;
		_replaceStrategy = null;
		_populationBuilder = new PopulationBuilder();
		_stopIteEvolutionDefined = false;
		_stopIteBestFitnessDefined = false;
		_populationFitnessEvolution = null;
		_populationBestFitnessEvolution = null;
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
				selectionStrategy, replaceStrategy)) { System.out.println("Entries uncorrect"); }		
		
		//Population init
		List<Individual> population = _populationBuilder.BuildPopulation(individualModel, populationCount);
		
		int iterationCounter = 1;
		List<Individual> tempPop = null;
		while(iterationCounter <= stopIteMax)
		{
			//1. Evaluate
			population = Evaluate(population);
			//2. Selection
			tempPop = _selectionStrategy.Selection(population, childrenToGenerate+1);
			//3. CrossBeed/Mutation	on selectedPop	
			tempPop = CrossAndMutate(tempPop, childrenToGenerate, mutationProbability);
			//4. Replacement
			population = _replaceStrategy.Replace(tempPop, population);
			
			// Manage computing stop if population don't evolve anymore
			if(_stopIteEvolutionDefined)
			{
				// Compute population mean fitness 
				_populationFitnessEvolution[(iterationCounter % stopIteEvolution)] = MedianPopulationFitness(population);
				if(iterationCounter % stopIteEvolution == 0)
				{					
					if(!IsPopulationEvolving())
					{
						System.out.println("Iteration n°"+iterationCounter+" population doesn't evolve anymore");
						return population;
					}
					else _populationFitnessEvolution = new double[stopIteEvolution];
				}				
			}
			
			// Manage computing stop if best fitness individual don't change
			if(_stopIteBestFitnessDefined)
			{
				// Get Best Fitness individual
				_populationFitnessEvolution[(iterationCounter % stopIteBestFitnessChanged)] = PopulationBestFitness(population);
				if(iterationCounter % stopIteBestFitnessChanged == 0)
				{					
					if(!IsPopulationBestFitnessChanged())
					{
						System.out.println("Iteration n°"+iterationCounter+" best fitness doesn't change anymore");
						return population;
					}
					else _populationBestFitnessEvolution = new Individual[stopIteBestFitnessChanged];
				}		
			}
			
			iterationCounter++;
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
	protected abstract List<Individual> CrossAndMutate(List<Individual> selectedPopulation, int nbChildrenToGenerate, int mutationProbability);		
	
	/**
	 * Compute the population median value
	 * @param population
	 * @return Median value
	 */
	private double MedianPopulationFitness(List<Individual> population)
	{
		population.sort(new SortByFitnessAsc());
		int populationSize = population.size();
		
		if (populationSize % 2 == 0)
		    return ((double) population.get(populationSize/2).GetFitness() + (double) population.get(populationSize/2 - 1).GetFitness()/2);
		else
		    return (double) population.get(populationSize/2).GetFitness();
	}
	
	/**
	 * Determine if the population evolved 
	 * @return Yes / No
	 */
	private boolean IsPopulationEvolving()
	{
		// if median evolve less than 5% => consider that population don't evolve anymore
		double threshold = 1.05f;
		
		if(_populationFitnessEvolution[_populationFitnessEvolution.length-1] / _populationFitnessEvolution[0] < threshold) 
			return false;
		
		return true;		
	}
	
	/**
	 * Get the best fitness individual
	 * @param population
	 * @return Best fitness individual
	 */ 
	private double PopulationBestFitness(List<Individual> population)
	{
		Individual tempInd = population.get(0);
		
		for(Individual i : population)
		{
			if(i.GetFitness() >= tempInd.GetFitness()) 
				tempInd = i; 
		}
		
		return tempInd.GetFitness();
	}

	/**
	 * Determine if the best fitness individual changed
	 * @return Yes / No
	 */
	private boolean IsPopulationBestFitnessChanged()
	{		
		Individual tempInd = _populationBestFitnessEvolution[0];		
		
		for(Individual i : _populationBestFitnessEvolution)
		{
			if(tempInd != i) return false;
		}
		
		return true;		
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
		
		// Check stopIteEvolution to be lower than stopIteMax and positive
		if(stopIteEvolution >= stopIteMax) {
			System.out.println("StopIteEvolution must be lower than StopIteMax");
			return false;
		}else if(stopIteEvolution > 0)  { 
			_stopIteEvolutionDefined = true; 
			_populationFitnessEvolution = new double[stopIteEvolution];				
		}
		
		// Check stopIteBestFitness to be lower than stopIteMax and positive 
		if(stopIteBestFitness >= stopIteMax) {
			System.out.println("StopIteBestFitness must be lower than StopIteMax");
			return false;
		}else if(stopIteBestFitness > 0)  { 
			_stopIteBestFitnessDefined = true; 
			_populationBestFitnessEvolution = new Individual[stopIteBestFitness];				
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