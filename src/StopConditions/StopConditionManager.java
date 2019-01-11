package StopConditions;

import java.util.ArrayList;
import java.util.List;

import Model.Individual;

/**
 * Manager for algorithm stop conditions
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux *
 */
public class StopConditionManager {

	private List<StopCondition> _stopConditions = null;
	private List<Individual> _currentPopulation = null;
	private int _iterationCounter = 0;
	
	/**
	 * Constructor
	 */
	public StopConditionManager() {
		_stopConditions = new ArrayList<StopCondition>();
	}
	
	/**
	 * Add a stop condition to the manager
	 * @param sc : stop condition to add
	 */
	public void AddStopCondition(StopCondition sc)
	{
		_stopConditions.add(sc);
	}
	
	/**
	 * Check all conditions added to the manager
	 * @param population : population to check
	 * @return Yes / No
	 */
	public boolean CheckConditions(List<Individual> population)
	{
		boolean check = true;
		int length = _stopConditions.size();
		int i = 0;
		
		_currentPopulation = population;
		_iterationCounter++;
		
		do{
			check &= _stopConditions.get(i).CheckCondition();
			i++;			
		}while(check && i<length);	
				
		return check;
	}
	
	/**
	 * Get the current population to check
	 * @return population
	 */
	public List<Individual> GetCurrentPopulation()
	{
		return _currentPopulation;
	}
	
	/**
	 * Get the current iteration counter in the algorithm loop
	 * @return iteration counter
	 */
	public int GetCurrentIteration()
	{
		return _iterationCounter;
	}
}
