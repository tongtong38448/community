package com.it.community.controller;

import com.it.community.dto.AccessTokenDTO;
import com.it.community.dto.GiteeUser;
import com.it.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GiteeProvider giteeProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret("d484bf1feccb62739a9680db33bbfc5747dae0f3");
        accessTokenDTO.setStates(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback ");
        accessTokenDTO.setClient_id("c7356a6f597b5795149b");
        String token = giteeProvider.getAccessToKen(accessTokenDTO);
        GiteeUser user = giteeProvider.getUser(token);
        System.out.println(user.getName());
        return "index";
    }
}
