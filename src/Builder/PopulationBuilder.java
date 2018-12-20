package Builder;

import java.util.ArrayList;
import java.util.List;

import Model.Individual;

/***
 * Population builder interface implementation
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class PopulationBuilder implements IPopulationBuilder {

	@Override
	public List<Individual> BuildPopulation(Individual model, int populationCount) {
		
		List<Individual> newPopulation = new ArrayList<Individual>();
		
		for(int i=0; i<populationCount; i++)
		{
			newPopulation.add(model.CreateIndividual());
		}
		
		return newPopulation;
	}
}