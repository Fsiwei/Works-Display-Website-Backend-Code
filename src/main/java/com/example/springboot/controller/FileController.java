package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author : siwei.fan
 * @date : 2024/3/4 22:10
 * @modyified By :
 */

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    
    @Value("${ip:localhost}") // 引入 application.yml 文件定义的参数，其中 localhost 是默认值，防止该参数时报错
    String ip;
    
    @Value("${server.port}")
    String port;
    
    // 文件存储根目录
    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";
    
    @AuthAccess
    @GetMapping("/") // 检查接口是否正常运行
    public Result hello(){
        return Result.success("success");
    }
    
    // @AuthAccess // 这个注释只在测试接口时使用
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename(); // 获取文件的原始名称
        String mainName = FileUtil.mainName(originalFileName); // 获取不含后缀名的文件名
        String extName = FileUtil.extName(originalFileName); // 获取文件的后缀名
        if(!FileUtil.exist(ROOT_PATH)) { // 判断文件存储路径的父级目录是否存在
            FileUtil.mkdir(ROOT_PATH); // 不存在则创建父级目录
        }
        if(FileUtil.exist(ROOT_PATH + File.separator + originalFileName)) { // 判断当前上传的文件的文件名是否已经存在
            originalFileName = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFileName); // 已存在，在 mainName 中添加一个随机数重命名该文件
        file.transferTo(saveFile); // 存储文件到本地磁盘
        String url = "http://" + ip + ":" + port + "/api/files/download/" + originalFileName;
        return Result.success(url); // 返回上传文件的链接，该链接是文件的下载地址，后端提供
    }
    
    
    // @AuthAccess
    // @GetMapping("/download/{fileName}")
    // public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException { // 定义返回值类型 void，是因为要以文件流的形式输出
    //     String filePath = ROOT_PATH + File.separator + fileName;
    //     if(!FileUtil.exist(filePath)){ // 判断请求下载文件是否存在
    //         return; // 不存在直接返回
    //     }
    //     byte[] bytes = FileUtil.readBytes(filePath); // 文件存在，读取文件的字节流
    //     ServletOutputStream outputStream = response.getOutputStream();
    //     outputStream.write(bytes); // 将文件写入，该方法参数是一个字节数组，即文件的字节流
    //     outputStream.flush(); // 刷新
    //     outputStream.close(); // 关闭，不关闭会占内存资源
    // }
    
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        try {
            String filePath = ROOT_PATH + File.separator + fileName;
            if(!FileUtil.exist(filePath)){ // 判断请求下载文件是否存在
                return; // 不存在直接返回
            }
            byte[] bytes = FileUtil.readBytes(filePath); // 文件存在，读取文件的字节流
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes); // 将文件写入，该方法参数是一个字节数组，即文件的字节流
            outputStream.flush(); // 刷新
            outputStream.close(); // 关闭，不关闭会占内存资源
        } catch (ClientAbortException e) {
            // 客户端中断连接，可以记录日志，但不需要进一步处理
            log.info("Client aborted connection", e);
        } catch (IOException e) {
            // 其他IO异常处理
            log.error("Error occurred while sending file to client", e);
        } catch (Exception e) {
            // 处理其他可能的异常
            log.error("Unexpected error", e);
        }
    }
    
}
