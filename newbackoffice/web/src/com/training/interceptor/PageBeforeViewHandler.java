/**
 *
 */
package com.training.interceptor;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author maxyang
 *
 */
public class PageBeforeViewHandler implements BeforeViewHandler
{
	private static final Logger LOG = Logger.getLogger(PageBeforeViewHandler.class);

	@Resource
	private UserService userService;

//	@Resource
//	NewBackofficeService newBackofficeService;


	@Override
	public void beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView)
	{
		final Object urlEncodingAttributes = request.getAttribute("encodingAttributes");
		final String contextPath = StringUtils.remove(request.getContextPath(),
				(urlEncodingAttributes != null) ? urlEncodingAttributes.toString() : "");

		final String resourcePath = contextPath + "/ui";

		final String encodedContextPath = request.getContextPath();
		//		final LanguageModel currentLanguage = commerceCommonI18NService.getCurrentLanguage();

		final UserModel user = userService.getCurrentUser();
		final Language language = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		modelAndView.addObject("language", language.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("contextPath", contextPath);
		modelAndView.addObject("resourcePath", resourcePath);
		modelAndView.addObject("encodedContextPath", encodedContextPath);
		//		modelAndView.addObject("language", (currentLanguage != null ? currentLanguage.getIsocode() : "en"));
//		modelAndView.addObject("CSRFToken", CSRFTokenManager.getTokenForSession(request.getSession()));


		//		final DeviceData currentDetectedDevice = deviceDetectionFacade.getCurrentDetectedDevice();
		//		modelAndView.addObject("detectedDevice", currentDetectedDevice);

		
//		modelAndView.addObject("isCTIGroup",Boolean.valueOf(newBackofficeService.isCTIGroup(user)));
	}
}
