package com.fangda.maintain.web.domain;

import java.io.Serializable;

import com.fangda.maintain.web.utils.ObjectToStringUtils;

public class BaseParameterDTO implements Serializable {

    private static final long serialVersionUID = 7980928886695392757L;


    /**
     * -----------------------------------------------------------------------------------------
     * 业务公用参数
     * -----------------------------------------------------------------------------------------
     */
    /**
     * 请求方穿过来的transactionUuid ，目的在于系统查看日志的是根据这个transactionUuid可以快速定位所有的日志
     */
    private String transactionUuid;

    public final String getTransactionUuid() {
        return transactionUuid;
    }

    public final void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    @Override
    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    	return ObjectToStringUtils.toStringForBeanObject(this);
    }

	public void printFormattedFieldValue(StringBuilder buffer, String fieldName, Object fieldValue) {
		buffer.append(fieldValue);
	}

}
