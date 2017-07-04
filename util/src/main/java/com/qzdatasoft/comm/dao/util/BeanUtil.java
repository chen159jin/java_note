/**
 * 说明：Bean操作工具类
 * 编写者：谢平
 * 日期：Nov 20, 2007
 *      Dec 10,2007  改进getProperty掷出异常的方法
 * 湖南强智科技版权所有。
 * 2008-04-01 解决属性不存在错误问题
 * 			  增加30属性，set类型处理
 * 			  增加对非POJO对象中取值支持
 * 			  31 是否同意
 * 			  32 是否审核（批）通过
 * 			  33 0 院系未批 1院系通过 2 领导未批 3 领导通过
 * 			  40 用户类型
 * 2008-04-19 修改属性取值异常问题
 */
package com.qzdatasoft.comm.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.hibernate.collection.PersistentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title:Bean操作工具类
 * <p>
 * Description:Bean操作工具类，请使用这个工具类完成Bean操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 湖南强智科技发展有限公司
 * </p>
 * 
 */
public class BeanUtil
{
    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 拷贝Bean属性
     * 
     * @param target
     * @param source
     */
    public static void copyProperties(Object target, Object source)
    {
	try
	{
	    BeanUtils.copyProperties(target, source);
	} catch (Exception e)
	{
	    log.error(e.getMessage());
	}
    }

    private static String[] BOOLEANCN = { "否", "是" };

    /**
     * 帐号类型
     */
    private static String[] ACCOUNTTYPE = { "系统管理", "教职工", "学生" };

    /**
     * 院系审批类型
     */
    private static String[] YXSPTYPE = { "院系未批", "院系通过", "领导未批", "领导通过" };

    /**
     * 同意类型
     */
    private static String[] TONGYITYPE = { "不同意", "同意" };

    /**
     * 审批类型
     */
    private static String[] SHENGPITYPE = { "未通过", "通过", "未审" };

    /**
     * 计划状态 （zt）
     */
    private static String[] ZHIDAOJIHUATYPE = { "未送审", "已送审", "返回修改",
	    "审核通过，送往审批", "审批通过" };

    /**
     * 审核状态 （shzt）
     */
    private static String[] SHENHEZHUANGTAITYPE = { "未送审", "审核不通过", "审核通过",
	    "审核" };

    /**
     * 审批状态（spzt）
     */
    private static String[] SHENPIZHUANGTAI = { "未送审", "审批不通过", "审批通过", "审批" };

    /**
     * 学期标志(xqbz)
     */
    private static String[] XUEQIBIAOZHI = { "过往学期", "当前学年", "排课学期" };

