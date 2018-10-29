package Template;

import java.util.Collection;
import java.util.function.Function;

public class ConcreteGeneticTemplate extends GeneticTemplate {

	@Override
	public Collection<?> Evaluation(Collection<?> population, Function<?, ?> evalFunc) 
	{
		return population;
		
	}

	@Override
	public Collection<?> Selection(Collection<?> population) 
	{
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public void CrossBeed() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Replace() 
	{
		// TODO Auto-generated method stub
		
	}	
}
