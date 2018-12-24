package Model;

/***
 * Individual definition
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public abstract class Individual implements Runnable{

	/**
	 * Individual evaluation score
	 */
	protected float _fitness;
	
	/**
	 * Set method for fitness parameter
	 * @param f
	 */
	public void SetFitness(float f)	{
		_fitness = f;
	}
	
	/***
	 * Get method for fitness parameter
	 * @return
	 */
	public float GetFitness() {
		return _fitness;
	}
	
	/***
	 * Create an individual
	 * @return Single individual
	 */
	public abstract Individual CreateIndividual();
		
	/***
	 * Threaded evaluation 
	 */
	public void run() { Evaluate(); }
	
	/***
	 * Evaluate the current individual
	 */
	public abstract void Evaluate();
		
	/***
	 * Mutate the current individual (parent) to generate another one (children)
	 * @return Children
	 */
	public abstract Individual Mutate();
	
	/***
	 * Crossing the current individual with another (parents) to generate another one (children)
	 * @param indtoCross : second parent to crossing with
	 * @return Children
	 */
	public abstract Individual Crossing(Individual indtoCross);
}