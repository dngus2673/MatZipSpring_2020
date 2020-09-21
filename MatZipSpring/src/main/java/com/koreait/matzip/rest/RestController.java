package com.koreait.matzip.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestPARAM;
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
	@RequestMapping("/ajaxGetList")
	@ResponseBody public String ajaxGetList(RestPARAM param) {
		System.out.println("sw_lat : " + param.getSw_lat());
		System.out.println("sw_lng : " + param.getSw_lng());
		System.out.println("ne_lat : " + param.getNe_lat());
		System.out.println("ne_lng : " + param.getNe_lng());
		
		return service.selRestList(param);
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		final int I_M = 1;
		
		model.addAttribute(Const.TITLE, "RegPage");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestVO param) {
		return "";
	}
	

}
