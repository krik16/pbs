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
            if(request.getServletPath().equals("/auth/login")) {

            }else if(request.getServletPath().contains("/css/")){

            }else if(request.getServletPath().contains("/js/")){

            }else if(request.getServletPath().contains("/image/")){

            }else if(request.getServletPath().contains("/fonts/")){

            }else if(request.getServletPath().contains("/bill/mobileBillList")){

            }else if(request.getServletPath().contains("/pay/weixin/")){

            }else if(request.getServletPath().contains("/pay/ali/")){

            }else {
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
     * @return
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
}
