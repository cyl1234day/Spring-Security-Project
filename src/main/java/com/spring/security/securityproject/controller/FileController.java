package com.spring.security.securityproject.controller;

import com.spring.security.securityproject.pojo.FileInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author chengyl
 * @create 2019-03-15-16:38
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private String filePath = "E://";


    @PostMapping("/upload")
    public FileInfo uploadFile(MultipartFile file) {

        if(file == null) {
            return null;
        }
        //文件的参数名
        System.out.println(file.getName());
        //文件的原始名（上传时候的名字）
        String originalName = file.getOriginalFilename();
        System.out.println(originalName);
        //文件的后缀名
        String end = StringUtils.substring(originalName, StringUtils.lastIndexOf(originalName, "."));
        //文件大小
        System.out.println(file.getSize());

        File localFile = new File(filePath, System.currentTimeMillis()+ end);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new FileInfo(localFile.getAbsolutePath());
    }



    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {

        File localFile = new File(filePath + id);

        try(InputStream inputStream = new FileInputStream(localFile);
            OutputStream outputStream = response.getOutputStream()
            ) {
            //设置响应类型和响应头，不设置的话只是在浏览器上显示文件内容，并不能下载
            response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);//"application/x-download"
            response.setHeader("Content-Disposition", "attachment;filename=" + id);

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
