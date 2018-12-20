package Main;

import java.security.SecureRandom;

import Model.Individual;

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
		/*
		 * This code is contributed by Anshika Goyal.
		 * https://www.geeksforgeeks.org/count-set-bits-in-an-integer/
		 */	
		
		// The fitness is defined by number of 1 in the byte
        while (_individualData > 0) 
        { 
            _fitness += _individualData & 1; 
            _individualData >>= 1; 
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