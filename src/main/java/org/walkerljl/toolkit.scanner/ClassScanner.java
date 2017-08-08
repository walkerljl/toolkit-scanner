/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.toolkit.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器
 *
 * @author lijunlin
 */
interface ClassScanner {

    /**
     * 获取指定包名中的所有类
     *
     * @param packageName
     * @return
     */
    List<Class<?>> getClassList(String packageName);

    /**
     * 获取指定包名中指定注解的相关类
     *
     * @param packageName
     * @param annotationClass
     * @return
     */
    List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * 获取指定包名中指定父类或接口的相关类
     *
     * @param packageName
     * @param superClass
     * @return
     */
    List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass);
}