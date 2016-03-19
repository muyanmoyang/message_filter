package muyanmoyang.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *  KNN�㷨���ࡪ���������ƶ�����ѡ�����ڡ�������
 * @author hadoop
 *
 */
public class KNN_Classification {
	
	/**
	 *  ��ÿƪ�����ı��������ƶ�����
	 * @param distanceMap
	 * @param threshold
	 * @throws IOException 
	 */
	static void sortOfDistance(int threshold, String distanceDir,String sortDisDir) throws IOException{
		FileWriter writer = new FileWriter(new File(sortDisDir)) ;
		FileReader reader = new FileReader(new File(distanceDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<String,Double> distanceMap = new HashMap<String,Double>() ;
		
		String line ;
		if((line=BR.readLine()) == null){
			writer.write("null");
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			distanceMap.put(str[0],Double.parseDouble(str[1])) ;
		}
		
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(distanceMap.entrySet());
        //sort list based on comparator
        Collections.sort(list, new Comparator(){
             public int compare(Object o1, Object o2) 
             {
            	 return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
             }
        });
 
        //put sorted list into map again
        for (Iterator it = list.iterator(); it.hasNext();) {
        	Map.Entry entry = (Map.Entry)it.next();
        	writer.write(entry.getKey() + "\t" + entry.getValue());
        	writer.write("\n"); 
        	writer.flush();
        }
        writer.close();
	}
	
	/**
	 *  ѡ��K������,����û�н��ڵ��ı����д���
	 * @param sortDisDir
	 * @param k
	 * @throws IOException 
	 */
	static void selectKNeighbors(int k ,String sortDisDir, String kNeighborDir) throws IOException{
		FileWriter writer = new FileWriter(new File(kNeighborDir)) ;
		FileReader reader = new FileReader(new File(sortDisDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			if(! line.equals("null")){
				String str[] = line.split("\t") ;
				if(count <= k){
					writer.write(str[0] + "\t" + str[1] + "\n") ;
					writer.flush();
				}
			}else{
				writer.write("no neighbor");
			}
		}
	}
	
	/**
	 *  �� �������ı� ���з���
	 * @param args
	 * @throws IOException
	 */
	static int classification(String kNeighborDir) throws IOException{
		Map<Integer,Double> neighborMap = new HashMap<Integer,Double>() ;   // ѵ���ı���� �� ���ƶ�
		FileReader reader = new FileReader(new File(kNeighborDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> messageAndLabel = getLabel() ; // ��ȡ�ı����Ӧ���
		
		String line ;
		if((line=BR.readLine()) == null){               // ����޽��ڣ�����0��
			return 0 ;
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			neighborMap.put(Integer.parseInt(str[0]), Double.parseDouble(str[1])) ;  // ѵ�����ı���� �� ���ƶ�ֵ
		}
		
		Iterator<Map.Entry<Integer, Double>> it = neighborMap.entrySet().iterator() ;
		
		int spamClass = 0, normalClass = 0;  // ���������Ŀ �� ���������Ŀ
		while (it.hasNext()) {
			Entry<Integer, Double> entry = it.next(); 
			if(messageAndLabel.get(entry.getKey()) == 1){
				spamClass ++ ;
			}if(messageAndLabel.get(entry.getKey()) == 0){
				normalClass ++ ;
			}
		}
		if(spamClass >= normalClass){
			return 1; 
		}else{
			return 0 ;
		}
	}
	
	/**
	 * ת������б�
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer,Integer> getLabel() throws IOException{
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/����б�.txt")) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> messageAndLabel = new HashMap<Integer,Integer>() ;
		String textLine ;
		while((textLine = BR.readLine())!= null){
			String str[] = textLine.split(" ") ;
			messageAndLabel.put(Integer.parseInt(str[0]),Integer.parseInt(str[1])) ;
		}
		return messageAndLabel ;
	}
	
	/**
	 *  ��ȡ��ʵ�������
	 * @param testSetDir
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static Map<Integer,Integer> getTestRealResult(String testSetDir) throws NumberFormatException, IOException{
		FileReader reader = new FileReader(new File(testSetDir)) ; 
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> testResultMap = new LinkedHashMap<Integer,Integer>() ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){ 
			count ++ ;
			String str[] = line.split("\t") ; 
			testResultMap.put(count,Integer.parseInt(str[1])) ;
		}
		return testResultMap ;
	}
	
	/**
	 * �����ٻ���
	 * @param args
	 * @throws IOException
	 */
	public static Double computeRecallrate(int classLabel, String resultDir, Map<Integer,Integer> testResultMap) throws IOException{
		
		FileReader reader = new FileReader(new File(resultDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> resultMap = new LinkedHashMap<Integer,Integer>() ;
		
		Double resultCount = 0.0 ; // ��¼��ȷ����ĸ���
		Double recall = 0.0 ; // �ٻ���
		String line ;
		while((line = BR.readLine()) != null){ 
			String str[] = line.split("\t") ;
			resultMap.put(Integer.parseInt(str[0]),Integer.parseInt(str[1])) ;    // ��¼��ѵ�������ķ�����
		}
		
//		Iterator<Map.Entry<Integer, Integer>> it = testResultMap.entrySet().iterator() ; // �������Լ������������
//		while(it.hasNext()){
//			Entry<Integer, Integer> entry = it.next() ;
//			if(entry.getValue() == classLabel){
//				classLabelNum ++ ;
//			}
//		}
		
		//  ѵ��׼ȷ�ĸ���
		for(Map.Entry<Integer, Integer> entry1 : testResultMap.entrySet()){
            int m1value = entry1.getValue() ;
            if(m1value == classLabel){
            	int m2value = resultMap.get(entry1.getKey()) ;
                if (m1value == m2value){             // �������ͬ
                	resultCount ++ ;
                }
            }
        } 
		recall = resultCount / 4000.0  ;   // �ٻ���
		System.out.println("���# " + classLabel + " #���ٻ��ʣ�" + recall) ;
		return recall ;
	}
	
	/**
	 *  ����׼ȷ��
	 * @param classLabel
	 * @param resultDir
	 * @param testResultMap    ��¼�������ķ����� <----------��Դ�ڲ��Լ�
	 * @throws IOException
	 */
	public static Double computePrecision(int classLabel, String resultDir, Map<Integer,Integer> testResultMap) throws IOException{
		
		FileReader reader = new FileReader(new File(resultDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		Map<Integer,Integer> resultMap = new LinkedHashMap<Integer,Integer>() ;  // ��¼��ѵ�������ķ�����
		
		Double resultCount = 0.0 ; // ��¼��ȷ����ĸ��� 
		Double numOfError = 0.0 ; // ����ֵ�classlabel����Ŀ
		Double precision = 0.0 ; // �ٻ���
		
		String line ;
		while((line = BR.readLine()) != null){
			String str[] = line.split("\t") ;
			resultMap.put(Integer.parseInt(str[0]),Integer.parseInt(str[1])) ;  // // ��¼��ѵ�������ķ�����
		}
		
		Iterator<Map.Entry<Integer, Integer>> it = resultMap.entrySet().iterator() ; // �������Լ����
		while(it.hasNext()){
			Entry<Integer, Integer> entry = it.next() ;
			if(entry.getValue() == classLabel){
				if(testResultMap.get(entry.getKey()) == classLabel){
					resultCount ++ ;
				}else{
					numOfError ++ ;
				}
			}
		}
		
		precision = resultCount / (resultCount + numOfError) ;  // ׼ȷ��
		System.out.println("���# " + classLabel + " #��׼ȷ�ʣ�" + precision) ;
		return precision ;
	}
	
	
	
	/*
	 *  ���ࡢָ�����
	 */
	public static void main(String[] args) throws IOException {
		int threshold = 100 ;
		int k = 10 ;
		int classLabel = 1 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/���.txt"));
		for(int i=1; i<=8000; i++){
			
			//  �����ƶ�����
//			System.out.println("��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......") ;
//			sortOfDistance(threshold, "C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/��" + i +"ƪ�����ı����ƶ�.txt",
//					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt") ;
			
			//  �� ѡȡK����
			System.out.println("ѡȡKNN���ڣ���8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
			selectKNeighbors(k ,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt",
					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_"+ k +"/��" + i +"ƪ�����ı����ƶ�.txt") ;
			
			 //  �� ������
//			System.out.println("���࣬��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			int predictLabel = classification("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt");
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		// �� ����ָ��
//		System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
//		// ��ʵ�������
//		Map<Integer, Integer> testResultMap = getTestRealResult("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)���Լ�8000��.txt") ;
//		
//		// �ٻ���
//		recall = computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/���.txt", testResultMap);
//		
//		// ׼ȷ��
//		precision = computePrecision(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/���.txt", testResultMap);
//		
//		// Fֵ
//		Fscore = 2*precision*recall / (precision + recall) ;
//		System.out.println("���# " + classLabel + " #��Fֵ��" + Fscore) ;
		
//		writer.close();
	}
	
}



