package com.qzdatasoft.comm.dao.util;

/**
 * 类型转变
 */
import java.util.Date;

public class TypeUtil {

	public static Object cast(String value, Class propertyType) {

		String className = convertType(propertyType);
		return cast(className, value);
	}

	private static Object cast(String className, String value) {

		if (className.equals("string")) {
			return value;
		} else if (className.equals("bool")) {
			return Boolean.valueOf(value);
		} else if (className.equals("date")) {
			return new Date(value);
		} else if (className.equals("short")) {
			return Short.valueOf(value);
		} else if (className.equals("int")) {
			return Integer.valueOf(value);
		} else if (className.equals("long")) {
			return Long.valueOf(value);
		} else if (className.equals("float")) {
			return Float.valueOf(value);
		} else if (className.equals("double")) {
			return Double.valueOf(value);
		} else if (className.equals("byte")) {
			return Byte.valueOf(value);
		} else if (className.equals("char")) {
			return new Character(value.charAt(0));
		} else {
			throw new RuntimeException("[" + className + "," + value + "类型转化错误");
		}

	}

	public static String convertType(Class clazz) {

		if (clazz.isPrimitive()) {
			if (clazz.equals(Boolean.TYPE))
				return "bool";
			else {

				String classname = clazz.getName();

				int pos = classname.lastIndexOf(".");

				return classname.substring(pos + 1);

			}
		}

		String local_name = clazz.getName();
		String common_name = null;
		if (local_name.equals("java.lang.String")) {
			common_name = "string";
		} else if (local_name.equals("java.lang.Boolean")) {
			common_name = "bool";
		} else if (local_name.equals("java.util.Date")) {
			common_name = "date";
		} else if (local_name.equals("java.lang.Short")) {
			common_name = "short";
		} else if (local_name.equals("java.lang.Integer")) {
			common_name = "int";
		} else if (local_name.equals("java.lang.Long")) {
			common_name = "long";
		} else if (local_name.equals("java.lang.Float")) {
			common_name = "float";
		} else if (local_name.equals("java.lang.Double")) {
			common_name = "double";
		} else if (local_name.equals("java.lang.Byte")) {
			common_name = "byte";
		} else if (local_name.equals("java.lang.Character")) {
			common_name = "char";
		} else if (local_name.equals("java.lang.Object")) {
			common_name = "object";
		} else {
			common_name = clazz.getName();
		}

		return common_name;
	}

}
