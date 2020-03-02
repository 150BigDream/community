package com.example.community.Controller;

import com.example.community.Provider.GithubProvider;
import com.example.community.dto.AccessTokenDto;
import com.example.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    //这个spring注解会去在配置文件里找github.client.id这个key的value，赋值到clientId
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect_uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){//这个注解参数接收，操作参数
        AccessTokenDto accessTokenDto = new AccessTokenDto();

        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);

        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getId());

        if (user!=null){
            //登陆成功
            request.getSession().setAttribute("user",user);
        }
        else {
            //登陆失败
        }
        return "redirect:/";
    }
}
