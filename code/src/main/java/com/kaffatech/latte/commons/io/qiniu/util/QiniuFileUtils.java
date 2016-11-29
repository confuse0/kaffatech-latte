package com.kaffatech.latte.commons.io.qiniu.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.commons.io.model.ImgInfo;
import com.kaffatech.latte.commons.io.util.FileUtils;
import com.kaffatech.latte.commons.net.http.util.HttpUtils;
import com.kaffatech.latte.commons.json.util.JsonUtils;


public class QiniuFileUtils {

    public static final String DOMAIN = "7xjqgr.com1.z0.glb.clouddn.com";
    public static final String BUCKET_NAME = "sweet";

    public static final String DOMAIN_DEV = "7xjl7i.com1.z0.glb.clouddn.com";
    public static final String BUCKET_NAME_DEV = "sweet-dev";

    public static final String ACCESS_KEY = "LSHSvHr0YjMl6OBhBjNj1aeCNQ3OPGo2Pta6kzXZ";
    public static final String SECRET_KEY = "_7AhXORXIaDw00q0GKdXAA9lETcd4v3t5U7nekqo";

    public static final String ACCESS_KEY_DEV = "sYRiKcDAEAL0psZFG60Bt1EKRnJvu4KZFCD_7LB9";
    public static final String SECRET_KEY_DEV = "DbBenAUgP6zPSjov701H5KoWHSL6_RffI9CIQM9m";

    /**
     * @return
     */
    public static String getAk() {
        if (SystemProperties.isProduction()) {
            return ACCESS_KEY;
        }
        return ACCESS_KEY_DEV;
    }

    /**
     * @return
     */
    public static String getSk() {
        if (SystemProperties.isProduction()) {
            return SECRET_KEY;
        }
        return SECRET_KEY_DEV;
    }

    /**
     * @return
     */
    public static String getBucketName() {
        if (SystemProperties.isProduction()) {
            return BUCKET_NAME;
        }
        return BUCKET_NAME_DEV;
    }

    /**
     * @return
     */
    public static String getDomain() {
        if (SystemProperties.isProduction()) {
            return DOMAIN;
        }
        return DOMAIN_DEV;
    }

    /**
     * 上传文件
     *
     * @param key
     * @param filename
     */
    public static PutRet uploadFile(String key, String filename) {
        return uploadFile(key, new File(filename));
    }

    /**
     * 上传文件
     *
     * @param key
     * @param file
     */
    public static PutRet uploadFile(String key, File file) {
        String uptoken = generateUptoken();

        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.putFile(uptoken, key, file, extra);
        return ret;
    }

    /**
     * 上传流
     *
     * @param key
     * @param inputStream
     * @return
     */
    public static PutRet uploadStream(String key, InputStream inputStream) {
        String uptoken = generateUptoken();

        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);
        return ret;
    }

    /**
     * 下载文件
     *
     * @param key
     * @param filename
     */
    public static void downloadFile(String key, String filename) {
        downloadFile(key, new File(filename));
    }

    /**
     * 下载文件
     *
     * @param key
     * @param file
     */
    public static void downloadFile(String key, File file) {
        String url = generateUrl(key);

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
                public byte[] handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            return EntityUtils.toByteArray(entity);
                        } else {
                            return null;
                        }
                    }
                    return null;
                }
            };

            byte[] data = httpClient.execute(get, handler);
            FileUtils.writeByteArrayToFile(file, data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * 产生下载路径
     *
     * @param key
     * @return
     */
    public static String generateUrl(String key) {
        return generateUrl(key, null);
    }

    /**
     * 产生下载路径
     *
     * @param key
     * @param param
     * @return
     */
    public static String generateUrl(String key, String param) {
        return generateUrl(key, param, 0);
    }

    /**
     * 产生下载路径
     *
     * @param key
     * @param param
     * @param expires
     * @return
     */
    public static String generateUrl(String key, String param, int expires) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        // Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
        String getUrl = null;
        try {
            if (StringUtils.equals(StringUtils.substring(key, 0, 4), "http")) {
                getUrl = key;
            } else {
                String pubUrl = "http://" + getDomain() + "/" + key;
                if (StringUtils.isNotEmpty(param)) {
                    pubUrl = pubUrl + "?" + param;
                }
                getUrl = pubUrl;
                // GetPolicy policy = new GetPolicy();
                // if (expires > 0) {
                // policy.expires = expires;
                // }
                // getUrl = policy.makeRequest(pubUrl, mac);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return getUrl;
    }

    /**
     * 产生上传token
     *
     * @return
     */
    public static String generateUptoken() {
        Mac mac = new Mac(getAk(), getSk());
        PutPolicy putPolicy = new PutPolicy(getBucketName());

        String upToken = null;
        try {
            upToken = putPolicy.token(mac);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return upToken;
    }

    public static ImgInfo getImgInfo(final String key) {
        String getUrl = generateUrl(key);
        String imgInfoUrl = getUrl + "?imageInfo";
        ImgInfo imgInfo = HttpUtils.get(imgInfoUrl,
                new ResponseHandler<ImgInfo>() {
                    @Override
                    public ImgInfo handleResponse(HttpResponse response)
                            throws ClientProtocolException, IOException {

                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity httpEntity = response.getEntity();
                            Map obj = JsonUtils.toJsonObject(
                                    EntityUtils.toString(httpEntity), Map.class);

                            ImgInfo imgInfo = new ImgInfo();
                            imgInfo.setWidth((Integer) obj.get("width"));
                            imgInfo.setHeight((Integer) obj.get("height"));
                            return imgInfo;
                        } else {
                            HttpEntity httpEntity = response.getEntity();
                            Map obj = JsonUtils.toJsonObject(
                                    EntityUtils.toString(httpEntity), Map.class);
                            String error = obj.get("code") + "_"
                                    + obj.get("error");

                            throw new IllegalStateException("get imgInfo ["
                                    + key + "] fail:" + error);
                        }
                    }
                });
        return imgInfo;
    }

    public static void main(String[] args) {
        String path = "dt/mct/123456789/33123465468837888.png";

        System.out.println(generateUrl(path));

        System.out
                .println(getImgInfo("dt/mct/44427381829531648/fb9c2a849754bfe11941548528be4344.jpg"));
    }
}
