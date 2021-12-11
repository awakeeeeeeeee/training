/**
 *
 */
package com.training.controller.security;

import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 */
public class GUIDAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

	Logger LOG = Logger.getLogger(GUIDAuthenticationSuccessHandler.class);
	@Resource
	private UserService userService;

	@Resource
	SessionService sessionService;
	
	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{
		if(sessionService.getCurrentSession() != null && sessionService.getCurrentSession().getAttribute("backurl") != null)
		{
			if(LOG.isDebugEnabled())
			{
				LOG.debug("newbackoffice sso login, principal=" + authentication.getPrincipal() + ", backurl=" + sessionService.getCurrentSession().getAttribute("backurl"));
			}
			String backurl = sessionService.getCurrentSession().getAttribute("backurl");
			if(backurl.contains("http"))
			{
				response.sendRedirect(backurl);
			}
			else
			{
				response.sendRedirect(backurl.startsWith("/") ? backurl : "/" + backurl);
			}
			sessionService.getCurrentSession().removeAttribute("backurl");
		}
		else
		{
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
