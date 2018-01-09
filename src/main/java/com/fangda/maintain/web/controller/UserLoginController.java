package com.fangda.maintain.web.controller;

import com.alibaba.fastjson.JSON;
import com.fangda.maintain.web.annotation.TokenAccess;
import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.constant.MaintainServiceReturnCode;
import com.fangda.maintain.web.constant.RedisConstant;
import com.fangda.maintain.web.domain.*;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.service.MccUserService;
import com.fangda.maintain.web.service.UserLoginService;
import com.fangda.maintain.web.session.SysLogin;
import com.fangda.maintain.web.utils.RedisUtils;
import com.fangda.maintain.web.utils.ValidateCode;
import com.fangda.maintain.web.utils.WebCookie;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mortbay.jetty.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserLoginController extends RestBaseController {

	private static Logger LOGGER = LogManager.getLogger(UserLoginController.class);
	@Autowired
	MccUserService mccUserServiceImpl;
	@Autowired
	RedisUtils redisUtils;
	@Autowired
	UserLoginService userLoginService;

	@Value("${cookie_domain}")
	protected String cookieDomain;

	@RequestMapping(value = "/loginNew", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public JsonResponseObject loginByPwdNew(@RequestBody UserLoginInputDTO inputTmp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 初始化
		JsonResponseObject res = new JsonResponseObject();
		try{
			LOGGER.debug("login begin  -> {}",JSON.toJSONString(inputTmp));
			BaseOutputDTO output = this.checkUser(inputTmp);

			if (!MaintainServiceReturnCode.SUCCESS.getCode().equals(output.getCode())) {
				res.setCode(ErrorCode.SMSCODE_EMPTY.getCode());
				res.setMsg(ErrorCode.SMSCODE_EMPTY.getMsg());
				res.setData(output);
				return res;
			}
			String userName = inputTmp.getUserName();
			String passWord = inputTmp.getPassWord();
			String inputCode = inputTmp.getCode();//验证码
			String uuid = inputTmp.getUuid();
			boolean flag = false;
			String validateCode= redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_UUID_IMAGE,uuid);
			if(StringUtils.isBlank(validateCode) ){
				res.setCode(ErrorCode.IMAGE_VALIDATE_EMPTY.getCode());
				res.setMsg(ErrorCode.IMAGE_VALIDATE_EMPTY.getMsg());
				return res;
			}
			validateCode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(validateCode),"UTF-8");
			LOGGER.debug("redis validateCode -> {}",validateCode);
			// 忽略验证码大小写
			if (StringUtils.equalsIgnoreCase(inputCode, validateCode)) {
				// 是否存在
				MccUserInputDTO mccUserInputDTO = new MccUserInputDTO();
				mccUserInputDTO.setUserName(userName);
				mccUserInputDTO.setIsDeleted(Byte.valueOf("0"));
				BaseOutputDTO baseOutputDTO1 = mccUserServiceImpl.isUserExist(mccUserInputDTO);
				LOGGER.debug("isUserExist return -> {}",JSON.toJSONString(baseOutputDTO1));
				if (baseOutputDTO1 == null || !MaintainServiceReturnCode.SUCCESS.getCode().equals(baseOutputDTO1.getCode()) || baseOutputDTO1.getData().get("flag").toString().equals("false")) {
					res.setCode(ErrorCode.INVALID_LOGINPWD_ERROR.getCode());
					res.setMsg(ErrorCode.INVALID_LOGINPWD_ERROR.getMsg());
					return res;
				}
				Long userId = Long.parseLong(baseOutputDTO1.getData().get("userId").toString());
				// TODO 未作操作日志记录
				MccUserInfoDTO mccUserInfo = mccUserServiceImpl.getUserBaseInfo(userId);
				LOGGER.debug("adminUser return -> {}",JSON.toJSONString(mccUserInfo));
				String pwd = mccUserInfo.getLgnPwd();
				passWord = MD5.digest(passWord).replace("MD5:", "");
				if (pwd.equals(passWord)) {// 密码验证成功

					String userToken = getUUID();
					UserLoginController.clearUserCashe(redisUtils,userId,userToken);
					UserLoginController.delayUserTime(redisUtils, userToken, mccUserInfo);
					WebCookie.addCookieByDomain(cookieDomain, response, WebCookie.USER_TOKEN, userToken);
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("token", userToken);
					data.put("userName", userName);
					data.put("userId", userId);
					data.put("accountType", "1");
					res.setCode(output.getCode());
					res.setMsg(output.getMsg());
					res.setData(data);
					return res;
				} else {
					res.setCode(ErrorCode.INVALID_LOGINPWD_ERROR.getCode());
					res.setMsg(ErrorCode.INVALID_LOGINPWD_ERROR.getMsg());
					return res;
				}
			} else {
				res.setCode(ErrorCode.IMAGE_VALIDATE_FAIL.getCode());
				res.setMsg(ErrorCode.IMAGE_VALIDATE_FAIL.getMsg());
				return res;
			}
		} catch (Throwable e) {
			LOGGER.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}


	/**
	 * 设置用户的超时时间为30分钟,手机端小程序是30天。
	 * @param redisUtils
	 * @param userToken
	 * @param mccUserInfo
	 */
	public static void delayUserTime(RedisUtils redisUtils,String userToken,MccUserInfoDTO mccUserInfo){
		if(mccUserInfo != null){
			String key = "";
			int time = 30*60;
			if(StringUtils.endsWith(userToken, "_phone")){
				key = RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE;
				time = 60*60*24*30;
    		}else{
    			key = RedisConstant.REDIS_PREFIX_TOKEN_USERID;
    		}
			redisUtils.setMemberSet(key, userToken, mccUserInfo, time);
			redisUtils.setMemberSet(key, mccUserInfo.getUserId()+"", userToken, time);
		}
	}
	public static void clearUserCashe(RedisUtils redisUtils,Long userId,String userToken){
		String key = "";
		if(StringUtils.endsWith(userToken, "_phone")){
			key = RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE;
		}else{
			key = RedisConstant.REDIS_PREFIX_TOKEN_USERID;
		}
		String oldToken = redisUtils.getMemberSet(key, userId+"");
		if(StringUtils.isNotBlank(oldToken)){
			redisUtils.del(key, oldToken);
		}
		redisUtils.del(key, userId+"");
	}
	
	/**
	 * 用户基本信息校验
	 * @param inputTmp
	 * @return
	 */
	private BaseOutputDTO checkUser(UserLoginInputDTO inputTmp){
		BaseOutputDTO output = new BaseOutputDTO();
		// 校验，不能为空
		if (StringUtils.isBlank(inputTmp.getUserName()) || StringUtils.isBlank(inputTmp.getPassWord())) {
			output.setCode(ErrorCode.USERNAME_OR_LGNPWD_EMPTY.getCode());
			output.setMsg(ErrorCode.USERNAME_OR_LGNPWD_EMPTY.getMsg());
		}
		return output;
	}
	/**
	 * 微信小程序，用户基本信息校验
	 * @param inputTmp
	 * @return
	 */
	private BaseOutputDTO checkUserForPhone(UserLoginInputDTO inputTmp){
		BaseOutputDTO output = new BaseOutputDTO();
		// 校验，不能为空
		if ((null == inputTmp || StringUtils.isBlank(inputTmp.getUserName()) || StringUtils.isBlank(inputTmp.getPassWord()))) {
			output.setCode(ErrorCode.SMSCODE_EMPTY.getCode());
			output.setMsg(ErrorCode.SMSCODE_EMPTY.getMsg());
		}
		return output;
	}
	
	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@TokenAccess
	public JsonResponseObject loginOut(@RequestBody BaseInputDTO inputTmp, HttpServletRequest request) {
		JsonResponseObject res = new JsonResponseObject();
		try{
			String userToken = inputTmp.getToken();
			if(StringUtils.isNotBlank(userToken)){
				MccUserInfoDTO mccUserInfo = SysLogin.getUserInfo(redisUtils, userToken);
				if(mccUserInfo != null){
					UserLoginController.clearUserCashe(redisUtils,mccUserInfo.getUserId(),userToken);
				}
				WebCookie.delCookieByName(request, WebCookie.USER_TOKEN);
			}
			return res;
		} catch (Throwable e) {
			LOGGER.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}

	// checkLogined
	@RequestMapping(value = "/isUserLogined", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@TokenAccess
	public JsonResponseObject checkUserLogined(@RequestBody BaseInputDTO inputTmp) {
		// 初始化
		JsonResponseObject res = new JsonResponseObject();
		BaseOutputDTO outputDTO = new BaseOutputDTO();
		try{
			String token = inputTmp.getToken();
			String key = "";
			if(StringUtils.endsWith(token, "_phone")){
				key = RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE;
			}else{
				key = RedisConstant.REDIS_PREFIX_TOKEN_USERID;
			}
			MccUserInfoDTO mccUserInfo = redisUtils.getMemberSet(key, token,MccUserInfoDTO.class);
			if (mccUserInfo == null || mccUserInfo.getUserId() == null) {
				outputDTO.setData("isLogined", false);
				outputDTO.setCode(ErrorCode.OUT_LOGIN_ERROR.getCode());
				outputDTO.setMsg(ErrorCode.OUT_LOGIN_ERROR.getMsg());
				res.setCode(outputDTO.getCode());
				res.setMsg(outputDTO.getMsg());
			} else {
				outputDTO.setData("isLogined", true);
			}
			res.setData(outputDTO);
			return res;
		} catch (Throwable e) {
			LOGGER.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}

	// B15.获得图片验证码
	@RequestMapping(value = "/validateCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public JsonResponseObject getValidateCode(@RequestBody @Valid BaseInputDTO inputTmp,BindingResult result) throws Exception {
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}

		// 初始化
		JsonResponseObject res = new JsonResponseObject();
		BaseOutputDTO output = new BaseOutputDTO();
		LOGGER.info("UserLoginController -> getValidateCode");

		//HttpServletRequest request,HttpServletResponse response
		
		/*
		 * // 设置响应的类型格式为图片格式 response.setContentType("image/png"); // 禁止图像缓存。
		 * response.setHeader("Pragma", "no-cache");
		 * response.setHeader("Cache-Control", "no-cache");
		 * response.setDateHeader("Expires", 0);
		 */
		try{
			// 生成
			ValidateCode vCode = new ValidateCode(150, 30, 4, 1);
			// 转为base64
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(vCode.getBuffImg(), "png", out);
			String code = new String(org.apache.commons.codec.binary.Base64.encodeBase64(vCode.getCode().getBytes()));
			String enB64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64(out.toByteArray()));

			String uuid = "mc" + getUUID();
			userLoginService.setImage(uuid, code);

			output.setData("uuid", uuid);
			output.setData("codeImg", enB64);
			loger.info("=========:"+enB64);
			// 联调完成后，移除
			//output.setData("biaoji",vCode.getCode());
//		output.setData("code", code);
			res.setData(output);
			return res;
		} catch (Throwable e) {
			LOGGER.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}

	// B16.校验图片验证码
//	@RequestMapping(value = "/validateCodeVerify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public JsonResponseObject getValidateCodeVerify(@RequestBody UserLoginCodeInputDTO inputTmp) throws Exception {
		// 初始化
		JsonResponseObject res = new JsonResponseObject();
		BaseOutputDTO output = new BaseOutputDTO();
		LOGGER.info("UserLoginController -> getValidateCodeVerify");
		try{
			// 校验，不能为空
			if (null == inputTmp || StringUtils.isBlank(inputTmp.getCode()) || StringUtils.isBlank(inputTmp.getUuid())) {
				output.setCode(ErrorCode.SMSCODE_EMPTY.getCode());
				output.setMsg(ErrorCode.SMSCODE_EMPTY.getMsg());
				res.setData(output);
				return res;
			}

			String inputCode = inputTmp.getCode();
			String uuid = inputTmp.getUuid();
			boolean flag = false;

			BaseOutputDTO output2 = new BaseOutputDTO();
			output2 = userLoginService.getImage(uuid);
			if (!output2.getCode().equals(ErrorCode.SYSTEM_SUCCESS.getCode())) {
				output.setData("flag", flag);
				output.setCode(ErrorCode.IMAGE_VALIDATE_EMPTY.getCode());
				output.setMsg(ErrorCode.IMAGE_VALIDATE_EMPTY.getMsg());
				res.setData(output);
				return res;
			} else {
				String redisCode = output2.getData().get("codeB64").toString();
				String redisCode2 = new String(org.apache.commons.codec.binary.Base64.decodeBase64(redisCode));

				// 忽略验证码大小写
				if (StringUtils.equalsIgnoreCase(inputCode, redisCode2)) {
					flag = true;
					userLoginService.delImage(uuid);
				}else{
					output.setCode(ErrorCode.IMAGE_VALIDATE_FAIL.getCode());
					output.setMsg(ErrorCode.IMAGE_VALIDATE_FAIL.getMsg());

				}

			}

			output.setData("flag", flag);
			res.setData(output);
			return res;
		} catch (Throwable e) {
			LOGGER.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}

	/**
	 * 
	 * @return A unique key generated by UUID
	 */
	public static final synchronized String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 将Spring对请求数据绑定过程中累积的Validation错误转化为JsonResponseObject。
	 * 
	 * @param result
	 * @return
	 * @author wengyongyi
	 */
	protected JsonResponseObject createValidErrorMsgAsRestResponse(BindingResult result) {
		List<FieldError> errors = result.getFieldErrors();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < errors.size(); i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(errors.get(i).getField())
			  .append(errors.get(i).getDefaultMessage());
		}
		JsonResponseObject jsonResponse = new JsonResponseObject();
		jsonResponse.setCode(MaintainRestReturnCode.ERR_REST_INPUT_VALIDATION_REJECTED.getCode());
		jsonResponse.setMsg(sb.toString());
		return jsonResponse;
	}

}
