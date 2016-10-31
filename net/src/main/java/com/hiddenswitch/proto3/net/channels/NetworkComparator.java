package com.hiddenswitch.proto3.net.channels;

import com.hiddenswitch.proto3.net.models.NetworkMessage;

public class NetworkComparator implements java.util.Comparator<NetworkMessage> {
	public NetworkComparator() {
	}

	@Override
	public int compare(NetworkMessage o1, NetworkMessage o2) {
		return new Integer(o1.getOrder()).compareTo(o2.getOrder());
	}
}
