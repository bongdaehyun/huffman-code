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
		
		JLabel lab=new JLabel("�ҷ��� ������ �����ּ���");
		lab.setBounds(40, 25, 300, 30);
		lab.setLayout(null);
		lab.setVisible(true);
		
		
		filename.setBounds(200,25,100,30);
		filename.setVisible(true);
		filename.setLayout(null);
		
		JButton btn=new JButton("���� �ҷ�����");
		JButton btn1=new JButton("���� ����");
		JButton btn2=new JButton("���� ���� ����");
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
		
		JLabel lab1=new JLabel("<���� ����>");
		lab1.setBounds(210,10,200,200);
		lab1.setVisible(true);
		
		JLabel lab2=new JLabel("<��� â>");
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
		
		btn.addActionListener(new ActionListener() {		//���� �ҷ��ͼ� ���� ���� �ؽ�Ʈarea�� ���
			public void actionPerformed(ActionEvent e) {
				fileinf.setText("");
				information.setText(" ");
				fileinput();
			}
		});
		
		btn1.addActionListener(new ActionListener() {	//���� ����	
			public void actionPerformed(ActionEvent e) {
				information.setText("");
				long start = System.currentTimeMillis();
				HuffmanTree tree=new HuffmanTree();
				tree.makecode(filename, information);
				long end = System.currentTimeMillis();
				information.append("\n���� �ð� : " + (end - start) / 1000.0 + "��");
			}
		});
		btn2.addActionListener(new ActionListener() {		//���� ��������
			public void actionPerformed(ActionEvent e) {
				information.setText("");
				long start = System.currentTimeMillis();
				decoding decode=new decoding(filename, information);
				decode.filedecode(filename,  information);
				long end = System.currentTimeMillis();
				information.append("\n���� �ð� : " + (end - start) / 1000.0 + "��");
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
	
	public void fileinput()//���� �ҷ�����
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
			information.append("���� ũ��"+filesize+"\n");
			filesize=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fileinf.append("������ �������� �ʽ��ϴ�.");
		}
		
	}
	public static void main(String[] args) 
	{
		new gui();
	}

}