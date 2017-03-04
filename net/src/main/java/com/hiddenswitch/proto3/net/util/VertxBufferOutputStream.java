package com.hiddenswitch.proto3.net.util;

import io.vertx.core.buffer.Buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bberman on 11/26/16.
 */
public class VertxBufferOutputStream extends OutputStream {
	private final Buffer buffer;

	public VertxBufferOutputStream() {
		buffer = Buffer.buffer(24000);
	}

	public VertxBufferOutputStream(Buffer input) {
		buffer = input;
	}

	@Override
	public void write(int b) throws IOException {
		buffer.appendByte((byte) b);
	}

	public void write(byte b[], int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) ||
				((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}

		buffer.appendBytes(b, off, len);
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public int size() {
		return getBuffer().length();
	}
}

