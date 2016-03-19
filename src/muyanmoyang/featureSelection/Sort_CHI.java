package muyanmoyang.featureSelection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sort_CHI {
	/**
	 * �� CHIֵ���н������� ,����ÿ�����и�ѡ��50��������
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int label = 1 ;
		Map<String,Double> ChiMap = getMapOfCHI("C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHIofWords_���" + label + ".txt") ;
		Map<String,Double> sortIgMap = sortCHI(ChiMap ,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHIֵ����_���" + label + ".txt") ;
		int CHIthreshold = 100 ;
		featureSelectOfWords("C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHIֵ����_���" + label + ".txt", 
							"C:/Users/Administrator/Desktop/����(��С������ݼ�)/" + "CHI������.txt",CHIthreshold);
	}
	
	/**
	 *  ��ȡCHIֵMap
	 * @return
	 * @throws IOException 
	 */
	private static Map<String,Double> getMapOfCHI(String CHIFileDir) throws IOException{
		FileReader reader = new FileReader(new File(CHIFileDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<String,Double> CHIMap = new HashMap<String,Double>() ;
		String line ;
		while((line=BR.readLine())!=null){
			String str[] = line.split("\t") ;
			CHIMap.put(str[0],Double.parseDouble(str[1])) ;
		}
		return CHIMap ;
	}
	
	/**
	 *  ��IGֵ��������
	 * @throws IOException 
	 */
	private static Map<String,Double> sortCHI(Map<String,Double> CHIMap , String ChiWriter) throws IOException{
		
		FileWriter writer = new FileWriter(new File(ChiWriter)) ;
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(CHIMap.entrySet());
        //sort list based on comparator
        Collections.sort(list, new Comparator(){
             public int compare(Object o1, Object o2) 
             {
            	 return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
             }
        });
 
        //put sorted list into map again
        Map sortedMap = new HashMap(); 
        for (Iterator it = list.iterator(); it.hasNext();) {
        	Map.Entry entry = (Map.Entry)it.next();
        	sortedMap.put(entry.getKey(), entry.getValue()) ; 
        	writer.write(entry.getKey() + "\t" + entry.getValue());
        	writer.write("\n"); 
        	writer.flush();
        }
        writer.close();
        return sortedMap;
	}
	
	/**
	 *  ��������ȡ
	 * @throws IOException 
	 */
	private static void featureSelectOfWords(String ChiFileDir, String selectWordsWriterDir, int CHIthreshold) throws IOException{
		FileReader reader = new FileReader(new File(ChiFileDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		// ������ѡ���д���ļ�
		FileWriter selectionWordsWriter = new FileWriter(new File(selectWordsWriterDir)) ;
		int count = 0 ;
		String line ;
		while((line=BR.readLine())!=null){
			count ++ ;
			if(count <= CHIthreshold){
				System.out.println("ѡ���˵�" + count + "����");
				selectionWordsWriter.write(line); 
				selectionWordsWriter.write("\n"); 
			}
			selectionWordsWriter.flush();
		}
		selectionWordsWriter.close();
	}
	
}
