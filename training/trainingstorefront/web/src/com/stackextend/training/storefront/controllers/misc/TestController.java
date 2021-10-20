package com.stackextend.training.storefront.controllers.misc;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.order.Cart;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class TestController extends AbstractController {

    final static Logger logger = Logger.getLogger(TestController.class);

    @GetMapping("/test")
    void test(HttpServletRequest request, HttpServletResponse response){

        JaloSession jaloSession = JaloSession.getCurrentSession();
        logger.info(jaloSession);

        String jaloSessionSessionID = jaloSession.getSessionID();
        logger.info(jaloSessionSessionID);

        String httpSessionId = jaloSession.getHttpSessionId();
        logger.info(httpSessionId);


//        Cart cart = jaloSession.getCart();
//        logger.info(cart.getPK());


        HttpSession session = request.getSession();
        logger.info(session.getId());

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            logger.info(cookie.getName());
            logger.info(cookie.getValue());
        }

    }

}
