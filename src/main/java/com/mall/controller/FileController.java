package com.mall.controller;

import com.mall.entity.RespBody;
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
    public RespBody upload(String fileName, String size, String data) {
        RespBody respBody = new RespBody(false, "文件上传失败", null);
        try {
            // BASE64 解密图片文件
            byte[] fileData = new BASE64Decoder().decodeBuffer(data);
            // 上传图片
            respBody = qiniuUploadUtil.uploadPhoto(fileData, fileName);
        } catch (Exception e) {
            respBody.setMsg(respBody.getMsg() + ": " + e.toString());
        }
        return respBody;
    }

    @RequestMapping("/**")
    public String file(HttpServletRequest request) {
        return "redirect:" + environment.getProperty("file_url") + request.getServletPath();
    }


    @RequestMapping("/test")
    public String test() {
        return "/test";
    }
}
