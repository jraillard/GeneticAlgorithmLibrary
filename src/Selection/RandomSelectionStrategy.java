package Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Individual;

/***
 * Random strategy for selection
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class RandomSelectionStrategy implements ISelectionStrategy{

	@Override
	public List<Individual> Selection(List<Individual> population, int nbParent) {

		List<Individual> selectedPopulation = new ArrayList<>();
		List<Integer> idsSelected = new ArrayList<>();
		Random rand = new Random();
		boolean randomIdOk = false;
		int populationLength = population.size();
		int tempIdSelected = -1;
		
		for(int i=0; i<nbParent; i++)
		{
			while(!randomIdOk)
			{
				tempIdSelected = rand.nextInt(populationLength);
				randomIdOk &= idsSelected.contains(tempIdSelected);
			}
			
			idsSelected.add(tempIdSelected);
			selectedPopulation.add(population.get(tempIdSelected));
			randomIdOk = false;
		}		
		
		return selectedPopulation;
	}
}