package com.fangda.maintain.web.controller;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.utils.OSSClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("upload")
public class UploadController {
    private static final Logger logger = LogManager.getLogger(UploadController.class);



    @Autowired
    private OSSClientUtil ossClientUtil;

    @RequestMapping(value = "/uploadMaintainTypePic",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseObject uploadMaintainTypePic(@RequestParam("file") MultipartFile file) throws IOException{
        JsonResponseObject res = new JsonResponseObject();
        BaseOutputDTO baseOutputDTO = new BaseOutputDTO();
        logger.info("开始上传文件："+file.getOriginalFilename());
        try{
            String name = ossClientUtil.uploadImg2Oss(file);
            String imgUrl = ossClientUtil.getImgUrl(name);
            logger.info("Object：" + file.getOriginalFilename() + "存入OSS成功。");
            res.setData(name);
            res.setCode(MaintainRestReturnCode.SUCCESS.getCode());
            res.setCode(MaintainRestReturnCode.SUCCESS.getMsg());
            return res;
        }catch (Throwable e) {
            logger.error("上传图片返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
                    e.getMessage());
            if (e instanceof MaintainServiceException) {
                MaintainServiceException ex = (MaintainServiceException) e;
                return new JsonResponseObject(ex.getCode(), ex.getMessage());
            }
            return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
                    MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
        }

    }

}
