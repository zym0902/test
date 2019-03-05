# Spring Boot 笔记

> 介绍嵌入Intellij的Spring Initializr工具，他同web提供的功能一样，可以帮助我们快速的构建出一个基础的Spring Boot工程

## 一、使用Intellij中的Spring Initializr快速构建Spring Boot工程

### ①创建一个新的工程，选择Spring Initializr

![Alt text](img/1.png)

### ②输入groupId、artifactId和package

![2](C:\Users\YongMING\Desktop\Spring Boot\img\2.png)

### ③选择相关依赖

![3](C:\Users\YongMING\Desktop\Spring Boot\img\3.png)

### ④确定project name

![4](C:\Users\YongMING\Desktop\Spring Boot\img\4.png)

### ⑤创建完成，删除高亮无用文件

![5](C:\Users\YongMING\Desktop\Spring Boot\img\5.png)



## 二、HelloWorld详解

SpringBoot是用来简化Spring应用的搭建到开发的过程。应用开箱即用，只要通过“just run‘就可以启动项目。SpringBoot只要很少的Spring配置文件，因为”习惯优先于配置“的原则，使得Spring Boot在快速开发应用和微服务实践中得到了广泛好评。

### ①pom.xml设置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.telchina.springboot.test</groupId>
    <artifactId>spring-boot-test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-test</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

### ②Controller层

```java
package com.telchina.springboot.test.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @RequestMapping("/hello")
    public String sayhello(){
        return "hello";
    }

}

```

> @RestController=@Controller+@ResponseBody：Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody配合，如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式
>
> @Controller : 修饰class，用来创建处理http请求的对象
>
> @ResponseBody : 将返回值返回给请求对象
>
> @RequestMapping : 提供路由信息，告诉Spring任何来自“/hello”路径的HTTP请求都应该被映射到sayhello方法

### ③启动应用类

```java
package com.telchina.springboot.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
    }
}
```

### ④测试层

```java
package com.telchina.springboot.test.springboot;

import com.telchina.springboot.test.springboot.controller.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTestApplicationTests {
    private final static Logger log = LoggerFactory.getLogger(SpringBootTestApplication.class);
    @Test
    public void test(){
        log.info(new HelloController().sayhello());
    }

}
```

测试也可以将主程序启动起来，在浏览器中请求

http://localhost:8080/hello

![6](C:\Users\YongMING\Desktop\Spring Boot\img\6.png)

## 三、Spring Boot配置文件详解

### ①自定义属性与加载

在resources/application.properties中编写

```properties
com.name=zhang
com.age=12
```

编写一个类

```java
package com.telchina.springboot.test.springboot.pojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class BlogProperties {

    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private Integer age;

    @Override
    public String toString() {
        return "BlogProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

编写相关测试文件

```java
package com.telchina.springboot.test.springboot;

import com.telchina.springboot.test.springboot.pojo.BlogProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTestApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(SpringBootTestApplication.class);

    @Autowired
    private BlogProperties blogProperties;

    @Test
    public void test(){
        log.info(blogProperties.getName()+"=="+blogProperties.getAge());
    }
}

```

输出结果：

```properties
2019-01-03 13:48:00.261  INFO 10004 --- [           main] c.t.s.t.s.SpringBootTestApplication      : zhang==12
```

### ②参数间的引用

```properties
com.name=zhang
com.age=12
com.desc=${com.name} 的年龄是 ${com.age}
```

### ③使用随机数

```properties
# 随机字符串
com.didispace.blog.value=${random.value}
# 随机int
com.didispace.blog.number=${random.int}
# 随机long
com.didispace.blog.bignumber=${random.long}
# 10以内的随机数
com.didispace.blog.test1=${random.int(10)}
# 10-20的随机数
com.didispace.blog.test2=${random.int[10,20]}
```

### ④通过命令行设置属性

```xml
java -jar xxx.jar --server.port=8080
```

也可以通过java语句来禁用命令行访问属性

```java
SpringApplication.setAddCommandLineProperties(false);
```

## 四、多环境配置

### ①通过配置多份不同文件的形式

在Spring Boot中多环境配置文件名需满足application-{profile}.properties的格式，其中{profile}对应你的环境标识

```properties
application-dev.properties
application-test.properties
application-prod.properties
```

具体哪个文件生效，最后需要在**application.properties**文件中通过**spring.profiles.active**属性来设置

示例：

创建application-dev.properties

```properties
server.port=8090
```

创建application-test.properties

```properties
server.port=8091
```

在application.properties中配置

```properties
spring.profiles.active=test
```

运行起来：

![7](C:\Users\YongMING\Desktop\Spring Boot\img\7.png)



也可以通过代码块的方式进行设置：

但是只能在yml文件中进行设置

```yaml
server:
  port: 8080
