package com.it.community.provider;

import com.alibaba.fastjson.JSON;
import com.it.community.dto.AccessTokenDTO;
import com.it.community.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToKen(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String str = response.body().string();
                System.out.println("打印"+str);
                String[] split = str.split("&");
                String s = split[0];
                String[] split1 = s.split("=");
                String token = split1[1];
                System.out.println(token);
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }

    public GiteeUser getUser(String accessToKen){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToKen)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
           GiteeUser giteeUser =  JSON.parseObject(str,GiteeUser.class);
           return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
