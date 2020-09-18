package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user") //2차 주소값 주는 방식
public class UserController {
	@Autowired
	private UserService service;
	
	//model은 request의  setAttribute				// get, put, delete, post CRUD와 같은 역할 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(Const.TITLE, "LOGIN");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserDTO param) {
		int result = service.login(param);
		if(result == 1) {
			return "redirect:/rest/map";
		}
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue = "0" ) int err ) {
		System.out.println("err :  "+ err);
		if(err > 0) {
			model.addAttribute("msg", "Error가 발생하였습니다.");
		}
		model.addAttribute(Const.TITLE, "Join");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO param) {
		int result = service.join(param);
		if(result == 1) {
			return "redirect:/user/login";
		}
	return "redirect:/user/join?err=" + result;
	}
}
