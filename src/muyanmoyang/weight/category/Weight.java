package muyanmoyang.weight.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  给每条训练文本加权
 * @author hadoop
 *
 */
public class Weight {
	
	public static Map<Integer,Double> weight() throws IOException{
		int threshold = 100 ; // 特征词个数
		Map<Integer,String> featureWordMap = loadFeatureWord("C:/Users/Administrator/Desktop/论文(改小后的数据集)/" + threshold + "特征词/特征词选择_" + threshold + ".txt") ;
		Map<String,Integer> featureWordAndTimeMap = computeTimesOfFeatureWord(featureWordMap, 
													"C:/Users/Administrator/Desktop/论文(改小后的数据集)/词语-出现的文章数.txt");
		Map<Integer,String[]> trainMap = loadTrainTextOfSegment("C:/Users/Administrator/Desktop/论文(改小后的数据集)/noStopWordsSegments.txt") ;
		Map<Integer,Double> weightMap = computeWeightOfTrain(trainMap, featureWordAndTimeMap, 
									"C:/Users/Administrator/Desktop/论文(改小后的数据集)/相似度"+ threshold + "/文本加权.txt");
		return weightMap ;
	}
	
	/**
	 *  加载选取的特征词到Map集合
	 * @param featureWordDir
	 * @throws IOException 
	 */
	static Map<Integer,String> loadFeatureWord(String featureWordDir) throws IOException{
		FileReader reader = new FileReader(new File(featureWordDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String> featureWordMap = new LinkedHashMap<Integer,String>() ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			String str[] = line.split("\t") ;
			featureWordMap.put(count ,str[0]) ;
		}
		return featureWordMap ;   // return the collection of feature word
	}
	
	/**
	 *  保存 (特征词：出现的文本数量)
	 * @param featureWordMap
	 * @param wordsAndTimesDir
	 * @param testDir
	 * @return
	 * @throws IOException
	 */
	static Map<String,Integer> computeTimesOfFeatureWord(Map<Integer,String> featureWordMap ,String wordsAndTimesDir) 
																			throws IOException{
		FileReader reader = new FileReader(new File(wordsAndTimesDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<String,Integer> wordsAndTimesMap = new LinkedHashMap<String,Integer>() ;
		Map<String,Integer> featureWordAndTimeMap = new LinkedHashMap<String,Integer>() ;
//		FileWriter writer = new FileWriter(new File(testDir)) ;
		String line ;
		while((line=BR.readLine()) != null){
			String str[] = line.split(" ") ;
			wordsAndTimesMap.put(str[0],Integer.parseInt(str[1])) ;
		}
		
		for(int i=1;i<=featureWordMap.size(); i++){
			String word = featureWordMap.get(i) ;
//			System.out.println(word);
			int times = wordsAndTimesMap.get(word) ;
			featureWordAndTimeMap.put(word, times) ;
		}
		
		return featureWordAndTimeMap ;   // return the map of "word and frequency"
	}
	
	/**
	 * 加载训练文本Map
	 * @param trainDir
	 * @throws IOException
	 */
	static Map<Integer,String[]> loadTrainTextOfSegment(String trainDir) throws IOException{
		FileReader reader = new FileReader(new File(trainDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String[]> trainMap = new LinkedHashMap<Integer,String[]>() ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine())!=null){
			count ++ ; // 文本序号
			String str[] = line.split(" ") ;
			trainMap.put(count , str) ;
		}
		return trainMap ;  // 返回训练文本集合Map
	}
	
	/**
	 * 计算每篇训练文本的权重
	 * @param trainMap
	 * @param featureWordAndTimeMap
	 * @throws IOException 
	 */
	static Map<Integer,Double> computeWeightOfTrain(Map<Integer,String[]> trainMap, Map<String,Integer> featureWordAndTimeMap,String testDir) throws IOException{
		
		FileWriter writer = new FileWriter(new File(testDir)) ;
		Map<Integer,Double> weightMap = new LinkedHashMap<Integer,Double>() ;
		String word  ;
		int N = 20000 ;
		Double numerator = 0.0 ;  // 分子
		Double weight = 0.0 ;
		for(int i=1; i<=trainMap.size(); i++){  // 对于每一篇训练文本
			System.out.println("加权处理，处理到了第" + i + "篇文本......") ;
			String line[] = trainMap.get(i) ;  
			for(int j=0; j<line.length ; j++){
				word = line[j] ;
				if(featureWordAndTimeMap.get(word) != null){
					int time = featureWordAndTimeMap.get(word) ;
					numerator += time ;
				}
			}
			if(numerator == 0.0){
				weight = 1.0 / N ;
			}else{
				weight = numerator / (N + numerator) ;
			}
			weightMap.put(i, weight) ;
			writer.write(i + "\t" + weight);
			writer.write("\n") ;
			writer.flush();
			numerator = 0.0 ;
		}
		writer.close();
		return weightMap ;
	}
	
	
	
}





