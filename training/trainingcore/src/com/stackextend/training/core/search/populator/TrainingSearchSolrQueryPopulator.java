package com.stackextend.training.core.search.populator;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class TrainingSearchSolrQueryPopulator extends SearchSolrQueryPopulator {

    private static final Logger LOG = Logger.getLogger(TrainingSearchSolrQueryPopulator.class);
    private FlexibleSearchService flexibleSearchService;
    private TrainingArticleSearchSolrQueryPopulator trainingArticleSearchSolrQueryPopulator;

    @Override
    public void populate(SearchQueryPageableData source, SolrSearchRequest target) {

        SolrSearchQueryData searchQueryData = (SolrSearchQueryData) source.getSearchQueryData();
        if(searchQueryData.getSearchIndexType() != null && "article".equals(searchQueryData.getSearchIndexType())){

            trainingArticleSearchSolrQueryPopulator.populate(source,target);
        }else {
            super.populate(source,target);
//            IndexedType indexedType = (IndexedType) target.getIndexedType();
//            ComposedTypeModel composedType = indexedType.getComposedType();
//            Boolean catalogItemType = composedType.getCatalogItemType();
//            LOG.info(catalogItemType);
//
//            SearchQuery searchQuery = (SearchQuery) target.getSearchQuery();
//            IndexedType indexedType1 = searchQuery.getIndexedType();
//            ComposedTypeModel composedType1 = indexedType1.getComposedType();
//            Boolean catalogItemType1 = composedType1.getCatalogItemType();
//            LOG.info(catalogItemType1);
        }
    }






    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    public TrainingArticleSearchSolrQueryPopulator getTrainingArticleSearchSolrQueryPopulator() {
        return trainingArticleSearchSolrQueryPopulator;
    }

    public void setTrainingArticleSearchSolrQueryPopulator(TrainingArticleSearchSolrQueryPopulator trainingArticleSearchSolrQueryPopulator) {
        this.trainingArticleSearchSolrQueryPopulator = trainingArticleSearchSolrQueryPopulator;
    }
}
