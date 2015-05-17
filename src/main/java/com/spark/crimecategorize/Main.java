package com.spark.crimecategorize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {

	public static void main(String [] args) throws IOException {
		// TODO Auto-generated constructor stub
		File co = new File("crimeOutput");
		if(!co.exists()){
			//co.delete();
			//FileUtils.deleteDirectory(co);
			//return;
			CrimeCategorize.main(null);
		}
		
		
		Map<String, Integer> result = readFile();
		
		 DefaultCategoryDataset barchart = new DefaultCategoryDataset();
		 
		 for(String key: result.keySet()){
			 barchart.addValue(result.get(key), "Value", key);
		 }
		 
		 JFreeChart BarChartObject=ChartFactory.createBarChart("Crime Categories and their Rates","crimes","rate",barchart,PlotOrientation.HORIZONTAL,true,false,false);
		 
		 int width=1800; /* Width of the image */
		 int height=800; /* Height of the image */                
		 File BarChart=new File("output_chart.png");              
		 ChartUtilities.saveChartAsPNG(BarChart,BarChartObject,width,height);
	}
	
	public static Map<String, Integer> readFile() throws IOException{
		
		Map<String, Integer> output = new HashMap<String, Integer>();
		File crimeOutput = new File("crimeOutput/part-00000");
		
		FileReader fr = new FileReader(crimeOutput);
		
		BufferedReader br = new BufferedReader(fr);
		String line;
		String[] fields;
	    while((line = br.readLine()) != null){
	    	line=line.replace('(',' ').replace(')', ' ').trim();
	        fields = line.split(",");
	        output.put(fields[0], Integer.parseInt(fields[1]));
	    }
	    br.close();
	    fr.close();
		
		return output;
	}
}
