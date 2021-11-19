package com.hzb.erp.service;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.entity.Attachment;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.service.AttachmentService;
import com.hzb.erp.service.bo.UploadResultBO;
import com.hzb.erp.service.bo.UploadValidateBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Ryan
 * description 通用服务
 **/
@Service
@Slf4j
public class FileService {

    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AttachmentService attachmentService;
    /**
     * 文件上传
     *
     * @param file       文件
     * @param validateBO 验证类
     * @param param      上传附带的1个参数,上传功能会返回前端。
     * @return UploadResultBO
     */
    public UploadResultBO upload(MultipartFile file, UploadValidateBO validateBO, String param) throws FileUploadException {

        if(StringUtils.isBlank(validateBO.getAcceptExt())) {
            if(validateBO.getIsImage()) {
                validateBO.setAcceptExt("jpg,png,gif,bmp");
            } else {
                validateBO.setAcceptExt("jpg,png,gif,bmp,doc,docx,xls,xlsx,pdf");
            }
        }

        validateFile(file, validateBO);

        // 上传文件夹
        String newDir = LocalDate.now().toString() + "/";
        String uploadDir = systemConfig.getUploadDir();
        String uploadPath = uploadDir + newDir;

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            boolean mk = dir.mkdirs();
            if (!mk) {
                throw new FileUploadException("创建文件夹失败");
            }
        }

        // 上传文件
        String orFileName = file.getOriginalFilename();
        String suffix = getFileExt(orFileName);

        String fileName = UUID.randomUUID() + suffix;
        File dest = new File(uploadPath + fileName);
        UploadResultBO res = new UploadResultBO();
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }

        validateFileExt(dest, validateBO.getAcceptExt());

        log.info("上传成功：" + newDir + fileName);
        String url = systemConfig.getUploadFileUrlPrefix() + newDir + fileName;

        Attachment atta = new Attachment();
        atta.setName(fileName);
        atta.setUrl(url);
        atta.setSavePath(uploadPath);
        atta.setFileName(orFileName);
        atta.setFileSize(file.getSize());
        atta.setFileType(suffix);
        atta.setAddTime(LocalDateTime.now());
        attachmentService.save(atta);

        res.setAttaId(atta.getId());
        res.setUrl(url);
        res.setParam(param);

        return res;
    }

    /**
     * 服务端验证文件格式
     * */
    private void validateFileExt(File file, String ext) throws FileUploadException {
        if(StringUtils.isBlank(ext)) {
            throw new FileUploadException("未指定上传格式");
        }
        // 可获文件类型有限制，需要查看方法内
        String type = getFileType(file);
        System.out.println(type);
        if(type == null) {
            throw new FileUploadException("错误的文件格式!");
        }
        String[] extList = ext.trim().split(",");
        for (String ex : extList) {
            if (ex.equalsIgnoreCase(type)) {
                return;
            }
        }
        throw new FileUploadException("文件格式不允许");
    }

    private String getFileType(File file) throws IORuntimeException {
        FileInputStream in = null;
        String var2;
        try {
            in = IoUtil.toStream(file);
            var2 = FileTypeUtil.getType(in);
        } finally {
            IoUtil.close(in);
        }
        return var2;
    }

    /**
     * description 文件上传验证
     *
     * @param file       文件
     * @param validateBO 验证方法
     *                   return void
     */
    public static void validateFile(MultipartFile file, UploadValidateBO validateBO) throws FileUploadException {
        if (file.isEmpty()) {
            throw new FileUploadException("请选择文件");
        }
        if (validateBO == null) {
            return;
        }
        Long maxSize = validateBO.getMaxSize();
        Boolean isImage = validateBO.getIsImage();

        if (maxSize != null && file.getSize() > maxSize) {
            throw new FileUploadException("上传的文件太大");
        }

        // 改为使用服务端验证扩展名
//        String acceptExt = validateBO.getAcceptExt();
//        String originalFilename = file.getOriginalFilename();
//        if (StringUtils.isNotBlank(acceptExt) && !CommonUtil.checkFileExt(originalFilename, acceptExt)) {
//            throw new FileUploadException("请上传扩展名为" + acceptExt + "的文件");
//        }

        if (isImage != null && isImage) {
            try {
                BufferedImage bi = ImageIO.read(file.getInputStream());
                if (bi == null) {
                    throw new FileUploadException("上传的文件必须是图片");
                }
            } catch (IOException e) {
                throw new FileUploadException("获取上传的文件出错");
            }
        }

    }

    /**
     * 获取带.的扩展名
     */
    public static String getFileExt(String filename) {
        if (StringUtils.isBlank(filename)) {
            throw new BizException("无效文件名");
        }
        String ext;
        if (filename.contains(".")) {
            ext = filename.substring(filename.lastIndexOf("."));
        } else {
            throw new BizException("无效扩展名");
        }
        return ext;
    }

}
