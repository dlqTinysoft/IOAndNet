package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

import org.junit.Test;

import com.test.nio.NIODemo;

public class NIOTest {
	
	
	@Test
	public void testNIO(){
		NIODemo nioDemo = new NIODemo();
		nioDemo.NIOGatterWrite("D:/test/1.txt");
	}
	
	@Test
	public void testNIO1(){
		NIODemo nioDemo = new NIODemo();
		nioDemo.NIOScatterRead("D:/test/2.txt");
	}
	
	@Test
	public void testNIO2(String path){
		try {
			String path1 = "D:/test/1.txt";
			FileOutputStream out = new FileOutputStream(path);
			FileOutputStream in = new FileOutputStream(path);
			FileChannel source = in.getChannel();
			source.write(ByteBuffer.wrap("head".getBytes()));
			FileChannel fout =out.getChannel();
			fout.transferFrom(source, 0, source.size());
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			fout.read(byteBuffer);
		    System.out.println(new String(byteBuffer.array()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * stockChannle��ʹ�ù���, stockChannle �����ӵ�tcp������׽���
	 * ��һ��stockChannle�����ӵ���������
	 */
	@Test
	public void StockChannle(){
		
	}
	
	public static void main(String[] args) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			//���ӵ�Զ�̵ķ�����
			socketChannel.connect(new InetSocketAddress(4499));
			//����Ϊ������ģʽ
			socketChannel.configureBlocking(false);
			System.out.println("���ӳɹ���");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	
	
	
	
	

}
