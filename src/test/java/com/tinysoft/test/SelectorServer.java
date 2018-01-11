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
			//1. 获得一个ServerSocket通道
			ServerSocketChannel  socketChannel = ServerSocketChannel.open();
			//2.设置通道为非阻塞
			socketChannel.configureBlocking(false);
			//3.把该通道绑定到一个端口
			socketChannel.socket().bind(new InetSocketAddress(4499));
			//4.获得通道管理器
			this.selector = Selector.open();
			//5.把通道管理器注册到通道中,同时注册一个与接收客户端链接的事件
			socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//采用轮训的方式监听Selector上是否有需要处理的事件，如果有进行处理
	public void listen() throws Exception{
		System.out.println("start server!!");
		//到注册事件到达时，方法返回，否则一直处于阻塞状态
		selector.select();
		
		Iterator<SelectionKey> iterator = selector.keys().iterator();
		
		while(iterator.hasNext()){
			SelectionKey selectionKey = iterator.next();
			//移除当前选中的
			iterator.remove();
			
			//客户端，请求的连接事件
			if(selectionKey.isAcceptable()){
				ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
				//获得和客户端连接,与客户端建立通道
				SocketChannel socketChannel = serverSocketChannel.accept();
				//设置成非阻塞
				socketChannel.configureBlocking(false);
				//发送消息给客户端
				socketChannel.write(ByteBuffer.wrap("client".getBytes()));
				
				
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	
}