spring:
  profiles:
    active: dev
---

server:
     port: 8091
spring:
  profiles: dev
  
---
server:
  port: 8092
spring:
  profiles: test
```

## 五、SpringBoot配置文件

### ①自动配置

Spring Boot在启动的时候加载主配置类，开启了自动配置功能

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringBootTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
    }
}
```

看一下@SpringBootApplication注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```

由@EnableAutoConfiguration开启了自动配置功能

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

现在我们来看一下AutoConfigurationImportSelector.class，其中有一个方法

```java
@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
				.loadMetadata(this.beanClassLoader);
		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(
				autoConfigurationMetadata, annotationMetadata);
		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
	}
```

```java
protected AutoConfigurationEntry getAutoConfigurationEntry(
			AutoConfigurationMetadata autoConfigurationMetadata,
			AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
		List<String> configurations = getCandidateConfigurations(annotationMetadata,
				attributes);
		configurations = removeDuplicates(configurations);
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		checkExcludedClasses(configurations, exclusions);
		configurations.removeAll(exclusions);
		configurations = filter(configurations, autoConfigurationMetadata);
		fireAutoConfigurationImportEvents(configurations, exclusions);
		return new AutoConfigurationEntry(configurations, exclusions);
	}
```

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
			AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
				getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
		Assert.notEmpty(configurations,
				"No auto configuration classes found in META-INF/spring.factories. If you "
						+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
```

List<String> configurations = SpringFactoriesLoader.loadFactoryNames扫描所有jar包类路径下

META-INF/spring.factories，把扫描到的这些文件包装成properties对象





![8](C:\Users\YongMING\Desktop\Spring Boot\img\8.png)

```properties
# AutoConfigureCache auto-configuration imports
org.springframework.boot.test.autoconfigure.core.AutoConfigureCache=\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration

# AutoConfigureDataJdbc auto-configuration imports
org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc=\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataJpa auto-configuration imports
org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa=\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataLdap auto-configuration imports
org.springframework.boot.test.autoconfigure.data.ldap.AutoConfigureDataLdap=\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration

# AutoConfigureDataMongo auto-configuration imports
org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo=\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration

# AutoConfigureDataNeo4j auto-configuration imports
org.springframework.boot.test.autoconfigure.data.neo4j.AutoConfigureDataNeo4j=\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataRedis auto-configuration imports
org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis=\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration

# AutoConfigureJdbc auto-configuration imports
org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc=\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureTestDatabase auto-configuration imports
org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase=\
org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

# AutoConfigureJooq auto-configuration imports
org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq=\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureJson auto-configuration imports
org.springframework.boot.test.autoconfigure.json.AutoConfigureJson=\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration

# AutoConfigureJsonTesters auto-configuration imports
org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters=\
org.springframework.boot.test.autoconfigure.json.JsonTestersAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration

# AutoConfigureWebClient auto-configuration imports
org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient=\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientAutoConfiguration

# AutoConfigureWebFlux auto-configuration imports
org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux=\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration

# AutoConfigureMockMvc auto-configuration imports
org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc=\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebDriverAutoConfiguration

# AutoConfigureMockRestServiceServer
org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer=\
org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerAutoConfiguration

# AutoConfigureRestDocs auto-configuration imports
org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs=\
org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration

# AutoConfigureTestEntityManager auto-configuration imports
org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager=\
org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration

# AutoConfigureWebClient auto-configuration imports
org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient=\
org.springframework.boot.test.autoconfigure.web.client.WebClientRestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration

# AutoConfigureWebMvc auto-configuration imports
org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc=\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration

# DefaultTestExecutionListenersPostProcessors
org.springframework.boot.test.context.DefaultTestExecutionListenersPostProcessor=\
org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener$PostProcessor

# Spring Test ContextCustomizerFactories
org.springframework.test.context.ContextCustomizerFactory=\
org.springframework.boot.test.autoconfigure.OverrideAutoConfigurationContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.filter.TypeExcludeFiltersContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory

# Test Execution Listeners
org.springframework.test.context.TestExecutionListener=\
org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener

```

从properties中获取到EnableAutoConfiguration.class类对应的值，然后把他们添加在容器中

### ②自定义属性

首先定义application.properties字段

```properties
com.name=zhang
com.age=12
com.desc=${com.name} age is ${com.age}
```

定义pojo

```java
package com.telchina.springboot.test.springboot.pojo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
//通过@ConfigurationProperties(prefix = "com")将配置文件中com前缀的属性值自动绑定到对应的字段中
@Component
@ConfigurationProperties(prefix = "com")
public class BlogProperties {
    private String name;
    private Integer age;
    private String desc;
    @Override
    public String toString() {
        return "BlogProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

```

