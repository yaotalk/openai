package com.minivision.aop.face.thrift.pool;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.SocketException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.minivision.aop.face.service.ex.ErrorType;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.minivision.aop.face.thrift.pool.ThriftClientPoolFactory.PoolOperationCallBack;

/**
 * @author panxinmiao Thrift客户端连接池代理 TODO 完善泛型设计，架构思考中
 */
@Component
@ConfigurationProperties("algorithm.client")
public class ThriftServiceClientProxyFactory implements FactoryBean<Object>, InitializingBean {
	private String service;
	private Integer maxActive = 16;// 最大活跃连接数
	//// ms,default 3 min,链接空闲时间
	// -1,关闭空闲检测
	private Integer idleTime = 60000;

	// private String serverAddress;
	@Autowired
	private ThriftServerAddressProvider addressProvider;

	private Object proxyClient;

	private static final Logger logger = LoggerFactory.getLogger(ThriftServiceClientProxyFactory.class);

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setAddressProvider(ThriftServerAddressProvider addressProvider) {
		this.addressProvider = addressProvider;
	}

	private Class<?> objectClass;

	private GenericObjectPool<TServiceClient> pool;

	private PoolOperationCallBack callback = new PoolOperationCallBack() {

		@Override
		public void make(TServiceClient client) {
			logger.debug("create a client");
		}

		@Override
		public void destroy(TServiceClient client) {
			logger.debug("destroy a client");
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		/*
		 * if(serverAddress != null){ addressProvider = new
		 * FixedAddressProvider(serverAddress); }
		 */
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// 加载Iface接口
		objectClass = classLoader.loadClass(service + "$Iface");
		// 加载Client.Factory类
		Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader
				.loadClass(service + "$Client$Factory");
		TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();
		ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(addressProvider, clientFactory, callback);
		GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = maxActive;
		poolConfig.minIdle = maxActive;
		poolConfig.maxIdle = maxActive;
		poolConfig.minEvictableIdleTimeMillis = idleTime;
		poolConfig.timeBetweenEvictionRunsMillis = idleTime / 2L;
		poolConfig.testOnBorrow = true;
		poolConfig.testOnReturn = true;
		pool = new GenericObjectPool<TServiceClient>(clientPool, poolConfig);
		proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { objectClass }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				int count = 3; // 每个请求最多三次机会
				while (count > 0) {
					TServiceClient client = pool.borrowObject();
					try {
						logger.trace("-------------> thrift method [{}] start <----------", method.getName());
						long start = System.currentTimeMillis();
						Object res = method.invoke(client, args);
						long cost = System.currentTimeMillis() - start;
						logger.trace("-------------> thrift method [{}] end, cost: {}ms<----------", method.getName(),
								cost);
						pool.returnObject(client);
						return res;
					} catch (Exception e) {
						try {
							pool.returnObject(client);
						} catch (Exception ex) {
							pool.invalidateObject(client);
						}
						logger.error("thrift communication error", e);
						count--;
					}
				}
				throw new SocketException("thrift communication error");
			}
		});
	}

	@Override
	public Object getObject() {
		return proxyClient;
	}

	@Override
	public Class<?> getObjectType() {
		return objectClass;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void close() {
		if (addressProvider != null) {
			addressProvider.close();
		}
	}
}