package com.wenky.ddd.config;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DefaultDubboFallback;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import java.util.Arrays;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 14:05
 */
public class SentinelConfig {

    static {
        // 定义资源 定义规则 校验规则是否生效

        // https://github.com/alibaba/Sentinel/wiki/%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6
        FlowRule flowRule = new FlowRule();
        // 资源名 dubbo颗粒度: 服务接口/服务方法
        flowRule.setResource("");
        // 限流阈值类型(QPS或并发线程数) consumer适用线程数模式限流，provider适用QPS模式限流
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 限流阈值
        flowRule.setCount(1d);
        // 流控针对的调用来源。default不区分来源
        flowRule.setLimitApp("default");
        // 调用关系限流策略
        flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        // 配置链式资源限制。如果多个操作都会调用资源，只有refResource才会记录统计
        //        flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
        //        flowRule.setRefResource("");
        // 关联流量控制。通过refResource来限制resource的操作
        //        flowRule.setStrategy(RuleConstant.STRATEGY_RELATE);
        //        flowRule.setRefResource("");

        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        // 流量控制效果为预热方式时，预热时间。当流量突增，缓慢增加逐渐达到阈值上限
        //        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        //        flowRule.setWarmUpPeriodSec(10);  // 默认10s
        // 匀速排队，严格控制请求通过的间隔时间，漏桶算法。超过等待时间，请求被拒绝。grade必须为QPS 匀速排队时间为1*1000/count(QPS)
        //        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        //        flowRule.setMaxQueueingTimeMs(500); // 默认500ms，排队等待时间
        // 冷启动 + 匀速器
        //        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER);

        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("");
        // 异常调用比例（仅针对业务异常，非BlockException子类）
        degradeRule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        degradeRule.setCount(0.7d); // 请求异常比例达到70%，熔断
        // 慢调用比例阈值 RT(response time)
        //        degradeRule.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType());
        //        degradeRule.setSlowRatioThreshold(0.7d);  // 慢调用比例阈值
        //        degradeRule.setCount(30); // RT计算规则 statIntervalMs/count=2s，超过2秒为慢调用
        // 异常调用数
        //        degradeRule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType());
        //        degradeRule.setCount(10d);  // 异常调用数量达到10，熔断

        // 单位统计时长
        degradeRule.setStatIntervalMs(60 * 1000);
        // 熔断时长(s)
        degradeRule.setTimeWindow(5);
        // 熔断触发的最小请求数
        degradeRule.setMinRequestAmount(5);

        // 限流控制规则
        FlowRuleManager.loadRules(Arrays.asList(flowRule));
        // 熔断降级规则
        DegradeRuleManager.loadRules(Arrays.asList(degradeRule));
        DubboAdapterGlobalConfig.setConsumerFallback(new DefaultDubboFallback());

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
