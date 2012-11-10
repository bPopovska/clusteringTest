package com.mahout.clustering.utils;

import com.mahout.clustering.model.InputParams;

public class FormatingUtils {

	public static String analyzerParametersToString(){
		return Boolean.toString(InputParams.HYPERNYMS) + "_" + Boolean.toString(InputParams.MERONYMS) + "_" + Boolean.toString(InputParams.CONDITIONALITY) 
				+ "_" + InputParams.MAX_SYNONYMES + "_" + InputParams.GENERALIZATION_LEVELS + "_" + InputParams.STOP_WORDS_FILE;
	}

}
