package com.tinysoft.cn;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
/**
 *客户端 处理类
 * @author HP
 */
public class SimpleChatClientAdapter  extends IoHandlerAdapter{
	
	//客户端接收到消息，这个客户端会触发
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		System.out.println(str);
	}
}
