package muyanmoyang.weight.category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import muyanmoyang.category.KNN_Classification;

public class CHI_KNN_Improve {
	
	
	public static Map<Integer,Double> weight() throws IOException{
		int threshold = 100 ; // �����ʸ���
		Map<Integer,String> featureWordMap = Weight.loadFeatureWord("C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI������.txt") ;
		Map<String,Integer> featureWordAndTimeMap = Weight.computeTimesOfFeatureWord(featureWordMap, 
													"C:/Users/Administrator/Desktop/����(��С������ݼ�)/����-���ֵ�������.txt");
		Map<Integer,String[]> trainMap = Weight.loadTrainTextOfSegment("C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt") ;
		Map<Integer,Double> weightMap = Weight.computeWeightOfTrain(trainMap, featureWordAndTimeMap, 
									"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI���ƶ�/�ı���Ȩ.txt");
		return weightMap ;
	}
	
	public static void main(String[] args) throws IOException {
		int threshold = 100 ;
		int k = 10 ;
		int classLabel = 0 ;
		Double recall, precision ,Fscore ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI���ƶ�/����_" + k + "/�Ľ����.txt"));
		
//		Map<Integer,Double> weightMap = weight() ; // ��ȡ�ı���Ȩ���
		for(int i=1; i<=8000; i++){
			
			
			//������
//			System.out.println("���࣬��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			int predictLabel = Improve_KNN.improvedKNNClassification(
//					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI���ƶ�/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt",weightMap);
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		
		// �� ����ָ��
		System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
		// ��ʵ�������
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)���Լ�8000��.txt") ;
		
		// �ٻ���
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI���ƶ�/����_" + k + "/�Ľ����.txt", testResultMap);
		
		// ׼ȷ��
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/CHI���ƶ�/����_" + k + "/�Ľ����.txt", testResultMap);
		
		// Fֵ
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("���# " + classLabel + " #��Fֵ��" + Fscore) ;
		
//		writer.close();
	}
}
