package com.stackextend.training.core.search.strategy;

import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.model.SolrIndexModel;
import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchContext;
import de.hybris.platform.solrfacetsearch.search.context.FacetSearchContextFactory;
import de.hybris.platform.solrfacetsearch.search.impl.DefaultFacetSearchStrategy;
import de.hybris.platform.solrfacetsearch.search.impl.SearchQueryConverterData;
import de.hybris.platform.solrfacetsearch.search.impl.SearchResultConverterData;
import de.hybris.platform.solrfacetsearch.solr.Index;
import de.hybris.platform.solrfacetsearch.solr.SolrIndexService;
import de.hybris.platform.solrfacetsearch.solr.SolrSearchProvider;
import de.hybris.platform.solrfacetsearch.solr.SolrSearchProviderFactory;
import de.hybris.platform.solrfacetsearch.solr.exceptions.SolrServiceException;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.IOUtils;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.util.Map;

public class TrainingDefaultFacetSearchStrategy extends DefaultFacetSearchStrategy {

    private static final Logger LOG = Logger.getLogger(TrainingDefaultFacetSearchStrategy.class);

    public SearchResult search(SearchQuery searchQuery, Map<String, String> searchHints) throws FacetSearchException {
        this.checkQuery(searchQuery);
        SolrClient solrClient = null;

        SearchResult var17;
        try {
            FacetSearchConfig facetSearchConfig = searchQuery.getFacetSearchConfig();
            IndexedType indexedType = searchQuery.getIndexedType();
            FacetSearchContext facetSearchContext = super.getFacetSearchContextFactory().createContext(facetSearchConfig, indexedType, searchQuery);
            facetSearchContext.getSearchHints().putAll(searchHints);
            super.getFacetSearchContextFactory().initializeContext();
            SolrSearchProvider solrSearchProvider = super.getSolrSearchProviderFactory().getSearchProvider(facetSearchConfig, indexedType);
            SolrIndexModel activeIndex = super.getSolrIndexService().getActiveIndex(facetSearchConfig.getName(), indexedType.getIdentifier());
            Index index = solrSearchProvider.resolveIndex(facetSearchConfig, indexedType, activeIndex.getQualifier());
            solrClient = solrSearchProvider.getClient(index);
            SearchQueryConverterData searchQueryConverterData = new SearchQueryConverterData();
            searchQueryConverterData.setFacetSearchContext(facetSearchContext);
            searchQueryConverterData.setSearchQuery(searchQuery);
            SolrQuery solrQuery = (SolrQuery)super.getFacetSearchQueryConverter().convert(searchQueryConverterData);
            if (LOG.isDebugEnabled()) {
                LOG.debug(solrQuery);
            }

//            LOG.info("index type: " + indexedType.getIndexName());
            LOG.info("solrQuery: " + solrQuery);
//            LOG.info("query: " + solrQuery.getQuery());
//            LOG.info("facet query: " + solrQuery.getFacetQuery());

            SolrRequest.METHOD method = this.resolveQueryMethod(facetSearchConfig);
            QueryResponse queryResponse = solrClient.query(index.getName(), solrQuery, method);
            SearchResultConverterData searchResultConverterData = new SearchResultConverterData();
            searchResultConverterData.setFacetSearchContext(facetSearchContext);
            searchResultConverterData.setQueryResponse(queryResponse);
            SearchResult searchResult = (SearchResult)super.getFacetSearchResultConverter().convert(searchResultConverterData);
            super.getFacetSearchContextFactory().getContext().setSearchResult(searchResult);
            super.getFacetSearchContextFactory().destroyContext();
            var17 = searchResult;
        } catch (SolrServerException | IOException | RuntimeException | SolrServiceException var20) {
            super.getFacetSearchContextFactory().destroyContext(var20);
            throw new FacetSearchException(var20.getMessage(), var20);
        } finally {
            IOUtils.closeQuietly(solrClient);
        }

        return var17;
    }
}
