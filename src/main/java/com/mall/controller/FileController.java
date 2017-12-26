package com.mall.controller;

import com.mall.vo.ErrorInfo;
import com.mall.utils.QiniuUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private Environment environment;
    @Autowired
    private QiniuUploadUtil qiniuUploadUtil;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ErrorInfo upload(String fileName, String size, String data) {
        ErrorInfo<String> errorInfo = new ErrorInfo<>(false, "上传失败", "", "");
        try {
            // BASE64 解密图片文件
            byte[] fileData = new BASE64Decoder().decodeBuffer(data);
            // 上传图片
            errorInfo = qiniuUploadUtil.uploadPhoto(fileData, fileName);
        } catch (Exception e) {
            errorInfo.setMsg(errorInfo.getMsg() + ": " + e.getMessage());
        }
        return errorInfo;
    }

    @RequestMapping("/**")
    public String file(HttpServletRequest request) {
        return "redirect:" + environment.getProperty("file_url") + request.getServletPath();
    }


    @RequestMapping("/test")
    public String test() throws Exception {
        throw new Exception("测试异常");
    }
}
