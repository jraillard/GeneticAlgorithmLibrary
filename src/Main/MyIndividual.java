package Main;

import java.security.SecureRandom;

import Model.Individual;

/**
 * Individual sample implementation (Best individual => fitness = 7.0) 
 * @author usrlocal
 *
 */
public class MyIndividual extends Individual{

	private byte _individualData = 0;
	
	/**
	 * Empty constructor for Main instance
	 */
	public MyIndividual() {}
	
	/**
	 *  Constructor called by CreateIndividual() to generate a new individual
	 * @param data : Individual data
	 */
	public MyIndividual(byte data)	{
		_individualData = data;
	}
	
	/**
	 * Get method for individual data
	 */
	public byte GetIndividualData() {
		return _individualData;
	}
	
	/**
	 * Set method for individual data
	 * @param by : new individual data
	 */
	public void SetIndividualData(byte by)	{
		_individualData = by;
	}
	
	@Override
	public Individual CreateIndividual() {
		
		SecureRandom random = new SecureRandom();
		byte mybyte = (byte) (random.nextInt(256));
		
		return new MyIndividual(mybyte);
	}
	
	@Override
	public void Evaluate() {

		// Bit number (between 0 and 32)
		_fitness = Integer.bitCount(_individualData);
		
		if(_individualData < 0)
        {
			// Must retrieve 1 for bit sign 
        	_fitness = (31 - _fitness);
        }	
	}	

	@Override
	public Individual Mutate() {
		
		// Mutation is defined as a bit shift
		byte childrenData = _individualData;
		MyIndividual children  = new MyIndividual(childrenData>>=1);
		// Evaluate children to set fitness
		children.Evaluate();
		
		return children;
	}

	@Override
	public Individual Crossing(Individual indtoCross) {
		
		MyIndividual secondInd = (MyIndividual) indtoCross;
		// Crossing is defined as a OR between current individual and the individual to cross with
		byte childrenData = (byte) (secondInd.GetIndividualData() | _individualData);
		MyIndividual children  = new MyIndividual(childrenData);
		// Evaluate children to set fitness
		children.Evaluate();
		
		return children;
	}
}