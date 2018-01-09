package com.fangda.maintain.web.domain;

import java.util.List;

public class UserInfosInputDTO extends BaseInputDTO {
	private static final long serialVersionUID = 3066468700840058565L;
	public static final String RETURN_MEMBER_INFOS = "infos";
	private int memberCount = -1;
	private List<MccUserInputDTO> mccUserInfos;

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public List<MccUserInputDTO> getMccUserInfos() {
		return mccUserInfos;
	}

	public void setMccUserInfos(List<MccUserInputDTO> mccUserInfos) {
		this.mccUserInfos = mccUserInfos;
	}

}
