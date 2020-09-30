# base-springframework
基于spring各个组件进行二次封装

## base-framework
用于处理jar包版本统一依赖
base-framework下每个项目都有一个application.yml文件里面配置着默认的配置
如果在自己的项目中配置相同配置的话会覆盖默认配置
将不常变动的配置写死在jar包里减少项目对应的配置,通过引入jar包的形式来加载配置,缺点是修改的话需要重新发包
也可以将通用配置通过cloud config来管理 然后通过spring.cloud.config.name来配置服务本身的配置及公共配置

### 技术栈
注册中心:consul
配置中心:git2consul
json序列化工具:sping自带的jackson,由于fastJson问题较多就不使用了
redis:采用redisson工具类
使用了lombok来减少实体中的代码,如果要提供给第三方引用的jar包,那么第三方jar包中就不要用lombok了
使用了swagger3来提供文档,好处:更改代码可以同步修改文档
持久层框架: mybatis-plus

## consul
consul安装教程