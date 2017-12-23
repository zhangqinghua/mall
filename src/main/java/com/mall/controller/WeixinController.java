package com.mall.controller;

import com.mall.utils.JSON;
import com.mall.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;

@Controller
@RequestMapping("weixin")
public class WeixinController {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private Environment environment;

    /**
     * 凭证
     * 凭证获取时间，单位：Linux时间
     * 凭证有效时间，单位：秒
     */
    private String accessToken;
    private long accessTokenExpiresTime;
    private int accessTokenExpiresIn;

    /**
     * 临时票据
     * 临时票据获取时间，单位：Linux时间
     * 临时票据有效时间，单位：秒
     */
    private String ticket;
    private long ticketExpiresTime;
    private int ticketExpiresIn;


    /**
     * 签名
     * 生成签名的随机串
     * 生成签名的时间戳
     */
    private String signature;
    private String noncestr = "helloWorld";
    private long signatureTimestamp;

    @RequestMapping("/scan")
    private String scan(Model model, HttpServletRequest request) {
        model.addAttribute("signature", getSignature("http://k.teamtor.com/weixin/scan"));
        model.addAttribute("appId", environment.getProperty("weixin.appid"));
        model.addAttribute("signatureTimestamp", signatureTimestamp);
        model.addAttribute("nonceStr", noncestr);
        return "weixin/scan";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return getAccessToken() + "<br>" + getJsapiTicket();
    }

    private String getSignature(String url) {
        signatureTimestamp = System.currentTimeMillis();
        signature = "jsapi_ticket={jsapi_ticket}&noncestr={noncestr}&timestamp={timestamp}&url={url}";
        signature = signature.replace("{jsapi_ticket}", getJsapiTicket()).replace("{noncestr}", noncestr).replace("{timestamp}", signatureTimestamp + "").replace("{url}", url);
        signature = Utils.getSha1(signature);
        return signature;
    }

    private String getAccessToken() {
        // 如果凭证不存在或者过时，则重新获取
        if (Utils.isEmpty(accessToken) || System.currentTimeMillis() - accessTokenExpiresTime > accessTokenExpiresIn) {
            String access_token_url = environment.getProperty("weixin.access_token_url");
            JSON result = new JSON(restTemplate.getForObject(access_token_url, String.class));
            accessToken = result.getStr("access_token");
            accessTokenExpiresIn = Integer.parseInt(result.getStr("expires_in")) * 1000;
            accessTokenExpiresTime = System.currentTimeMillis();
        }
        return accessToken;
    }

    private String getJsapiTicket() {
        // 如果凭证不存在或者过时，则重新获取
        if (Utils.isEmpty(ticket) || System.currentTimeMillis() - ticketExpiresTime > ticketExpiresIn) {
            String jsapi_ticket_url = environment.getProperty("weixin.jsapi_ticket_url");
            JSON result = new JSON(restTemplate.getForObject(jsapi_ticket_url.replace("ACCESS_TOKEN", getAccessToken()), String.class));
            ticket = result.getStr("ticket");
            ticketExpiresIn = Integer.parseInt(result.getStr("expires_in")) * 1000;
            ticketExpiresTime = System.currentTimeMillis();
        }
        return ticket;
    }

    public static void main(String[] args) throws Exception {

        String sha1 = Utils.getSha1("jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com");
        System.out.println(sha1);
    }

}
