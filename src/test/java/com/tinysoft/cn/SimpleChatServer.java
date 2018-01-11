package com.tinysoft.cn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 开启服务端
 * @author HP
 *
 */
public class SimpleChatServer {
	public static void main(String[] args) {
		int port;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		else
			//要监听的端口号
			port = 8080;
		//acceptor底层是基于socket进行通信的
		SocketAcceptor acceptor = new NioSocketAcceptor();
		//对acceptor的一些设置
		TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName("UTF-8"));
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(textLineCodecFactory));
		//设置聊天处理类
		acceptor.setHandler(new SimpleChatServerHandler());
		//会话设置，设置会话缓存大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//设置闲置时间
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 100);
		try {
			//要监听的端口号
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[server]Listening on port "+port);
	}
}
