package com.fangda.maintain.web.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.fangda.maintain.web.domain.BaseParameterDTO;

public abstract class ObjectToStringUtils {

	/**
	 * 打印实体类的属性及属性值
	 * 
	 * @return
	 */
	public static String toStringForBeanObject(final BaseParameterDTO beanObject) {
		if (null == beanObject) {
			return "<null>";
		}
		final Class<?> topBeanClass = beanObject.getClass();
		StringBuilder sb = new StringBuilder();
        sb.append(topBeanClass.getSimpleName());
        sb.append(" [");
        
		Class<?> clazz = beanObject.getClass();
		appendFieldsIn(clazz, beanObject, sb);
		while (clazz.getSuperclass() != null && clazz.getSuperclass() != topBeanClass) {
			clazz = clazz.getSuperclass();
			appendFieldsIn(clazz, beanObject, sb);
		}
		
		sb.append("]");
		return sb.toString();
	}

	/**
	 * <p>
	 * Appends the fields and values defined by the given object of the given
	 * Class.
	 * </p>
	 *
	 * <p>
	 * If a cycle is detected as an object is &quot;toString()'ed&quot;, such an
	 * object is rendered as if <code>Object.toString()</code> had been called
	 * and not implemented by the object.
	 * </p>
	 *
	 * @param clazz
	 *            The class of object parameter
	 */
	private static void appendFieldsIn(final Class<?> clazz, BaseParameterDTO bean, StringBuilder buffer) {

		final Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (final Field field : fields) {
			final String fieldName = field.getName();
			if (accept(field)) {
				try {
					// Warning: Field.get(Object) creates wrappers objects
					// for primitive types.
					final Object fieldValue = field.get(bean);

					if (fieldName != null) {
						buffer.append(fieldName);
						buffer.append("=");
					}
					if (fieldValue == null) {
						buffer.append("<null>");

					} else {
						//
						addFieldValue(buffer, fieldName, fieldValue, bean);
					}

					buffer.append(",");
				} catch (final IllegalAccessException ex) {
					// this can't happen. Would get a Security exception
					// instead
					// throw a runtime exception in case the impossible
					// happens.
					throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
				}
			}
		}
	}

	/**
	 * 此处可以做复杂的数据类型判断,子类可以继承此方法做复杂的业务逻辑处理，在对fieldValue值类型解析后加入到buffer中，以下示例可参考：
	 * if(fieldName.equals("xxx"){ SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	 * buffer.append(sdf.format(fieldValue); }else{ buffer.append(fieldValue); }
	 * 
	 * @param buffer
	 * @param fieldName
	 * @param fieldValue
	 */
	private static void addFieldValue(StringBuilder buffer, String fieldName, Object fieldValue,BaseParameterDTO bean) {
		bean.printFormattedFieldValue(buffer,fieldName,fieldValue);
	}

	private static boolean accept(final Field field) {
		if (field.getName().indexOf('$') != -1) {
			// Reject field from inner class.
			return false;
		}
		if (Modifier.isTransient(field.getModifiers())) {
			// Reject transient fields.
			return false;
		}
		if (Modifier.isStatic(field.getModifiers())) {
			// Reject static fields.
			return false;
		}
		return true;
	}
}
