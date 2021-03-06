package com.khlud.ciprian.flatcollection.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectionResolver {

    public List<String> Imports = new ArrayList<>();

    public Map<String, Class<?>> FixedTypeNames = new HashMap<>();
    public Map<String, Class<?>> FixedTypes = new HashMap<>();

    public ReflectionResolver(List<String> imports) {
        for (String imprt : imports) {
            if (imprt.endsWith(".*")) {
                String importSubstracted = imprt.substring(0, imprt.length() - 2);
                Imports.add(importSubstracted);
            } else {
                Class clazz = null;
                try {
                    clazz = Class.forName(imprt);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                FixedTypes.put(imprt, clazz);
                String fixedTypeName = StringUtils.substringAfterLast(imprt, ".");
                FixedTypeNames.put(fixedTypeName, clazz);
            }
        }

    }

    public boolean hasDefaultConstructor(Class<?> clazz) {
        Constructor[] allConstructors = clazz.getDeclaredConstructors();
        for (Constructor ctor : allConstructors) {
            if (ctor.getParameterCount() == 0) {
                String modifierString = Modifier.toString(ctor.getModifiers());
                if (modifierString.contains("public")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Constructor firstPublicConstructor(Class<?> clazz) {
        Constructor[] allConstructors = clazz.getDeclaredConstructors();
        for (Constructor ctor : allConstructors) {
            String modifierString = Modifier.toString(ctor.getModifiers());
            if (modifierString.contains("public")) {
                return ctor;
            }
        }
        return null;
    }

    public Class<?> resolve(String typeName) {
        if (FixedTypes.containsKey(typeName)) {
            return FixedTypes.get(typeName);
        }
        if (FixedTypeNames.containsKey(typeName)) {
            return FixedTypeNames.get(typeName);
        }

        for (String imprt : Imports) {
            String baseName = imprt + "." + typeName;
            Class<?> clazz = getClassByFullName(baseName);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    public Class<?> getClassByFullName(String baseName) {
        try {
            Class clazz = ClassLoader.getSystemClassLoader().loadClass(baseName);
            return clazz;
        } catch (Exception ex) {
        }
        return null;
    }

    public Method getMethod(Class<?> clz, String name, Optional<Integer> paramCount) {
        Method[] methods = clz.getMethods();
        int paramenterCount = paramCount.isPresent() ? paramCount.get() : -1;
        for (Method mth : methods) {
            int methodparameterCount = mth.getParameterCount();
            if ((paramenterCount != -1) && (methodparameterCount == paramenterCount)) {
                String methodName = mth.getName();
                if (methodName.equals(name)) {
                    return mth;
                }
            }
        }
        return null;
    }

    public Method resolveClassProperty(Class<?> clz, String name, boolean isSetter) {
        Method mth;
        if (isSetter) {
            mth = getMethod(clz, "set" + StringUtils.indent(name), Optional.of(1));
        } else {
            mth = getMethod(clz, "get" + StringUtils.indent(name), Optional.of(0));
        }
        return mth;
    }
}
