package StopConditions;

/**
 * Manage computing stop if we are at max iteration 
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux *
 */
public class IteMaxStopCondition extends StopCondition {

	private int _iteMax = 0;
	private int _iterationCounter = 0;
	
	public IteMaxStopCondition(StopConditionManager scm, int iteMax)
	{
		super(scm);
		if(iteMax > 0) _iteMax = iteMax;		
	}
	
	@Override
	public boolean CheckCondition() {
		return (++_iterationCounter > _iteMax);		
	}
}
