/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.springmvctests;

import de.hybris.bootstrap.annotations.UnitTest;

import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import junit.framework.Assert;

import org.junit.Test;

import javax.annotation.Resource;


//@UnitTest
public class EmptyTest extends ServicelayerTest
{
	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;

	@Test
	public void emptyTest()
	{
		Assert.assertTrue(true);

		//create a user
		final EmployeeModel user = new EmployeeModel();
		user.setUid("testUser");
//		modelService.save(user);

		//test creation
		Assert.assertNotNull(userService.getUserForUID("testUser"));

	}
}
