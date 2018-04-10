package lt.itakademija.servlet;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] bodyBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        bodyBytes = read(request.getInputStream());
    }

    private byte[] read(ServletInputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int singleByte = -1;
        while ((singleByte = is.read()) != -1) {
            baos.write(singleByte);
        }

        return baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream baos = new ByteArrayInputStream(bodyBytes);

        return new ServletInputStream() {
            public int read() throws IOException {
                return baos.read();
            }

            @Override
            public boolean isFinished() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isReady() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), getCharacterEncoding()));
    }

}
