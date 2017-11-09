package com.minivision.aop.face.thrift.pool;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("algorithm")
public class FixedAddressProvider implements ThriftServerAddressProvider {
	private String name;
	private List<String> serverAddress;

	private final List<InetSocketAddress> container = new ArrayList<InetSocketAddress>();

	private Queue<InetSocketAddress> inner = new LinkedList<InetSocketAddress>();

	private Object lock = new Object();

	public FixedAddressProvider() {

	}

	public FixedAddressProvider(String serverAddress) {
		String[] ads = serverAddress.split(",");
		this.serverAddress = Arrays.asList(ads);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<InetSocketAddress> findServerAddressList() {
		return Collections.unmodifiableList(container);
	}

	@Override
	public InetSocketAddress selector() {
		if (inner.isEmpty()) {
			if (!container.isEmpty()) {
				inner.addAll(container);
			} else {
				synchronized (lock) {
					for (String address : serverAddress) {
						container.addAll(transfer(address));
					}
					Collections.shuffle(container);
					inner.addAll(container);
				}
			}
		}
		return inner.poll();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	public List<String> getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(List<String> serverAddress) {
		this.serverAddress = serverAddress;
	}

	private List<InetSocketAddress> transfer(String address) {
		String[] hostname = address.split(":");
		int priority = 1;
		if (hostname.length == 3) {
			priority = Integer.valueOf(hostname[2]);
		}
		String ip = hostname[0];
		Integer port = Integer.valueOf(hostname[1]);
		List<InetSocketAddress> result = new ArrayList<InetSocketAddress>();
		for (int i = 0; i < priority; i++) {
			result.add(new InetSocketAddress(ip, port));
		}
		return result;
	}

}