## 六、复杂RESTful API以及单元测试

| **请求类型** | **URL**   | 功能说明           |
| ------------ | --------- | ------------------ |
| GET          | /users    | 查询用户列表       |
| POST         | /users    | 创建一个用户       |
| GET          | /users/id | 根据id查询一个用户 |
| PUT          | /users/id | 根据id更新一个用户 |
| DELETE       | /userd/id | 根据id删除一个用户 |

### ①创建一个POJO

```java
public class User {

    private Long id;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

### ②创建一个controller

```java
import com.telchina.springboot.test.springboot.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    //处理 "/users/"的GET请求，用来获取用户列表
    //还可以通过@RequestParam从页面中传递参数来进行条件查询
    @RequestMapping(value="/",method= RequestMethod.GET)
    public List<User> getUserList(){
        List<User> r = new ArrayList<>(users.values());
        return r;
    }

    //处理"/users/"请求的post请求，用来创建User
    @RequestMapping(value="/",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){
        users.put(user.getId(),user);
        return "success";
    }

    //处理"/users/{id}"的PUT请求，用来更新User信息
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return users.get(id);
    }

    //处理"/users/{id}"的delete请求，用来删除User
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public  String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }
}

```

## 七、实现Restful服务，基于HTTP/JSON传输

### ①创建domain

```java
package org.spring.springboot.domain;

public class City {
    private Long id;
    private Long provinceId;
    private String cityName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

```

### ②创建接口类

```java
package org.spring.springboot.service;

import org.spring.springboot.domain.City;

import java.util.List;

public interface CityService {
    List<City> findAllCity();
    City findCityById(Long id);
    Long saveCity(City city);
    Long updateCity(City city);
    Long deleteCity(Long id);
}

```

### ③创建controller层

```java
package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public City findOneCity(@PathVariable("id") Long id) {
        return cityService.findCityById(id);
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public List<City> findAllCity() {
        return cityService.findAllCity();
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.POST)
    public void createCity(@RequestBody City city) {
        cityService.saveCity(city);
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.PUT)
    public void modifyCity(@RequestBody City city) {
        cityService.updateCity(city);
    }

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.DELETE)
    public void modifyCity(@PathVariable("id") Long id) {
        cityService.deleteCity(id);
    }
}

```

### ④创建DAO层

```java
package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.City;

import java.util.List;

public interface CityDao {
    List<City> findAllCity();
    City findById(@Param("id") Long id);
    Long saveCity(City city);
    Long updateCity(City city);
    Long deleteCity(Long id);
}

```

### ⑤对DAO的映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.spring.springboot.dao.CityDao">
    <resultMap id="BaseResultMap" type="org.spring.springboot.domain.City">
        <result column="id" property="id"/>
        <result column="province_id" property="provinceId"/>
        <result column="city_name" property="cityName"/>
        <result column="description" property="description"/>
    </resultMap>

    <parameterMap id="City" type="org.spring.springboot.domain.City"/>

    <sql id="Base_Column_List">
        id, province_id, city_name, description
    </sql>

    <select id="findById" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from city
        where id = #{id}
    </select>

    <select id="findAllCity" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from city
    </select>

    <insert id="saveCity" parameterMap="City" useGeneratedKeys="true" keyProperty="id">
        insert into
        city(id,province_id,city_name,description)
        values
        (#{id},#{provinceId},#{cityName},#{description})
    </insert>

    <update id="updateCity" parameterMap="City">
        update
        city
        set
        <if test="provinceId!=null">
            province_id = #{provinceId},
        </if>
        <if test="cityName!=null">
            city_name = #{cityName},
        </if>
        <if test="description!=null">
            description = #{description}
        </if>
        where
        id = #{id}
    </update>

    <delete id="deleteCity" parameterType="java.lang.Long">
        delete from
        city
        where
        id = #{id}
    </delete>
</mapper>

```

### ⑥实现接口

```java
package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    public List<City> findAllCity() {
        return cityDao.findAllCity();
    }

    public City findCityById(Long id) {
        return cityDao.findById(id);
    }

    @Override
    public Long saveCity(City city) {
        return cityDao.saveCity(city);
    }

    @Override
    public Long updateCity(City city) {
        return cityDao.updateCity(city);
    }

    @Override
    public Long deleteCity(Long id) {
        return cityDao.deleteCity(id);
    }

}

```

### ⑦数据库配置

```properties
## 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
## Mybatis 配置
mybatis.typeAliasesPackage=org.spring.springboot.domain
mybatis.mapperLocations=classpath:mapper/*.xml
```



## 八、集成FreeMarker

项目结构列表

![9](C:\Users\YongMING\Desktop\Spring Boot\img\9.png)

