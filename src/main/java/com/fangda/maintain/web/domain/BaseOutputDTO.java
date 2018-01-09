package com.fangda.maintain.web.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fangda.maintain.web.constant.MaintainServiceReturnCode;
import com.fangda.maintain.web.utils.DateUtils;

/**
 * 
 * @author zhangjian559
 *
 */
public class BaseOutputDTO extends BaseParameterDTO {

	private static final long serialVersionUID = 5750566845117908426L;

	/**
	 * 返回结果编码
	 */
	private String code;

	/**
	 * 错误描述
	 */
	private String msg;

	/**
	 * id键值
	 */
	private Long id;

	/**
	 * k/v值
	 */
	private Map<String, Object> data;

	public BaseOutputDTO() {
		this.code = MaintainServiceReturnCode.SUCCESS.getCode();
		this.msg = MaintainServiceReturnCode.SUCCESS.getMsg();
	}

	public BaseOutputDTO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
    /**
     * 对于实体属性比较多的时候可以将整个实体传入自动将非空属性映射到map里
     */
    public void copyPropertiesToMap(Object source){
        if (source == null) {
            return;
        }
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String key = field.getName();
            Object value = null;
            try {
                value = field.get(source);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                if (value instanceof Date) {
                    data.put(key, DateUtils.formatDatetime((Date) value, "yyyy-MM-dd"));
                } else if (value instanceof Timestamp) {
                    data.put(key, DateUtils.formatDatetime((Timestamp) value, "yyyy-MM-dd HH:mm:ss"));
                } else {
                    data.put(key, value);
                }
            }
        }

    }


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(String key, Object value) {
		if (null == data) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}

}
