package com.stackextend.training.core.search.populator;

import de.hybris.bootstrap.typesystem.jaxb.IndexType;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.exceptions.NoValidSolrConfigException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TrainingArticleSearchSolrQueryPopulator extends TrainingSearchSolrQueryPopulator{

    private static final Logger LOG = Logger.getLogger(TrainingArticleSearchSolrQueryPopulator.class);

    @Override
    public void populate(SearchQueryPageableData source, SolrSearchRequest target) throws ConversionException {
        // Setup the SolrSearchRequest

        final SolrSearchQueryData solrSearchQueryData = (SolrSearchQueryData) source.getSearchQueryData();

        target.setSearchQueryData(solrSearchQueryData);
        target.setPageableData(source.getPageableData());

        CatalogVersionModel catalogVersion = getCatalogVersionService().getCatalogVersion("articleCatalog", "Online");

        target.setCatalogVersions(Arrays.asList(catalogVersion));

        FacetSearchConfig facetSearchConfig = this.getFacetSearchConfig(solrSearchQueryData.getSearchIndexType());

        IndexedType indexedType = getIndexedType(facetSearchConfig);

        try
        {
            target.setFacetSearchConfig(facetSearchConfig);
        }
        catch (final Exception e)
        {
            LOG.error("No valid solrFacetSearchConfig found for the current context", e);
            throw new ConversionException("No valid solrFacetSearchConfig found for the current context", e);
        }

        // We can only search one core so select the indexed type
//        target.setIndexedType(indexType);
        target.setIndexedType(indexedType);

        // Create the solr search query for the config and type (this sets-up the default page size and sort order)
        SearchQuery searchQuery;

        if (facetSearchConfig.getSearchConfig().isLegacyMode())
        {
            searchQuery = createSearchQueryForLegacyMode(facetSearchConfig, indexedType,
                    solrSearchQueryData.getSearchQueryContext(), solrSearchQueryData.getFreeTextSearch());
        }
        else
        {
            searchQuery = createSearchQuery(facetSearchConfig, indexedType,
                    solrSearchQueryData.getSearchQueryContext(), solrSearchQueryData.getFreeTextSearch());
        }

        searchQuery.setCatalogVersions(target.getCatalogVersions());
        String currency = getCommonI18NService().getCurrency("CNY").getIsocode();
        searchQuery.setCurrency(currency);
        searchQuery.setLanguage(getCommonI18NService().getCurrentLanguage().getIsocode());

        // enable spell checker
        searchQuery.setEnableSpellcheck(true);

        target.setSearchQuery(searchQuery);

        IndexedType indexedType1 = searchQuery.getIndexedType();
        ComposedTypeModel composedType = indexedType1.getComposedType();
        Boolean catalogItemType = composedType.getCatalogItemType();
        LOG.info(catalogItemType);

        ComposedTypeModel composedType1 = indexedType.getComposedType();
        Boolean catalogItemType1 = composedType1.getCatalogItemType();
        LOG.info(catalogItemType1);

    }

    protected FacetSearchConfig getFacetSearchConfig(String code) {
        FacetSearchConfig facetSearchConfig;
        try{
            String sql = "select {pk} from {SolrFacetSearchConfig} where {name} like '" + code.toLowerCase() + "%'";
            FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
            SearchResult<SolrFacetSearchConfigModel> searchResult = getFlexibleSearchService().search(query);
            SolrFacetSearchConfigModel solrFacetSearchConfigModel = searchResult.getResult().get(0);
            facetSearchConfig = getFacetSearchConfigService().getConfiguration(solrFacetSearchConfigModel.getName());
        }catch (Exception e){
            LOG.error("getFacetSearchConfig error: " +e.getMessage());
            facetSearchConfig = null;
        }

        return facetSearchConfig;
    }

    protected IndexType getIndexType(String code){
        String sql = "select {pk} from {IndexType} where {name} like '" + code.toLowerCase() + "%'";
        FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
        SearchResult<IndexType> searchResult = getFlexibleSearchService().search(query);
        return searchResult.getResult().get(0);
    }


}
