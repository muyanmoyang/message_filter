package muyanmoyang.preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 *  ��ȡѵ�����ݼ��������������ı�Ԥ����ͣ�ôʡ��ִʴ���
 * @author moyang
 *
 */
public class CharacterSegment {
	
	public static void main(String[] args) throws IOException, SQLException {
		Map<Integer,StringBuffer> trainMap = goodWordsinPieceArticle();  // Map[��𣺷ִ��ı� ; ......] 
	}
	/*
	 *  ��ȡѵ������
	 */
	private static Map<Integer,String[]> segmentation() throws IOException{
//		BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/Administrator/Desktop/����(��С������ݼ�)/ѵ����70W��.txt"),"utf-8"));
		FileReader reader1 = new FileReader(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/(��)ѵ����2w��.txt"));
		BufferedReader BR = new BufferedReader(reader1) ;
		Map<Integer,String[]> trainMap = new HashMap<Integer, String[]>() ;  // ���ڴ洢ѵ������
		FileWriter segmentResultWriter = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/segmentResult.txt")) ;
		Analyzer anal = new IKAnalyzer(true);  
		int count = 0;
		String tmpStr = "" ;
		String[] tmpSegment = null ;
		String line ;
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			
			StringReader reader = new StringReader(str[2]);
			TokenStream ts = anal.tokenStream("", reader); 
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class); 
			count ++ ;
			while(ts.incrementToken()){
				segmentResultWriter.write(term.toString() + "|") ;
				segmentResultWriter.flush(); 
				tmpStr += term.toString() + "|" ;
		    }
			tmpSegment = tmpStr.split("\\|") ;
			trainMap.put(count,tmpSegment) ;
			tmpStr = "" ;
			System.out.println("���ִʽ��д��segmentResult.txt�ļ�" + ",���ǵ�"+ count +"��д��");
			reader.close();
			segmentResultWriter.write("\n") ;
			segmentResultWriter.flush();
		}
		segmentResultWriter.close();
		return trainMap ;
	}
	
	/**
	 * ����ͣ�ô�,����ֻ����"�ô�"���������ݣ������л�,д��noStopWordsSegments.txt�ļ�
	 * @return Map<Integer,StringBuffer> resultMap
	 * @throws IOException 
	 * @throws SQLException
	 */
	public static Map<Integer,StringBuffer> goodWordsinPieceArticle() throws IOException, SQLException
	{
		//��ȥ��ͣ�ôʺ�����Ϸִʽ��д��txt�ļ�
		FileWriter noStopWordsSegmentsWriter = new FileWriter(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/noStopWordsSegments.txt")) ;  
		Set<String> stopwordsSet = getStopwordsSet("H:/myeclipseWorkspace/Text_Classify/stopwords.txt") ;//ͣ�ôʴ��� , ��ȡͣ�ôʱ�
		ArrayList<StringBuffer> list = new ArrayList<StringBuffer>() ;
		Map<Integer,StringBuffer> resultMap = new HashMap<Integer,StringBuffer>() ; 
		Map<Integer, String[]> segmentResult = segmentation() ;//���ķִ�
		int count = 0;
		
		String regex = "[a-zA-Z]+$";
		String regex2 = "^[A-Za-z0-9]+$" ;
		String regex3 = "^[0-9]+[\u4e00-\u9fa5]+$" ;
		String regex4 = "(?![^a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$)" ;
		String regex5 = "^[0-9|a-zA-Z]+[\\+|.|-]$" ;
		String regex6 = "[\u0800-\u4e00]" ;
		
		for(int i=1 ;i<=segmentResult.size() ;i++)
		{
			String[] contentStr = segmentResult.get(i) ;
			for(int j=0 ;j<contentStr.length ;j++)
			{
				if(contentStr[j].matches(regex) || contentStr[j].matches(regex2) || contentStr[j].matches(regex3) 
						|| contentStr[j].matches(regex4) || contentStr[j].matches(regex5) 
						|| contentStr[j].matches(regex6) || stopwordsSet.contains(contentStr[j])) //����ִʽ���г���ͣ�ôʣ�������Ϊnull
				{
					contentStr[j] = null ;
				}
			}
			//�ѹ��˺���ַ���������뵽һ���ַ�����
			StringBuffer finalStr = new StringBuffer() ;
			for(int x=0 ;x<contentStr.length ;x++)
			{
				if(contentStr[x] != null)
				{       
					finalStr = finalStr.append(contentStr[x]).append(" "); //��null��" "����
				}
			}
			list.add(finalStr) ;
			resultMap.put(i,finalStr) ;
			
		}
		for(int m=0;m<list.size();m++)
		{
			count ++ ;
//			System.out.println(list.get(m));
			System.out.println("�����˺�Ľ��д���ļ������ǵ�" + count + "��д��");
//			noStopWordsSegmentsWriter.write(count + " "); //�����µı��д��ÿ�еĿ�ʼ�����������淽����ȡ���±�Ųο�
			noStopWordsSegmentsWriter.write(list.get(m).toString()); 
			noStopWordsSegmentsWriter.write("\n"); 
			noStopWordsSegmentsWriter.flush(); 
		}
		//���Դ洢����
//		for(int i=1 ;i<=resultMap.size() ;i++)
//		{
//			System.out.println("******" + resultMap.get(i)); 
//		}
		noStopWordsSegmentsWriter.close(); 
		return resultMap ;
	}
	
	/**
	 * ͣ�ôʴ��� , ��ȡͣ�ôʱ�
	 * @param stopFileDir
	 * @return
	 * @throws IOException 
	 */
	public static Set<String> getStopwordsSet(String stopFileDir) throws IOException
	{
		File readerFile = new File(stopFileDir);
		InputStreamReader stopWordsReader = new InputStreamReader(new FileInputStream(readerFile),"UTF-8"); 
		
		Set<String> stopwordsSet = new HashSet<String>() ;
		BufferedReader BR = new BufferedReader(stopWordsReader) ;
		String stopwordsStr ; 
		while((stopwordsStr = BR.readLine()) != null)
		{
			stopwordsSet.add(stopwordsStr) ;
		}
		Iterator it = stopwordsSet.iterator() ;
		while(it.hasNext())
		{
			Object obj = it.next() ;
		}
		return stopwordsSet ;
	}
}
