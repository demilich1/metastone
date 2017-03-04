package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.MulliganRequest;
import com.hiddenswitch.proto3.net.models.MulliganResponse;
import com.hiddenswitch.proto3.net.models.RequestActionRequest;
import com.hiddenswitch.proto3.net.models.RequestActionResponse;

/**
 * Created by bberman on 12/8/16.
 */
public interface Bots {
	MulliganResponse mulligan(MulliganRequest request);

	RequestActionResponse requestAction(RequestActionRequest request);
}
