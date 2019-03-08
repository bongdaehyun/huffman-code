package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

public class incoding {
	private String code;
	private JTextField filename;
	private JTextArea infor;
	private HashMap <Character,Integer> huffmantable;
	
	incoding(String code,JTextField filename, JTextArea infor,HashMap <Character,Integer> huffmantable)
	{
		this.code=code;
		this.filename=filename;
		this.infor=infor;
		this.huffmantable=huffmantable;
	}
	
	public void makebit(JTextField filename, JTextArea infor)
	{
		 int size=code.length();//허프만 코드의 사이즈
	     int buffer=0;//비트를 저장할 변수
	     int bitshift=6;
	     String name=filename.getText();
	     
	     try{  
	    	   
	         FileWriter zip = new FileWriter (name+"code(zip).txt");//허프만 코드를 저장
	         FileWriter table = new FileWriter (name+"table(zip).txt");//허프만 테이블 저장
	         BufferedWriter out = new BufferedWriter(zip, 2000);
	         BufferedWriter tableout = new BufferedWriter(table, 2000);
	         
	         for(char key:huffmantable.keySet())
	         {
	        	 tableout.write(key);//문자 저장
	        	 tableout.write(huffmantable.get(key));//빈도수 저장
	        	 tableout.newLine();
	         }
	         
	         for(int i=0;i<size;i++)
	         {
	            if(code.charAt(i) == '1')//허프만 코드 중 1일때만 쉬프트 
	            {
	               buffer=(buffer |(1<<bitshift));// 00000001을 왼쪽으로 쉬프트   
	            }
	            bitshift--;
	            if(bitshift==-1)//8비트가 됬을때
	            {
	               out.write(buffer);//8비트가 되면 저장
	               buffer=0;//초기화
	               bitshift=6;
	            }
	            if(i==size-1)
	            {
	            	out.write(buffer);//마지막 문자는 그냥 저장
	            }
	         }       
	        out.close();
	     	}
	     	catch (IOException e)
	     	{
	              System.out.println("파일이 없습니다.");
	     	}
	}
	
	public int textsize(JTextField filename,JTextArea infor)
	{
		int size=0;
		try {
			FileReader zip=new FileReader(filename.getText()+"code(zip).txt");
			BufferedReader in=new BufferedReader(zip,2000);
			int c;
			while((c=in.read())!=-1)
			{
				size++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
}
