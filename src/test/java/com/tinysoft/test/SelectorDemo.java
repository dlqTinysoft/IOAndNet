package com.tinysoft.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

public class SelectorDemo {
	public static void main(String [] args){
		try {
			String path="";
			FileOutputStream out = new FileOutputStream(path);
			FileInputStream in = new FileInputStream(path);
			
			FileChannel fout = out.getChannel();
			FileChannel fin = out.getChannel();
		    //向selector中注册通道和事件
			Selector selector = Selector.open();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
