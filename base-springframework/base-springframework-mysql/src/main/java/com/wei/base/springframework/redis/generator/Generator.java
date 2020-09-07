package com.wei.base.springframework.redis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wei.base.springframework.redis.constants.GeneratorParam;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class Generator {

    private static final GeneratorParam param = new GeneratorParam();

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = getGlobalConfig();
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig(gc);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = getPackageConfig();
        mpg.setPackageInfo(pc);

        String mapstructPageName = pc.getParent() + ".mapstruct";
        String mapstructPath = StringUtils.replace(mapstructPageName, ".", "/");

        // 自定义配置
        List<FileOutConfig> focList = getFileOutConfig(mapstructPath);
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = Maps.newHashMap();
                map.put("mapstructPageName", mapstructPageName);
                setMap(map);
            }
        };

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(param.getAuthor());
        gc.setOutputDir(PROJECT_PATH + param.getOutputDir());
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(Boolean.FALSE);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        //设置日期类型
        gc.setDateType(DateType.TIME_PACK);
        gc.setServiceName("%sService");

        return gc;
    }

    private static DataSourceConfig getDataSourceConfig(GlobalConfig globalConfig) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//            // 自定义数据库表字段类型转换【可选】
//            public IColumnType processTypeConvert(String fieldType) {
//                System.out.println("Model转换类型：" + fieldType);
//                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
//                return super.processTypeConvert(globalConfig, fieldType);
//            }
//        });

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(param.getDataSourceUserName());
        dsc.setPassword(param.getDataSourcePassWord());
        dsc.setUrl(param.getDataSourceUrl());
        return dsc;
    }

    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[]{"user_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(param.getInclude()); // 需要生成的表
        strategy.setExclude(param.getExclude()); // 排除生成的表
        strategy.setEntityLombokModel(Boolean.TRUE);
        strategy.setRestControllerStyle(Boolean.TRUE);
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        return strategy;
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(param.getParent());
//        pc.setModuleName("test");
        return pc;
    }

    /**
     * 设置自定义模板
     *
     * @param mapstructPath
     * @return
     */
    private static List<FileOutConfig> getFileOutConfig(String mapstructPath) {
        List<FileOutConfig> list = Lists.newArrayList();
        list.add(new FileOutConfig("/templates/mapstruct.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //输出的位置
                return PROJECT_PATH + param.getOutputDir() + mapstructPath + "/" + tableInfo.getEntityName() + "Mapstruct.java";
            }
        });

        return list;
    }
}