    /**
     * 返回该对象的值
     * 
     * @param opObj
     *            源对象
     * @param objProperty
     *            对象属性
     * @param propertyType
     *            类型 <br>
     *            0 数字 <br>
     *            1 字符 <br>
     *            2 布尔型(是否)，把0，1转换成是，否 <br>
     *            10 日期yyyy-MM-dd <br>
     *            11 日期加时间yyyy-MM-dd HH:mm:ss <br>
     *            12 年月 yyyyMM <br>
     *            13 年月日 yyyyMMdd <br>
     *            14 年月日时分秒 yyyyMMddhhmmss <br>
     *            20 钱币2位小数 <br>
     *            30 集合类型<br>
     *            31 是否同意<br>
     *            32 是否审核（批）通过<br>
     *            33 院系未批 1院系通过 2 领导未批 3 领导通过<br>
     *            34 计划状态 （zt）<br>
     *            35 审核状态 （shzt）<br>
     *            36 审批状态（spzt）<br>
     *            37 学期标志(xqbz)<br>
     *            40 用户类型
     * @return Object 取值对象
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getProperty(Object opObj, String objProperty,
	    String propertyType)
    {
	try
	{
	    if (propertyType.equals("99"))
		return objProperty;
	    // 判断是否是多对象返回
	    if (opObj.getClass().isArray())
	    {
		Object[] objs = (Object[]) opObj;
		if (objProperty.indexOf(".") <= 0)
		    return getObjPropertyByType(objs[Integer
			    .parseInt(objProperty)], propertyType);
		else
		    return getObjPropertyByType(PropertyUtils.getProperty(
			    objs[Integer.parseInt(objProperty.substring(0,
				    objProperty.indexOf(".")))], objProperty
				    .substring(objProperty.indexOf(".") + 1)),
			    propertyType);
	    } else
		return getObjPropertyByType(PropertyUtils.getProperty(opObj,
			objProperty), propertyType);
	} catch (Exception e)
	{
	    // log.error(opObj.toString() + "对象取值异常：" + objProperty + "属性并不存在");
	    // e.printStackTrace();
	    return "";
	}
    }

    /**
     * 返回该对象的值
     * 
     * @param opObj
     * @param objProperty
     * @return Object 取值对象
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getProperty(Object opObj, String objProperty)
    {
	try
	{
	    // 判断是否是多对象返回
	    if (opObj.getClass().isArray())
	    {
		Object[] objs = (Object[]) opObj;
		// 如果不是点击符，就应该是一个序号，例如0，1，2
		if (objProperty.indexOf(".") <= 0)
		    return objs[Integer.parseInt(objProperty)];
		// 如果是点击符，就取这个对象的属性
		else
		    return PropertyUtils.getProperty(objs[Integer
			    .parseInt(objProperty.substring(0, objProperty
				    .indexOf(".")))], objProperty
			    .substring(objProperty.indexOf(".") + 1));
	    } else
		return PropertyUtils.getProperty(opObj, objProperty);
	} catch (Exception e)
	{
	    // log.error(opObj.toString() + "对象取值异常：" + objProperty + "属性并不存在");
	    // e.printStackTrace();
	    return "";
	}
    }

    /**
     * 对象赋值
     * 
     * @param opObj
     *            操作对象
     * @param objProperty
     *            对象的属性
     * @param valueObject
     *            值对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void setProperty(Object opObj, String objProperty,
	    Object valueObject) throws IllegalAccessException,
	    InvocationTargetException, NoSuchMethodException
    {
	// 判断是否是多对象返回
	if (opObj.getClass().isArray())
	{
	    Object[] objs = (Object[]) opObj;
	    // 获得这个对象的CLASSTYPE，并与设置的属性第一个字段比较
	    if (objProperty.indexOf(".") > 0)
		BeanUtils.setProperty(objs[Integer.parseInt(objProperty
			.substring(0, objProperty.indexOf(".")))], objProperty
			.substring(objProperty.indexOf(".") + 1), valueObject);
	} else
	{
	    BeanUtils.setProperty(opObj, objProperty, valueObject);
	}
    }

    /**
     * 将ActionForm的属性值复制到Model
     * 
     * @param actionForm
     * @param model
     */
    public static void actionFormToModel(ActionForm actionForm, Object model)
    {
	Class modelClass = model.getClass();
	Class afClass = actionForm.getClass();
	try
	{
	    Method[] modelMethod = modelClass.getMethods();
	    for (int i = 0; i < modelMethod.length; i++)
	    {
		String setMethodName = modelMethod[i].getName();
		if (setMethodName.indexOf("set") == 0)
		{
		    String getMethodName = setMethodName.replaceFirst("set",
			    "get");
		    Object value[] = { afClass.getMethod(getMethodName, null)
			    .invoke(actionForm, null) };
		    modelMethod[i].invoke(model, value);
		}
	    }
	} catch (Exception e)
	{
	    log.error(actionForm.getClass() + "对象拷贝异常" + e.getMessage());
	}
    }

    /**
     * 将Model的属性值复制到ActionForm
     * 
     * @param actionForm
     * @param model
     */
    public static void modelToActionForm(Object model, ActionForm actionForm)
    {
	Class modelClass = model.getClass();
	Class afClass = actionForm.getClass();
	try
	{
	    Method[] modelMethod = modelClass.getMethods();
	    for (int i = 0; i < modelMethod.length; i++)
	    {
		String setMethodName = modelMethod[i].getName();

		if (setMethodName.indexOf("set") == 0)
		{
		    String getMethodName = setMethodName.replaceFirst("set",
			    "get");
		    Object value[] = { modelClass
			    .getMethod(getMethodName, null).invoke(model, null) };
		    Class type[] = { modelClass.getMethod(getMethodName, null)
			    .getReturnType() };

		    afClass.getMethod(setMethodName, type).invoke(actionForm,
			    value);
		}
	    }
	} catch (Exception e)
	{
	    log.error(actionForm.getClass() + "对象拷贝异常" + e.getMessage());
	}
    }

    /**
     * 将一个序列化的对象缓存成文件
     * 
     * @param f
     *            保存的文件路径
     * @param obj
     *            对象
     * @throws Exception
     */
    public static void serializableObj(String f, Object obj) throws Exception
    {
	File file = new File(f);
	FileOutputStream os = new FileOutputStream(file);
	ObjectOutputStream oos = new ObjectOutputStream(os);
	oos.writeObject(obj);
	oos.close();
	os.close();
    }

    /**
     * 将一个文件转换成一个对象
     * 
     * @param f
     *            文件路径
     * @return Object
     * @throws Exception
     */
    public static Object unSerializableObj(String f) throws Exception
    {
	File file = new File(f);
	FileInputStream is = new FileInputStream(file);
	ObjectInputStream ois = new ObjectInputStream(is);
	Object obj = ois.readObject();
	ois.close();
	is.close();
	return obj;
    }

