package test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.logging.Logger;

public class PieWriteAndReadData {
	private static final Logger log = Logger.getLogger(PieWriteAndReadData.class.getName());
	public static void main(String [] args){
		//��һ���ܵ�
		try {
			//��һ���ܵ���������ܵ��п��Եõ�sinkͨ����sourceͨ����sinkͨ����д���ݣ�sourceͨ���Ƕ�����
			Pipe pipe = Pipe.open();
			//��ܵ���д������
			SinkChannel sink = pipe.sink();
			ByteBuffer  byteBuffer = ByteBuffer.allocate(48);
			byteBuffer.clear();
			byteBuffer.put("new Data".getBytes());
			byteBuffer.flip();
			while(byteBuffer.hasRemaining()){
				sink.write(byteBuffer);
			}
			//�ӹܵ��ж�ȡ����
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
