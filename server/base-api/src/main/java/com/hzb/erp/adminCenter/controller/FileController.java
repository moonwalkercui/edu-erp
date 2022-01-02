package com.hzb.erp.adminCenter.controller;

import com.hzb.erp.annotation.Log;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.Attachment;
import com.hzb.erp.common.service.AttachmentService;
import com.hzb.erp.service.FileService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@RestController
@RequestMapping("/common/file")
@Slf4j
@Api(value = "文件上传", tags = "文件上传")
public class FileController {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private FileService fileService;

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/getInfo")
    @ResponseBody
    public Map<String,Object> getInfo(@RequestParam(value = "id", defaultValue = "") Long id,
                                      @RequestParam(value = "url", defaultValue = "") String url) {
        Attachment atta = null;
        if(id != null ) {
            atta = attachmentService.getById(id);
        } else if (StringUtils.isNotBlank(url)){
            atta = attachmentService.getByUrl(url);
        }
        Map<String,Object> res = new HashMap<>();
        if(atta == null) {
            return res;
        }
        res.put("id", atta.getId());
        res.put("url", atta.getUrl());
        res.put("fileName", atta.getFileName());
        return res;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    @Log(description = "上传文件", type = "文件管理")
    public UploadResultBO uploadFile(@RequestParam("file") MultipartFile file,
                                   @RequestParam(name = "ext", defaultValue = "") String ext,
                                   @RequestParam(name = "param", defaultValue = "") String param) throws FileUploadException {

        UploadValidateBO validateBO = new UploadValidateBO();
        validateBO.setMaxSize(systemConfig.getUploadImgMaxSize());
        validateBO.setAcceptExt(StringUtils.isBlank(ext) ? "jpg,jpeg,png,gif,bmp,doc,docx,xlsx,xls,pdf" : ext);

        return fileService.upload(file, validateBO, param);
    }

}
