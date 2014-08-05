package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;

public class CloneContainer extends CustomCloneable {

	private CustomCloneable[] cloneableData;

	@Override
	public CloneContainer clone() {
		CloneContainer clone = (CloneContainer) super.clone();
		if (cloneableData == null) {
			return clone;
		}
		
		clone.cloneableData = new CustomCloneable[cloneableData.length];
		for (int i = 0; i < cloneableData.length; i++) {
			clone.cloneableData[i] = cloneableData[i] != null ? cloneableData[i].clone() : null;
		}
		return clone;
	}

	protected CustomCloneable[] getCloneableData() {
		return cloneableData;
	}

	@SuppressWarnings("unchecked")
	protected <T extends CustomCloneable> List<T> getCloneableDataCollection() {
		List<T> collection = new ArrayList<T>(cloneableData.length);
		for (int i = 0; i < cloneableData.length; i++) {
			collection.add((T) cloneableData[i]);
		}
		return collection;
	}
	
	protected void setCloneableData(CustomCloneable... cloneables) {
		cloneableData = cloneables;
	}
}
