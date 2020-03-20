package life.guan.community.controller;

import life.guan.community.mapper.UserMapper;
import life.guan.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //进入index页面 如果查到有登陆过 那么新建session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //没查到有用户登陆过 那么不做处理，用户可以点击登陆按钮去登陆
        return "index";
    }
}