package com.wenky.ddd.config;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DefaultDubboFallback;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
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
        // 流量控制效果 0直接拒绝，1Warm Up，2匀速排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);

        // 配置链式资源限制。如果多个操作都会调用资源，只有refResource才会记录统计
        //        flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
        //        flowRule.setRefResource("");

        // 关联流量控制。通过refResource来限制resource的操作
        //        flowRule.setStrategy(RuleConstant.STRATEGY_RELATE);
        //        flowRule.setRefResource("");

        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("");
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setCount(1d);
        degradeRule.setTimeWindow(5);
        degradeRule.setMinRequestAmount(5);
        degradeRule.setStatIntervalMs(10 * 1000);

        FlowRuleManager.loadRules(Arrays.asList(flowRule));
        DegradeRuleManager.loadRules(Arrays.asList(degradeRule));

        DubboAdapterGlobalConfig.setConsumerFallback(new DefaultDubboFallback());
    }
}
