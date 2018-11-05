package Builder;

import java.util.Collection;

import Model.Individual;

public interface IPopulationBuilder {

	public Collection<Individual> BuildPopulation(Individual model, int populationCount);
}
