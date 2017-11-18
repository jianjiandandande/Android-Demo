package com.gaowenxing.okhttpdemo.LocalServer;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Vincent on 2017/10/19.
 */

/**
 * 上传进度的实现
 * 封装原有的RequestBody
 */

public class CountingRequestBody extends RequestBody{

    protected RequestBody delegate;//RequestBody的一个代理

    private Listener mListener;

    public CountingRequestBody(RequestBody delegate, Listener listener) {
        this.delegate = delegate;
        mListener = listener;
    }

    private CountingSink mCountingSink;

    @Nullable
    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength(){
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        mCountingSink = new CountingSink(sink);

        BufferedSink bufferedSink = Okio.buffer(mCountingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();

    }

    public final class CountingSink extends ForwardingSink{

        private long buffWritten;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            buffWritten += byteCount;

            mListener.countProgress(buffWritten,contentLength());
        }
    }

    public static interface Listener{

        /**
         * @param buffWritten 已经写入的长度
         * @param contentLength 总长度
         */
        void countProgress(long buffWritten,long contentLength);
    }

}
