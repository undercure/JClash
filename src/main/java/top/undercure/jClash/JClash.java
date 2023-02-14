package top.undercure.jClash;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import top.undercure.jClash.domain.ClashVersion;
import top.undercure.jClash.domain.ProxiesInfo;
import top.undercure.jClash.domain.exception.JClashException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 调用本地Clash，方便的使用代理服务
 * @author underCure
 * @date 2023/02/14 12:20
 */
@Data
public class JClash {
    private ClashVersion clashVersion;
    private String port;
    private String secret;
    private ArrayList<String> allProxy;
    private String now;

    /**
     * 初始化JClash，获取基础信息
     */
    public void init(){
        //获取版本
        getVersion();
        //获取代理清单
        getProxyList();

    }

    /**
     *
     * @param index 节点在allProxy中的索引
     */
    public void switchProxy(int index) {
        if (allProxy == null){
            throw new JClashException("请先使用init()初始化");
        }
        if (allProxy.size()<index || index <0){
            throw new JClashException("非法索引，请检查");
        }
        PutMethod putMethod = new PutMethod("http://127.0.0.1:"+this.port+"/proxies/PROXY");
        RequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity("{\"name\":\""+this.allProxy.get(index)+"\"}","TEXT","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        putMethod.setRequestEntity(requestEntity);
        connection(addSameRequestHeader(putMethod, this.secret));
        getProxyList();
    }

    /**
     * 获取或更新代理清单
     */
    public void getProxyList() {
        GetMethod getMethod = new GetMethod("http://127.0.0.1:"+this.port+"/proxies");
        ProxiesInfo proxiesInfo= JSON.parseObject(connection(addSameRequestHeader(getMethod,this.secret)), ProxiesInfo.class);
        this.allProxy = proxiesInfo.getProxies().getProxy().getAll();
        this.now = proxiesInfo.getProxies().getProxy().getNow();
    }

    /**
     * 获取当前Clash的版本
     */
    private void getVersion(){
        ClashVersion version;
        if (this.clashVersion == null){
            GetMethod getMethod = new GetMethod("http://127.0.0.1:"+this.port+"/version");
            version = JSON.parseObject(connection(addSameRequestHeader(getMethod,this.secret)),ClashVersion.class)   ;
            this.clashVersion = version;
        }
    }

    /**
     * 发起http请求
     * */
    private String connection(HttpMethod httpMethod)  {
        HttpClient httpClient = new HttpClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream responseBodyStream = null;
        String result = "";
        try {
            int code = httpClient.executeMethod(httpMethod);
            if (code == 200){
                byte[] b = new byte[10240];
                int n;
                responseBodyStream = httpMethod.getResponseBodyAsStream();
                while ((n = responseBodyStream.read(b)) != -1) {
                    outputStream.write(b, 0, n);
                }
                result = outputStream.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("链接失败，检查端口和密钥");
        }finally {
            try {
                outputStream.close();
                if (responseBodyStream != null){
                    responseBodyStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置相同的请求头
     */
    private static HttpMethod addSameRequestHeader(HttpMethod httpMethod,String secret){
        httpMethod.addRequestHeader("accept", "*/*");
        httpMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        httpMethod.addRequestHeader("Cache-Control","no-cache");
        httpMethod.addRequestHeader("User-Agent","PostmanRuntime/7.30.1");
        httpMethod.addRequestHeader("Accept","*/*");
        httpMethod.addRequestHeader("Accept-Encoding","gzip, deflate, br");
        httpMethod.addRequestHeader("Connection","keep-alive");
        httpMethod.addRequestHeader("Authorization","Bearer "+secret);
        return httpMethod;
    }
}