package Model;

import java.util.Comparator;

public abstract class Individual {

	protected float _fitness;
	
	
	public void SetFitness(float f)
	{
		_fitness = f;
	}
	
	public float GetFitness()
	{
		return _fitness;
	}
	
	//defined by user by extending
	public abstract Individual CreateIndividual();
	public abstract void Evaluate();
	public abstract Individual Mutate();
	public abstract Individual[] CrossBeed(Individual indtoCross);
}