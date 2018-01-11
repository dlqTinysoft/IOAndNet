package com.tinysoft.cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class SimpleChatClient {
	private static final long CONNECT_TIMEOUT = 30 * 1000L;
	private static final String HOST_NAME = "127.0.0.1";
	private static final int PORT = 8080;

	public static void main(String[] args) {
		// 客户端，底层是基于TCP协议的
		NioSocketConnector nioSocketConnector = new NioSocketConnector();
		nioSocketConnector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF-8")));
		nioSocketConnector.getFilterChain().addLast("codec", protocolCodecFilter);
		nioSocketConnector.setHandler(new SimpleChatClientAdapter());
		ConnectFuture connectFuture = nioSocketConnector.connect(new InetSocketAddress(HOST_NAME, PORT));
		//在这一步进行阻塞，直到连接建立
		connectFuture.awaitUninterruptibly();
		IoSession session = connectFuture.getSession();
		while (true) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String readLine = bufferedReader.readLine();
				// 发送消息
				session.write(readLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
