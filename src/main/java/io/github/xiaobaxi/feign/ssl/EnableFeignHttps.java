package io.github.xiaobaxi.feign.ssl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * enable feign https based on httpclient or okhttp
 * @author xiaobaxi
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ EnableFeignHttpsImportSelector.class })
public @interface EnableFeignHttps {

	String name() default "httpclient";

}
