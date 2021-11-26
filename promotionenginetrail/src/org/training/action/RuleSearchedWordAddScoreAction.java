package org.training.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.ruleengine.model.AbstractRuleEngineRuleModel;
import de.hybris.platform.ruleengineservices.calculation.RuleEngineCalculationService;
import de.hybris.platform.ruleengineservices.model.AbstractRuleModel;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.AddOrderEntryDiscountRAOAction;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleEvaluationException;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleExecutableAction;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;
import de.hybris.platform.ruleengineservices.rule.services.RuleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RuleSearchedWordAddScoreAction extends AbstractRuleExecutableSupport implements RuleExecutableAction {

    private static final Logger LOG = LoggerFactory.getLogger(RuleSearchedWordAddScoreAction.class);

    private RuleEngineCalculationService addOrderEntryDiscountRAOAction;

    private RuleService ruleService;

    @Override
    public void executeAction(final RuleActionContext context, final Map<String, Object> parameters) throws RuleEvaluationException {

        final BigDecimal value = (BigDecimal) parameters.get("value");
        final RuleEngineResultRAO result = context.getValue(RuleEngineResultRAO.class);
        final CartRAO cartRAO = context.getValue(CartRAO.class);


        //获取促销中设定的条件,产品描述信息中包含哪些值才有促销
        String paramValue = null;
        Map<String, Object> ruleMetadata = context.getRuleMetadata();
        if(null !=  ruleMetadata){
            String ruleCode = (String) ruleMetadata.get("ruleCode");
            PromotionSourceRuleModel rule = (PromotionSourceRuleModel) ruleService.getRuleForCode(ruleCode);
            String conditions = rule.getConditions();
            JsonArray conditionArr = new JsonParser().parse(conditions).getAsJsonArray();
            for (JsonElement jsonElement : conditionArr) {
                JsonObject paramObject = jsonElement.getAsJsonObject().get("parameters").getAsJsonObject();
                JsonObject searchedWord = paramObject.get("searchedWord").getAsJsonObject();
                paramValue = searchedWord.get("value").getAsString();
            }
        }

        if(StringUtils.isEmpty(paramValue)){
            LOG.error("do not find searchWorld promotion paramValue, return ");
            return;
        }


        Set<OrderEntryRAO> entries = cartRAO.getEntries();
        for (OrderEntryRAO entry : entries) {
            if(entry.getProduct().getDescription().contains(paramValue)){
                DiscountRAO discountRAO = this.addOrderEntryDiscountRAOAction.addOrderLevelDiscount(cartRAO, true, value);
//                getRaoUtils().addAction(cartRao, changeUserGroupRAO);
                result.getActions().add(discountRAO);
                //增加元数据信息，context中会存在strategy和action的信息
                setRAOMetaData(context, discountRAO);
//                context.insertFacts(changeUserGroupRAO, userGroupRAO);
//                context.scheduleForUpdate(user);
            }

        }
    }


    public void setAddOrderEntryDiscountRAOAction(RuleEngineCalculationService addOrderEntryDiscountRAOAction) {
        this.addOrderEntryDiscountRAOAction = addOrderEntryDiscountRAOAction;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }
}
