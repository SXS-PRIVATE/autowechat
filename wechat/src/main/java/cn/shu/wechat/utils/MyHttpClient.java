package cn.shu.wechat.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP访问类，对Apache HttpClient进行简单封装，适配器模式
 *
 * @author ShuXinSheng
 * @version 1.1
 * @date 创建时间：2017年4月9日 下午7:05:04
 */
@Log4j2
public class MyHttpClient {

    private static final CloseableHttpClient myHttpClient;


    public static final CookieStore cookieStore;

    static {
        cookieStore = new BasicCookieStore();
        // 将CookieStore设置到MyHttpClient中
        myHttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    public static String getCookie(String name) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(name)) {
                return cookie.getValue();
            }
        }
        return null;

    }

    private MyHttpClient() {

    }

    /**
     * 获取cookies
     *
     * @author SXS
     * @date 2017年5月7日 下午8:37:17
     * @return
     */
/*	public static MyHttpClient getInstance() {
		if (instance == null) {
			synchronized (MyHttpClient.class) {
				if (instance == null) {
					instance = new MyHttpClient();
				}
			}
		}
		return instance;
	}*/

    /**
     * 处理GET请求
     *
     * @param url
     * @param params
     * @return
     * @author SXS
     * @date 2017年4月9日 下午7:06:19
     */
    public static HttpEntity doGet(String url, List<BasicNameValuePair> params, boolean redirect,
                                   Map<String, String> headerMap) {
        HttpEntity entity = null;
        HttpGet httpGet;

        try {
            if (params != null) {
                String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
                httpGet = new HttpGet(url + "?" + paramStr);
            } else {
                httpGet = new HttpGet(url);
            }
            if (!redirect) {
                httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build()); // 禁止重定向
            }
            httpGet.setHeader("User-Agent", Config.USER_AGENT);
            if (headerMap != null) {
                Set<Entry<String, String>> entries = headerMap.entrySet();
                for (Entry<String, String> entry : entries) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpResponse response = myHttpClient.execute(httpGet);
            entity = response.getEntity();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return entity;
    }

    /**
     * 处理POST请求
     *
     * @param url
     * @param paramsStr
     * @return
     * @author SXS
     * @date 2017年4月9日 下午7:06:35
     */
    public static HttpEntity doPost(String url, String paramsStr) {
        HttpEntity entity = null;
        HttpPost httpPost;
        try {
            StringEntity params = new StringEntity(paramsStr, Consts.UTF_8);
            httpPost = new HttpPost(url);
            httpPost.setEntity(params);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("User-Agent", Config.USER_AGENT);
            CloseableHttpResponse response = myHttpClient.execute(httpPost);
            entity = response.getEntity();

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return entity;
    }

    /**
     * 上传文件到服务器
     *
     * @param url
     * @param reqEntity
     * @return
     * @author SXS
     * @date 2017年5月7日 下午9:19:23
     */
    public static HttpEntity doPostFile(String url, HttpEntity reqEntity) {
        HttpEntity entity = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", Config.USER_AGENT);
        httpPost.setEntity(reqEntity);
        try {
            CloseableHttpResponse response = myHttpClient.execute(httpPost);
            entity = response.getEntity();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

}