package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user") //2차 주소값 주는 방식
public class UserController {
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/";
	}
	
	//model은 request의  setAttribute				// get, put, delete, post CRUD와 같은 역할 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {

		model.addAttribute(Const.TITLE, "LOGIN");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
//	@RequestMapping(value="/login", method = RequestMethod.GET)
//	public ModelAndView login(ModelAndView model) {
//		System.out.println("Controller - login");
//		model.addAttribute(Const.TITLE, "LOGIN");
//		model.addAttribute(Const.VIEW, "user/login");
//		model.setViewName(ViewRef.TEMP_DEFAULT);
//		return model;
//	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
		
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/";
		}
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해 주세요";
		}else if(result == Const.NO_PW) {
			msg= "비밀번호를 확인해 주세요";
		}
		param.setMsg(msg);
		ra.addFlashAttribute("data", param);
		//세션에서 받고 보낼때  지움
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue = "0" ) int err ) {
		if(err > 0) {
			model.addAttribute("msg", "Error가 발생하였습니다.");
		}
		model.addAttribute(Const.TITLE, "Join");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVO param, RedirectAttributes ra) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login"; //주소 이동
		}
		ra.addAttribute("err", result);
		//쿼리스트링처럼 사용
		return "redirect:/user/join";
	}
	@RequestMapping(value = "/ajaxIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		int result = service.login(param);
		return String.valueOf(result);
		// responsebody의 결과물, 값 자체가 응답
	}
	
	@RequestMapping(value = "/ajaxToggleFavorite", method = RequestMethod.GET)
	@ResponseBody
	public int ajaxToggleFavorite(UserPARAM param, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);

		return service.ajaxToggleFavorite(param);
	}
	
	@RequestMapping("/favorite")
	public String restFavorite(Model model) {
		model.addAttribute(Const.CSS, new String[] {"userFavorite"});
		model.addAttribute(Const.TITLE, "찜 리스트");
		model.addAttribute(Const.VIEW, "user/favorite");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
