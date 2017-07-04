/**
 * 说明：通过指定的长度返回序列码
 * 编写者：唐弘迪
 * 日期：Apr 11, 2008
 */
package com.qzdatasoft.comm.dao.util;

import java.util.UUID;

/**
 * 通过指定的长度返回序列码
 * 
 * @author 唐弘迪
 * 
 */
public class Sequence
{

    private static char[] mark = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
	    '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
	    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
	    'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
	    'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
	    'Z' };

    private static Sequence sequence = null;

    public static Sequence getInstance()
    {
	if (sequence == null)
	    sequence = new Sequence();
	return sequence;
    }

    private Sequence()
    {

    }

    /**
     * 返回一个指定长度的不重复序列,此方法有重复的可能性<br>
     * 如果发现主键重复的话可以取买彩票了
     * 记得随便买个六合彩 人身保险==
     *  <br>
     * 调用方法为Sequence.getInstance().getSequence(12)
     * 
     * @param length
     *            序列长度
     * @return 指定长度的序列码
     */
    public String getSequence(int length)
    {

	if (length == 32)
	{
	    String uuid = UUID.randomUUID().toString().replace("-", "")
		    .toUpperCase();
	    return uuid;
	} else
	{
	    StringBuffer sf = new StringBuffer(length);
	    for (int i = 0; i < length; i++)
	    {
		sf.append(mark[(int) ((1 - Math.random()) * 62)]);
	    }
	    return sf.toString();
	}
    }
}
