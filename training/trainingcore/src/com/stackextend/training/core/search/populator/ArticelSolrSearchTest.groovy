import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig
import de.hybris.platform.solrfacetsearch.config.IndexedType
import de.hybris.platform.solrfacetsearch.search.SearchQuery
import de.hybris.platform.solrfacetsearch.search.SearchResult

facetSearchConfigService = spring.getBean("facetSearchConfigService");
facetSearchService = spring.getBean("facetSearchService");

FacetSearchConfig facetSearchConfig = facetSearchConfigService.getConfiguration("articleIndex");
println facetSearchConfig;

Map<String,IndexedType> indexedTypeMap = facetSearchConfig.getIndexConfig().getIndexedTypes();
for(String key : indexedTypeMap.keySet()){
    println key;
}

IndexedType indexedType = facetSearchConfig.getIndexConfig().getIndexedTypes().get("Article");
println indexedType;

SearchQuery searchQuery = new SearchQuery(facetSearchConfig, indexedType);

SearchResult searchResult = facetSearchService.search(searchQuery);

println searchResult;
println searchResult.getResults().size();