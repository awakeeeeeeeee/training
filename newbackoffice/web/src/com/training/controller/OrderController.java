package com.training.controller;


import com.training.data.OrderRequest;
import com.training.data.OrderResponse;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/seachOrder")
public class OrderController {

    @Autowired
    private PagedFlexibleSearchService pagedFlexibleSearchService;

    @GetMapping
    String view(){
        return "orderSearch";

    }

    @PostMapping
    @ResponseBody
    List<OrderResponse> dataGrid(@RequestParam("page") int page,@RequestParam("rows") int rows){

        PageableData pageableData = new PageableData();
        pageableData.setCurrentPage(page);
        pageableData.setPageSize(rows);

        FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {order} order by {creationtime} desc");

        SearchPageData<OrderModel> searchPageData = pagedFlexibleSearchService.search(query,pageableData);
        List<OrderModel> results = searchPageData.getResults();
        searchPageData.getPagination().getNumberOfPages();

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderModel result : results) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setCode(result.getCode());
            orderResponse.setTotal(result.getTotalPrice());
            orderResponse.setUserName(result.getUser().getName());
            orderResponse.setCreationTime(result.getCreationtime());
            orderResponses.add(orderResponse);
        }

        return orderResponses;

    }
}
