package com.tinysoft.cn;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * �������ӹ���ʱ���ᴥ����Щ�¼�
 * @author HP
 */
public class SimpleChatServerHandler extends IoHandlerAdapter {
    
	//1.�����洢���������ϵ�session
	//ÿ���ͻ�ά��һ��session
	private final static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	
	//2.����session
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		//��session���뵽������
		sessions.add(session);
		//֪ͨ������������session,���û��Ѿ����뵽��������
		broadcast("has join the chat ", session);
	}
	
	//3.�ر�session
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		//��session���������Ƴ�
		sessions.remove(session);
	}
	
	//4.���տͻ��˵���Ϣ
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		//���������ӿͻ��˶�ȡ�����ݵ�ʱ�򣬰Ѹ�����Ϣ�㲥�����ߵ����пͻ���
		broadcast(str, session);
	}
	
	//5.�õ����ӵ�����˵Ŀͻ�
	//����˼������ͻ��˵��������
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println(session.getRemoteAddress());
	}
	
	//6.�쳣����
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println("[server]Client:"+session.getRemoteAddress()+"�쳣");
		//δ�����쳣�������ر�session
		session.close(true);
	}
	
	//7.�㲥��Ϣ
	@SuppressWarnings("unused")
	private void broadcast(String message,IoSession exceptionSession){
		for(IoSession ioSession: sessions){
			if(ioSession.isConnected()){
				if(ioSession.equals(exceptionSession)){
					ioSession.write("[you]"+message);
				}else{
					ioSession.write("[Client"+ioSession.getRemoteAddress()+"]"+message);
				}
			}
		}
	}
}
