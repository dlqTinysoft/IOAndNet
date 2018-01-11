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
	 * 将一个通道的数据写入到多个Buffer
	 */
	public void NIOScatterRead(String path) {
		ByteBuffer head = ByteBuffer.allocate(10);
		ByteBuffer body = ByteBuffer.allocate(10);
		ByteBuffer foot = ByteBuffer.allocate(10);

		try {
			@SuppressWarnings("resource")
			//从通道中把数据读入到多个ByteBuffer中去
			FileChannel fin = new FileInputStream(path).getChannel();
			// 读入到通道中来
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
	 * 多个缓冲区的内容写入到同一个通道中来
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
			// 打开一个ServerSocketChannel一个服务端通道
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			//把通道配置为非阻塞模式
			serverSocketChannel.configureBlocking(false);
			//通道中传输的是socket
			ServerSocket socket = serverSocketChannel.socket();
			//socket绑定一个端口
			socket.bind(new InetSocketAddress(4499));
			//轮训监听客户端过来的信息
			while (true) {
				//接收到客户端过来的连接,注意因为配置为非阻塞模式，所以如果监听不到客户端的连接，立即执行下一句，不会阻塞
				//得到客户端的通道SocketChannel
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel != null) {
					String socketName = socketChannel.getLocalAddress().toString();
					System.out.println("socketName: " + socketName);
				}
				System.out.println("非阻塞模式");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * DatagramChannel是能够收发UDP包的通道
	 * UDP发送和接送的数据包
	 */
	public void dataGramChannel(){
		
	}
	
	/**
	 * 使用url来读取网页中的内容
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
			//要监听的端口号
			ServerSocket serverSocket = new ServerSocket(4499);
			//接收客户端，过来的连接,注意这种是阻塞的
			Socket socket = serverSocket.accept();
			//读取客户端的信息
			InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader bReader = new BufferedReader(reader);
			//读到客户端，过来的信息
			String clientData = bReader.readLine();
			System.out.println("客户端说："+clientData);
			
			//服务端给客户端进行反馈
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter pWriter = new PrintWriter(outputStream);
			outputStream.close();
			pWriter.close();
			pWriter.write("我是服务端：");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void TCPclient(){
		try {
			//使用socket进行连接
			Socket socket = new Socket("localhost", 4499);
			//客户端发送信息
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter pWriter = new PrintWriter(outputStream);
			pWriter.write("username:admin,password:123");
			//读取信息
		    InputStream inputStream = socket.getInputStream();
		    InputStreamReader streamReader = new InputStreamReader(inputStream);
		    BufferedReader bufferedReader = new BufferedReader(streamReader);
		    String data = bufferedReader.readLine();
		    System.out.println("服务端说："+data);
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
	//服务端
	public void UDPServer(){
		try {
			//监听端口
			DatagramSocket dSocket = new DatagramSocket(4499);
			byte[] data = new byte[2048];
			DatagramPacket dPacket = new DatagramPacket(data, data.length);
			//接收客户端发送过来的数据
			dSocket.receive(dPacket);//如果没有接收到任何客户端的数据，会一直在这个地方阻塞
			String readData = new String(data, 0, data.length);
			System.out.println("我是服务端：客户端告诉我"+readData);
			
			/***************************************************/
			//向客户端响应数据
			InetAddress address = dPacket.getAddress();
			int port = dPacket.getPort();
			byte[] data2 =  "欢迎你!".getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(data2, 0, data2.length, address, port);
			//响应客户端
			dSocket.send(datagramPacket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void UDPclient(){
		//定义服务端的地址，端口号，以及要向服务端发送的数据
		try {
			InetAddress address = InetAddress.getByName("localhost");
			int port = 4499;
			byte[] data = "username:admin;password:123".getBytes();
			//创建数据包
			DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);
			//创建DatagramSocket对象，来发送数据
			DatagramSocket socket = new DatagramSocket();
			socket.send(datagramPacket);
			
			/**********************接收服务端，过来的响应信息******************************/
			byte[] data1 = new byte[1024];
			DatagramPacket datagramPacket2 = new DatagramPacket(data1, data1.length);
			socket.receive(datagramPacket2);
			String reply = new String(data1,0,data1.length);
			System.out.println("我是客户端：服务器端说："+reply);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
