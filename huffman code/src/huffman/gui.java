package huffman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class gui extends JFrame {
	
	static JTextField filename=new JTextField(10);
	JTextArea fileinf=new JTextArea();
	static JTextArea information=new JTextArea();
	int filesize=0;
	
	gui()
	{
		JPanel menu = new JPanel();
		menu.setBounds(0, 0, 1050,80);
		menu.setBackground(Color.LIGHT_GRAY);
		menu.setLayout(null);
		
		JLabel lab=new JLabel("불러올 파일을 적어주세요");
		lab.setBounds(40, 25, 300, 30);
		lab.setLayout(null);
		lab.setVisible(true);
		
		
		filename.setBounds(200,25,100,30);
		filename.setVisible(true);
		filename.setLayout(null);
		
		JButton btn=new JButton("파일 불러오기");
		JButton btn1=new JButton("파일 압축");
		JButton btn2=new JButton("파일 압축 해제");
		btn.setBounds(400,15,130,50);
		btn1.setBounds(550,15,130,50);
		btn2.setBounds(700,15,130,50);
		btn.setVisible(true);
		btn1.setVisible(true);
		btn2.setVisible(true);
		btn.setLayout(null);
		btn1.setLayout(null);
		btn2.setLayout(null);
		
		JPanel print = new JPanel();
		print.setBounds(0, 100, 1050, 500);
		print.setLayout(null);
		
		JLabel lab1=new JLabel("<파일 내용>");
		lab1.setBounds(210,10,200,200);
		lab1.setVisible(true);
		
		JLabel lab2=new JLabel("<결과 창>");
		lab2.setBounds(670,10,200,200);
		lab2.setVisible(true);
		
		
		fileinf.setVisible(true);
		information.setVisible(true);
		fileinf.setBounds(50,130,400,400);
		information.setBounds(500,130,400,400);
		JScrollPane scroll=new JScrollPane(fileinf,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(50, 130, 400, 400);
		JScrollPane scroll2=new JScrollPane(information,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(500,130,400,400);
		
		scroll.setVisible(true);
		scroll2.setVisible(true);
		
		add(menu);
		add(print);
		menu.add(lab);
		menu.add(filename);
		menu.add(btn);
		menu.add(btn1);
		menu.add(btn2);
		
		btn.addActionListener(new ActionListener() {		//파일 불러와서 파일 내용 텍스트area에 출력
			public void actionPerformed(ActionEvent e) {
				fileinf.setText("");
				information.setText(" ");
				fileinput();
			}
		});
		
		btn1.addActionListener(new ActionListener() {	//파일 압축	
			public void actionPerformed(ActionEvent e) {
				information.setText("");
				long start = System.currentTimeMillis();
				HuffmanTree tree=new HuffmanTree();
				tree.makecode(filename, information);
				long end = System.currentTimeMillis();
				information.append("\n압축 시간 : " + (end - start) / 1000.0 + "초");
			}
		});
		btn2.addActionListener(new ActionListener() {		//파일 압축해제
			public void actionPerformed(ActionEvent e) {
				information.setText("");
				long start = System.currentTimeMillis();
				decoding decode=new decoding(filename, information);
				decode.filedecode(filename,  information);
				long end = System.currentTimeMillis();
				information.append("\n압축 시간 : " + (end - start) / 1000.0 + "초");
			}
		});
		print.add(scroll);
		print.add(scroll2);
		print.add(lab1);
		print.add(lab2);
		
		setSize(1050, 650);
		setLocation(200, 30);
		setVisible(true);
	}
	
	public void fileinput()//파일 불러오기
	{
		try {
			FileReader file = new FileReader(filename.getText());
			int c;
			String str;
			while ((c = file.read()) != -1)
			{
				filesize++;
				str=Character.toString((char)c);
				fileinf.append(str);
			}
			information.append("파일 크기"+filesize+"\n");
			filesize=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fileinf.append("파일이 존재하지 않습니다.");
		}
		
	}
	public static void main(String[] args) 
	{
		new gui();
	}

}