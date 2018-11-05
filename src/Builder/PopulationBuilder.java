package Builder;

import java.util.ArrayList;
import java.util.Collection;

import Model.Individual;

public class PopulationBuilder implements IPopulationBuilder {

	@Override
	public Collection<Individual> BuildPopulation(Individual model, int populationCount) {
		Collection<Individual> newPopulation = new ArrayList<Individual>();
		
		for(int i=0; i<populationCount; i++)
		{
			newPopulation.add(model.CreateIndividual());
		}
		
		return newPopulation;
	}

}
