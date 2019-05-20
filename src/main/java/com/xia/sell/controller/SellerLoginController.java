package com.xia.sell.controller;

import com.xia.sell.dto.LoginDTO;
import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.SellerInfoService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerLoginController {
	@Autowired
	private SellerInfoService sellerInfoService;
	@GetMapping("/tologin")
	public String login(Model model){
		model.addAttribute("loginError", false);
		return "login";
	}
	@PostMapping("/tologin")
	//@ResponseBody
	public String toLogin(LoginDTO loginDTO, Model model, HttpServletRequest request, RedirectAttributes ra){
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken = null;
		if (loginDTO.getIsRememberMe() != null && loginDTO.getIsRememberMe().equals("on")){
			 usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword(),true);
		}else {
			 usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword(),false);
		}
		try {
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			//用户不存在时的异常
		}catch (UnknownAccountException e){
			model.addAttribute("loginError", true);
			model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
			return "login";
			//密码错误的异常
		}catch (IncorrectCredentialsException e){
			model.addAttribute("loginError", true);
			model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
			//HashMap<String, String> map = new HashMap<>();
			//map.put("code", "0");
			//map.put("msg", "登陆失败，请检查账号密码。");
			return "tologin";
		}
		HttpSession session = request.getSession();
		session.setAttribute("sellerId", loginDTO.getUsername());
		SellerInfo sellerInfo = sellerInfoService.selectSellerInfoByUsername(loginDTO.getUsername());
		String shopName = sellerInfo.getShopName();
		String shopIcon = sellerInfo.getShopIcon();
		session.setAttribute("shopName", shopName);
		session.setAttribute("shopIcon", shopIcon);

		//HashMap<String, String> map = new HashMap<>();
		//map.put("code", "1");
		//map.put("msg", "登陆成功");
		//return map;
		return "redirect:/seller/order/list";
	}
}
