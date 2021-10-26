solr:


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

