package com.chinasoft.controller;

import com.chinasoft.dto.AccessTokenDto;
import com.chinasoft.dto.GithubUser;
import com.chinasoft.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
接收github登录
* */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id("732981b010f1832ffece");
        accessTokenDto.setClient_secret("008a094a76e916b4d0d9728a259231b770bd76c2");
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName()   );

        return "index";
    }

}