### ①POM依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>springboot</groupId>
    <artifactId>springboot-freemarker</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>整合 FreeMarker 案例</name>

    <!-- Spring Boot 启动父依赖 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.1.RELEASE</version>
    </parent>

    <properties>
        <mybatis-spring-boot>1.2.0</mybatis-spring-boot>
        <mysql-connector>5.1.39</mysql-connector>
    </properties>

    <dependencies>
        <!-- Spring Boot Freemarker 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>

        <!-- Spring Boot Web 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Test 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Boot Mybatis 依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot}</version>
        </dependency>

        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector}</version>
        </dependency>

        <!-- Junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
</project>

```

### ②配置FreeMarker

在application.properties中加入FreeMarker相关的配置

```properties
## 数据源配置
spring.datasource.url=jdbc:mysql://139.224.14.39:3306/springbootdb?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=Hello123!@
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
## Mybatis 配置
mybatis.typeAliasesPackage=org.spring.springboot.domain
mybatis.mapperLocations=classpath:mapper/*.xml
## Freemarker 配置
## 文件配置路径
spring.freemarker.template-loader-path=classpath:/web/
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8
spring.freemarker.check-template-location=true
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.request-context-attribute=request
spring.freemarker.suffix=.ftl
```

### ③Controller层

```java
package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public String findOneCity(Model model, @PathVariable("id") Long id) {
        model.addAttribute("city", cityService.findCityById(id));
        return "city";
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public String findAllCity(Model model) {
        List<City> cityList = cityService.findAllCity();
        model.addAttribute("cityList", cityList);
        return "cityList";
    }
}

```

对应资源文件夹下的.ftl文件

```swift
<!DOCTYPE html>

<html lang="en">

<body>
City: ${city.cityName}! <br>
Q:Why I like? <br>
A:${city.description}!
</body>

</html>
```

```swift
<!DOCTYPE html>

<html lang="en">

<body>
<#list cityList as city>

City: ${city.cityName}! <br>
Q:Why I like? <br>
A:${city.description}!

</#list>
</body>

</html>
```

### ④Service层

```java
package org.spring.springboot.service;

import org.spring.springboot.domain.City;

import java.util.List;

public interface CityService {

    /**
     * 获取城市信息列表
     *
     * @return
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID,查询城市信息
     *
     * @param id
     * @return
     */
    City findCityById(Long id);

    /**
     * 新增城市信息
     *
     * @param city
     * @return
     */
    Long saveCity(City city);

    /**
     * 更新城市信息
     *
     * @param city
     * @return
     */
    Long updateCity(City city);

    /**
     * 根据城市 ID,删除城市信息
     *
     * @param id
     * @return
     */
    Long deleteCity(Long id);
}

```

### ⑤Impl

```java
package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市业务逻辑实现类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    public List<City> findAllCity() {
        return cityDao.findAllCity();
    }

    public City findCityById(Long id) {
        return cityDao.findById(id);
    }

    @Override
    public Long saveCity(City city) {
        return cityDao.saveCity(city);
    }

    @Override
    public Long updateCity(City city) {
        return cityDao.updateCity(city);
    }

    @Override
    public Long deleteCity(Long id) {
        return cityDao.deleteCity(id);
    }

}

```

### ⑥DAO层

```java
package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.City;

import java.util.List;

public interface CityDao {

    /**
     * 获取城市信息列表
     *
     * @return
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     *
     * @param id
     * @return
     */
    City findById(@Param("id") Long id);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);
}

```

### ⑦Mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.spring.springboot.dao.CityDao">
    <resultMap id="BaseResultMap" type="org.spring.springboot.domain.City">
        <result column="id" property="id"/>
        <result column="province_id" property="provinceId"/>
        <result column="city_name" property="cityName"/>
        <result column="description" property="description"/>
    </resultMap>

    <parameterMap id="City" type="org.spring.springboot.domain.City"/>

    <sql id="Base_Column_List">
        id, province_id, city_name, description
    </sql>

    <select id="findById" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from city
        where id = #{id}
    </select>

    <select id="findAllCity" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from city
    </select>

    <insert id="saveCity" parameterMap="City" useGeneratedKeys="true" keyProperty="id">
        insert into
        city(id,province_id,city_name,description)
        values
        (#{id},#{provinceId},#{cityName},#{description})
    </insert>

    <update id="updateCity" parameterMap="City">
        update
        city
        set
        <if test="provinceId!=null">
            province_id = #{provinceId},
        </if>
        <if test="cityName!=null">
            city_name = #{cityName},
        </if>
        <if test="description!=null">
            description = #{description}
        </if>
        where
        id = #{id}
    </update>

    <delete id="deleteCity" parameterType="java.lang.Long">
        delete from
        city
        where
        id = #{id}
    </delete>
</mapper>

```

