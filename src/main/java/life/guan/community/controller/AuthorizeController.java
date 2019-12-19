package life.guan.community.controller;

import life.guan.community.dto.AccessTokenDTO;
import life.guan.community.dto.GithubUser;
import life.guan.community.provider.GithubProvider;
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

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String accesstoken = githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accesstoken);
        //System.out.println(user.getName());
        if (user != null) {
            // 登陆成功 写cookie和session
            request.getSession().setAttribute("user", user);
            // 重定向
            return "redirect:/";
        } else{
            return "redirect:/";
        }
        //return "index";
    }
}
