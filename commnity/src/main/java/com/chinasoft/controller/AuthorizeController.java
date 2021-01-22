package com.chinasoft.controller;

import com.chinasoft.dto.AccessTokenDto;
import com.chinasoft.dto.GithubUser;
import com.chinasoft.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${github.clent.id}")
    private String clent_id;

    @Value("${github.redicert.uri}")
    private String redirect_uri;

    @Value("${github.clent.secret}")
    private String clent_secret;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clent_id);
        accessTokenDto.setClient_secret(clent_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}
