package com.test.nio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.Test;

public class NIODemo {

	/**
	 * ��һ��ͨ��������д�뵽���Buffer
	 */
	public void NIOScatterRead(String path) {
		ByteBuffer head = ByteBuffer.allocate(10);
		ByteBuffer body = ByteBuffer.allocate(10);
		ByteBuffer foot = ByteBuffer.allocate(10);

		try {
			@SuppressWarnings("resource")
			//��ͨ���а����ݶ��뵽���ByteBuffer��ȥ
			FileChannel fin = new FileInputStream(path).getChannel();
			// ���뵽ͨ������
			fin.read(new ByteBuffer[] { head, body, foot });
			System.out.println("head:" + new String(head.array()));
			System.out.println("body:" + new String(body.array()));
			System.out.println("foot:" + new String(foot.array()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * ���������������д�뵽ͬһ��ͨ������
	 */
	public void NIOGatterWrite(String path) {
		ByteBuffer head = ByteBuffer.wrap("head".getBytes());
		ByteBuffer foot = ByteBuffer.wrap("foot".getBytes());
		try {
			@SuppressWarnings("resource")
			FileChannel fout = new FileOutputStream(path).getChannel();
			fout.write(new ByteBuffer[] { head, foot });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public  void ChannelServer() {
		try {
			// ��һ��ServerSocketChannelһ�������ͨ��
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			//��ͨ������Ϊ������ģʽ
			serverSocketChannel.configureBlocking(false);
			//ͨ���д������socket
			ServerSocket socket = serverSocketChannel.socket();
			//socket��һ���˿�
			socket.bind(new InetSocketAddress(4499));
			//��ѵ�����ͻ��˹�������Ϣ
			while (true) {
				//���յ��ͻ��˹���������,ע����Ϊ����Ϊ������ģʽ������������������ͻ��˵����ӣ�����ִ����һ�䣬��������
				//�õ��ͻ��˵�ͨ��SocketChannel
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel != null) {
					String socketName = socketChannel.getLocalAddress().toString();
					System.out.println("socketName: " + socketName);
				}
				System.out.println("������ģʽ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * DatagramChannel���ܹ��շ�UDP����ͨ��
	 * UDP���ͺͽ��͵����ݰ�
	 */
	public void dataGramChannel(){
		
	}
	
	/**
	 * ʹ��url����ȡ��ҳ�е�����
	 */
	@Test
	public void UrlDemo(){
		try {
			URL url = new URL("http://www.baidu.com");
			InputStream stream = url.openStream();
			InputStreamReader reader = new InputStreamReader(stream,"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String data = bufferedReader.readLine();
			while(data!=null){
				System.out.println(data);
				data = bufferedReader.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void TCPServer(){
		try {
			//Ҫ�����Ķ˿ں�
			ServerSocket serverSocket = new ServerSocket(4499);
			//���տͻ��ˣ�����������,ע��������������
			Socket socket = serverSocket.accept();
			//��ȡ�ͻ��˵���Ϣ
			InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader bReader = new BufferedReader(reader);
			//�����ͻ��ˣ���������Ϣ
			String clientData = bReader.readLine();
			System.out.println("�ͻ���˵��"+clientData);
			
			//����˸��ͻ��˽��з���
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter pWriter = new PrintWriter(outputStream);
			outputStream.close();
			pWriter.close();
			pWriter.write("���Ƿ���ˣ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void TCPclient(){
		try {
			//ʹ��socket��������
			Socket socket = new Socket("localhost", 4499);
			//�ͻ��˷�����Ϣ
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter pWriter = new PrintWriter(outputStream);
			pWriter.write("username:admin,password:123");
			//��ȡ��Ϣ
		    InputStream inputStream = socket.getInputStream();
		    InputStreamReader streamReader = new InputStreamReader(inputStream);
		    BufferedReader bufferedReader = new BufferedReader(streamReader);
		    String data = bufferedReader.readLine();
		    System.out.println("�����˵��"+data);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	@Test
	//�����
	public void UDPServer(){
		try {
			//�����˿�
			DatagramSocket dSocket = new DatagramSocket(4499);
			byte[] data = new byte[2048];
			DatagramPacket dPacket = new DatagramPacket(data, data.length);
			//���տͻ��˷��͹���������
			dSocket.receive(dPacket);//���û�н��յ��κοͻ��˵����ݣ���һֱ������ط�����
			String readData = new String(data, 0, data.length);
			System.out.println("���Ƿ���ˣ��ͻ��˸�����"+readData);
			
			/***************************************************/
			//��ͻ�����Ӧ����
			InetAddress address = dPacket.getAddress();
			int port = dPacket.getPort();
			byte[] data2 =  "��ӭ��!".getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(data2, 0, data2.length, address, port);
			//��Ӧ�ͻ���
			dSocket.send(datagramPacket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void UDPclient(){
		//�������˵ĵ�ַ���˿ںţ��Լ�Ҫ�����˷��͵�����
		try {
			InetAddress address = InetAddress.getByName("localhost");
			int port = 4499;
			byte[] data = "username:admin;password:123".getBytes();
			//�������ݰ�
			DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);
			//����DatagramSocket��������������
			DatagramSocket socket = new DatagramSocket();
			socket.send(datagramPacket);
			
			/**********************���շ���ˣ���������Ӧ��Ϣ******************************/
			byte[] data1 = new byte[1024];
			DatagramPacket datagramPacket2 = new DatagramPacket(data1, data1.length);
			socket.receive(datagramPacket2);
			String reply = new String(data1,0,data1.length);
			System.out.println("���ǿͻ��ˣ���������˵��"+reply);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
