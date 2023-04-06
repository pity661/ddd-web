package com.wenky.provider;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.wenky.commons.dubbo.model.DubboInvokeResult;
import java.util.Arrays;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 18:45
 */
@Component
public class SentinelRuleInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FlowRule flowRule = new FlowRule();
        // 针对单个方法配置资源限制
        // com.wenky.provider.dubbo.service.IHelloService:getWrapperByName(java.lang.String)
        flowRule.setResource("com.wenky.provider.dubbo.service.IHelloService");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(5d);
        // ResourceTypeConstants.COMMON_RPC
        flowRule.setLimitApp("default"); // dubbo.application.name
        flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);

        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("com.wenky.provider.dubbo.service.IHelloService");
        // 异常调用比例（仅针对业务异常，非BlockException子类）
        degradeRule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType());
        degradeRule.setCount(2d);
        degradeRule.setStatIntervalMs(10 * 1000);
        degradeRule.setTimeWindow(5);
        degradeRule.setMinRequestAmount(2);

        // 限流控制规则
        FlowRuleManager.loadRules(Arrays.asList(flowRule));
        // 熔断降级规则
        DegradeRuleManager.loadRules(Arrays.asList(degradeRule));

        // provider fallback handle
        DubboAdapterGlobalConfig.setProviderFallback(
                (invoker, invocation, ex) ->
                        AsyncRpcResult.newDefaultAsyncResult(
                                DubboInvokeResult.exception(ex), invocation));

        // 熔断器事件监听
        EventObserverRegistry.getInstance()
                .addStateChangeObserver(
                        "logging",
                        (prevState, newState, rule, snapshotValue) -> {
                            if (newState == CircuitBreaker.State.OPEN) {
                                // 变换至 OPEN state 时会携带触发时的值
                                System.err.println(
                                        String.format(
                                                "%s -> OPEN at %d, snapshotValue=%.2f",
                                                prevState.name(),
                                                TimeUtil.currentTimeMillis(),
                                                snapshotValue));
                            } else {
                                System.err.println(
                                        String.format(
                                                "%s -> %s at %d",
                                                prevState.name(),
                                                newState.name(),
                                                TimeUtil.currentTimeMillis()));
                            }
                        });
    }
}
