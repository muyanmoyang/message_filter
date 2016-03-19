package muyanmoyang.fpgrowth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 *  扩展测试集
 */
public class FrequentItems{
	
	public static void main(String[] args) throws IOException {
//		removeSameOfTrain() ;
//		getTextWithNum();
		
		Map<String,Double> map = contransformNum2Word();
		sortFrequency("C:/Users/Administrator/Desktop/论文(改小后的数据集)/sortFrequencyOfFrequentItemSet.txt",map) ;
	} 
	
	private static void sortFrequency(String frequencyWriter, Map<String,Double> map) throws IOException { 
		// TODO Auto-generated method stub
		FileWriter writer = new FileWriter(new File(frequencyWriter)) ;
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(map.entrySet());
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
	}

	/*
	 *  将频繁项集里的序号转化为词语集合
	 */
	private static Map<String,Double> contransformNum2Word() throws IOException {
		// TODO Auto-generated method stub
		BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/频繁项集.txt"),"UTF-8")) ;
		BufferedReader BR2 = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/词语集合.txt"),"GBK")) ;
		FileWriter writer = new FileWriter(new File(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/频繁项集_word.txt")) ;	
		Map<String,Double> map = new LinkedHashMap<String,Double>() ;
		String line ;
		int count = 0 ;
		Map<Integer,String> wordMap = new LinkedHashMap<Integer,String>() ;
 		while((line=BR2.readLine())!=null){
			count ++ ;
			wordMap.put(count,line) ;
		}
 		String line2 ;
 		
 		while((line2=BR.readLine())!=null){
 			String tmp = "" ;
			String str[] = line2.split("\t") ;
			String str2[] =str[0].split(",") ; 
			String frequency = str[1] ;
			
			for(int i=0; i<str2.length; i++){
				writer.write(wordMap.get(Integer.parseInt(str2[i])) + " ");
				tmp += wordMap.get(Integer.parseInt(str2[i])) + "," ;
			}
			writer.write("\t" + frequency);
			writer.write("\n");
			writer.flush();
			map.put(tmp,Double.parseDouble(str[1])) ;
		}
 		writer.close();
 		return map ;
	}

	/*
	 *  将训练文本转成词语序号组成的文本
	 */
	private static void getTextWithNum() throws IOException {
		// TODO Auto-generated method stub
		BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/newNoStopWordsSegments.txt"),"UTF-8")) ;
		BufferedReader BR2 = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/词语集合.txt"),"GBK")) ;
		FileWriter writer = new FileWriter(new File(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/textWithWordNum.txt")) ;	
		String line ;
		int count = 0 ;
		Map<String,Integer> wordMap = new LinkedHashMap<String,Integer>() ;
 		while((line=BR2.readLine())!=null){
			count ++ ;
			wordMap.put(line,count) ;
		}
 		String line2 ;
 		while((line2=BR.readLine())!=null){
			String str[] = line2.split(" ") ;
			for(int i=0; i<str.length; i++){
				writer.write(wordMap.get(str[i]) + " ");
			}
			writer.write("\n");
			writer.flush();
		}
 		writer.close();
	}

	/*
	 *  去除原始训练数据集分词后的文本中的重复词语
	 */
	private static void removeSameOfTrain() throws IOException { 
		// TODO Auto-generated method stub 
		BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/noStopWordsSegments.txt"),"GBK")) ;
		FileWriter writer = new FileWriter(new File(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/newNoStopWordsSegments.txt")) ;	
		String line ;
		while((line=BR.readLine())!=null){
			List<String> list = new ArrayList<String>() ;
			String str[] = line.split(" ") ;
			for(int i=0;i<str.length; i++){
				if(!list.contains(str[i])){
					list.add(str[i]) ;
				}
			}
			for(int i=0; i<list.size(); i++){
				writer.write(list.get(i) + " ") ;
			}
			writer.write("\n");
			writer.flush();
		}
		writer.close();
	}

	public static List<String> getWordOfFrequent() throws IOException { 
		// TODO Auto-generated method stub
		BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(
				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/频繁项集_word.txt"), "UTF-8"));
		String line ;
		List<String> list = new ArrayList<String>() ;
		while((line=BR.readLine())!=null){
			String str[] = line.split(" ") ;
			for(int i=0;i<str.length; i++){
				if(!list.contains(str[i])){
					list.add(str[i]) ;
				}
			}
		}
		return list ;
	}
	
	
}