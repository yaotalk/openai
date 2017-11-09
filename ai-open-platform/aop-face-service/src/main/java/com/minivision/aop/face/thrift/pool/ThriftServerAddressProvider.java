package com.minivision.aop.face.thrift.pool;

import java.net.InetSocketAddress;
import java.util.List;

public interface ThriftServerAddressProvider {

	// 获取服务名称
	String getName();

	/**
	 * 获取所有服务端地址
	 * 
	 * @return
	 */
	List<InetSocketAddress> findServerAddressList();

	/**
	 * 选取一个合适的address,可以随机获取等' 内部可以使用合适的算法.
	 * 
	 * @return
	 */
	InetSocketAddress selector();

	void close();
}