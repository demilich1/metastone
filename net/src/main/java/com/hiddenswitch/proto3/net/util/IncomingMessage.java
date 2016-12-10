package com.hiddenswitch.proto3.net.util;

import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by bberman on 11/26/16.
 */
public class IncomingMessage {
	private static final Logger logger = LoggerFactory.getLogger(IncomingMessage.class);
	public static final byte[] MAGIC_BYTES = new byte[]{1, 2, -127, 83};
	public static final int HEADER_SIZE = MAGIC_BYTES.length + 4;
	private final int expectedLength;
	private final Buffer buffer;

	public IncomingMessage(Buffer firstBuffer) throws IOException {
		if (firstBuffer.length() >= HEADER_SIZE) {
			final byte[] magicHeader = firstBuffer.getBytes(0, 4);
			for (int i = 0; i < 4; i++) {
				if (magicHeader[i] != MAGIC_BYTES[i]) {
					throw new IOException("The magic header does not match!");
				}
			}
			this.expectedLength = ByteBuffer.wrap(firstBuffer.getBytes(4, 8)).getInt(0);
			boolean isFirstBufferComplete = this.expectedLength - firstBuffer.length() + HEADER_SIZE <= 0;
			if (isFirstBufferComplete) {
				// This buffer contains two messages.
				this.buffer = firstBuffer.slice(0, this.expectedLength + HEADER_SIZE);
			} else {
				this.buffer = firstBuffer;
			}

			logger.debug("IncomingMessage with expected length {}", this.expectedLength);
		} else {
			throw new IOException("This is not a first buffer, since we couldn't possibly have a header byte.");
		}
	}

	private int sizeRemaining() {
		return expectedLength - buffer.length() + HEADER_SIZE;
	}

	/**
	 * The completion status of this message.
	 *
	 * @return Returns <b>true</b> if the buffer is greater than or equal to the expected length.
	 */
	public boolean isComplete() {
		return sizeRemaining() <= 0;
	}

	/**
	 * Append a buffer to this incoming message.
	 *
	 * @param incoming An incoming message buffer.
	 * @return The number of bytes read from the incoming buffer.
	 */
	public int append(Buffer incoming) {
		if (incoming.length() <= sizeRemaining()) {
			buffer.appendBuffer(incoming);
			return incoming.length();
		} else {
			int bytesToRead = sizeRemaining();
			buffer.appendBuffer(incoming, 0, bytesToRead);
			return bytesToRead;
		}
	}

	public Buffer getBufferWithoutHeader() {
		return buffer.slice(HEADER_SIZE, buffer.length());
	}

	public int getExpectedLength() {
		return expectedLength;
	}
}
