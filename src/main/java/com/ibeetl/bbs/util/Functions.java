package com.ibeetl.bbs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeetl.bbs.common.WebUtils;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSService;

/**
 * beetl 自定义函数
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年7月4日下午6:05:07
 */
@Service
public class Functions {
	@Autowired
	WebUtils webUtils;
	
	@Autowired
	BBSService bbsService;
	
	/**
	 * 继续encode URL (url,传参tomcat会自动解码)
	 * 要作为参数传递的话，需要再次encode
	 * @param encodeUrl
	 * @return String
	 */
	public String encodeUrl(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return url;
	}


	/**
	 * 模版中拿取cookie中的用户信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public BbsUser currentUser(HttpServletRequest request, HttpServletResponse response) {
		return webUtils.currentUser(request, response);
	}
	
	public Integer myMessageCount(Integer userId){
		return bbsService.getMyTopicsCount(userId);
	}

}
