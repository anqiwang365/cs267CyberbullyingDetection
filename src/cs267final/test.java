package cs267final;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.*;

public class test {
	
	public static void main(String[] args) {
		
		
		Map<String,Map<Integer,Integer>> featureCount =  new HashMap<>();
		Map<Integer,Integer> classifierCount = new HashMap<>();
		NaiveBayes nb = new NaiveBayes(featureCount,classifierCount);
		String testBullyPath = "testBully";
		String trainBullyPath = "trainBully";
		String trainNonBullyPath = "trainNonBully";
		String testNonBullyPath = "testNonBully";
		Set<String> stopword = readStopWord();
		List<List<String>> trainBullyData = cleanData(trainBullyPath,stopword);
		List<List<String>> trainNonBullyData = cleanData(trainNonBullyPath,stopword);
		List<List<String>> testBullyData = cleanData(testBullyPath,stopword);
		List<List<String>> testNonBullyData = cleanData(testNonBullyPath,stopword);
		List<String> testBullyRes = new ArrayList<>();
		int normalNum = 0;
		int bullyNum = 0;
		for(List<String> list:trainBullyData) {
			nb.loadData(list, 0);
		}
		for(List<String> list:trainNonBullyData) {
			nb.loadData(list, 1);
		}
		int total = 0;
		for(List<String> list:testBullyData) {
			String res = nb.naiveBay(list);
			if(res.equals("normal")) {
				normalNum++;
			}else {
				bullyNum ++;
			}
			testBullyRes.add(res);
			total++;
		}
		System.out.print("number of normal data is: "+normalNum);
		System.out.println("number of bully data is: "+bullyNum);
		System.out.println("total test cyberbullying data is "+total);
		System.out.println("accuracy of model is "+ (double)bullyNum*100/total+"%" );
		//toString(testBullyRes);
		 normalNum = 0;
		 bullyNum = 0;
		 total = 0;
		for(List<String> list:testNonBullyData) {
			String res = nb.naiveBay(list);
			if(res.equals("normal")) {
				normalNum++;
			}else {
				bullyNum ++;
			}
			testBullyRes.add(res);
			total++;
		}
		System.out.print("number of normal data is: "+normalNum);
		System.out.println("number of bully data is: "+bullyNum);
		System.out.println("total test non cyberbullying data is "+total);
		System.out.println("accuracy of model is "+ (double)normalNum*100/total+"%" );
	}
	
	public static Set<String> readStopWord() {
		Set<String> stopword = new HashSet<>();
		File file = new File("stopword");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while((temp = reader.readLine())!=null) {
				String[] items = temp.split(" ");
				for(String item:items) {
					stopword.add(item);
				}
			}
			reader.close();
		} catch(Exception e) {
			System.out.print("load file error");
		}
		return stopword;
		
	}
	
	/**
	 * clean data from train dataset and test dataset
	 */
	public static List<List<String>> cleanData(String path,Set<String> stopword) {
		
		List<List<String>> res = new ArrayList<>();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while((temp = reader.readLine())!=null) {
				List<String> message = new ArrayList<>();
				String[] items = temp.split(" ");
				for(int i =1;i<items.length-1;i++) {
					if(stopword.contains(items[i])) {
						continue;
					}
					message.add(items[i]);
				}
				res.add(message);
			}
			reader.close();
		} catch(Exception e) {
			System.out.print("load file error");
		}
		return res;
	}
	
	public static void toString(List<String> res) {
		for(String s:res) {
			System.out.println(s);
		}
	}
	
	
}
