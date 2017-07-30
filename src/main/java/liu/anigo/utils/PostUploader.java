package liu.anigo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;

public class PostUploader {
    public static void main(String[] args) {

    }

    public boolean uploadFiles(String url, File file, Map<String, String> params) throws Exception {

        ContentType contentType = ContentType.create("application/octet-stream", "UTF-8");
        HttpClient client = HttpClientBuilder.create().build();// 开启一个客户端 HTTP 请求
        HttpPost post = new HttpPost(url);//创建 HTTP POST 请求

        /// 关注builder的操作
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式

        builder.addBinaryBody("file", file);
        builder.addTextBody("method", params.get("method"));//设置请求参数
        builder.addTextBody("fileTypes", params.get("fileTypes"));//设置请求参数
        StringBody stringBody = new StringBody("中文乱码", contentType);
        builder.addPart("test", stringBody);

        HttpEntity entity = builder.build();// 生成 HTTP POST 实体
        post.setEntity(entity);//设置请求参数
        HttpResponse response = client.execute(post);// 发起请求 并返回请求的响应
        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        }
        return false;
    }
}
