package com.wenky.ddd

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author 克林* @date 2023/6/13 17:17
 */
class GroovySpec extends Specification {

    def setup() {
        println( "setup" )
    }

    def cleanup() {
        println( "cleanup" )
    }

    @Unroll
    def "testMethod"() {

        println "hello world"
        expect: "期望结果"
        1 < 2

    }

    static String calc(int income) {
        if (income < 0) {
            return "0"
        }
        return new BigDecimal(income).divide(2 as BigDecimal).stripTrailingZeros().toPlainString()
    }

    @Unroll
    def "计算: #income, 结果: #result"() {
        expect: "when + then 组合"
        GroovySpec.calc(income) == result

        where: "分支逻辑"
        income || result
        -1     || "0"
        1      || "0.5"
    }
}
