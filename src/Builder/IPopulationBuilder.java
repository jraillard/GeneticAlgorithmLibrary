package Builder;

import java.util.Collection;
import java.util.List;

import Model.Individual;

public interface IPopulationBuilder {

	public List<Individual> BuildPopulation(Individual model, int populationCount);
}
