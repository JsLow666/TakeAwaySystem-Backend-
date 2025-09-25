package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags= "通用接口")
@Slf4j
public class CommonController {

    //TODO：阿里云无法使用，现本地储存，后改为AWS
//    @Autowired
//    private AliOssUtil aliOssUtil;
//    @PostMapping("/upload")
//    @ApiOperation("文件上传")
//    public Result<String> upload(MultipartFile file){
//        log.info("文件上传: {}",file);
//
//        try {
//            //原始文件名
//            String originalFilename = file.getOriginalFilename();
//            //文件后缀
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            //构造新文件名称
//            String objectName = UUID.randomUUID().toString() + extension;
//
//            //文件请求路径
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//            return Result.success(filePath);
//        } catch (IOException e) {
//            log.error("文件上传失败：{}",e);
//        }
//        return Result.error(MessageConstant.UPLOAD_FAILED);
//    }
    private static final String UPLOAD_DIR = "C:/Users/user/Documents/personal_develop/CangQiong_takeAwaySystem/imageFile/";

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传: {}",file);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构造新文件名称
        String objectName = UUID.randomUUID().toString() + extension;

        File targetFile = new File(UPLOAD_DIR + objectName);

        // 如果目标目录不存在，则创建它
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        // 保存文件
        try {
            file.transferTo(targetFile);
            return Result.success(UPLOAD_DIR + objectName);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }
}
