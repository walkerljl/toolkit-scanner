package org.walkerljl.toolkit.lang.scanner;

import java.lang.annotation.Annotation;

/**
 * 获取注解的模板类
 *
 * @author lijunlin
 */
abstract class AnnotationClassTemplate extends org.walkerljl.toolkit.lang.scan.ClassTemplate {

    protected final Class<? extends Annotation> annotationClass;

    protected AnnotationClassTemplate(String packageName, Class<? extends Annotation> annotationClass) {
        super(packageName);
        this.annotationClass = annotationClass;
    }
}
