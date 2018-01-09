package com.fangda.maintain.web.constant;

/**
 * 系统接口返回code定义
 * @Class Name MemberErrorCode
 * @Author 张健
 * @Create In Nov 27, 2014
 */
public enum ErrorCode {

    	SYSTEM_SUCCESS("S0A00000", "操作成功！"),
    	/*
    	 * 001开头：系统，网络、数据库异常
    	 */
        SYSTEM_GENERAL_ERROR("00100000","系统异常，请稍后再试"),
    	NETWORK_ERROR("00100001","网络连接异常"),
    	DATABASE_ERROR("00100002","数据库操作异常"),
    	REMOTE_INVOKE_ERROR("00100003", "远程服务调用异常！"),
    	SEND_SMSCODE_ERROR("00100004", "发送短信失败！"), 
    	MQ_INVOKE_ERROR("00100005", "消息队列访问异常！"),
    	INVALID_CHAR_ERROR("00100006", "非法字符异常！"),
    	INIT_DATA_ERROR("00100007", "初始化数据异常！"),
    /*
     * 002开头：用户操作异常
     */
    	DATA_EMPTY_ERROR("00200001", "参数错误或者参数为空"),
    	INVALID_LOGINPWD_ERROR("00200002", "用户名或者密码错误!"), 
    	INVALID_SMSCODE_ERROR("00200003", "校验码错误！"),
    	ACCOUNT_EXIST_ERROR("00200004", "帐号已存在!"),
    	ACCOUNT_NOEXIST_ERROR("00200005", "帐号不存在!"),
        MEMBER_BASEINFO_ERROR("00200006", "验证签名失败!"),
        OUT_LOGIN_ERROR("00200007", "用户未登陆"),
        LOGIN_ERROR("00200008", "登陆失败!"),  
        BIND_ERROR("00200009", "绑定失败!"),
        USER_TIMES_LOCKED("00200010", "密码输入错误超过限制"),
        UPDATE_PASSWORD_ERROR("00200011", "修改密码失败！"),
        INVALID_OLD_MOBILE_EXIST("00200012", "原手机号码不正确!"),
	    PAYPASSWORD_NOEXIST("00200013", "支付密码不存在!"),
	    ADDRESS_ERROR("00200014", "无效的地址!"),
		ENCODE_ERROR("00200015", "图片文件编码错误!"),
        CRTCARD_GEN_MAX_ERROR("00200016","每次生成卡的数量不能超过%d！"),
        CRTCARD_BUCODE_NOTEXIST_ERROR("00200017","该BU不存在，请确认！"),
        LOGOUT_ERROR("00200018", "登出失败!"),
        MOBILE_EMPTY("00200019", "手机号不能为空!"),
        MOBILE_NO_MATCH("00200020", "手机号码不符合规则!"),
        SMSCODE_EMPTY("00200021", "验证码不能为空!"),
        CHANNELID_EMPTY("00200022", "来源渠道不能为空!"),
        VERIFY_FREQUENCY("00200023", "验证码发送过于频繁,休息一下再试吧.!"),
        VERIFY_OVER_MAX("00200024", "今天该手机号码已超过接收验证码上限，发送失败！"),                   
        VERIFY_SEND_ERROR("00200025", " 验证码发送失败！"),
        VERIFY_NO_EXIST("00200026", " 验证码失效或不存在！"),
        LOGIN_PWD_ERROR("00200027", " 登录密码不正确！"),
        PAY_PWD_ERROR("00200028", " 支付密码不正确！"),
        PASSWORD_RESET_FAIL("00200029", " 密码重置失败！"),
        OLD_NEW_PWD_SAME_ERROR("00200030", "新支付密码不允许跟原支付密码相同！"),
        PAYPASSWORD_UPDATE_FAIL("00200031", "支付密码更新失败！"),
        REPEAT_APPY_ERROR("00200032", "重复的申请!"),
        CERT_CODE_ERROR("00200033", "输入的证件号码已被其他用户使用!"),
        OPREATION_ERROR("00200034", "不支持的操作类型!"),
        CHECK_CARD_ERROR("00200035", "传递的卡CVN信息格式不对!"),
        POINTS_DISPATCHED_ERROR("00200036", "积分已经分发!"),
        MEMBER_REGISTER_FORBIDDEN("00200037", "手机号不允许注册!"),
        MEMBER_DICT_ERROR("00200038", "会员系统参数信息错误!"),
        TOKEN_INVALID_ERROR("00200039", "token已经失效!"),
        PAYPASSWORD_ERROR("00200040", "支付密码错误!"),
        CARD_NOEXIST_ERROR("00200041", "会员卡不存在!"),
        CARD_INVALID_ERROR("00200042", "无效会员卡!"),
        MARCHANT_CODE_ERROR("00200043", "商户编码错误!"),
        RANDOM_CODE_NO_EXIST("00200044", "随机码为空!"),
        CARD_SIGN_ERROR("00200045", "验证会员卡签名错误!"),
        MEMBER_NOT_EXIST("00200046", "会员不存在!"),
        MEMBER_CARD_ERROR("00200047", "会员卡号不匹配!"),
        MEMBER_ONLY_ERROR("00200048", "会员验证不唯一!"),
        MEMBER_CARD_EXIST("00200049", "会员卡已绑定!"),
        STORE_IS_EMPTY("00200050", "请选择门店信息!"),
        INSERT_OR_UPDATE_FAIL("00200051", "新增或编辑失败!"),
        COMBMEMBER_ERROR("00200052","本功能仅对内部商户开放，您暂无访问权限"),
        MERCHANT_ORGTYPE_ERROR("00200053","商户类型数据不正确"),
        USER_PASSWORD_EXPIRE("00200054","请尽快修改登录密码"),
        USER_PASSWORD_EXPIRE_UPDATE("00200055","Rest"),
        MARCHANT_CODE_EMPTY("00200056","商户编码不能为空"),
        STORE_CODE_EMPTY("00200057","门店不能为空"),
        FROM_DATE_EMPTY("00200058","开始时间不能为空"),
        TO_DATE_EMPTY("00200059","结束时间不能为空"),
        MARCHANT_ID_EMPTY("00200060","商户ID不能为空"),
        PARAMETER_INVALID_ERROR("00200061", "不允许输入和显示  ?  \\  \"  /  \'  <  ~  > 这些字符"),
        USERNAME_OR_LGNPWD_EMPTY("00200062", "用户名或密码不能为空!"),
        USERMBL_EMPTY("00200064", "手机号不能为空!"),
        USERMBL_EXIT("00200065", "手机号已存在!"),
        MERCHANTNAMEZH_EXIT("00200067", "商户名称已存在!"),
        MERCHANTNAMEZH_EMPTY("00200068", "商户不存在!"),
//        SECURITYCODE_EMPTY("00200065", "验证码不能为空!"),
        LOGIN_OUT_ERROR("00200069", "该账号登录已失效，请重新登录"),
        UPLOAD_FILE_ERROR("00200070","文件上传失败"),
        
