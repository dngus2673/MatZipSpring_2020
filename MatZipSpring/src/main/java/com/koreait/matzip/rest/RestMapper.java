package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;
import com.koreait.matzip.rest.model.RestVO;

@Mapper
public interface RestMapper {
	/* INSERT */
	int insRest(RestPARAM param);
	int insRestMenu(RestRecMenuVO param);
	int insRestRecMenu(RestRecMenuVO param);
	/* SELECT */
	int selRestChkUser(int param);
	RestDMI selRest(RestPARAM param);
	List<RestDMI> selRestList(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	List<RestRecMenuVO> selRestMenus(RestPARAM param);
	/* DELETE */
	int delRestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
}
