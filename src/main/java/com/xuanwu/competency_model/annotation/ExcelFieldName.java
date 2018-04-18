package com.xuanwu.competency_model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月4日
 * @version 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) 
public @interface ExcelFieldName {
	/**
	 * 列的中文名称
	 */
	String label() default "";
	/**
	 * 排序
	 */
	int index() default 1;
	/**
	 * 列长度
	 */
	int width() default 5000;
}