        // zl
        USER_IS_DELETE("00200501","用户已被删除"),
        USER_IS_LOCKED("00200502","用户已锁定"),
        USER_LOCKED_CODE("00200503","用户状态变更码错误！"),
        MEMBER_OPENAPI_HRTATTRS_EMPTY("E1B00046", "openAPI的公共参数是必填！"),
        MEMBER_TOKEN_ERRRO("E1B00047", "token和会员ID不一致!"),
        MEMBER_PERMISSION_EMPTY("E1B00048", "权限访问字段不存在!"),
        PERMISSION_MCMENUID_ILLEGAL("E1B00049", "非法的商户的menuId格式!"),
        MEMBER_PERMISSION_DENIED("E1B00050", "访问被拒绝，没有权限!"),
        MEMBER_TOKEN_BASEINFO_EMPTY("E1B00051", "token取用户的基本信息不存在!"),
        MEMBER_APPID_ATYPISM("E1B00052", "appId不一致，非法请求!"),
        NO_MCC_ADMIN("E1B00053","非法操作！不是系统管理员，或商户中心管理员！"),
        NO_MCC_USERID("E1B00054","非法操作！业务参数userId或merchantId是必须的！"),
        NO_MCM_ADMIN("E1B00055","非法操作！非商户管理账号！"),
        NO_MC_ADMIN("E1B00056","非法操作！仅商户中心系统管理员可操作！"),
        IMAGE_VALIDATE_EMPTY("00200503","图片验证码失效或不存在！"),
        IMAGE_VALIDATE_FAIL("00200503","图片验证码错误！"),
        MCC_USER_NO_GROUP("00300001","该用户不在组织中！"),
        MCC_USER_NO_ROLE("00300002","该用户没有相应的角色！"),
        MCC_USER_NO_MC_ROLE("00300003","仅商户管理员才能新增子账号！"),
        MCC_USER_NO_MC_ADMIN("00300004","账号不存在，请新增商户管理员！"),
        MCC_USER_EXIST_MC_ADMIN("00300005","该商户账号已存在，每个商户仅有1个管理员！"),
        MCC_USER_DENY_ONESELF("00300006","非法操作！该请求不能自己操作自己！"),
        MCC_USER_ONLY_ONESELF("00300007","非法操作！该请求仅自己操作自己！"),
        MCC_USER_DENY_ADMIN("00300008","非法操作！不能操作系统管理员！"),
        MCC_USER_DENY_MCADMIN("00300009","非法操作！商户管理员不能操作其他商户管理员！"),
        MCC_USER_DENY_MCADMIN_OTHER("00300010","非法操作！商户管理员不能操作其他商户的子账户！"),
        MCC_USER_DENY_PAYMENT_OPER("00300011","注意：商户管理员已经启用了按商户发分规则，发分密码和小额免密额度以商户管理员的为准，子账户不能单独设置发分密码和额度！"),
        MCC_USER_NOT_EXIST_MC_ADMIN("00300012","该商户没有商户管理员账号，每个商户仅有1个！"),


        /*
        * 003开头：会员信息操作相关异常
        */
        // **********************************************************
        // ** 注意：
        // ** APP前端会根据 00300001 这个错误做一些逻辑判断处理，
        // ** 忽必不要随意改动或占用该错误码，除非已跟前端协调统一修改。
        // **********************************************************
        UPDATE_MEMBER_PROFILE_NO_FIELDS_CHANGED("00300001", "会员信息更新失败，必须至少提供一个或一个以上的更新项");
    
	
        private String code;
        private String msg;

        private ErrorCode() {
        };

        private ErrorCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
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
    
}