    /**
     * 格式化字符串
     * 
     * @param o
     * @param propertyType
     * @return
     */
    private static Object getObjPropertyByType(Object o, String propertyType)
	    throws Exception
    {
	switch (Integer.parseInt(propertyType))
	{
	    case 0:// 数字
	    {
		if (o == null)
		    return "";
		return o;
	    }
	    case 1:// 字符
	    {
		if (o == null)
		    return "";
		return o;
	    }
	    case 2:// 布尔型
	    {
		if (o == null)
		    return BOOLEANCN[0];
		else
		    return BOOLEANCN[Integer.parseInt(o.toString())];
	    }
	    case 10:// 日期yyyy-MM-dd
		return StringUtils.formatDateByFormatStr(o, "yyyy-MM-dd");
	    case 11:// 日期加时间yyyy-MM-dd HH:mm:ss
		return StringUtils.formatDateByFormatStr(o,
			"yyyy-MM-dd HH:mm:ss");
	    case 12:// 年月 yyyyMM
		return StringUtils.formatDateByFormatStr(o, "yyyyMM");
	    case 13:// 年月日 yyyyMMdd
		return StringUtils.formatDateByFormatStr(o, "yyyyMMdd");
	    case 14:// 年月日时分秒 yyyyMMddhhmmss
		return StringUtils.formatDateByFormatStr(o, "yyyyMMddhhmmss");
	    case 20:// 货币2位小数
		return StringUtils.formatDouble(o, "#.00");
	    case 30:// 集合类型
	    {
		if (o == null)
		    return "";
		if (o.getClass().toString().indexOf("PersistentSet") > 0)
		{
		    try
		    {
			PersistentSet set = (PersistentSet) o;
			if (set.isEmpty())
			    return "";
			else
			{
			    StringBuffer sb = new StringBuffer();
			    Iterator it = set.iterator();
			    while (it.hasNext())
			    {
				if (sb.length() <= 0)
				    sb.append(it.next().toString());
				else
				    sb.append(",").append(it.next().toString());
			    }
			    return sb.toString();
			}
		    } catch (Exception ex)
		    {
			ex.printStackTrace();
		    }
		}
		return "";
	    }
	    case 31: // 是否同意
	    {
		if (o == null)
		    return TONGYITYPE[0];
		else
		    return TONGYITYPE[Integer.parseInt(o.toString())];
	    }
	    case 32: // 是否审核（批）通过
	    {
		if (o == null)
		    return SHENGPITYPE[2];
		else
		    return SHENGPITYPE[Integer.parseInt(o.toString())];
	    }
	    case 33: // 0 院系未批 1院系通过 2 领导未批 3 领导通过
	    {
		if (o == null)
		    return YXSPTYPE[0];
		else
		    return YXSPTYPE[Integer.parseInt(o.toString())];
	    }
	    case 34: // 计划状态 （zt）
	    {
		if (o == null)
		    return ZHIDAOJIHUATYPE[0];
		else
		    return ZHIDAOJIHUATYPE[Integer.parseInt(o.toString())];
	    }
	    case 35: // 审核状态 （shzt）
	    {
		if (o == null)
		    return SHENHEZHUANGTAITYPE[0];
		else
		    return SHENHEZHUANGTAITYPE[Integer.parseInt(o.toString())];
	    }
	    case 36: // 审批状态（spzt）
	    {
		if (o == null)
		    return SHENPIZHUANGTAI[0];
		else
		    return SHENPIZHUANGTAI[Integer.parseInt(o.toString())];
	    }
	    case 37: // 学期标志(xqbz)
	    {
		if (o == null)
		    return XUEQIBIAOZHI[0];
		else
		    return XUEQIBIAOZHI[Integer.parseInt(o.toString())];
	    }
	    case 40://
	    {
		if (o == null)
		    return ACCOUNTTYPE[0];
		else
		    return ACCOUNTTYPE[Integer.parseInt(o.toString())];
	    }
	}

	return "";
    }

    /**
     * 获得一个类的所有属性
     * 
     * @param className
     * @return List
     * @throws ClassNotFoundException
     */
    public static List getClassAllProperty(String className)
	    throws ClassNotFoundException
    {
	Class c = Class.forName(className);
	Field[] f = c.getDeclaredFields();
	List ls = new ArrayList();
	for (int i = 0; i < f.length; i++)
	{
	    if (f[i].getType().getName().length() > 10)
		ls.add(f[i].getType().getName());
	}
	return ls;
    }
}
