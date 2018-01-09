package com.fangda.maintain.web.controller;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.exception.MaintainServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("upload")
public class UploadController {
    private static final Logger logger = LogManager.getLogger(UploadController.class);

    @RequestMapping(value = "/uploadMaintainTypePic",method = RequestMethod.POST)
    public JsonResponseObject uploadMaintainTypePic(@RequestParam("file") MultipartFile file) throws IOException{
        JsonResponseObject res = new JsonResponseObject();
        BaseOutputDTO baseOutputDTO = new BaseOutputDTO();
        logger.info("开始上传文件："+file.getOriginalFilename());
        try{
            String path = "/app/fangda/uploadfile/"+ file.getOriginalFilename();
//            FileUtils.writeByteArrayToFile(new File("e:/upload/"+ file.getOriginalFilename()), file.getBytes());
            FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
            logger.info("上传图片完毕!");
//            MaintainInfo maintainInfo = new MaintainInfo();
//            maintainInfo.setProjectid(projectId);
//            maintainInfo.setDesc(desc);
//            maintainInfo.setMaintainId(maintainTypeId);
//            maintainInfo.setPictureUrl(path);
//            maintainInfo.setUpdateTime(new Date());
//            res.setData(baseOutputDTO);
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
