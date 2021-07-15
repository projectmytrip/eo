package com.eni.ioc.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.eni.ioc.pojo.emission.EmissionPojo;
import com.eni.ioc.utils.MapperUtils;

public class PojoTester {

	public static void main(String[] args) {
		String json = readJsonFromFile("/home/srizzi/Desktop/json2.txt");
		
		EmissionPojo pojo = MapperUtils.<EmissionPojo>convertJsonToPojo(json, EmissionPojo.class);
		
		System.out.println(pojo.getAt());
	}

	private static String readJsonFromFile(String filename) {
		BufferedReader br = null;
		FileReader fr = null;
		String output = "";
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				output += sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return output;
	}
}
