package test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.logging.Logger;

public class PieWriteAndReadData {
	private static final Logger log = Logger.getLogger(PieWriteAndReadData.class.getName());
	public static void main(String [] args){
		//打开一个管道
		try {
			//打开一个管道，从这个管道中可以得到sink通道和source通道，sink通道是写数据，source通道是读数据
			Pipe pipe = Pipe.open();
			//向管道中写入数据
			SinkChannel sink = pipe.sink();
			ByteBuffer  byteBuffer = ByteBuffer.allocate(48);
			byteBuffer.clear();
			byteBuffer.put("new Data".getBytes());
			byteBuffer.flip();
			while(byteBuffer.hasRemaining()){
				sink.write(byteBuffer);
			}
			//从管道中读取数据
			SourceChannel source = pipe.source();
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
		    
			log.info("hello world");
			source.read(buffer);
			System.out.println(new String(buffer.array()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
