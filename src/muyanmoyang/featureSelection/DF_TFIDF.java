package muyanmoyang.featureSelection;

import java.io.IOException;

import muyanmoyang.category.VSMofTestSet;

public class DF_TFIDF {
	public static void main(String[] args) throws IOException {
		TfIdf.computeTfIdf("C:/Users/Administrator/Desktop/����(��С������ݼ�)/DFofWords.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF_TFIDF/DF_tfidf.txt",100) ; 
		
		// ���Լ�TFIDF
		
		VSMofTestSet.computeTfIdf("C:/Users/Administrator/Desktop/����(��С������ݼ�)/DFofWords.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�/noStopWordsSegments.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/DF_TFIDF_���Լ�/DF_tfidf_test.txt",100) ;
		
	}
}
