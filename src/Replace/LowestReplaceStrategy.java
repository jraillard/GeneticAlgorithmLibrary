package Replace;

import java.util.Comparator;
import java.util.List;

import Model.Individual;

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

	class SortByFitnessDesc implements Comparator<Individual>{

		public int compare(Individual a, Individual b) {
			return (int) (b.GetFitness() - a.GetFitness());
		}	
	}
}
