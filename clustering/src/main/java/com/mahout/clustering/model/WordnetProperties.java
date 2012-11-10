package com.mahout.clustering.model;

public class WordnetProperties {
	
	private boolean addHypernyms;
	
	private int generalizationLevels;
	
	private boolean addMeronyms;
	
	private boolean addConditionality;
	
	public boolean isAddHypernyms() {
		return addHypernyms;
	}
	
	public void setAddHypernyms(boolean addHypernyms) {
		this.addHypernyms = addHypernyms;
	}
	
	public int getGeneralizationLevels() {
		return generalizationLevels;
	}
	
	public void setGeneralizationLevels(int generalizationLevels) {
		this.generalizationLevels = generalizationLevels;
	}
	
	public boolean isAddMeronyms() {
		return addMeronyms;
	}
	
	public void setAddMeronyms(boolean addMeronyms) {
		this.addMeronyms = addMeronyms;
	}
	
	public boolean isAddConditionality() {
		return addConditionality;
	}
	
	public void setAddConditionality(boolean addConditionality) {
		this.addConditionality = addConditionality;
	}
	
}
