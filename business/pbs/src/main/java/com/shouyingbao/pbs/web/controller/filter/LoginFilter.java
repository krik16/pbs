package com.shouyingbao.pbs.web.controller.filter;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器
 */
public class LoginFilter extends BaseController implements Filter {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    public static final String TOKEN = "user";



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        try{
            if(shouldFilter(request.getServletPath())){
                getSessionUser(request);
            }
            chain.doFilter(request, response);
        }catch (UserNotFoundException e){
            response.sendRedirect(request.getContextPath()+"/auth/login");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取当前session里面的用户
     */
    public  User getSessionUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(TOKEN);
        if(user == null){
            user = new User();
            user.setUserAccount(this.getUserDetails().getUsername());
            session.setAttribute(TOKEN, user);
        }
        return user;
    }
    @Override
    public void destroy() {

    }

    /**
     * 检查请求是否应该过滤
     */
    private boolean shouldFilter(String path){
        boolean result = true;
        if(path.equals("/auth/login")) {
            result = false;
        }else if(path.contains("/css/")){
            result = false;
        }else if(path.contains("/js/")){
            result = false;
        }else if(path.contains("/image/")){
            result = false;
        }else if(path.contains("/fonts/")){
            result = false;
        }else if(path.contains("/bill/mobileBillList")){
            result = false;
        }else if(path.contains("/pay/weixin/")){
            result = false;
        }else if(path.contains("/pay/ali/")){
            result = false;
        }else if(path.contains("/commodityCategory/")){
            result = false;
        }else if(path.contains("/commodity/")){
            result = false;
        }else if(path.contains("/mobile")){
            result = false;
        }
        return result;
    }
}
