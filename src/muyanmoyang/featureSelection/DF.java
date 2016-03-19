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

/**
 *  Document Frequency ������ȡ
 * @author hadoop
 *
 */
public class DF {
	public static void main(String[] args) throws IOException {
		DF();
	}
	
	private static void DF() throws IOException{
		
		Map<String,Double> featureWords = getMapOfDF("C:/Users/Administrator/Desktop/����(��С������ݼ�)/����-���ֵ�������.txt");
		sortCHI(featureWords, "C:/Users/Administrator/Desktop/����(��С������ݼ�)/DFofWords.txt") ;
	}
	
	/**
	 *  ��ȡCHIֵMap
	 * @return
	 * @throws IOException 
	 */
	private static Map<String,Double> getMapOfDF(String CHIFileDir) throws IOException{
		FileReader reader = new FileReader(new File(CHIFileDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<String,Double> CHIMap = new HashMap<String,Double>() ;
		String line ;
		while((line=BR.readLine())!=null){
			String str[] = line.split(" ") ;
			CHIMap.put(str[0],Double.parseDouble(str[1])) ;
		}
		return CHIMap ;
	}
	
	/**
	 *  ��IGֵ��������
	 * @throws IOException 
	 */
	private static Map<String,Double> sortCHI(Map<String,Double> DFMap , String DFWriter) throws IOException{
		
		FileWriter writer = new FileWriter(new File(DFWriter)) ;
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(DFMap.entrySet());
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
	
}
