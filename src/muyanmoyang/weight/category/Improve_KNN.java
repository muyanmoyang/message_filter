package muyanmoyang.weight.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import muyanmoyang.category.KNN_Classification;

/**
 *  改进的KNN算法
 * @author hadoop
 *
 */
public class Improve_KNN {
	
	/**
	 *  文本加权KNN
	 * @param kNeighborDir
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	static int improvedKNNClassification(String kNeighborDir, Map<Integer,Double> weightMap) throws IOException{
		Map<Integer,Double> neighborMap = new HashMap<Integer,Double>() ;   // 训练文本序号 ： 相似度
		FileReader reader = new FileReader(new File(kNeighborDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> messageAndLabel = KNN_Classification.getLabel() ; // 获取文本与对应类别
		
		String line ;
		if((line=BR.readLine()) == null){               // 如果无近邻，返回0类
			return 0 ;
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			neighborMap.put(Integer.parseInt(str[0]), Double.parseDouble(str[1])) ;  // 训练集文本序号 ： 相似度值
		}
		Iterator<Map.Entry<Integer, Double>> it = neighborMap.entrySet().iterator() ;
		Double spamClass = 0.0, normalClass = 0.0 ;  // 垃圾类的数目 ， 正常类的数目
		
		while (it.hasNext()) {
			Entry<Integer, Double> entry = it.next();
			if(messageAndLabel.get(entry.getKey()) == 1){
				Double weight =  weightMap.get(entry.getKey()) ;
				Double similarity = entry.getValue() ;
				spamClass += (weight * similarity) ;
			}else{
				Double weight =  weightMap.get(entry.getKey()) ;
				Double similarity = entry.getValue() ;
				normalClass += (weight * similarity) ;
			}
		}
		if(spamClass >= normalClass){
			return 1; 
		}else{
			return 0 ;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		int threshold = 100 ;
		int k = 300 ;
		int classLabel = 0 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/论文(改小后的数据集)/相似度"+ threshold + "/近邻_" + k + "/改进结果.txt"));
		
//		Map<Integer,Double> weightMap = Weight.weight() ; // 获取文本加权结果
		for(int i=1; i<=8000; i++){
			
			
			//分类结果
//			System.out.println("分类，共8000篇测试文本，处理到了第:" + i + "篇文本......"); 
//			int predictLabel = improvedKNNClassification(
//					"C:/Users/Administrator/Desktop/论文(改小后的数据集)/相似度"+ threshold + "/近邻_" + k + "/第" + i +"篇测试文本相似度.txt",weightMap);
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		
		// Ⅳ 计算指标
		System.out.println("计算类别#" + classLabel + "#的指标 ----> 特征词取" + threshold + "个，近邻取" + k + "个......") ;
		// 真实类别结果集
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/论文(改小后的数据集)/(新)测试集8000条.txt") ;
		
		// 召回率
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/相似度"+ threshold + "/近邻_" + k + "/改进结果.txt", testResultMap);
		
		// 准确率
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/相似度"+ threshold + "/近邻_" + k + "/改进结果.txt", testResultMap);
		
		// F值
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("类别# " + classLabel + " #的F值：" + Fscore) ;
		
//		writer.close();
	}
	
	
}
