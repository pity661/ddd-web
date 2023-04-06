package com.wenky.gateway;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.wenky.commons.dubbo.model.HandleResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-04-04 14:42
 */
@Component
public class SentinelRuleInitRunner implements ApplicationRunner {

    // TODO 熔断处理

    // Spring Cloud Gateway + Sentinel
    // SentinelGatewayFilter Route ID识别，API路径匹配
    // BlockRequestHandler 限流请求处理
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // curl "http://127.0.0.1:8088/provider/sentinel?name=wenky"
        GatewayFlowRule rule1 = initGatewayFlowParamRule();
        // curl http://127.0.0.1:8088/consumer/info/wenky
        GatewayFlowRule rule2 = initGatewayFlowRule();
        // api
        GatewayFlowRule apiRule = initApiDefinitionRule();
        GatewayRuleManager.loadRules(Stream.of(rule1, rule2, apiRule).collect(Collectors.toSet()));

        // 自定义限流响应结果
        GatewayCallbackManager.setBlockHandler(
                (exchange, t) -> {
                    // redirect => RedirectBlockRequestHandler
                    // restful接口请求
                    HandleResult handleResult = HandleResult.fetch(t);
                    SingleResponse singleResponse =
                            SingleResponse.buildFailure(
                                    String.valueOf(handleResult.getCode()),
                                    handleResult.getMessage());
                    return ServerResponse.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(singleResponse);
                });
    }

    private GatewayFlowRule initApiDefinitionRule() {
        ApiDefinition apiDefinition = new ApiDefinition();
        apiDefinition.setApiName("dubbo-consumer-provider");
        ApiPathPredicateItem item1 = new ApiPathPredicateItem();
        item1.setPattern("/consumer/**")
                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX);
        ApiPathPredicateItem item2 = new ApiPathPredicateItem();
        item2.setPattern("/provider/**")
                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX);
        apiDefinition.setPredicateItems(Stream.of(item1, item2).collect(Collectors.toSet()));
        GatewayApiDefinitionManager.loadApiDefinitions(
                Stream.of(apiDefinition).collect(Collectors.toSet()));

        GatewayFlowRule apiRule = new GatewayFlowRule();
        apiRule.setResource("dubbo-consumer-provider");
        apiRule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
        apiRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        apiRule.setCount(5d); // 限流阈值
        apiRule.setIntervalSec(5L); // 时间窗口 单位是秒，默认1秒
        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        apiRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        return apiRule;
    }

    private GatewayFlowRule initGatewayFlowRule() {
        // 针对整个路由来配置限流
        GatewayFlowRule flowRule = new GatewayFlowRule();
        flowRule.setResource("dubbo-consumer");
        flowRule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(5d); // 限流阈值
        flowRule.setIntervalSec(5L); // 时间窗口 单位是秒，默认1秒
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
        flowRule.setIntervalSec(5L); // 时间窗口 单位是秒，默认1秒
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
