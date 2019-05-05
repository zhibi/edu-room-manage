package edu.room.manage.service;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * 文件上传服务
 *
 * @author 执笔
 * @date 2019/4/12 15:29
 */
public interface FileUploadService {

    /**
     * 上传文件
     *
     * @param file  需要上传的文件
     * @param model 上传文件的模块
     * @return
     */
    String upload(MultipartFile file, @NotBlank String model);
}
