package Replace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Individual;

public class RandomReplaceStrategy implements IReplaceStrategy {

	@Override
	public List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population){
		
		List<Integer> idsSelected = new ArrayList<>();
		Random rand = new Random();
		boolean randomIdOk = false;
		int populationLength = population.size();
		int tempIdSelected = -1;
		
		for(Individual i : newIndividuals)
		{
			while(!randomIdOk)
			{
				tempIdSelected = rand.nextInt(populationLength);
				randomIdOk &= idsSelected.contains(tempIdSelected);
			}
			
			// Disable possibility to replace new individual by another new individual
			idsSelected.add(tempIdSelected); 	
			// Replace by new individual
			population.set(tempIdSelected, i);			
			randomIdOk = false;
		}			
			
		return population;		
	}

}
