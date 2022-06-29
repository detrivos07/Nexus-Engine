package nexus.engine.utils.loaders;

import java.util.ArrayList;
import java.util.List;

import nexus.engine.utils.loaders.builders.Builder;

public abstract class Loader {
	
	protected List<Builder> builders;
	
	/**Default constructor*/
	public Loader() {
		builders = new ArrayList<Builder>();
	}
	
	/**
	 * Addes the {@link Builder} objects within the specified array to this {@link Loader}'s list
	 * @param array Array of {@link Builder} objects to be added to the list
	 */
	protected void addObjectsToList(Builder... array) {
		if (array == null) System.out.println("Tried to add ");
		else for (int i = 0; i < array.length; i++) builders.add(array[i]);
	}
	
	/**Clears the {@link Builder} list*/
	public void reset() {
		builders.clear();
	}
	
	//GETTERS
	public List<Builder> getAllBuilders() {
		return builders;
	}
	
	/**
	 * Fetches builder instance from its Internal ID, returns NULL if no Builder can be found
	 * @param id Internal ID of the Builder to fetch
	 * @return The Builder object with the specified ID, NULL if no Builder exists
	 */
	public Builder getBuilderFromID(long id) {
		for (int i = 0; i < builders.size(); i++) {
			Builder temp = builders.get(i);
			if (temp.getInternalID() == id) return temp;
		}
		return null;
	}
}