## 九、SpringDataJPA

### ①POM

添加pom依赖

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
```

### ②配置数据库信息

在application.xml中添加数据库配置信息

```xml
spring.profiles.active=test
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=langsin
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.hbm2ddl.auto=create-drop
```

### ③创建实体

```java
package com.telchina.springboot.test.springboot.pojo;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

### ④创建数据访问接口

```java
package com.telchina.springboot.test.springboot.repo;

import com.telchina.springboot.test.springboot.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,String> {

    User findByName(String name);

    User findByNameAndAge(String name,Integer age);

    @Query("from User u where u.name=?1")
    User findUser(@Param("name")String name);
}

```

    ## 十、多数据源配置

创建一个spring配置类，定义两个DataSource用来读取application.properties中的不同配置。

主数据源配置为spring.datasource.primary

第二数据源配置为spring.datasource.secondary

### ①application.properties添加配置

```properties
spring.datasource.primary.url=jdbc:mysql://localhost:3306/test1
spring.datasource.primary.username=root
spring.datasource.primary.password=root
spring.datasource.primary.driver-class-name=com.mysql.jdbc.Driver

spring.datasource.secondary.url=jdbc:mysql://localhost:3306/test2
spring.datasource.secondary.username=root
spring.datasource.secondary.password=root
spring.datasource.secondary.driver-class-name=com.mysql.jdbc.Driver
```

### ②添加数据库配置文件

```java
package com.telchina.springboot.test.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Configuration
public class DataSourceConfig {

    @Bean(name="primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }
}

```

### ③JdbcTemplate

```java
package com.telchina.springboot.test.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class JdbcTemplateTest {

    @Bean(name="primaryJdbcTemplate")
    @Qualifier("primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryJdbcTemplate")
    @Qualifier("secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}

```

## 十、使用NoSQL数据库Redis

### Redis

#### ①配置依赖

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-redis</artifactId>
            <version>1.4.7.RELEASE</version>
  </dependency>
```

#### ②加入Redis服务相关的配置

```properties
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=localhost
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=0
```

#### ③测试访问

```java
 @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test1(){
        stringRedisTemplate.opsForValue().set("111","11");
        Assert.assertEquals("11",stringRedisTemplate.opsForValue().get("111"));
    }
