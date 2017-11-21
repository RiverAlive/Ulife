package butao.ulife.com.retrofit;


import okhttp3.RequestBody;
import butao.ulife.com.util.JsonUtil;

/**
 * Post请求格式
 * Created by bodong on 2017/1/20.
 */
public class ApiPost {
    /**
     * 目标对象到JSON数据格式转换Post数据参数
     * Post请求用
     * @param src 目标对象
     */
    public static RequestBody toPost(Object src) {
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(src));
        return body;
    }
}
