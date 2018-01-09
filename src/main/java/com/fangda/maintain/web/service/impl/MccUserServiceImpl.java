package com.fangda.maintain.web.service.impl;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.RedisConstant;
import com.fangda.maintain.web.dao.MccUserDao;
import com.fangda.maintain.web.dao.mapper.MccUserMapper;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.MccUserInfoDTO;
import com.fangda.maintain.web.domain.MccUserInputDTO;
import com.fangda.maintain.web.domain.UserLoginInputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.MccUser;
import com.fangda.maintain.web.service.MccUserCacheService;
import com.fangda.maintain.web.service.MccUserService;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import com.fangda.maintain.web.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.joda.time.DateTime;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class MccUserServiceImpl implements MccUserService {
	private static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MccUserServiceImpl.class);
	@Autowired
	RedisUtils redisUtils;

	@Autowired
	MccUserCacheService mccUserCacheService;

	@Autowired
	MccUserDao mccUserDao;

	@Autowired
	MccUserMapper mccUserMapper;

	@Override
	public boolean isUserExist(Long userId) {
		boolean flag = false;
		try {
			MccUser mccUser = mccUserMapper.selectByPrimaryKey(userId);
			if (mccUser != null) {
				flag = true;
			}
		} catch (MyBatisSystemException ex) {
			LOGGER.info("NOEXIST_ERROR:userId" + userId);
		} catch (Exception e) {
			LOGGER.info("SYSTEM_ERROR:userId" + userId);
			e.printStackTrace();
		}
		return flag;
	}

	public BaseOutputDTO isUserExist(MccUserInputDTO input) {
		// 校验：是否存在用户名／邮箱／手机号和密码
		if (null == input || (StringUtils.isBlank(input.getUserName()) & StringUtils.isBlank(input.getUserEml())
				& StringUtils.isBlank(input.getUserMbl()))) {
			throw new MaintainServiceException(ErrorCode.DATA_EMPTY_ERROR.getCode(), ErrorCode.DATA_EMPTY_ERROR.getMsg());
		}
		// 初始化
		BaseOutputDTO outputDTO = new BaseOutputDTO();
		MccUser mccUser = new MccUser();
		boolean flag = false;
		try {
			// 入参
			EnumBeanUtils.beanCopy(input, mccUser);
			MccUser mccUserResult = mccUserDao.selectByUserName(mccUser.getUserName());
			if (mccUserResult != null) {
				flag = true;
				outputDTO.setData("userId", mccUserResult.getUserId());
			}
			// 出参
			outputDTO.setData("flag", flag);
			LOGGER.info("账号是否存在：" + (flag == true ? "存在":"不存在"));
		} catch (Exception e) {
//			outputDTO.setCode(MerchantServiceReturnCode.ERR_APPLICATION_OPER_ERROR.getCode());
//			outputDTO.setMsg(MerchantServiceReturnCode.ERR_APPLICATION_OPER_ERROR.getMsg());
			LOGGER.error("",e);
			throw new RuntimeException(e.getMessage());
		}
		return outputDTO;
	}

	@Override
	public MccUserInfoDTO getUserBaseInfo(Long userId) {

		MccUserInfoDTO userInfoDTO = new MccUserInfoDTO();
		// 用户已存在
		if (isUserExist(userId)) {

			MccUser mccUser = mccUserMapper.selectByPrimaryKey(userId);

			EnumBeanUtils.beanCopy(mccUser,userInfoDTO);

		}
		return userInfoDTO;
	}

	@Override
	public MccUserInfoDTO getUserBaseInfoByName(String userName) {
		MccUserInfoDTO userInfoDTO = new MccUserInfoDTO();

		MccUser mccUser = mccUserDao.selectByUserName(userName);

		EnumBeanUtils.beanCopy(userInfoDTO,mccUser);

		return userInfoDTO;
	}

	@Override
	public BaseOutputDTO resetPassword(Long userId) {
		// 初始化
		DateTime nowTime = DateTime.now();
		Date currentDate = nowTime.toDate();
		BaseOutputDTO output = new BaseOutputDTO();
		MccUser mccUser = new MccUser();
		boolean updateFlag = false;
		int i = 0;

		try {
			// 是否已存在
			if (!this.isUserExist(userId)) {
				output.setData("isUserExist", "false");
				return output;
			}
			mccUser.setUserId(userId);
			mccUser.setLgnPwd(new Md5Hash("Hrt11111").toHex());
			i = mccUserMapper.updateByPrimaryKeySelective(mccUser);

			if (i == 1) {
				updateFlag = true;
			}

			String redisUserIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,
					String.valueOf(userId));
			if (redisUtils.exists(redisUserIdKey)) {
				String oldToken = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,String.valueOf(userId));
				mccUserCacheService.delUserTokenPhone(oldToken);
			}
			output.setData("updateFlag", updateFlag);
			if(!updateFlag){
				output.setData("失败", "msg");
			}
		} catch (Exception e) {
			LOGGER.info("mccUserService,resetPassword,SYSTEM_ERROR:userId" + userId);
			e.printStackTrace();
		}
		return output;

	}

	@Override
	public BaseOutputDTO updatePassword(UserLoginInputDTO input) {
		// 初始化
		DateTime nowTime = DateTime.now();
		Date currentDate = nowTime.toDate();
		BaseOutputDTO output = new BaseOutputDTO();
		MccUser mccUser2 = new MccUser();
		boolean updateFlag = false;
		int i = 0;

		Long userId = input.getUserId();
		String passwordNew = input.getPassWord();
		String passwordOld = input.getPassWordOld();

		try {
			// 是否已存在
			if (!this.isUserExist(userId)) {
				output.setData("isUserExist", "false");
				return output;
			}
			MccUser mccUser = mccUserMapper.selectByPrimaryKey(userId);
			if (mccUser.getLgnPwd().equals(passwordOld)) {
				mccUser2.setUserId(userId);
				mccUser2.setLgnPwd(passwordNew);
				i = mccUserMapper.updateByPrimaryKeySelective(mccUser2);

				if (i == 1) {
					updateFlag = true;
				}
			}
			String redisUserIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,
					String.valueOf(userId));
			if (redisUtils.exists(redisUserIdKey)) {
				String oldToken = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,String.valueOf(userId));
				mccUserCacheService.delUserTokenPhone(oldToken);
			}
			output.setData("updateFlag", updateFlag);
		} catch (Exception e) {
			LOGGER.info("mccUserService,updatePassword,SYSTEM_ERROR:userId" + userId);
			e.printStackTrace();
		}
		return output;

	}

	@Override
	public BaseOutputDTO updateUserStatus(Long userId, byte status) {
		// 初始化
		DateTime nowTime = DateTime.now();
		Date currentDate = nowTime.toDate();
		BaseOutputDTO output = new BaseOutputDTO();
		MccUser mccUser = new MccUser();
		boolean updateFlag = false;
		int i = 0;

		try {
			// 是否已存在
			if (!this.isUserExist(userId)) {
				output.setData("isUserExist", "false");
				return output;
			}
			mccUser.setUserId(userId);
			i = mccUserMapper.updateByPrimaryKeySelective(mccUser);

			if (i == 1) {
				updateFlag = true;
			}

			output.setData("updateFlag", updateFlag);
			String redisUserIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,
					String.valueOf(userId));
			// 删除token
			if ((status + "").equals("1") && redisUtils.exists(redisUserIdKey)) {
				String oldToken = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,String.valueOf(userId));
				mccUserCacheService.delUserTokenPhone(oldToken);
			}
		} catch (Exception e) {
			LOGGER.info("mccUserService,updateUserStatus,SYSTEM_ERROR:userId" + userId);
			e.printStackTrace();
		}
		return output;
	}

	@Override
	public BaseOutputDTO updateUser(MccUser input) {
		// 初始化
		BaseOutputDTO output = new BaseOutputDTO();
		boolean updateFlag = false;
		int i = 0;

		Long userId = input.getUserId();

		try {
			if (this.isUserExist(userId)) {
				input.setLgnPwd(null);
				input.setUserName(null);
				input.setIsDeleted(null);
				i = mccUserMapper.updateByPrimaryKeySelective(input);
			}
			updateFlag = true;

		} catch (Exception e) {
			throw e;
		}
		output.setData("updateFlag", updateFlag);
		return output;
	}

	@Override
	public BaseOutputDTO saveUser(MccUser input) {
		// 初始化
		BaseOutputDTO output = new BaseOutputDTO();
		int i = mccUserMapper.insert(input);

		output.setData("insertFlag", i > 0 ? true : false);
		return output;
	}

	@Override
	public int countByParam(Map<String, Object> param) {
		return this.countByParam(param);
	}

}
