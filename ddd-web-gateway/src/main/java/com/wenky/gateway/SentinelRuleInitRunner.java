package com.wenky.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-04-04 14:42
 */
@Component
public class SentinelRuleInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // curl "http://127.0.0.1:8088/provider/sentinel?name=wenky"
        GatewayFlowRule rule1 = initGatewayFlowParamRule();

        // curl http://127.0.0.1:8088/consumer/info/wenky
        GatewayFlowRule rule2 = initGatewayFlowRule();

        GatewayRuleManager.loadRules(Stream.of(rule1, rule2).collect(Collectors.toSet()));
    }

    private GatewayFlowRule initGatewayFlowRule() {
        // 针对整个路由来配置限流
        GatewayFlowRule flowRule = new GatewayFlowRule();
        flowRule.setResource("dubbo-consumer");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(5d); // 限流阈值
        flowRule.setIntervalSec(1L); // 统计时间窗口 单位是秒，默认1秒
        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        return flowRule;
    }

    private GatewayFlowRule initGatewayFlowParamRule() {
        // 针对整个路由来配置限流
        GatewayFlowRule flowRule = new GatewayFlowRule();
        flowRule.setResource("dubbo-provider");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(5d); // 限流阈值
        flowRule.setIntervalSec(1L); // 统计时间窗口 单位是秒，默认1秒
        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);

        // 针对参数限流
        GatewayParamFlowItem flowItem = new GatewayParamFlowItem();
        flowItem.setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM);
        flowItem.setFieldName("name");
        flowRule.setParamItem(flowItem);

        return flowRule;
    }
}
