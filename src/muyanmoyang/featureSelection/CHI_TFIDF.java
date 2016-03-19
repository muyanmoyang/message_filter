package muyanmoyang.featureSelection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import muyanmoyang.category.VSMofTestSet;

public class CHI_TFIDF {
	public static void main(String[] args) throws IOException {
		TfIdf.computeTfIdf("C:/Users/Administrator/Desktop/����(��С������ݼ�)/" + "CHI������.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI_TFIDF/CHI_tfidf.txt",100) ; 
		
		// ���Լ�TFIDF
		
		VSMofTestSet.computeTfIdf("C:/Users/Administrator/Desktop/����(��С������ݼ�)/" + "CHI������.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�/noStopWordsSegments.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI_TFIDF_���Լ�/CHI_tfidf_test.txt",100) ;
		
	}

}
