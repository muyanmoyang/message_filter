package muyanmoyang.weight.category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import muyanmoyang.category.KNN_Classification;

public class CHI_KNN_Improve {
	
	
	public static Map<Integer,Double> weight() throws IOException{
		int threshold = 100 ; // 特征词个数
		Map<Integer,String> featureWordMap = Weight.loadFeatureWord("C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI特征词.txt") ;
		Map<String,Integer> featureWordAndTimeMap = Weight.computeTimesOfFeatureWord(featureWordMap, 
													"C:/Users/Administrator/Desktop/论文(改小后的数据集)/词语-出现的文章数.txt");
		Map<Integer,String[]> trainMap = Weight.loadTrainTextOfSegment("C:/Users/Administrator/Desktop/论文(改小后的数据集)/noStopWordsSegments.txt") ;
		Map<Integer,Double> weightMap = Weight.computeWeightOfTrain(trainMap, featureWordAndTimeMap, 
									"C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI相似度/文本加权.txt");
		return weightMap ;
	}
	
	public static void main(String[] args) throws IOException {
		int threshold = 100 ;
		int k = 10 ;
		int classLabel = 0 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI相似度/近邻_" + k + "/改进结果.txt"));
		
//		Map<Integer,Double> weightMap = weight() ; // 获取文本加权结果
		for(int i=1; i<=8000; i++){
			
			
			//分类结果
//			System.out.println("分类，共8000篇测试文本，处理到了第:" + i + "篇文本......"); 
//			int predictLabel = Improve_KNN.improvedKNNClassification(
//					"C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI相似度/近邻_" + k + "/第" + i +"篇测试文本相似度.txt",weightMap);
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		
		// Ⅳ 计算指标
		System.out.println("计算类别#" + classLabel + "#的指标 ----> 特征词取" + threshold + "个，近邻取" + k + "个......") ;
		// 真实类别结果集
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/论文(改小后的数据集)/(新)测试集8000条.txt") ;
		
		// 召回率
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI相似度/近邻_" + k + "/改进结果.txt", testResultMap);
		
		// 准确率
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/CHI相似度/近邻_" + k + "/改进结果.txt", testResultMap);
		
		// F值
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("类别# " + classLabel + " #的F值：" + Fscore) ;
		
//		writer.close();
	}
}
