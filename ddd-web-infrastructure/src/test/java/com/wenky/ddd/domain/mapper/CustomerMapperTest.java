package com.wenky.ddd.domain.mapper;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenky.ddd.domain.entity.Customer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 克林
 * @date 2023/6/30 14:21
 */
public class CustomerMapperTest {
    private static CustomerMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder =
                new MybatisSqlSessionFactoryBuilder()
                        .build(
                                CustomerMapperTest.class
                                        .getClassLoader()
                                        .getResourceAsStream(
                                                "mybatisTestConfiguration/MysqlMapperTestConfiguration.xml"));
        // you can use builder.openSession(false) to not commit to database
        mapper =
                builder.getConfiguration()
                        .getMapper(CustomerMapper.class, builder.openSession(true));
    }

    @Test
    public void testFindByName() {
        mapper.findByName("wendy");
    }

    @Test
    public void queryWrapperTest() {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(Customer::getName, "wendy")
                .orderByDesc(Customer::getId)
                .last("limit 1");
        Customer customer = mapper.selectOne(queryWrapper);
        Assert.assertNotNull(customer);
    }
}
