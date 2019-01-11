package StopConditions;

public abstract class StopCondition {

	StopConditionManager _manager = null;
	
	public StopCondition(StopConditionManager scm)
	{
		_manager = scm;
	}
	
	/**
	 * Check the defined condition allowing to continue algorithm computing
	 * @return Yes / No
	 */
	public abstract boolean CheckCondition();
}
