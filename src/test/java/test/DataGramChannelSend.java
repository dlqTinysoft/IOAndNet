package test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DataGramChannelSend {
	
	public static void main(String []args){
		String newData = "send data";
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		byteBuffer.clear();
		byteBuffer.put(newData.getBytes());
		
		try {
			DatagramChannel datagramChannel = DatagramChannel.open();
			datagramChannel.send(byteBuffer, new InetSocketAddress(4499));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
