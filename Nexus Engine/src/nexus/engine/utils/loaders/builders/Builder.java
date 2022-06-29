package nexus.engine.utils.loaders.builders;

public abstract class Builder {
	
	/**Internal ID*/
	protected long iID;
	
	/**Default constructor*/
	public Builder(){}
	
	/**
	 * Sets the Internal ID from file
	 * @param iID Internal ID
	 */
	public Builder(long iID) {
		this.iID = iID;
	}
	
	/**
	 * Builds the {@link Object} of the Builder. Only runs the Constructor Method
	 * @return a new {@link Object} of the Builder
	 */
	public abstract Object build();
	
	//GETTERS
	public long getInternalID() {
		return iID;
	}
}
