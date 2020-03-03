package com.example.community.Controller;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;

    /**
     * //TODO 其实还蛮疑惑的，每次登陆的话，如果有GitHubuser，不管是不是同一个，都会重新生成一个随机token，
     * 这样子的话，重启或者什么，不还是会重新生成一个token吗？怎么识别在数据库有，是你呢？
     * cookie是存在浏览器的。好像明白了，key-value这种形式，key不可以同名，value会被更新掉。但是也会更新的啊，我看token的值没有变
     * 不更新的原因是因为是insert语句而不是update语句吗？有些属性相同的，它就不会再插入了
     * @param request
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies
             ) {
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }
}
