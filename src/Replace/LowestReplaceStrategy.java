package Replace;

import java.util.List;
import Comparators.SortByFitnessDesc;
import Model.Individual;

/***
 * Lowest strategy for replacement
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class LowestReplaceStrategy implements IReplaceStrategy{

	@Override
	public List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population){
				
		//Order to put the lowest fitness at first
		population.sort(new SortByFitnessDesc());
		for(Individual i : newIndividuals)
		{
			population.remove(0);
			population.add(i);
		}
		
		return population;		
	}	
}