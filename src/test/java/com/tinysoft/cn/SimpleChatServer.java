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
 * ���������
 * @author HP
 *
 */
public class SimpleChatServer {
	public static void main(String[] args) {
		int port;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		else
			//Ҫ�����Ķ˿ں�
			port = 8080;
		//acceptor�ײ��ǻ���socket����ͨ�ŵ�
		SocketAcceptor acceptor = new NioSocketAcceptor();
		//��acceptor��һЩ����
		TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName("UTF-8"));
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(textLineCodecFactory));
		//�������촦����
		acceptor.setHandler(new SimpleChatServerHandler());
		//�Ự���ã����ûỰ�����С
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//��������ʱ��
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 100);
		try {
			//Ҫ�����Ķ˿ں�
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[server]Listening on port "+port);
	}
}
