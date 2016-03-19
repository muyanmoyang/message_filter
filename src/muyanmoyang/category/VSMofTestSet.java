package muyanmoyang.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import muyanmoyang.featureSelection.TfIdf;

/**
 *  ���Լ���VSMģ�͹���
 * @author moyang
 *
 */
public class VSMofTestSet {
	public static void main(String[] args) throws IOException {
		int threshold = 800 ;
		
		TfIdf.computeTfIdf("C:/Users/Administrator/Desktop/����(��С������ݼ�)/" + threshold + "������/" + "������ѡ��_" + threshold + ".txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�/noStopWordsSegments.txt", 
				"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���Լ�TFIDF_" + threshold + "������/TFIDF.txt",threshold) ;
	}
	

	/**
	 *  ������Լ�TF/IDFֵ
	 * @throws IOException 
	 */
	public static void computeTfIdf(String selectWordsDir,String testDir ,String TFIDFWriterDir,int threshold) throws IOException{
		FileReader reader = new FileReader(new File(selectWordsDir)) ; 
		BufferedReader BR = new BufferedReader(reader) ; 
		FileWriter TFIDFWriter = new FileWriter(new File(TFIDFWriterDir)) ;
		
		String featureWords[] = new String[threshold] ; // ���������ʽ����� 
		Map<String,Integer> wordAndTimeMap = getMapOfWordAndTime() ;  // ������ �� �ڶ���ƪ�ı��г��ֹ�
		int count = 0 ;
		String line;
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			featureWords[count] = str[0] ;
			count ++ ;
		}
		
		ArrayList<String[]> testTextWithSegment = getTextOfTest(testDir) ;   // ��ȡ�������ݼ�
		System.out.println("testTextWithSegment: " + testTextWithSegment.size()); 
		Double TF , IDF , TFIDF;
		
			for(int i=0; i<testTextWithSegment.size(); i++){             // ����ÿƪ�ı�
				System.out.println("�����˵�" + (i+1) + "ƪ�ı�......") ;
				for(int k=0; k<featureWords.length; k++){                         // ����ÿһ��������
					String[] lineWithSegment = testTextWithSegment.get(i) ;  
					String word = featureWords[k] ;
//					System.out.println("���" + word); 
					TF = computeTF(word,lineWithSegment) ;
					IDF = computeIDF(word,wordAndTimeMap) ;
					TFIDF = TF * IDF ;
					TFIDFWriter.write(TFIDF + " ");
				}
				TFIDFWriter.write("\n");   
				TFIDFWriter.flush();
			}
		TFIDFWriter.close() ;
	}
	
	private static Map<String,Integer> getMapOfWordAndTime() throws IOException{
		Map<String,Integer> map = new HashMap<String,Integer>() ;
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/����-���ֵ�������.txt"));
		BufferedReader BR = new BufferedReader(reader) ;
 		String line ;
		while((line=BR.readLine()) != null){
			String str[] = line.split(" ") ;
			map.put(str[0],Integer.parseInt(str[1])) ;
		}
		return map ;
	}
	
	/**
	 * ��ȡѵ������ÿƪ�ı�����һ�����飬�ټ��ؽ�List����
	 * @param trainDir
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<String[]> getTextOfTest(String testDir) throws IOException{
		
		FileReader reader = new FileReader(new File(testDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		ArrayList<String[]> list = new ArrayList<String[]>() ; 
		String line ;
		while((line=BR.readLine()) != null){
			String str[] = line.split(" ") ;
			list.add(str) ;
		}
		return list ;
	}
	
	/**
	 *  ����TFֵ
	 * @param selectWords
	 * @param trainTextWithSegment
	 * @return
	 */
	private static Double computeTF(String word , String[] lineWithSegment) {
		// TODO Auto-generated method stub
		Double TF = 0.0 ; // ��¼һ���������ڸ��ı��г��ֵĴ���
		for(int i=0; i<lineWithSegment.length; i++){
			if(word.equals(lineWithSegment[i])){
				TF ++ ; 
			}
		}
		return TF / lineWithSegment.length ;
	}
	
	/**
	 *  ����word��IDFֵ
	 * @param word
	 * @return
	 */
	private static Double computeIDF(String word ,Map<String,Integer> wordAndTimeMap) {
		// TODO Auto-generated method stub
		Double IDF ; 
		int time = wordAndTimeMap.get(word) ;
		IDF = Math.log((double)(20000.0 / time + 0.01)) ;
		return IDF;
	}
}