```

### 在redis中存储对象

#### ①创建对象，完成序列化

```java
package com.telchina.springboot.test.springboot.pojo;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -1L;
    private String username;
    private Integer age;

    public Person(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

#### ②完成对象的序列化接口

```properties
package com.telchina.springboot.test.springboot.redis;


import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisObjectSerillizer implements RedisSerializer<Object> {
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[],Object> deserializer = new DeserializingConverter();

    static final byte[] EMPTY_ARRAY = new byte[0];

    public Object deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }

        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }

        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            return EMPTY_ARRAY;
        }
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}

```

#### ③配置针对Person的RedisTemplate实例

```java
package com.telchina.springboot.test.springboot.redis;

import com.telchina.springboot.test.springboot.pojo.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Person> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String ,Person> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerillizer());
        return template;
    }
}
```

#### ④编写测试用例

```java
 @Autowired
    private RedisTemplate<String, Person> redisTemplate;

    @Test
    public void test2() throws Exception {

        // 保存对象
        Person person = new Person("超人", 20);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        person = new Person("蝙蝠侠", 30);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        person = new Person("蜘蛛侠", 40);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
        Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
        Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());

    }
```

### MongoDB

#### ①引入依赖

>在pom.xml中加入spring-boot-starter-data-mongodb引入对mongodb的访问支持依赖

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
  </dependency>
```

#### ②建立相关User类

```java
package com.telchina.springboot.test.springboot.mongodb;

import javax.persistence.Id;

public class User {
    @Id
    private String id;
    private String username;
    private Integer age;

    public User(String id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

#### ③实现User的数据访问对象UserRepo

```java
package com.telchina.springboot.test.springboot.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Long> {
    User findByUsername(String username);
}

```

#### ④在单元测试中调用

```java
package com.telchina.springboot.test.springboot.mongoDb;

import com.telchina.springboot.test.springboot.mongodb.User;
import com.telchina.springboot.test.springboot.mongodb.UserRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {

    @Autowired
    private UserRepo userRepo;

    @Before
    public void setUp(){
        userRepo.deleteAll();
    }

    @Test
    public void test(){
        userRepo.save(new User("aaa","didi",20));
        userRepo.save(new User("bbb","meimei",30));
        userRepo.save(new User("ccc","gege",40));

        User u1 = userRepo.findByUsername("didi");
        userRepo.delete(u1);
        Assert.assertEquals(2,userRepo.findAll().size());

        User u2 = userRepo.findByUsername("meimei");
        userRepo.delete(u2);
        Assert.assertEquals(1,userRepo.findAll().size());
    }
}

```

#### ⑤参数配置



> 实战中，应用服务器和MongoDB通常不会部署于同一台服务器上，无法使用自动化的本地配置，我们可以在application.properties中加入mongodb服务端的相关配置
>
> 首先在mongodb中创建具备读写权限的用户（用户名为name，密码pass）

```properties
spring.data.mongodb.uri=mongodb://name:pass@localhost:27017/test
```



## 十一、整合Mybatis

### 项目结构图

![10](C:\Users\YongMING\Desktop\Spring Boot\img\10.png)

### ①POM依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>springboot</groupId>
    <artifactId>springboot-mybatis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-mybatis :: 整合 Mybatis Demo</name>

    <!-- Spring Boot 启动父依赖 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.1.RELEASE</version>
    </parent>

    <properties>
        <mybatis-spring-boot>1.2.0</mybatis-spring-boot>
        <mysql-connector>5.1.39</mysql-connector>
    </properties>

    <dependencies>

        <!-- Spring Boot Web 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Test 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Boot Mybatis 依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot}</version>
        </dependency>

        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector}</version>
        </dependency>

        <!-- Junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
</project>

```

### ②数据库配置

```properties
## 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
## Mybatis 配置
mybatis.typeAliasesPackage=org.spring.springboot.domain
mybatis.mapperLocations=classpath:mapper/*.xml
```

### ③Service

```java
package org.spring.springboot.service;

import org.spring.springboot.domain.City;

public interface CityService {
    City findCityByName(String cityName);
}

```

### ④Dao

```java
package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.City;

public interface CityDao {
    City findByName(@Param("cityName") String cityName);
}


```

### ⑤Mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.spring.springboot.dao.CityDao">
    <resultMap id="BaseResultMap" type="org.spring.springboot.domain.City">
        <result column="id" property="id"/>
        <result column="province_id" property="provinceId"/>
        <result column="city_name" property="cityName"/>
        <result column="description" property="description"/>
    </resultMap>

    <parameterMap id="City" type="org.spring.springboot.domain.City"/>

    <sql id="Base_Column_List">
        id, province_id, city_name, description
    </sql>

    <select id="findByName" resultMap="BaseResultMap" parameterType="java.lang.String">
        select
        <include refid="Base_Column_List"/>
        from city
        where city_name = #{cityName}
    </select>

</mapper>

```

### ⑥ServiceImpl

```java
package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    public City findCityByName(String cityName) {
        return cityDao.findByName(cityName);
    }
}
```

### ⑦domain

```java
package org.spring.springboot.domain;

public class City {
    private Long id;
    private Long provinceId;
    private String cityName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

```

### ⑧Controller

```java
package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }

}

```

### ⑨Application

```java
package org.spring.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动类
 * <p>
 * Created by bysocket on 16/4/26.
 */
// Spring Boot 应用的标识
@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("org.spring.springboot.dao")
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class, args);
    }
}

```

## 十二、Spring Booot整合MyBatis Annotation

### 项目结构图

![11](C:\Users\YongMING\Desktop\Spring Boot\img\11.png)

### ①POM依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>springboot</groupId>
    <artifactId>springboot-mybatis-annotation</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>springboot-mybatis-annotation</name>
    <description>Springboot-mybatis :: 整合 Mybatis Annotation 案例</description>

    <!-- Spring Boot 启动父依赖 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.1.RELEASE</version>
    </parent>

    <properties>
        <mybatis-spring-boot>1.2.0</mybatis-spring-boot>
        <mysql-connector>5.1.39</mysql-connector>
    </properties>

    <dependencies>
        <!-- Spring Boot Web 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Test 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Boot Mybatis 依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot}</version>
        </dependency>

        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector}</version>
        </dependency>

        <!-- Junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>


</project>

```

### ②domain

```java
package org.spring.springboot.domain;


public class City {

    private Long id;
    private Long provinceId;
    private String cityName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

```

### ③Service

```java
package org.spring.springboot.service;

import org.spring.springboot.domain.City;


public interface CityService {

    City findCityByName(String cityName);
}

```

### ④Dao

```java
package org.spring.springboot.dao;

import org.apache.ibatis.annotations.*;
import org.spring.springboot.domain.City;

@Mapper // 标志为 Mybatis 的 Mapper
public interface CityDao {


    @Select("SELECT * FROM city")
    // 返回 Map 结果集
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "provinceId", column = "province_id"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "description", column = "description"),
    })
    City findByName(@Param("cityName") String cityName);
}

```

### ⑤Impl

```java
package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    public City findCityByName(String cityName) {
        return cityDao.findByName(cityName);
    }
}
```

