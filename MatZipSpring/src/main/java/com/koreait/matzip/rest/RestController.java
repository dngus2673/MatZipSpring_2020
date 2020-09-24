package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.koreait.matzip.rest.model.RestVO;

@Controller
@RequestMapping("/rest")
public class RestController {
	
	@Autowired
	private RestService service;
	
	@RequestMapping("/map")
	public String restMap(Model model) {
		
		model.addAttribute(Const.TITLE, "MapPage");
		model.addAttribute(Const.VIEW, "rest/restMap");
		
		return ViewRef.TEMP_MENU_TEMP;
	}
	@RequestMapping(value = "/ajaxGetList", produces={"application/json; charset=UTF-8"})
	@ResponseBody 
	public List<RestDMI> ajaxGetList(RestPARAM param) {
		param.getSw_lat();
		param.getSw_lng();
		param.getNe_lat();
		param.getNe_lng();
//		System.out.println("sw_lat : " + param.getSw_lat());
//		System.out.println("sw_lng : " + param.getSw_lng());
//		System.out.println("ne_lat : " + param.getNe_lat());
//		System.out.println("ne_lng : " + param.getNe_lng());
		
		return service.selRestList(param);
	}
	
	@RequestMapping("/reg")
	public String restReg(Model model) {
		
		model.addAttribute("categoryList", service.selCategoryList());
		
		model.addAttribute(Const.TITLE, "RegPage");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		
		param.setI_user(SecurityUtils.getLoginUserPk(hs));
		
		int result = service.restReg(param);
		
		if(result == 1) {
			return "redirect:/";			
		}else {
			return "redirect:/rest/reg";			
		}
		
	}
	@RequestMapping("/detail")
	public String detail(Model model, RestPARAM param) {
		RestDMI data = service.selRest(param);
		
		model.addAttribute("data", data);
		model.addAttribute(Const.TITLE, data.getNm()); // 가게명
		model.addAttribute(Const.VIEW, "rest/restDetail");
		model.addAttribute("recMenuList", service.selRestRecMenus(param));
		model.addAttribute("css", new String[] {"restaurant"});
		
		model.addAttribute("menuList", service.selRestMenus(param));
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping("/del")
	public String del(RestPARAM param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		
		int result = 1;
		try {
			service.delRestTran(param);
		}catch(Exception e) { result = 0; }
		System.out.println("result : " + result);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/recMenus", method = RequestMethod.POST)
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		
		int i_rest = service.insRecMenus(mReq);
		
		ra.addAttribute("i_rest", i_rest);
		
		return "redirect:/rest/detail";
		
//		for(MultipartFile file : fileList) {
//			System.out.println("isEmpty : " + file.isEmpty());
//			System.out.println("file : " + file.getOriginalFilename());
//		}
//		for (int i = 0; i < menuNmArr.length; i++) {
//			System.out.println("menuNm : " + menuNmArr[i]);
//			System.out.println("menuPrice : " + menuPriceArr[i]);
//		}	
	}
	@RequestMapping("/ajaxDelRecMenu")
	@ResponseBody public int ajaxDelRecMenu(RestPARAM param, HttpSession hs) {
		
		String path = "/resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		String realPath = hs.getServletContext().getRealPath(path);
		param.setI_user(SecurityUtils.getLoginUserPk(hs)); // 로그인 유저 pk담기
		
		return service.delRecMenu(param, realPath);
	}
	@RequestMapping("/menus")
	public String menus(RestFile param, HttpSession hs, RedirectAttributes ra) {
		
		int i_user = SecurityUtils.getLoginUserPk(hs);
		
		int result = service.insRestMenu(param, i_user);
		
		ra.addAttribute("i_rest", param.getI_rest());
		return "redirect:/rest/detail";
	}
}
