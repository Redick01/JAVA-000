package java0.nio01;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author liu_penghui
 * @date 2020/10/27.
 */
public class HttpClient {

    public static void main(String[] args) throws IOException {
        get("http://localhost:8801");
    }

    public static void get(String path) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明get请求
        HttpGet get = new HttpGet(path);

        CloseableHttpResponse response = null;
        try {
            response = response = httpClient.execute(get);
            // 判断状态码
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                System.out.println(entity.toString());
            }
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpClient) {
                httpClient.close();
            }
        }
    }
}
