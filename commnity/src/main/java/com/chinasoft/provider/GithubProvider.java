package com.chinasoft.provider;

import com.alibaba.fastjson.JSON;
import com.chinasoft.dto.AccessTokenDto;
import com.chinasoft.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {

    /**
     * 获得所需要的access_token
     */
    public String getAccessToken(AccessTokenDto accessTokenDto){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String access_token = string.split("&")[0].split("=")[1];
                System.out.println(access_token);
                return access_token;
        } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * 获取返回的用户信息
     * @param access_token
     * @return
     */
    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + access_token)
                .build();
        try {
        Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);

            return githubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
