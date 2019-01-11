package StopConditions;

import java.util.List;

import Model.Individual;

/**
 * Manage computing stop if best fitness individual don't change
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux *
 */
public class ChangeBestFitnessStopCondition extends StopCondition {	
				
	private int _stopIteBestFitness = 0;
	private Individual[] _populationBestFitnessEvolution = null;
	
	public ChangeBestFitnessStopCondition(StopConditionManager scm, int stopIteBestFitness)
	{
		super(scm);
		_stopIteBestFitness = stopIteBestFitness;
		_populationBestFitnessEvolution = new Individual[_stopIteBestFitness-1];
	}
	
	@Override
	public boolean CheckCondition() {
		
		int currentIterationCounter = _manager.GetCurrentIteration();
		List<Individual> currentPopulation = _manager.GetCurrentPopulation();
		
		// Get Best Fitness individual
		_populationBestFitnessEvolution[(currentIterationCounter % _stopIteBestFitness)] = PopulationBestFitness(currentPopulation);
		if(currentIterationCounter % _stopIteBestFitness == 0)
		{					
			if(!IsPopulationBestFitnessChanged())
			{
				System.out.println("Iteration n°"+currentIterationCounter+" best fitness doesn't change anymore");
				return false;
			}
			else _populationBestFitnessEvolution = new Individual[_stopIteBestFitness];
		}		
		return true;
	}
	
	/**
	 * Get the best fitness individual
	 * @param population
	 * @return Best fitness individual
	 */ 
	private Individual PopulationBestFitness(List<Individual> population)
	{
		Individual tempInd = population.get(0);
		
		for(Individual i : population)
		{
			if(i.GetFitness() >= tempInd.GetFitness()) 
				tempInd = i; 
		}
		
		return tempInd;
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
}
