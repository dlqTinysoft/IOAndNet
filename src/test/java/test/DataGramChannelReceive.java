package test;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DataGramChannelReceive {
	
	public static void main(String [] args){
		try {
			DatagramChannel datagramChannel = DatagramChannel.open();
			DatagramSocket datagramSocket = datagramChannel.socket();
			datagramSocket.bind(new InetSocketAddress(4499));
			ByteBuffer byteBuffer = ByteBuffer.allocate(48);
			byteBuffer.clear();
			datagramChannel.receive(byteBuffer);
			//得到接收的数据
			System.out.println("receive:"+new String(byteBuffer.array()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
