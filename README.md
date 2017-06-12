## spring cloud feign https starter

### feign调用https服务
1、feign的注解头上加https
2、openssl证书制作，truststore配置
3、加注解EnableFeignHttps
```
//默认httpclient, 可选：okhttp
@EnableFeignHttps(name="httpclient") 
public class Application() {
}
```
### pom dependency
```
<dependency>
	<groupId>io.github.xiaobaxi</groupId>
	<artifactId>spring-boot-feign-ssl-starter</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

httpclient
```
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
    <version>${feign.version}</version>
</dependency>
```

okhttp
```
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-okhttp</artifactId>
    <version>${feign.version}</version>
</dependency>
```