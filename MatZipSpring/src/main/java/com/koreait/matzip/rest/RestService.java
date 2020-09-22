package com.koreait.matzip.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestVO;

@Service
public class RestService {
	
	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public RestDMI selRest(RestPARAM param){
		return mapper.selRest(param);
	}
	
	public List<RestDMI> selRestList(RestPARAM param){
		return mapper.selRestList(param);
	}
	
	public List<CodeVO> selCategoryList(){
		CodeVO p =new CodeVO();
		p.setI_m(1);
		return cMapper.selCodeList(p);
	}
	
	public int restReg(RestPARAM param) {
		return mapper.insRest(param);
	}
} 
