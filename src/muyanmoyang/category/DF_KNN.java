package muyanmoyang.category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class DF_KNN {
	public static void main(String[] args) throws NumberFormatException, IOException {
		long start = System.currentTimeMillis() ; 
//    	int threshold = 100 ; // 选取的特征词数量
//    	
//        String testFile = "C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF_TFIDF_测试集/DF_tfidf_test.txt" ;   // 测试集的向量文件
////        String testFile = "C:/Users/Administrator/Desktop/论文(改小后的数据集)/测试_测试集TFIDF.txt" ;   // 测试集的向量文件
//        Vector<Double[]> testVector = KNN.initTestVector(testFile,threshold) ;       // 测试文本的向量表示，每个Double数组代表每篇文本的TF/IDF值集合
//        
//        String trainFile = "C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF_TFIDF/DF_tfidf.txt";   // 训练集的向量文件
////        String trainFile = "C:/Users/Administrator/Desktop/论文(改小后的数据集)/测试_训练集TFIDF.txt";   // 训练集的向量文件
//        Vector<Double[]> trainVector = KNN.initTrainVector(trainFile,threshold) ;       // 测试文本的向量表示，每个Double数组代表每篇文本的TF/IDF值集合
//       
//        String distanceDir = "C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/" ;
//        KNN.caculateDist(trainVector,testVector,distanceDir, threshold);  // 计算相似度
//        knnClassification(3);      // KNN分类
		
		classification() ;   // 分类
		
        long end = System.currentTimeMillis() ;
        System.out.println("计算时间：" + (end - start)/1000 + "秒......");
	}
	
	private static void classification() throws NumberFormatException, IOException{
		int threshold = 100 ;
		int k = 10 ;
		int classLabel = 0 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/近邻_" + k + "/结果.txt"));
		for(int i=1; i<=8000; i++){
			
			//  Ⅰ相似度排序
//			System.out.println("共8000篇测试文本，排序到了第:" + i + "篇文本......") ;
//			KNN_Classification.sortOfDistance(threshold, "C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/第" + i +"篇测试文本相似度.txt",
//					"C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/已排序/已排序_第" + i +"篇测试文本相似度.txt") ;
			
			//  Ⅱ 选取K近邻
//			System.out.println("选取KNN近邻，共8000篇测试文本，处理到了第:" + i + "篇文本......"); 
//			KNN_Classification.selectKNeighbors(k ,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/已排序/已排序_第" + i +"篇测试文本相似度.txt",
//					"C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/近邻_"+ k +"/第" + i +"篇测试文本相似度.txt") ;
			
			 //  Ⅲ 分类结果
//			System.out.println("分类，共8000篇测试文本，处理到了第:" + i + "篇文本......"); 
//			int predictLabel = KNN_Classification.classification("C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/近邻_" + k + "/第" + i +"篇测试文本相似度.txt");
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		// Ⅳ 计算指标
		System.out.println("计算类别#" + classLabel + "#的指标 ----> 特征词取" + threshold + "个，近邻取" + k + "个......") ;
		// 真实类别结果集
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/论文(改小后的数据集)/(新)测试集8000条.txt") ;
		
		// 召回率
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/近邻_" + k + "/结果.txt", testResultMap);
		
		// 准确率
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/论文(改小后的数据集)/DF相似度/近邻_" + k + "/结果.txt", testResultMap);
		
		// F值
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("类别# " + classLabel + " #的F值：" + Fscore) ;
		
//		writer.close();
	}
}
