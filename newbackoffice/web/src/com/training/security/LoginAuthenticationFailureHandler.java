/**
 *
 */
package com.training.security;

import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.session.SessionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
	private static final Logger LOG = Logger.getLogger(LoginAuthenticationFailureHandler.class);

	private BruteForceAttackCounter bruteForceAttackCounter;

	@Resource
	SessionService sessionService;

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException exception) throws IOException, ServletException
	{
		Session session = sessionService.getCurrentSession();
		String backurl = "";

		LOG.info("onAuthenticationFailure msg: " + exception.getMessage());
		request.getSession().setAttribute("logError",exception.getMessage());
		if(session != null)
		{
			backurl = session.getAttribute("backurl") != null ?  session.getAttribute("backurl").toString() : "";
		}
		
		if(StringUtils.isNotEmpty(backurl))
		{
			response.sendRedirect(backurl);
			session.removeAttribute("backurl");
		}
		else
		{
			// Register brute attacks
			bruteForceAttackCounter.registerLoginFailure(request.getParameter("j_username"));
			
			// Store the j_username in the session
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", request.getParameter("j_username"));
			
			super.onAuthenticationFailure(request, response, exception);
		}
	}



	protected BruteForceAttackCounter getBruteForceAttackCounter()
	{
		return bruteForceAttackCounter;
	}

	@Required
	public void setBruteForceAttackCounter(final BruteForceAttackCounter bruteForceAttackCounter)
	{
		this.bruteForceAttackCounter = bruteForceAttackCounter;
	}
}
