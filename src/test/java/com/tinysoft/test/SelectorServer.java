package com.tinysoft.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorServer {
	private Selector selector;
	public void initServer(int port){
		try {
			//1. ���һ��ServerSocketͨ��
			ServerSocketChannel  socketChannel = ServerSocketChannel.open();
			//2.����ͨ��Ϊ������
			socketChannel.configureBlocking(false);
			//3.�Ѹ�ͨ���󶨵�һ���˿�
			socketChannel.socket().bind(new InetSocketAddress(4499));
			//4.���ͨ��������
			this.selector = Selector.open();
			//5.��ͨ��������ע�ᵽͨ����,ͬʱע��һ������տͻ������ӵ��¼�
			socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//������ѵ�ķ�ʽ����Selector���Ƿ�����Ҫ������¼�������н��д���
	public void listen() throws Exception{
		System.out.println("start server!!");
		//��ע���¼�����ʱ���������أ�����һֱ��������״̬
		selector.select();
		
		Iterator<SelectionKey> iterator = selector.keys().iterator();
		
		while(iterator.hasNext()){
			SelectionKey selectionKey = iterator.next();
			//�Ƴ���ǰѡ�е�
			iterator.remove();
			
			//�ͻ��ˣ�����������¼�
			if(selectionKey.isAcceptable()){
				ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
				//��úͿͻ�������,��ͻ��˽���ͨ��
				SocketChannel socketChannel = serverSocketChannel.accept();
				//���óɷ�����
				socketChannel.configureBlocking(false);
				//������Ϣ���ͻ���
				socketChannel.write(ByteBuffer.wrap("client".getBytes()));
				
				
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	
}
