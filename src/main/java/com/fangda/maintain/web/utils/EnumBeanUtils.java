package com.fangda.maintain.web.utils;

import org.dozer.DozerBeanMapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumBeanUtils {
	
	/**
	 * 复制原object中的枚举类的value值到目标类中，
	 * @param t
	 * @param c
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static <T>T enumObjectTransfer(Object t,T c) throws NoSuchMethodException{
		Class<?> type = t.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				Class<?> pType = descriptor.getPropertyType();
				String propertyName = descriptor.getName();
				if (pType.isEnum()) {
					Method readMethod = descriptor.getReadMethod();
					//如果是枚举类，从类中取出value方法，如果存在value方法就取其值，更新枚举值为value的值
					Object result = readMethod.invoke(t, new Object[0]);
					//handle only when result is not null
					if(null != result) {
						readMethod = result.getClass().getMethod("value", null);
						if(readMethod != null){
							result = readMethod.invoke(result, null);
							Field f = hasField(c.getClass(),propertyName);
							if(null != f){
//								Field f = c.getClass().getDeclaredField(propertyName);
								f.setAccessible(true);
								f.set(c, result);
							}
						}
					}
				}
			}
		} catch (IntrospectionException |  SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
			
		return c;
	}
		
	private static Field hasField(Class c,String name){
		Field[] fields = c.getDeclaredFields();
		List<Field> allField = new ArrayList<Field>();
		allField.addAll(Arrays.asList(fields));
		Class t = c;
		//解决子类继承父类无法找到私有属性问题
		while(t.getSuperclass() != Object.class){
			t = t.getSuperclass();
			allField.addAll(Arrays.asList(t.getDeclaredFields()));
		}
		for(Field f:allField){
			if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}
	
	/**
	 * 实例类的属性值复制，枚举类直接使用value方法的值
	 * @param t
	 * @param c
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static <T>T beanCopy(Object t,T c){
		try{
//			BeanUtils.copyProperties(t, c);
			
			DozerBeanMapper mapper = new DozerBeanMapper();
			List<String> myMappingFiles = new ArrayList<String>();
			myMappingFiles.add("dozerBeanMapping.xml");
			mapper.setMappingFiles(myMappingFiles);
			mapper.map(t, c);
			
			enumObjectTransfer(t,c);			
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
		return c;
	}
	
	public static <T>T beanCopy(Object t,Class<T> clazz){
		try{
			T c = clazz.newInstance();
			return beanCopy(t,c);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
}
