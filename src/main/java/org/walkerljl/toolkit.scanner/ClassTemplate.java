/*
 * Copyright (c) 2010-2015 www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.toolkit.scanner;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.walkerljl.toolkit.lang.ClassUtils;
import org.walkerljl.toolkit.standard.exception.AppException;

/**
 * ClassTemplate
 *
 * @author lijunlin<walkerljl@qq.com>
 */
abstract class ClassTemplate {

    protected final String packageName;

    public ClassTemplate(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 获取类列表
     *
     * @return
     */
    public final List<Class<?>> getClassList() {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            // 从包名获取 URL 类型的资源
            Enumeration<URL> urls = ClassUtils.getClassLoader().getResources(
                    packageName.replace(".", "/"));
            // 遍历 URL 资源
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();


                if (url != null) {
                    // 获取协议名（分为 file 与 jar）
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        // 若在 class 目录中，则执行添加类操作
                        String packagePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        addClass(classList, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        // 若在 jar 包中，则解析 jar 包中的 entry
                        JarURLConnection jarURLConnection = (JarURLConnection) url
                                .openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            // 判断该 entry 是否为 class
                            if (jarEntryName.endsWith(".class")) {
                                // 获取类名
                                String className = jarEntryName.substring(0,
                                        jarEntryName.lastIndexOf("."))
                                        .replaceAll("/", ".");
                                // 执行添加类操作
                                doAddClass(classList, className);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AppException("获取类出错," + e.getMessage(), e);
        }
        return classList;
    }

    /**
     * 添加类
     *
     * @param classList
     * @param packagePath
     * @param packageName
     */
    private void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            // 获取包名路径下的 class 文件或目录
            File[] files = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class"))
                            || file.isDirectory();
                }
            });
            // 遍历文件或目录
            for (File file : files) {
                String fileName = file.getName();
                // 判断是否为文件或目录
                if (file.isFile()) {
                    // 获取类名
                    String className = fileName.substring(0,
                            fileName.lastIndexOf("."));
                    if (packageName != null && !"".equals(packageName)) {
                        className = packageName + "." + className;
                    }
                    // 执行添加类操作
                    doAddClass(classList, className);
                } else {
                    // 获取子包
                    String subPackagePath = fileName;
                    if (packagePath != null && !"".equals(packagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    // 子包名
                    String subPackageName = fileName;
                    if (packageName != null && !"".equals(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    // 递归调用
                    addClass(classList, subPackagePath, subPackageName);
                }
            }
        } catch (Exception e) {
            throw new AppException("添加类出错," + e.getMessage(), e);
        }
    }

    /**
     * 添加类
     *
     * @param classList
     * @param className
     */
    private void doAddClass(List<Class<?>> classList, String className) {
        // 加载类
        Class<?> cls = ClassUtils.loadClass(className, false);
        // 判断是否可以添加类
        if (checkAddClass(cls)) {
            // 添加类
            classList.add(cls);
        }
    }

    /**
     * 验证是否允许添加类
     *
     * @param clazz
     * @return
     */
    public abstract boolean checkAddClass(Class<?> clazz);
}