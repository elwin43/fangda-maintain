package com.fangda.maintain.web.service;

import com.fangda.maintain.web.domain.BaseOutputDTO;

public interface ImageValidateService {

	public void delImage(String uuid);

	public void setImage(String uuid, String code);

	public BaseOutputDTO getImage(String uuid);

}
