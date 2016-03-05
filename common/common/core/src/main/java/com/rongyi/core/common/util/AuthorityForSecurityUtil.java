package com.rongyi.core.common.util;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 登录用户权限工具类(非单点登录，且系统采用springSecurity)
 * @author pingkang
 *
 */
public class AuthorityForSecurityUtil {
	
	public static Collection<GrantedAuthority> getAuthorities(HttpServletRequest request){
		Principal principal = request.getUserPrincipal();
		if(principal == null){
			return null;
		}
	    Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken)principal).getAuthorities();
	    return authorities;
	}
	
	/*public static Map<String, Object> getAttributes(HttpServletRequest request){
		CasAuthenticationToken token = (CasAuthenticationToken) request.getUserPrincipal();
	    AttributePrincipal principal = token.getAssertion().getPrincipal();
	    Map<String, Object> attributes = principal.getAttributes();
	    return attributes;
	}*/
	
	/**
	 * 判断当前请求是否具有指定权限中的任何一个
	 * @param request
	 * @param roleAuthorities
	 * @return
	 */
	public static boolean hasAnyRoleAuthority(HttpServletRequest request,List<String> roleAuthorities){
		Collection<GrantedAuthority> authorities = getAuthorities(request);
		if(roleAuthorities != null && roleAuthorities.size() > 0){
			for (String roleAuthority : roleAuthorities) {
				if(authorities.contains(new SimpleGrantedAuthority(roleAuthority))){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断当前请求是否具有指定权限
	 * @param request
	 * @param roleAuthority
	 * @return
	 */
	public static boolean hasRoleAuthority(HttpServletRequest request,String roleAuthority){
		Collection<GrantedAuthority> authorities = getAuthorities(request);
		if(StringUtils.isNotBlank(roleAuthority)){
			if(authorities.contains(new SimpleGrantedAuthority(roleAuthority))){
				return true;
			}
		}
		return false;
	}
}
