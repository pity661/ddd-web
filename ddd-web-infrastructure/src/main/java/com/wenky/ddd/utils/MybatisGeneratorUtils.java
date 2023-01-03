package com.wenky.ddd.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: olading-operate-log-service
 * @description:
 * @author: huwenqi
 * @email: huwenqi@olading.com
 * @create: 2022-04-02 14:37
 */
public class MybatisGeneratorUtils {

    public static void main(String[] args) {
        String jdbcUrl =
                "jdbc:mysql://127.0.0.1:3307/ddd_web?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true";
        String username = "root";
        String password = "root";
        String packageName = "com.wenky.ddd";

        StringBuilder tables = new StringBuilder();
        /** *********需要创建的表*********** */
        tables.append("customer").append(",");
        //        tables.append("operate_request_record").append(",");
        /** ****************************** */
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/ddd-web-infrastructure/src/main/java");
        globalConfig.setAuthor("generator");
        globalConfig.setOpen(false);
        // 实体属性 Swagger2 注解
        //        globalConfig.setSwagger2(true);

        globalConfig.setServiceName("%sService");
        globalConfig.setEntityName("%s");
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setFileOverride(true);
        mpg.setGlobalConfig(globalConfig);
        // 数据源
        DataSourceConfig dsc = dataSourceConfig(jdbcUrl, username, password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("domain");
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg =
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        // to do nothing
                    }
                };

        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(
                new FileOutConfig(templatePath) {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                        return projectPath
                                + "/ddd-web-infrastructure/src/main/resources/mapper/"
                                + pc.getModuleName()
                                + "/"
                                + tableInfo.getEntityName()
                                + "Mapper"
                                + StringPool.DOT_XML;
                    }
                });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.disable(TemplateType.CONTROLLER);
        //        templateConfig.disable(TemplateType.SERVICE);
        //        templateConfig.disable(TemplateType.MAPPER);

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy =
                new StrategyConfig()
                        .setNaming(NamingStrategy.underline_to_camel)
                        .setColumnNaming(NamingStrategy.underline_to_camel)
                        .setEntityLombokModel(true)
                        .setInclude(tables.toString().split(","))
                        .setRestControllerStyle(false)
                        .setEntityTableFieldAnnotationEnable(true)
                        .setChainModel(true);
        //                .setTablePrefix("t_");

        // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    private static DataSourceConfig dataSourceConfig(
            String jdbcUrl, String username, String password) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(jdbcUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        return dsc;
    }
}
