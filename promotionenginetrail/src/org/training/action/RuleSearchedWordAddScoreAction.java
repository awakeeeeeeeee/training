package org.training.action;

import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.AddOrderEntryDiscountRAOAction;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleEvaluationException;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleExecutableAction;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class RuleSearchedWordAddScoreAction implements RuleExecutableAction {

//    @Resource
//    private AddOrderEntryDiscountRAOAction addOrderEntryDiscountRAOAction;

    @Override
    public void executeAction(final RuleActionContext context, final Map<String, Object> parameters) throws RuleEvaluationException {

        final String value = (String) parameters.get("value");
        final RuleEngineResultRAO result = context.getValue(RuleEngineResultRAO.class);
        final CartRAO cartRAO = context.getValue(CartRAO.class);

        Optional<OrderEntryRAO> orderEntry = cartRAO.getEntries()
                .stream()
                .filter(orderEntryRAO -> orderEntryRAO.getProduct().getDescription().equalsIgnoreCase(value))
                .findFirst();

        BigDecimal bigDecimal = new BigDecimal(value);
//        orderEntry.ifPresent(orderEntryRAO -> this.addOrderEntryDiscountRAOAction.addOrderEntryLevelDiscount(orderEntryRAO, false, bigDecimal, result, context.getDelegate()));
    }

}
