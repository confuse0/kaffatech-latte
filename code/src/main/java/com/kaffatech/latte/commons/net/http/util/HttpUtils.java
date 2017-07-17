/**
 *
 */
package com.kaffatech.latte.commons.net.http.util;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import com.kaffatech.latte.commons.json.util.JsonUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;

/**
 * @author zhen.ling
 *
 */
public class HttpUtils {

    public static final String DEFAULT_ENCODING = "utf8";
    public static final String JSON_CONTENT_TYPE = "application/json;chatset=utf8";
    public static final String XML_CONTENT_TYPE = "application/xml";

    public static <T> T postForm(String url, List<NameValuePair> parameter,
                                 ResponseHandler<T> handler) {
        T res = null;

        HttpClient client = createClient(url);

        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(parameter, HTTP.UTF_8));

            res = client.execute(post, handler);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            post.releaseConnection();
        }
        return res;
    }

    public static <T> T postJson(String url, ResponseHandler<T> handler) {
        return postJson(url, null, handler, null);
    }

    public static <T> T postJson(String url, String jsonString,
                                 ResponseHandler<T> handler) {
        return postJson(url, jsonString, handler, null);
    }

    public static <T> T postJson(String url, ResponseHandler<T> handler,
                                 Header... header) {
        return postJson(url, null, handler, header);
    }

    public static <T> T postJsonBean(String url, Object param, final Class<T> resultClazz,
                                 Header... header) {
        String jsonString = JsonUtils.toJsonString(param);
        ResponseHandler<T> handler = new ResponseHandler<T>() {
            public T handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {
                if (response.getStatusLine().getStatusCode() == 200) {
                    // http请求正常返回
                    HttpEntity httpEntity = response.getEntity();
                    T obj = JsonUtils.toJsonObject(
                            EntityUtils.toString(httpEntity), resultClazz);
                    return obj;
                } else {
                    throw new IllegalStateException("post json bean fail:" + response.getStatusLine().getStatusCode());
                }
            }
        };

        return postJson(url, jsonString, handler, header);
    }

    public static <T> T postJson(String url, String jsonString,
                                 ResponseHandler<T> handler, Header... header) {
        T res = null;

        HttpClient client = createClient(url);

        HttpPost post = new HttpPost(url);
        try {
            if (header != null) {
                for (Header each : header) {
                    post.setHeader(each);
                }
            }

            if (StringUtils.isNotEmpty(jsonString)) {
                StringEntity s = new StringEntity(jsonString, DEFAULT_ENCODING);
                s.setContentType(JSON_CONTENT_TYPE);
                post.setEntity(s);
            }

            res = client.execute(post, handler);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            post.releaseConnection();
        }
        return res;
    }

    public static <T> T get(String url, ResponseHandler<T> handler,
                            Header... header) {
        T res = null;

        HttpClient client = createClient(url);

        HttpGet get = new HttpGet(url);
        try {
            if (header != null) {
                for (Header each : header) {
                    get.setHeader(each);
                }
            }

            res = client.execute(get, handler);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            get.releaseConnection();
        }
        return res;
    }

    public static <T> T putJson(String url, String jsonString,
                                ResponseHandler<T> handler, Header... header) {
        T res = null;

        HttpClient client = createClient(url);

        HttpPut put = new HttpPut(url);
        try {
            if (header != null) {
                for (Header each : header) {
                    put.setHeader(each);
                }
            }

            if (StringUtils.isNotEmpty(jsonString)) {
                StringEntity s = new StringEntity(jsonString, DEFAULT_ENCODING);
                s.setContentType(JSON_CONTENT_TYPE);
                put.setEntity(s);
            }

            res = client.execute(put, handler);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            put.releaseConnection();
        }
        return res;
    }

    public static <T> T postXml(String url, String xmlString, String encoding,
                                ResponseHandler<T> handler) {
        return postXml(url, xmlString, encoding, handler, null);
    }

    public static <T> T postXml(String url, String xmlString, ResponseHandler<T> handler) {
        return postXml(url, xmlString, DEFAULT_ENCODING, handler, (Header[]) null);
    }

    public static <T> T postXml(String url, String xmlString, String encoding,
                                ResponseHandler<T> handler, Header... header) {
        T res = null;

        HttpClient client = createClient(url);

        HttpPost post = new HttpPost(url);
        try {
            if (header != null) {
                for (Header each : header) {
                    post.setHeader(each);
                }
            }

            if (StringUtils.isNotEmpty(xmlString)) {
                StringEntity s = new StringEntity(xmlString, encoding);
                s.setContentType(XML_CONTENT_TYPE);
                post.setEntity(s);
            }

            res = client.execute(post, handler);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            post.releaseConnection();
        }
        return res;
    }

    private static HttpClient createClient(String url) {
        HttpClient client = null;
        if (url.startsWith("https")) {
            client = createSSLClientDefault();
        } else if (url.startsWith("http")) {
            client = HttpClients.createDefault();
        }
        return  client;
    }

    private static HttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        // 信任所有
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        String a = "http://api.fist.xinxiannv.com/metadata/getRegion.do";
        postJson(
                a,// "http://www.xinxiannv.com:9080/fist/metadata/getRegion.do",
                "{\"channel\":2, \"protocolVer\":\"1.0\", \"appVer\":\"1.0\"}",
                new ResponseHandler<String>() {

                    @Override
                    public String handleResponse(HttpResponse response)
                            throws ClientProtocolException, IOException {

                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity httpEntity = response.getEntity();
                            System.out.println(EntityUtils.toString(httpEntity));

                        }
                        System.out.println(response.getStatusLine()
                                .getStatusCode());
                        return null;
                    }
                });
    }
}