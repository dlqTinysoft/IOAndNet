package com.tinysoft.cn;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
/**
 *�ͻ��� ������
 * @author HP
 */
public class SimpleChatClientAdapter  extends IoHandlerAdapter{
	
	//�ͻ��˽��յ���Ϣ������ͻ��˻ᴥ��
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		System.out.println(str);
	}
}
