/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.training.controller.security;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Constants;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.spring.security.CoreAuthenticationProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Derived authentication provider supporting additional authentication checks. See
 * {@link de.hybris.platform.spring.security.RejectUserPreAuthenticationChecks}.
 *
 * <ul>
 * <li>prevent login without password for users created via CSCockpit</li>
 * <li>prevent login as user in group admingroup</li>
 * </ul>
 *
 * any login as admin disables SearchRestrictions and therefore no page can be viewed correctly
 */
public class AcceleratorAuthenticationProvider extends CoreAuthenticationProvider
{
	private static final Logger LOG = Logger.getLogger(AcceleratorAuthenticationProvider.class);
	private static final String ROLE_ADMIN_GROUP = "ROLE_" + Constants.USER.ADMIN_USERGROUP.toUpperCase();

	private BruteForceAttackCounter bruteForceAttackCounter;
	private UserService userService;
	private ModelService modelService;
	private GrantedAuthority adminAuthority = new SimpleGrantedAuthority(ROLE_ADMIN_GROUP);

	@Resource
	SessionService sessionService;

	@Resource
	BaseSiteService baseSiteService;

	@Resource
	CatalogVersionService catalogVersionService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException
	{
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		UserModel userModel = null;
		initContext();
		try
		{
			userModel = getUserService().getUserForUID(username);
			final BaseSiteModel siteModel = baseSiteService.getAllBaseSites().iterator().next();
			if (siteModel != null)
			{
				sessionService.setAttribute("backendSite", siteModel.getUid());
			}
			sessionService.setAttribute("backendUser", username);
			sessionService.setAttribute("backendsUserModel", userModel);
		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("non existing user name " + username);
			throw new BadCredentialsException("賬號不存在 " + username);
		}


		if (userModel.isLoginDisabled()){
			throw new BadCredentialsException("您的賬號被鎖定，請聯繫IT");
		}


		//非LDAP的账号才锁
		if (getBruteForceAttackCounter().isAttack(username) && !userModel.getLdapaccount())
		{

			if (userModel != null)
			{
				userModel.setLoginDisabled(true);
				if (userModel instanceof EmployeeModel)
				{
//					((EmployeeModel) userModel).setLockTime(new Date());
				}



				getModelService().save(userModel);
				bruteForceAttackCounter.resetUserCounter(userModel.getUid());
			}
			else
			{
				LOG.warn("Brute force attack attempt for non existing user name " + username);
			}

			throw new BadCredentialsException("密碼錯誤");

		}

		if(authentication.isAuthenticated()){
			return authentication;
		}else{
			try
			{
				return super.authenticate(authentication);
			}catch (BadCredentialsException e){
				LOG.warn("BadCredentialsException: " + e.getMessage());
				throw new BadCredentialsException("密碼錯誤");
			}
		}

	}

	/**
	 * @see CoreAuthenticationProvider#additionalAuthenticationChecks(UserDetails,
	 *      AbstractAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(final UserDetails details, final AbstractAuthenticationToken authentication)
			throws AuthenticationException
	{
		super.additionalAuthenticationChecks(details, authentication);

		// Check if user has supplied no password
		if (StringUtils.isEmpty((String) authentication.getCredentials()))
		{
			throw new BadCredentialsException("Login without password");
		}

		//		// Check if the user is in role admingroup
		//		if (getAdminAuthority() != null && details.getAuthorities().contains(getAdminAuthority()))
		//		{
		//			throw new LockedException("Login attempt as " + Constants.USER.ADMIN_USERGROUP + " is rejected");
		//		}
	}

	public void setAdminGroup(final String adminGroup)
	{
		if (StringUtils.isBlank(adminGroup))
		{
			adminAuthority = null;
		}
		else
		{
			adminAuthority = new SimpleGrantedAuthority(adminGroup);
		}
	}

	protected GrantedAuthority getAdminAuthority()
	{
		return adminAuthority;
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

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	private void initContext()
	{

		final Language language = C2LManager.getInstance().getLanguageByIsoCode("zh_TW");
		final SessionContext ctx = JaloSession.getCurrentSession().getSessionContext();
		final BaseSiteModel baseSite = baseSiteService.getAllBaseSites().iterator().next();//Get the only web site
		final String siteid = baseSite.getUid();
		baseSiteService.setCurrentBaseSite(baseSite, false);
		final CatalogVersionModel productCatalog = catalogVersionService.getCatalogVersion(siteid + "ProductCatalog", "Online");
		final CatalogVersionModel contentCatalog = catalogVersionService.getCatalogVersion(siteid + "ContentCatalog", "Online");
		final List<Long> list = new ArrayList<Long>();
		list.add(productCatalog.getPk().getLong());
		list.add(contentCatalog.getPk().getLong());
		ctx.setLanguage(language);
		ctx.setAttribute("catalogversions", list);

	}
}
