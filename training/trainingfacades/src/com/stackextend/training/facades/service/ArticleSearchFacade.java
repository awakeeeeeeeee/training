package com.stackextend.training.facades.service;

import de.hybris.platform.category.CategoryService;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.solrfacetsearch.converters.populator.SolrSearchQueryDecoderPopulator;
import de.hybris.platform.commercefacades.search.solrfacetsearch.impl.DefaultSolrProductSearchFacade;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Resource;

public class ArticleSearchFacade extends TrainingProductSearchFacade {

    @Resource(name = "configurationService")
    ConfigurationService configurationService;

    @Resource
    CategoryService categoryService;

    @Resource
    FlexibleSearchService flexibleSearchService;

    @Resource
    SolrSearchQueryDecoderPopulator solrSearchQueryDecoderPopulator;

    Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> searchQueryPageableConverter;

    Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter;

    Converter<SolrSearchResponse, ProductSearchPageData<SolrSearchQueryData,ProductSearchPageData>> searchResponseConverter;


    public ProductSearchPageData<SolrSearchQueryData, ProductSearchPageData> serviceTypeSearch(final String searchQuery,
                                                                                               final SearchQueryContext searchQueryContext, final PageableData pageableData)
    {
        return getThreadContextService().executeInContext(
                new ThreadContextService.Executor<ProductSearchPageData<SolrSearchQueryData, ProductSearchPageData>, ThreadContextService.Nothing>()
                {
                    @Override
                    public ProductSearchPageData<SolrSearchQueryData, ProductSearchPageData> execute()

                    {
                        return doServiceTypeSearch(searchQuery,searchQueryContext,pageableData);
                    }
                });

    }

    public ProductSearchPageData<SolrSearchQueryData, ProductSearchPageData> doServiceTypeSearch(final String searchQuery,

                                                                                                 final SearchQueryContext searchQueryContext, final PageableData pageableData)
    {
        final SolrSearchQueryData solrSearchQueryData = new SolrSearchQueryData();
        final SearchQueryData searchQueryData = new SearchQueryData();
        searchQueryData.setValue(searchQuery);
        searchQueryData.setSearchQueryContext(searchQueryContext);
        solrSearchQueryDecoderPopulator.populate(searchQueryData, solrSearchQueryData);
        // Create the SearchQueryPageableData that contains our parameters
        final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = buildSearchQueryPageableData(solrSearchQueryData,
                pageableData);

        // Build up the search request
        final SolrSearchRequest solrSearchRequest = searchQueryPageableConverter.convert(searchQueryPageableData);

        // Execute the search
        final SolrSearchResponse solrSearchResponse = searchRequestConverter.convert(solrSearchRequest);

        // Convert the response
        return searchResponseConverter.convert(solrSearchResponse);
    }


    protected SearchQueryPageableData<SolrSearchQueryData> buildSearchQueryPageableData(final SolrSearchQueryData searchQueryData,
                                                                                        final PageableData pageableData)
    {
        final SearchQueryPageableData<SolrSearchQueryData> searchQueryPageableData = new SearchQueryPageableData<SolrSearchQueryData>();
        searchQueryPageableData.setSearchQueryData(searchQueryData);
        searchQueryPageableData.setPageableData(pageableData);
        return searchQueryPageableData;
    }


    public void setSearchQueryPageableConverter(Converter<SearchQueryPageableData<SolrSearchQueryData>, SolrSearchRequest> searchQueryPageableConverter) {
        this.searchQueryPageableConverter = searchQueryPageableConverter;
    }

    public void setSearchRequestConverter(Converter<SolrSearchRequest, SolrSearchResponse> searchRequestConverter) {
        this.searchRequestConverter = searchRequestConverter;
    }

    public void setSearchResponseConverter(Converter<SolrSearchResponse, ProductSearchPageData<SolrSearchQueryData, ProductSearchPageData>> searchResponseConverter) {
        this.searchResponseConverter = searchResponseConverter;
    }
}
