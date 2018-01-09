package com.fangda.maintain.web.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class OtherUtils {

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * @param 待验证的字符串
	 * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */
	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		boolean flag = false;
		try {
			// String check =
			// "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			String check = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param 待验证的字符串
	 * @return 如果是符合网址格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */
	public static boolean isHomepage(String str) {
		String regex = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
		return match(regex, str);
	}

	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// oath账号规则
	public static boolean isOathLogin(String str) {
		String temp1 = str.substring(0, 6);
		switch (temp1) {
		case "fb_id:":
			return true;
		case "tw_id:":
			return true;
		case "in_id:":
			return true;
		}
		return false;
	}

	// 手机短信验证码登陆，隐藏字符
	public static boolean isYTST(String str) {
		String temp1 = str.substring(0, 4);
		String temp2 = str.substring(str.length() - 7, str.length());
		if (temp1.equals("ytst") && temp2.equals("smscode")) {
			return true;
		} else {
			return false;
		}
	}
	
	// mc开头的uuid
	public static boolean isMerchant(String uuid){
		boolean flag = false;
		if (uuid.length() == 34 && uuid.substring(0, 2).equals("mc")) {
			flag = true;
		}
		return flag;
	}
	
	// 手机验证码登陆，获取手机号码
	public static String loginMbl(String str) {
		return str.substring(4, str.length() - 7);
	}

	/*
	 * 获取随即数字
	 */
	public static int nextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}

	/**
	 * filename: 相对路径+文件名（不要后缀）
	 */
	public synchronized static String getPropertyFromFile(String filename, String key) {
		ResourceBundle rb = ResourceBundle.getBundle(filename);
		return rb.getString(key).trim();
	}

	/**
	 * 生成订单SN，可逆的 参考:http://blog.csdn.net/lqclh502/article/details/48416777
	 * 
	 * @param orderId
	 * @param longTimeMillis
	 * @return orderSN
	 */

	public static String genOrderSN(int orderId, long longTimeMillis) {
		final String str1 = "400";
		final String str2 = "0";
		final String str3 = "7";
		final String str4 = "5";
		final String str5 = "5";

		int tempOrderId = orderId * 89;
		long longTimeMillis2 = longTimeMillis + tempOrderId;
		// longTimeMillis = longTimeMillis2 * 1000;
		String strTimeMillis = longTimeMillis2 + "";

		String temp1 = strTimeMillis.substring(0, 3);
		String temp2 = strTimeMillis.substring(3, 9);
		String temp3 = strTimeMillis.substring(9, 10);
		String temp4 = strTimeMillis.substring(10, 13);
		String tempStr = str1 + temp4 + str2 + temp3 + str3 + temp2 + str4 + temp1 + str5;

		return tempStr;

	}

	/**
	 * 还原订单id，逆反
	 * 
	 * @param orderSN
	 * @param longTimeMillis
	 * @return orderSN
	 */

	public static int retOrderSN(String orderSN, long longTimeMillis) {
		String orderSNStr = orderSN + "";

		String temp1 = orderSNStr.substring(3, 6);
		String temp2 = orderSNStr.substring(7, 8);
		String temp3 = orderSNStr.substring(9, 15);
		String temp4 = orderSNStr.substring(16, 19);
		String tempStr = temp4 + temp3 + temp2 + temp1;

		long tempLong = Long.parseLong(tempStr);
		long tempLong2 = (tempLong - longTimeMillis) / 89;

		return (int) tempLong2;

	}

	public static String getIpAddr(HttpServletRequest request) throws NullPointerException {
		  String ipAddress = null;
		  // ipAddress = request.getRemoteAddr();
		  ipAddress = request.getHeader("x-forwarded-for");
		  if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		    ipAddress = request.getHeader("Proxy-Client-IP");
		  }
		  if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		    ipAddress = request.getHeader("WL-Proxy-Client-IP");
		  }
		  if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		    ipAddress = request.getRemoteAddr();
		    if (ipAddress.equals("127.0.0.1")) {
		      // 根据网卡取本机配置的IP
		      // InetAddress inet = null;
		      try {
		        InetAddress inet = InetAddress.getLocalHost();
		        ipAddress = inet.getHostAddress();
		      } catch (UnknownHostException e) {
		        e.printStackTrace();
		      }
		    }

		  }

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/*
	 * list->String
	 */
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
}
