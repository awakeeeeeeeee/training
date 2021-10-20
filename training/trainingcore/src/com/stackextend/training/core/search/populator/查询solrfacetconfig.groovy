import de.hybris.platform.basecommerce.model.site.BaseSiteModel
import de.hybris.platform.catalog.model.CatalogVersionModel
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.FlexibleSearchService
import de.hybris.platform.servicelayer.search.SearchResult
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel

FlexibleSearchService flexibleSearchService = spring.getBean("flexibleSearchService");
catalogVersionService = spring.getBean("catalogVersionService");
baseSiteService = spring.getBean("baseSiteService");

String sql = "select {pk} from {SolrFacetSearchConfig} where {name} like 'article%'";
FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
SearchResult<SolrFacetSearchConfigModel> searchResult = flexibleSearchService.search(query);
SolrFacetSearchConfigModel solrFacetSearchConfigModel = searchResult.getResult().get(0);

println solrFacetSearchConfigModel;


CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("electronicsProductCatalog", "Online");
println catalogVersion;


final BaseSiteModel currentBaseSite = baseSiteService.getCurrentBaseSite();
if (currentBaseSite != null)
{
    SolrFacetSearchConfigModel solrFacetSearchConfigModel1 = currentBaseSite.getSolrFacetSearchConfiguration();
    println solrFacetSearchConfigModel1.getIndexNamePrefix()
}