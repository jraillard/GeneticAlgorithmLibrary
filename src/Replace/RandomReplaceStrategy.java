package Replace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Comparators.SortByFitnessDesc;
import Model.Individual;

/***
 * Random strategy for replacement
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class RandomReplaceStrategy implements IReplaceStrategy {

	@Override
	public List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population){
		
		List<Integer> idsSelected = new ArrayList<>();
		Random rand = new Random();
		boolean randomIdKo = true;
		int populationLength = population.size();
		int tempIdSelected = -1;
		
		// Put the better Fitness at first place
		population.sort(new SortByFitnessDesc());
		// Make it non-changeable
		idsSelected.add(0);
		
		for(Individual i : newIndividuals)
		{
			while(randomIdKo)
			{
				tempIdSelected = rand.nextInt(populationLength);
				randomIdKo &= idsSelected.contains(tempIdSelected);
			}
			
			// Disable possibility to replace new individual by another new individual
			idsSelected.add(tempIdSelected); 	
			// Replace by new individual
			population.set(tempIdSelected, i);			
			randomIdKo = true;
		}			
			
		return population;		
	}
}