package muyanmoyang.preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * 划分训练数据集和测试集,并分别写入两个文件,总共28000 条短信，训练集取20000(垃圾邮件10000) ， 测试集取8000(垃圾邮件4000) 。
 * @author moyang
 *
 */
public class CreateTrainAndTestSet {
	
	public static void main(String[] args) throws IOException { 
//		divideTrainAndTest("C:/Users/Administrator/Desktop/论文(改小后的数据集)/垃圾短信训练集80W条.txt",
//							"C:/Users/Administrator/Desktop/论文(改小后的数据集)/训练集2W条.txt",
//				"C:/Users/Administrator/Desktop/论文(改小后的数据集)/测试集8000条.txt");
//		getNumOfSpamAndNormalMessage("C:/Users/Administrator/Desktop/论文(改小后的数据集)/测试集8000条.txt");
//		getNumOfSpamAndNormalMessage("C:/Users/Administrator/Desktop/论文(改小后的数据集)/训练集2W条.txt");
	
	}
	
	/*
	 *  划分训练集 和 测试集
	 */
	private static void divideTrainAndTest(String fileDir,String trainDir,String testDir) throws IOException{
		File readerFile = new File(fileDir);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"UTF-8") ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		FileWriter trainWriter = new FileWriter(new File(trainDir)) ;
		FileWriter testWriter = new FileWriter(new File(testDir)) ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			if(count <= 20000){
				trainWriter.write(line);
				trainWriter.write("\n");
				trainWriter.flush();
			}
			if(count>20000 && count <=28000 ){
				testWriter.write(line);
				testWriter.write("\n");
				testWriter.flush();
			}
		}
		trainWriter.close();
		testWriter.close();
	} 
	
	/*
	 *  统计测试集中有多少垃圾短信和正常短信
	 */
	private static void getNumOfSpamAndNormalMessage(String testDir) throws IOException{
		//TODO 统计测试集中有多少垃圾短信和正常短信
		File readerFile = new File(testDir);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"UTF-8"); 
		BufferedReader BR = new BufferedReader(reader) ;
		String line ;
		int count = 0;
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			if(str[1].equals("1")){
				count ++ ;
			}
		}
//		System.out.println("测试集中垃圾邮件数量：" + count); 
		System.out.println("测试集中垃圾邮件数量：" + count); 
	}
}
