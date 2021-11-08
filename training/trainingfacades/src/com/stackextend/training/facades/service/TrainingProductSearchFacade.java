package com.stackextend.training.facades.service;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.search.solrfacetsearch.impl.DefaultSolrProductSearchFacade;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfigService;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.FacetSearchService;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class TrainingProductSearchFacade extends DefaultSolrProductSearchFacade implements ProductSearchFacade {


    private FacetSearchConfigService facetSearchConfigService;

    private FacetSearchService facetSearchService;

    public ProductSearchPageData<SearchStateData, ProductData> textArticleSearch(SearchStateData searchState, PageableData pageableData) {

        return super.textSearch(searchState,pageableData);

    }

    @Override
    protected SolrSearchQueryData decodeState(SearchStateData searchState, String categoryCode) {
        SolrSearchQueryData solrSearchQueryData = super.decodeState(searchState, categoryCode);
        //add new article index type
        solrSearchQueryData.setSearchIndexType(searchState.getQuery().getSearchIndexType());
        return solrSearchQueryData;
    }

    public SearchResult articleSearchTest() throws FacetConfigServiceException, FacetSearchException {
        FacetSearchConfig facetSearchConfig = facetSearchConfigService.getConfiguration("articleIndex");

        Map<String, IndexedType> indexedTypeMap = facetSearchConfig.getIndexConfig().getIndexedTypes();

        IndexedType indexedType = facetSearchConfig.getIndexConfig().getIndexedTypes().get("Article");

        SearchQuery searchQuery = new SearchQuery(facetSearchConfig, indexedType);

        SearchResult searchResult = facetSearchService.search(searchQuery);

        return searchResult;
    }


    //重写默认的text search，但是不调用getProductCategorySearchPageConverter.convert(),如果调用会出错，默认ootb会查询产品，但是这里搜索的不是产品
    public ProductSearchPageData<SearchStateData, ProductSearchPageData> articleTextSearch(final SearchStateData searchState,
                                                                   final PageableData pageableData)
    {
        Assert.notNull(searchState, "SearchStateData must not be null.");

        return getThreadContextService().executeInContext(
                new ThreadContextService.Executor<ProductSearchPageData<SearchStateData, ProductSearchPageData>, ThreadContextService.Nothing>()
                {
                    @Override
                    public ProductSearchPageData<SearchStateData, ProductSearchPageData> execute()
                    {
                        return getProductSearchService().searchAgain(decodeState(searchState, null), pageableData);
                    }
                });
    }


    public FacetSearchConfigService getFacetSearchConfigService() {
        return facetSearchConfigService;
    }

    public void setFacetSearchConfigService(FacetSearchConfigService facetSearchConfigService) {
        this.facetSearchConfigService = facetSearchConfigService;
    }

    public void setFacetSearchService(FacetSearchService facetSearchService) {
        this.facetSearchService = facetSearchService;
    }
}
