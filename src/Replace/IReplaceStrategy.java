package Replace;

import java.util.List;

import Model.Individual;

public interface IReplaceStrategy {

	public List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population);
}
