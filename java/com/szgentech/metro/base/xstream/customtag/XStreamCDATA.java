/**
 * 
 */
package com.szgentech.metro.base.xstream.customtag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className:XStreamCDATA.java
 * @classDescription:自定义CDATA标签，有该标签表示内容会加上CDATA
 * @author: luowq
 * @createTime: 2016年3月24日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface XStreamCDATA {

}
