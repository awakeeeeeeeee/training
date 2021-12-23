package com.training.service.impl;

import com.training.data.UIData;
import com.training.model.UITreeModel;
import com.training.service.UIService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DefaultUIService implements UIService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<UIData> loadMenuTree() {
        List<UIData> uiDatas = new ArrayList<>();
        List<UITreeModel> roots = getRoots();
        if(CollectionUtils.isNotEmpty(roots)){
            for (UITreeModel root : roots) {
                UIData data = new UIData();
                BeanUtils.copyProperties(root,data,"children");
                uiDatas.add(data);

                List<UITreeModel> children = root.getChildren();
                if(CollectionUtils.isNotEmpty(children)){
                    createTree(children,data);
                }
            }
        }

        return uiDatas;
    }

    private void createTree(List<UITreeModel> children, UIData uiData) {
        if(CollectionUtils.isNotEmpty(children)){
            for (UITreeModel child : children) {
                UIData data = new UIData();
                BeanUtils.copyProperties(child,data,"children");
                if(CollectionUtils.isNotEmpty(uiData.getChildren())){
                    uiData.getChildren().add(data);
                }else {
                    List<UIData> uiDatas = new ArrayList<>();
                    uiDatas.add(data);
                    uiData.setChildren(uiDatas);
                }

                createTree(child.getChildren(),data);
            }
        }
    }

    private List<UITreeModel> getRoots() {

        String sql = "select {pk} from {uitree} where {isroot} = 1";
        FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
        SearchResult<UITreeModel> searchResult = flexibleSearchService.search(query);
        if(CollectionUtils.isNotEmpty(searchResult.getResult())){
            return searchResult.getResult();
        }
        return null;
    }
}
