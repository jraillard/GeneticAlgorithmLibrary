package Template;

import java.util.Collection;
import java.util.function.Function;

import Model.Individual;

public class ConcreteGeneticTemplate extends GeneticTemplate {

	@Override
	public Collection<Individual> Evaluate(Collection<Individual> population) {
		for(Individual i : population)
		{
			i.Evaluate();
		}
		return population;		
	}

	@Override
	public Collection<Individual> CrossBeed(Collection<Individual> selectedPopulation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Individual> Mutate(Collection<Individual> selectedPopulation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void Replace() 
	{
		// TODO Auto-generated method stub
		
	}	
}
