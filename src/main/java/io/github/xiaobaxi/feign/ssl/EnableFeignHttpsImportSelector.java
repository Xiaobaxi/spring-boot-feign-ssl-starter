package io.github.xiaobaxi.feign.ssl;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import io.github.xiaobaxi.feign.ssl.client.HttpClientConfig;
import io.github.xiaobaxi.feign.ssl.client.OkhttpConfig;

/**
 * import selector
 * @author xiaobaxi
 *
 */
public class EnableFeignHttpsImportSelector implements ImportSelector {

	private static final String[] NO_IMPORTS = {};

	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
		String name = attributes.getString("name");
		if ("okhttp".equals(name)) {
			return new String[] { OkhttpConfig.class.getName() };
		} else if ("httpclient".equals(name)) {
			return new String[] { HttpClientConfig.class.getName() };
		} else {
			return null;
		}
	}

	/**
	 * Return the appropriate {@link AnnotationAttributes} from the
	 * {@link AnnotationMetadata}. By default this method will return attributes
	 * for {@link #getAnnotationClass()}.
	 * 
	 * @param metadata
	 *            the annotation metadata
	 * @return annotation attributes
	 */
	protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
		String name = getAnnotationClass().getName();
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
		Assert.notNull(attributes, "No auto-configuration attributes found. Is " + metadata.getClassName()
				+ " annotated with " + ClassUtils.getShortName(name) + "?");
		return attributes;
	}

	/**
	 * Return the source annotation class used by the selector.
	 * 
	 * @return the annotation class
	 */
	protected Class<?> getAnnotationClass() {
		return EnableFeignHttps.class;
	}

	protected boolean isEnabled(AnnotationMetadata metadata) {
		return true;
	}

}
