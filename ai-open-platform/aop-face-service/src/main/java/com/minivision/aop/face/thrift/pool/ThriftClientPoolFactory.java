package com.minivision.aop.face.thrift.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient> {
	private ThriftServerAddressProvider addressProvider;
	private TServiceClientFactory<TServiceClient> clientFactory;

	private PoolOperationCallBack callback;

	private static final Logger LOGGER = LoggerFactory.getLogger(ThriftClientPoolFactory.class);

	protected ThriftClientPoolFactory(ThriftServerAddressProvider addressProvider,
			TServiceClientFactory<TServiceClient> clientFactory) {
		this.addressProvider = addressProvider;
		this.clientFactory = clientFactory;
	}

	protected ThriftClientPoolFactory(ThriftServerAddressProvider addressProvider,
			TServiceClientFactory<TServiceClient> clientFactory, PoolOperationCallBack callback) {
		this.addressProvider = addressProvider;
		this.clientFactory = clientFactory;
		this.callback = callback;
	}

	@Override
	public TServiceClient makeObject() throws TTransportException {
		InetSocketAddress address = addressProvider.selector();
		LOGGER.debug("make client with: {}", address);
		TTransport transport = new TSocket(address.getHostName(), address.getPort(), 10000);
        TProtocol protocol = new TCompactProtocol(transport);
        TServiceClient client = this.clientFactory.getClient(protocol);
        transport.open();
        if (callback != null) {
            callback.make(client);
        }
        return client;
	}

	public void destroyObject(TServiceClient client) {
		if (callback != null) {
			callback.destroy(client);
		}
		TTransport pin = client.getInputProtocol().getTransport();
		pin.close();
	}

	public boolean validateObject(TServiceClient client) {
		TTransport pin = client.getInputProtocol().getTransport();
		return pin.isOpen();
	}

	static interface PoolOperationCallBack {
		// 销毁client之前执行
		void destroy(TServiceClient client);

		// 创建成功是执行
		void make(TServiceClient client);
	}

}
