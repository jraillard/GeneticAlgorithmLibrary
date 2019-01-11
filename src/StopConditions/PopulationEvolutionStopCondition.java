package StopConditions;

import java.util.List;

import Comparators.SortByFitnessAsc;
import Model.Individual;

/**
 * Manage computing stop if population don't evolve anymore
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class PopulationEvolutionStopCondition extends StopCondition {

	private int _stopIteEvolution = 0;
	private double[] _populationFitnessEvolution = null;
	
	public PopulationEvolutionStopCondition(StopConditionManager scm, int stopIteEvolution) {
		super(scm);
		_stopIteEvolution = stopIteEvolution;
		_populationFitnessEvolution = new double[_stopIteEvolution-1];
	}
	
	@Override
	public boolean CheckCondition() {
		
		int currentIterationCounter = _manager.GetCurrentIteration();
		List<Individual> currentPopulation = _manager.GetCurrentPopulation();
		
		// Compute population mean fitness 
		_populationFitnessEvolution[(currentIterationCounter % _stopIteEvolution)] = MedianPopulationFitness(currentPopulation);
		if(currentIterationCounter % _stopIteEvolution == 0)
		{					
			if(!IsPopulationEvolving())
			{
				System.out.println("Iteration n°"+currentIterationCounter+" population doesn't evolve anymore");
				return false;
			}
			else _populationFitnessEvolution = new double[_stopIteEvolution];
		}	
		
		return true;
	}
	
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
		    return (((double) population.get(populationSize/2).GetFitness() + (double) population.get(populationSize/2 - 1).GetFitness())/2);
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
}
