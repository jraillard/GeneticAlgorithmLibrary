package Model;

public abstract class Individual implements Runnable{

	protected float _fitness;
	
	public void SetFitness(float f)
	{
		_fitness = f;
	}
	
	public float GetFitness()
	{
		return _fitness;
	}
	
	//Make Evaluation Threadable
	public void run() { Evaluate(); }
	public abstract void Evaluate();
	
	public abstract Individual CreateIndividual();
	public abstract Individual Mutate();
	public abstract Individual CrossBeed(Individual indtoCross);
}