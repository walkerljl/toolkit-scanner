/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.toolkit.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * ClassScannerUtils
 *
 * @author lijunlin
 */
public class ClassScannerUtils {

    private static final ClassScanner CLASS_SCANNER = new DefaultClassScanner();

    public static List<Class<?>> getClassList(String packageName) {
        return CLASS_SCANNER.getClassList(packageName);
    }

    public static List<Class<?>> getClassListBySuper(String packageName, Class<?> superClass) {
        return CLASS_SCANNER.getClassListBySuper(packageName, superClass);
    }

    public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return CLASS_SCANNER.getClassListByAnnotation(packageName, annotationClass);
    }
}