### ⑥Controller

```java
package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }
}
```

### ⑦配置类

```properties
## 数据源配置
spring.datasource.url=jdbc:mysql://139.224.14.39:3306/springbootdb?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=yt0923666
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

## 十三、多数据源配置

### 项目目录结构

```java
├── pom.xml
└── src
    └── main
        ├── java
        │   └── org
        │       └── spring
        │           └── springboot
        │               ├── Application.java
        │               ├── config
        │               │   └── ds
        │               │       ├── ClusterDataSourceConfig.java
        │               │       └── MasterDataSourceConfig.java
        │               ├── controller
        │               │   └── UserRestController.java
        │               ├── dao
        │               │   ├── cluster
        │               │   │   └── CityDao.java
        │               │   └── master
        │               │       └── UserDao.java
        │               ├── domain
        │               │   ├── City.java
        │               │   └── User.java
        │               └── service
        │                   ├── UserService.java
        │                   └── impl
        │                       └── UserServiceImpl.java
        └── resources
            ├── application.properties
            └── mapper
                ├── cluster
                │   └── CityMapper.xml
                └── master
                    └── UserMapper.xml
```

### ①POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>springboot</groupId>
    <artifactId>springboot-mybatis-mutil-datasource</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-mybatis-mutil-datasource :: Spring Boot 实现 Mybatis 多数据源配置</name>

    <!-- Spring Boot 启动父依赖 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.1.RELEASE</version>
    </parent>

    <properties>
        <mybatis-spring-boot>1.2.0</mybatis-spring-boot>
        <mysql-connector>5.1.39</mysql-connector>
        <druid>1.0.18</druid>
    </properties>

    <dependencies>

        <!-- Spring Boot Web 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Test 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Boot Mybatis 依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot}</version>
        </dependency>

        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector}</version>
        </dependency>

        <!-- Druid 数据连接池依赖 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid}</version>
        </dependency>

        <!-- Junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
</project>

```

### ②application.properties

```properties
## master 数据源配置
master.datasource.url=jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&characterEncoding=utf8
master.datasource.username=root
master.datasource.password=123456
master.datasource.driverClassName=com.mysql.jdbc.Driver
## cluster 数据源配置
cluster.datasource.url=jdbc:mysql://localhost:3306/springbootdb_cluster?useUnicode=true&characterEncoding=utf8
cluster.datasource.username=root
cluster.datasource.password=123456
cluster.datasource.driverClassName=com.mysql.jdbc.Driver
```

### ③数据源配置

从数据源配置

```java
package org.spring.springboot.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "org.spring.springboot.dao.cluster";
    static final String MAPPER_LOCATION = "classpath:mapper/cluster/*.xml";

    @Value("${cluster.datasource.url}")
    private String url;

    @Value("${cluster.datasource.username}")
    private String user;

    @Value("${cluster.datasource.password}")
    private String password;

    @Value("${cluster.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "clusterDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
```

主数据源配置

```java
package org.spring.springboot.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "org.spring.springboot.dao.master";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Value("${master.datasource.url}")
    private String url;

    @Value("${master.datasource.username}")
    private String user;

    @Value("${master.datasource.password}")
    private String password;

    @Value("${master.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
@Primary标志这个Bean在多个同类Bean候选时，该Bean优先被考虑

```

### ④Service

```java
package org.spring.springboot.service;

import org.spring.springboot.domain.User;

public interface UserService {

    User findByName(String userName);
}
```

### ⑤Dao

CityDao

```java
package org.spring.springboot.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.City;

@Mapper
public interface CityDao {

    City findByName(@Param("cityName") String cityName);
}
```

UserDao

```java
package org.spring.springboot.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.User;
@Mapper
public interface UserDao {
    User findByName(@Param("userName") String userName);
}
```

### ⑥Mapper

CityMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.spring.springboot.dao.cluster.CityDao">
    <resultMap id="BaseResultMap" type="org.spring.springboot.domain.City">
        <result column="id" property="id"/>
        <result column="province_id" property="provinceId"/>
        <result column="city_name" property="cityName"/>
        <result column="description" property="description"/>
    </resultMap>

    <parameterMap id="City" type="org.spring.springboot.domain.City"/>

    <sql id="Base_Column_List">
        id, province_id, city_name, description
    </sql>

    <select id="findByName" resultMap="BaseResultMap" parameterType="java.lang.String">
        select
        <include refid="Base_Column_List"/>
        from city
        where city_name = #{cityName}
    </select>

</mapper>

