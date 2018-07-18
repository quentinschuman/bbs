package com.ibeetl.bbs.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.common.WebUtils;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BbsUserService;
import com.ibeetl.bbs.util.HashKit;
import com.ibeetl.bbs.util.VerifyCodeUtils;


@Controller
public class LoginController {
	
	
	@Autowired
	SQLManager sql;

	@Autowired
	BbsUserService bbsUserService;
	
	static final String CODE_NAME = "verCode";
	

	/**
	 * 登录方法改为ajax方式登录
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bbs/user/login")
	public JSONObject  login(String userName,String password,HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		result.put("err", 1);
		if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
			result.put("msg","请输入正确的内容！");
		}else{
			password = HashKit.md5(password);
			BbsUser user = bbsUserService.getUserAccount(userName, password);
			if(user==null){
				result.put("msg","用户不存在或密码错误");
			}else{
				WebUtils.loginUser(request, response, user, true);
				result.put("msg", "/bbs/index/1.html");
				result.put("err", 0);
			}
		}
		return result;
	}
	
	@GetMapping("/bbs/user/register.html")
	public ModelAndView  loginPage(HttpServletRequest request){
		ModelAndView view = new ModelAndView("/register.html");
		return view ;
	}
	
	/**
	 * 登出方法改为ajax方式登出
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@PostMapping("/bbs/user/logout")
	public void  logout(HttpServletRequest request,HttpServletResponse response){
		WebUtils.logoutUser(request,response);
	}
	/**
	 * 注册改为 ajax 方式注册
	 * @param user
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@PostMapping("/bbs/user/doRegister")
	public JSONObject  register(BbsUser user,String code,HttpServletRequest request,HttpServletResponse response){
		JSONObject result  = new JSONObject();
		result.put("err", 1);
		HttpSession session = request.getSession(true); 
		String verCode = (String)session.getAttribute(CODE_NAME);
		if(!verCode.equalsIgnoreCase(code)){
			result.put("msg", "验证码输入错误");
		}else if(bbsUserService.hasUser(user.getUserName())){
			result.put("msg", "用户已经存在");
		}else{
			String password = HashKit.md5(user.getPassword());
			user.setPassword(password);
			user.setBalance(10);
			user.setLevel(1);
			user.setScore(10);
			user = bbsUserService.setUserAccount(user);
			WebUtils.loginUser(request, response, user, true);
			result.put("err", 0);
			result.put("msg", "/bbs/index");
		}
		return result;
	}
	
	@RequestMapping("/bbs/user/authImage")
	public void authImage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute(CODE_NAME);
        session.setAttribute(CODE_NAME, verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
	
	}
	
	
}
