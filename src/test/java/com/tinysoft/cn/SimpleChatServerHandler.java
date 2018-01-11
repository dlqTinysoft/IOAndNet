package com.tinysoft.cn;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 当有链接过来时，会触发这些事件
 * @author HP
 */
public class SimpleChatServerHandler extends IoHandlerAdapter {
    
	//1.用来存储所有链接上的session
	//每个客户维持一个session
	private final static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	
	//2.创建session
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		//把session加入到链接中
		sessions.add(session);
		//通知服务器的所有session,有用户已经加入到聊天室了
		broadcast("has join the chat ", session);
	}
	
	//3.关闭session
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		//把session从链接中移除
		sessions.remove(session);
	}
	
	//4.接收客户端的消息
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		//到服务器从客户端读取到数据的时候，把该条信息广播给在线的所有客户端
		broadcast(str, session);
	}
	
	//5.得到链接到服务端的客户
	//服务端监听到客户端的闲置情况
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println(session.getRemoteAddress());
	}
	
	//6.异常捕获
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println("[server]Client:"+session.getRemoteAddress()+"异常");
		//未捕获异常，则必须关闭session
		session.close(true);
	}
	
	//7.广播消息
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
