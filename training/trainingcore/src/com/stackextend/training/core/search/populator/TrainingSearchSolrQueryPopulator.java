package com.stackextend.training.core.search.populator;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
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

    @Override
    public void populate(SearchQueryPageableData source, SolrSearchRequest target) {
        super.populate(source, target);
        CatalogVersionModel catalogVersion;
        FacetSearchConfig facetSearchConfig;
        SolrSearchQueryData solrSearchQueryData = (SolrSearchQueryData) source.getSearchQueryData();
        IndexedType indexedType = (IndexedType) target.getIndexedType();
        if(solrSearchQueryData != null && "article".equals(solrSearchQueryData.getSearchIndexType()) && indexedType.getIdentifier().contains("article")){
            catalogVersion = getCatalogVersionService().getCatalogVersion("articleCatalog", "Online");
            target.setCatalogVersions(Arrays.asList(catalogVersion));
            facetSearchConfig = this.getFacetSearchConfig(indexedType.getCode());
            target.setFacetSearchConfig(facetSearchConfig);
            target.setIndexedType(getIndexedType(facetSearchConfig));

        }else {
            catalogVersion = getCatalogVersionService().getCatalogVersion("electronicsProductCatalog", "Online");
            target.setCatalogVersions(Arrays.asList(catalogVersion));
            facetSearchConfig = this.getFacetSearchConfig("electronics");
            target.setFacetSearchConfig(facetSearchConfig);
            target.setIndexedType(getIndexedType(facetSearchConfig));
        }

        SearchQuery searchQuery = (SearchQuery) target.getSearchQuery();
        searchQuery.setCatalogVersions(Arrays.asList(catalogVersion));
        searchQuery.setf
    }

    protected FacetSearchConfig getFacetSearchConfig(String code) {
        FacetSearchConfig facetSearchConfig;
        try{
            String sql = "select {pk} from {SolrFacetSearchConfig} where {name} like '" + code.toLowerCase() + "%'";
            FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
            SearchResult<SolrFacetSearchConfigModel> searchResult = flexibleSearchService.search(query);
            SolrFacetSearchConfigModel solrFacetSearchConfigModel = searchResult.getResult().get(0);
            facetSearchConfig = getFacetSearchConfigService().getConfiguration(solrFacetSearchConfigModel.getName());
        }catch (Exception e){
            LOG.error("getFacetSearchConfig error: " +e.getMessage());
            facetSearchConfig = null;
        }

        return facetSearchConfig;
    }




    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
