### JDK SPI
META-INF/services/
通过serviceLoader获取配置的实现类

### Dubbo SPI
META-INF/dubbo/     存放用户自定义的SPI配置文件，在项目中
META-INF/dubbo/internal/    通过jar包引入的通用SPI配置文件

ExtensionLoader 负责扩展的加载和生命周期维护
扩展点 Extension Point 接口@SPI注解
扩展 Extension 实现类
扩展实例 Extension Instance 实现类实例
扩展自适应实例(扩展代理类) Extension Adaptive Instance @Adaptive注解在扩展接口上

### Spring SPI
META-INF/spring.factories
