package edu.hrm.service.impl;

import edu.hrm.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 本地文件上传
 *
 * @author 执笔
 * @date 2019/4/12 15:30
 */
@Service
@Slf4j
public class LocalFileUploadServiceImpl implements FileUploadService {

    @Value("${upload.prefix}")
    private String prefix;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ServletContext servletContext;

    @Override
    public String upload(MultipartFile file, String model) {
        String path       = "/" + model + "/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/";
        String fileName   = generateNewName(file.getOriginalFilename());
        File   newFile    = new File(uploadPath + path + fileName);
        newFile.getParentFile().mkdirs();
        try {
            newFile.createNewFile();
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("◎Upload File◎  " + newFile.getAbsolutePath());
        return prefix + path + fileName;
    }

    /**
     * 得到一个新名字
     *
     * @param name 原来的名字
     * @return
     */
    private String generateNewName(String name) {
        String extension = FilenameUtils.getExtension(name);
        if (StringUtils.isBlank(extension)) {
            RandomStringUtils.randomAlphanumeric(32);
        }
        return RandomStringUtils.randomAlphanumeric(32) + "." + extension;
    }
}
