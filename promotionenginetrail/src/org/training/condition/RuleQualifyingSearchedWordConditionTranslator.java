package org.training.condition;

import de.hybris.platform.ruleengineservices.compiler.*;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionDefinitionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleParameterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.action.DefaultAddUserToUserGroupActionStrategy;

import java.util.ArrayList;

public class RuleQualifyingSearchedWordConditionTranslator implements RuleConditionTranslator {

    private final String SEARCHED_WORD = "searchedWord";

    private final String DESCRIPTION = "description";

    private static final Logger LOG = LoggerFactory.getLogger(RuleQualifyingSearchedWordConditionTranslator.class);

    @Override
    public RuleIrCondition translate(RuleCompilerContext context, RuleConditionData condition, RuleConditionDefinitionData conditionDefinition) throws RuleCompilerException {
        try {
            RuleParameterData searchedWord = condition.getParameters().get(SEARCHED_WORD);
            if (searchedWord != null) {
                String word = searchedWord.getValue();

                final String cartRAOVariable = context.generateVariable(CartRAO.class);

                String productRAOVariable = context.generateVariable(ProductRAO.class);

                final RuleIrAttributeCondition ruleIrAttributeCondition = new RuleIrAttributeCondition();
                ruleIrAttributeCondition.setVariable(productRAOVariable);
                ruleIrAttributeCondition.setOperator(RuleIrAttributeOperator.CONTAINS);
                ruleIrAttributeCondition.setAttribute(DESCRIPTION);
                ruleIrAttributeCondition.setValue(word);

//                final RuleIrGroupCondition ruleIrGroupCondition = new RuleIrGroupCondition();
//                ruleIrGroupCondition.setOperator(RuleIrGroupOperator.AND);
//                ruleIrGroupCondition.setChildren(new ArrayList<>());
//                ruleIrGroupCondition.getChildren().add(ruleIrAttributeCondition);
//                return ruleIrGroupCondition;
                return ruleIrAttributeCondition;
            }
        } catch (Exception ex) {
            LOG.error("error,",ex);
            throw ex;
        }
        return new RuleIrFalseCondition();
    }
}
