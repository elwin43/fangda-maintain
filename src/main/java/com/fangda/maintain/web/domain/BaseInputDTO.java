package com.fangda.maintain.web.domain;

import com.fangda.maintain.web.utils.StringUtils;
import com.fangda.maintain.web.utils.TidManager;

/**
 * DESCRIPTION : api model request base java
 */
public class BaseInputDTO extends BaseParameterDTO {

    private static final long serialVersionUID = 5608922115404956030L;

	private OpenApiParams openApiParams;

	/**
	 * 请求方系统编码
	 */
	protected String sysId;
	
	private Long baseUserId;
	
	private String baseUserName;

    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public OpenApiParams getOpenApiParams() {
		return openApiParams;
	}

	public void setOpenApiParams(OpenApiParams openApiParams) {
		this.openApiParams = openApiParams;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public Long getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(Long baseUserId) {
		this.baseUserId = baseUserId;
	}

	public String getBaseUserName() {
		return baseUserName;
	}

	public void setBaseUserName(String baseUserName) {
		this.baseUserName = baseUserName;
	}

	public BaseInputDTO(){
        if(StringUtils.isBlank(this.getTransactionUuid())){
            this.setTransactionUuid(TidManager.getTransactionUuid());
        }
    }

}
