package com.qzdatasoft.comm.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import oracle.sql.CLOB;


public class ConvertUtils
{
    /**
     * 把源对象转换为String
     * @param o 源对象
     * @param defValue 默认值,转换失败将返回该值
     * @return 转换后的String值,如果失败将返回默认值
     */
    public static String toString(Object o, String defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            return o.toString();
        }
        catch (Exception e)
        {
            return defValue;
        }
    }
    
    /**
     * 把源对象转换为String
     * @param o 源对象
     * @return 转换后的String值,如果失败将返回空串
     */
    public static String toString(Object o)
    {
        return toString(o, "");
    }
    
    /**
     * 把源对象转换为double
     * @param o 源对象
     * @param defValue 默认值,转换失败将返回该值
     * @return 转换后的double值,如果失败将返回默认值
     */
    public static double toDouble(Object o, double defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            if (Boolean.class.isInstance(o))
            {
                boolean b = ((Boolean) o).booleanValue();
                return (b ? 1.0 : 0.0);   // 如果是bool值,true返回1,false返回0
            }
            
            return Double.parseDouble(o.toString());
        }
        catch (NumberFormatException e)
        {
            return defValue;
        }
    }
    
    /**
     * 把源对象转换为double
     * @param o 源对象
     * @return 转换后的double值,如果失败将返回0
     */
    public static double toDouble(Object o)
    {
        return toDouble(o, 0.0);
    }
    
    /**
     * 把源对象转换为float
     * @param o 源对象
     * @param defValue 默认值,转换失败将返回该值
     * @return 转换后的float值,如果失败将返回默认值
     */
    public static float toFloat(Object o, float defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        try
        {
            if (Boolean.class.isInstance(o))
            {
                boolean b = ((Boolean) o).booleanValue();
                return (b ? 1.0f : 0.0f);   // 如果是bool值,true返回1,false返回0
            }
            
            return Float.parseFloat(o.toString());
        }
        catch (NumberFormatException e)
        {
            return defValue;
        }
    }
    
    /**
     * 把源对象转换为float
     * @param o 源对象
     * @return 转换后的float值,如果失败将返回0
     */
    public static float toFloat(Object o)
    {
        return toFloat(o, 0.0f);
    }
    
    /**
     * 把源对象转换为int
     * @param o 源对象
     * @param defValue 默认值,转换失败将返回该值
     * @return 转换后的int值,如果失败将返回默认值
     */
    public static int toInt(Object o, int defValue) {
        if (null == o) {
            return defValue;
        }
        
        double dbValue = ConvertUtils.toDouble(o);
        return (int) dbValue;
    }
    
    /**
     * 把源对象转换为int
     * @param o 源对象
     * @return 转换后的int值,如果失败将返回默认值0
     */
    public static int toInt(Object o)
    {
        return toInt(o, 0);
    }
    
    /**
     * 把源对象转成bool值
     * @param o 源对象
     * @param defValue 如果源对象为空,则返回此默认值
     * @return 转换将根据以下规则进行转换
     *         如果转成数字不为0,则返回true,否则转成字符串再进行比较,
     *         如果字符串的值为true(忽略大小写),则返回true,否则返回false
     */
    public static boolean toBool(Object o, boolean defValue)
    {
        if (null == o)
        {
            return defValue;
        }
        
        int intValue = ConvertUtils.toInt(o);
        if (intValue != 0)
        {
            return true;
        }
        
        String strValue = ConvertUtils.toString(o);
        return Boolean.parseBoolean(strValue);
    }
    
    /**
     * 把源对象转成bool值
     * @param o 源对象
     * @return 转换将根据以下规则进行转换
     *         如果源对象为null,则返回false,否则:
     *         如果转成数字不为0,则返回true,否则转成字符串再进行比较,
     *         如果字符串的值为true(忽略大小写),则返回true,否则返回false
     */
    public static boolean toBool(Object o)
    {
        return toBool(o, false);
    }
    
    /**
     * 把字符串转换成数字字符串,比如.7将转换成0.7
     * @param o 源字符串
     * @param defaultVal 默认值
     * @param retSrcString 如果为true,当转换失败时将返回源字符串,否则返回默认值
     * @return
     */
    public static String toNumberString(String o, String defaultVal, boolean retSrcString)
    {
        try
        {
            if (null == o)
            {
                if (retSrcString) return o;
                else return defaultVal;
            }
            
            return toString(Double.parseDouble(o));
        }
        catch(NumberFormatException e)
        {
            if (retSrcString) return o;
            else return defaultVal;
        }
    }
    
    
    /**
     * 传人学年学期，转换为分区查询字符串
     * 
     * @param xnxq01id
     * @return
     */
    public static String toFqcxToString(String xnxq01id){
        if(xnxq01id==null || "".equals(xnxq01id)){
            return "";
        }
        return "partition(p"+xnxq01id.replaceAll("-","_")+")";
    }
    
    /**
     * 把字符串转换成数字字符串,比如.7将转换成0.7
     * @param o 源字符串
     * @return 转换后的字符串,如果失败将返回源字符串
     */
    public static String toNumberString(String o)
    {
        return toNumberString(o, "", true);
    }
    
    public static void main(String[] args)
    {
        System.out.println(toInt("23s"));
    }
    
    public static String clobToString(CLOB clob) throws SQLException, IOException {
	     String reString = "";
	     if( clob == null || clob.getCharacterOutputStream() == null )
	      return "";
	     Reader is = clob.getCharacterStream();// 得到流
	     BufferedReader br = new BufferedReader(is);
	     String s = br.readLine();
	     StringBuffer sb = new StringBuffer();
	     while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	      sb.append(s);
	      s = br.readLine();
	     }
	     reString = sb.toString();
	     return reString;
	    }
}
