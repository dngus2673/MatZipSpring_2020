package com.koreait.matzip.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface UserMapper {
	List<UserDMI> selFavoriteList(UserPARAM param);
	int insUser(UserVO param);
	UserDMI selUser(UserPARAM param);
	int insFavorite(UserPARAM param);
	int delFavorite(UserPARAM param);
}
