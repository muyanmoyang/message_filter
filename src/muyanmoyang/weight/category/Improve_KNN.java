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
 *  �Ľ���KNN�㷨
 * @author hadoop
 *
 */
public class Improve_KNN {
	
	/**
	 *  �ı���ȨKNN
	 * @param kNeighborDir
	 * @return
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	static int improvedKNNClassification(String kNeighborDir, Map<Integer,Double> weightMap) throws IOException{
		Map<Integer,Double> neighborMap = new HashMap<Integer,Double>() ;   // ѵ���ı���� �� ���ƶ�
		FileReader reader = new FileReader(new File(kNeighborDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,Integer> messageAndLabel = KNN_Classification.getLabel() ; // ��ȡ�ı����Ӧ���
		
		String line ;
		if((line=BR.readLine()) == null){               // ����޽��ڣ�����0��
			return 0 ;
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			neighborMap.put(Integer.parseInt(str[0]), Double.parseDouble(str[1])) ;  // ѵ�����ı���� �� ���ƶ�ֵ
		}
		Iterator<Map.Entry<Integer, Double>> it = neighborMap.entrySet().iterator() ;
		Double spamClass = 0.0, normalClass = 0.0 ;  // ���������Ŀ �� ���������Ŀ
		
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
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt"));
		
//		Map<Integer,Double> weightMap = Weight.weight() ; // ��ȡ�ı���Ȩ���
		for(int i=1; i<=8000; i++){
			
			
			//������
//			System.out.println("���࣬��8000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			int predictLabel = improvedKNNClassification(
//					"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt",weightMap);
//			writer.write(i + "\t" + predictLabel + "\n") ;
//			writer.flush();
			
		}
		
		// �� ����ָ��
		System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
		// ��ʵ�������
		Map<Integer, Integer> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)���Լ�8000��.txt") ;
		
		// �ٻ���
		recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt", testResultMap);
		
		// ׼ȷ��
		precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/����(��С������ݼ�)/���ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt", testResultMap);
		
		// Fֵ
		Fscore = 2*precision*recall / (precision + recall) ;
		System.out.println("���# " + classLabel + " #��Fֵ��" + Fscore) ;
		
//		writer.close();
	}
	
	
}
