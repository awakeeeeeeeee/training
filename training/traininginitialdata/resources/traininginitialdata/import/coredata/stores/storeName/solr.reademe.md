solr:

1.buidl search request

<ref bean="commerceSearchSolrQueryPopulator" />
<ref bean="commerceSearchPagePopulator" />
<ref bean="commerceSearchSortPopulator" />
<ref bean="commerceSearchTextPopulator" />
<ref bean="commerceSearchFiltersPopulator" />
				
				

2.execute the search 

<ref bean="solrSearchRequestResponsePopulator" />



3.convert the response


<ref bean="commerceSearchResponseQueryPopulator" />
<ref bean="commerceSearchResponsePaginationPopulator" />
<ref bean="commerceSearchResponseResultsPopulator" />
<ref bean="commerceSearchResponseFacetsPopulator" />
<ref bean="commerceSearchResponseFacetFilterPopulator" />
<ref bean="commerceSearchResponseBreadcrumbsPopulator" />
<ref bean="commerceSearchResponseSortsPopulator" />
<ref bean="commerceSearchResponseFreeTextSearchPopulator" />
<ref bean="commerceSearchResponseCategoryCodePopulator" />
<ref bean="commerceSearchResponseSubCategoriesPopulator" />
<ref bean="commerceSearchResponseSpellingSuggestionPopulator" />
<ref bean="commerceSearchResponseKeywordRedirectPopulator" />				



DefaultIndexNameResolver.resolve 创建了solr core的name

IndexerOperationListener solrSearchProvider.createIndex 创建core ，并且index

INSERT_UPDATE SolrIndexConfig;name[unique=true];batchSize;numberOfThreads;indexMode(code);
                             ;$indexConfigName;100;1;TWO_PHASE;
                             
设置二阶段提交，会创建两个solr core,一个 flip，一个flop，切换同步
设置直接提交，只会创建一个solr core default

查询当前处于激活的solr index
select {solrfacetsearchconfig.name},{solrindex.qualifier} 
from {solrindex join solrfacetsearchconfig on {solrindex.facetsearchconfig}={solrfacetsearchconfig.pk}}
where {solrindex.active}=true


model中有枚举时，必须定义默认值，否则solr index可能会类型转换错误
 <attribute qualifier="status" type="ArticleStatusEnum">
                        <description></description>
                        <modifiers optional="true" read="true" write="true"></modifiers>
                        <persistence type="property"/>
                        <defaultvalue>em().getEnumerationValue("ArticleStatusEnum","UP")</defaultvalue>
                    </attribute>
                    
                    
facetSearchQueryConverter 负责转换solr查询条件



solr自定义一个新的indexType然后完成搜索：

1.导入article-solr.impex
2.导入article-catalog.impex
3.导入article.impex
4.根据需要导入composeType.impex
5.修改searchPageController，新增一个searchType的参数去匹配index type
6.新增trainingSearchSolrQueryPopulator,根据不同的searchType去设定不同的facetSearchConfig,indexedType,catalogVersion等

这样调整后，一直搜索不到东西，后续排查后发现是因为在facetSearchQueryConverter中facetSearchQueryCatalogVersionsFilterPopulator会给查询
条件带上&fq=(catalogId:"articleCatalog"+AND+catalogVersion:"Online"),但是article中并没有设定改字段，所有导致查询不到，可以通过调整composeType.impex
来跳过该条件的绑定，也可以给article加上改字段

调整完后发现可以查询到数据了，但是转换结果的时候会出错，问题的原因是在与默认的ootb查询方法最后会用getProductCategorySearchPageConverter进行转换，这中间会去查询商品，但是搜索的类型并不是商品，所有会出错
解决办法啊，去掉这部分的结果转换


查询结果查不出来facet的几种原因:
1.SolrQuery solrQuery = (SolrQuery)super.getFacetSearchQueryConverter().convert(searchQueryConverterData);
转换facet.field的结果中 查看field name是否正确，检查
String translatedField = this.getFieldNameTranslator().translate(searchQuery, facet.getField(), FieldType.INDEX)这里将field name设定的是否正确

2.设定正确后使用solrClient查询结果中出现了facet，但是最终返回的ProductSearchPageData<SearchStateData, ProductSearchPageData> productSearchPageData中
还是没有facet，排查后发现是因为FacetSearchResultFacetsPopulator中转换facet的时候有问题，
protected List<FacetValue> populateFacetValues(List<Count> sourceFacetValues, SearchQuery searchQuery, IndexedProperty indexedProperty, de.hybris.platform.solrfacetsearch.search.FacetField facetField, String fieldName, boolean showFacet, long maxFacetValueCount) {
        List<FacetValue> facetValues = new ArrayList();
        Object facetValueDisplayNameProvider = this.getFacetValueDisplayNameProvider(facetField.getDisplayNameProvider());
        sourceFacetValues.stream().filter((count) -> {
            return showFacet || count.getCount() < maxFacetValueCount;
        }).forEach((count) -> {
            boolean selected = this.isFacetValueSelected(searchQuery, fieldName, count.getName());
            String displayName = this.getFacetValueDisplayName(searchQuery, indexedProperty, facetValueDisplayNameProvider, count.getName());
            if (displayName == null) {
                facetValues.add(new FacetValue(count.getName(), count.getCount(), selected));
            } else {
                facetValues.add(new FacetValue(count.getName(), displayName, count.getCount(), selected));
            }

        });
被上面的filter拦截掉了，前端输入"001"或者"00"查询结果只有一条，并没有根据code去模糊匹配出多条结果，还未查出是什么原因，但是手动debug模式中把count改成2，最终返回的结果中
就有了facet的值