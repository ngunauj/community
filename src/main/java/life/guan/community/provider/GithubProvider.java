package life.guan.community.provider;

import com.alibaba.fastjson.JSON;
import life.guan.community.dto.AccessTokenDTO;
import life.guan.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//把当前类初始化到spring的上下文
@Component
public class GithubProvider {
    public String getAccesstoken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String[] split = string.split("&");
            String tokenstring = split[0];
            String token = tokenstring.split("=")[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