```

UserMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.spring.springboot.dao.master.UserDao">
    <resultMap id="BaseResultMap" type="org.spring.springboot.domain.User">
        <result column="id" property="id"/>
        <result column="user_name" property="userName"/>
        <result column="description" property="description"/>
    </resultMap>

    <parameterMap id="User" type="org.spring.springboot.domain.User"/>

    <sql id="Base_Column_List">
        id, user_name, description
    </sql>

    <select id="findByName" resultMap="BaseResultMap" parameterType="java.lang.String">
        select
        <include refid="Base_Column_List"/>
        from user
        where id = 1
    </select>

</mapper>
```

### ⑦domain

```java
package org.spring.springboot.domain;

public class City {
    private Long id;
    private Long provinceId;
    private String cityName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

```

```java
package org.spring.springboot.domain;

/**
 * 用户实体类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
public class User {
    private Long id;
    private String userName;
    private String description;
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
```

### ⑧impl

```java
package org.spring.springboot.service.impl;

import org.spring.springboot.dao.cluster.CityDao;
import org.spring.springboot.dao.master.UserDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.User;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现层
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao; // 主数据源

    @Autowired
    private CityDao cityDao; // 从数据源

    @Override
    public User findByName(String userName) {
        User user = userDao.findByName(userName);
        City city = cityDao.findByName("温岭市");
        user.setCity(city);
        return user;
    }
}
```

### ⑨Controller

```java
package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.domain.User;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public User findByName(@RequestParam(value = "userName", required = true) String userName) {
        return userService.findByName(userName);
    }

}

```

## 十四、Spring Boot默认日志的配置

默认的日志输出：

```properties
2016-04-13 08:23:50.120  INFO 37397 --- [ main] org.hibernate.Version : HHH000412: Hibernate Core {4.3.11.Final}
```

输出内容元素如下：

+ 时间日期，具体到毫秒
+ 日志级别：ERROE、WAEN、INFO、DEBUG和TRACE
+ 进程ID
+ 分隔符：---标识实际日志的开始
+ 进程名：方括号括起来
+ Logger名
+ 日志内容

### 控制台输出

在Spring Boot中默认配置了**ERROR**、**WARN**和**INFO**级别的日志输出到控制台

通过两种方式切换至**DEBUG**级别：

+ 在运行命令后加入**--debug**标志：$ java -jar myapp.jar --debug
+ 在application.properties中配置debug=true

### 文件输出

若要增加文件输出，需要在applcation.properties中配置logging.files或logging.path属性

### 引入log4j依赖

在创建Spring Boot工程时，我们引入了**spring-boot-starter**，其中包含了**spring-boot-starter-logging**，该依赖内容就是SpringBoot默认的日志框架Logback，所以我们引入log4j之前，需要先排除该包 依赖，再引入log4j的依赖

```xml
		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
        </dependency>
```

### 配置log4j.properties

在引入了log4j依赖之后，只需要在**src/main/resources**目录下加入**log4j.properties**配置文件，就可以开始对应用的日志进行配置使用

**控制台输出**

设定root日志的输出级别为INFO，appender为控制台输出stdout

```properties
# LOG4J配置
log4j.rootCategory=INFO, stdout

# 控制台输出
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

**输出到文件**

生产或测试环境会持久化日志内容，方便追溯问题原因。可以通过添加如下的appender内容，按天输出到不同的文件中去，同时还需要为`log4j.rootCategory`添加名为file的appender，这样root日志就可以输出到`logs/all.log`文件中了。

```properties
log4j.rootCategory=INFO, stdout, file

# root日志输出
log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.file=logs/all.log
log4j.appender.file.DatePattern='.'yyyy-MM-dd
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

**分类输出**

当我们日志量较多的时候，查找问题会非常困难，常用的手段就是对日志进行分类，比如：

- 可以按不同package进行输出。通过定义输出到`logs/my.log`的appender，并对`com.didispace`包下的日志级别设定为DEBUG级别、appender设置为输出到`logs/my.log`的名为`didifile`的appender。

```properties
# com.didispace包下的日志配置
log4j.category.com.didispace=DEBUG, didifile

# com.didispace下的日志输出
log4j.appender.didifile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.didifile.file=logs/my.log
log4j.appender.didifile.DatePattern='.'yyyy-MM-dd
log4j.appender.didifile.layout=org.apache.log4j.PatternLayout
log4j.appender.didifile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L ---- %m%n
```

- 可以对不同级别进行分类，比如对ERROR级别输出到特定的日志文件中，具体配置可以如下。

  ```properties
  log4j.logger.error=errorfile
  # error日志输出
  log4j.appender.errorfile=org.apache.log4j.DailyRollingFileAppender
  log4j.appender.errorfile.file=logs/error.log
  log4j.appender.errorfile.DatePattern='.'yyyy-MM-dd
  log4j.appender.errorfile.Threshold = ERROR
  log4j.appender.errorfile.layout=org.apache.log4j.PatternLayout
  log4j.appender.errorfile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
  ```

  