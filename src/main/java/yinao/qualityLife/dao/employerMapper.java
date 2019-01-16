package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.Employer;
import yinao.qualityLife.model.domain.EmployerSign;
import yinao.qualityLife.model.domain.ViewCard;
import yinao.qualityLife.model.domain.ViewCardPrice;

@Mapper
@Component
public interface employerMapper {
	
	//获取雇主信息列表
	List<Employer> getEmployerInfo(
			@Param("username") String username,
			@Param("name") String name,
			@Param("servicestate") String servicestate); 
	//获取雇主签约列表
	List<EmployerSign> getSignList(
			@Param("emid") String emid,
			@Param("hkid") String hkid); 
	//获取雇主查询卡信息
	ViewCard getViewCard(
			@Param("emid") String emid,
			@Param("ccid") String ccid); 
	
	List<ViewCardPrice> getViewCardPrice(
			@Param("ccid") String ccid); 
	
	Employer getEmployerByUserName(
			@Param("username") String username); 
	
	//编辑雇主信息
	int updateEmployerInfo(
			@Param("username") String username,
			@Param("name") String name,
			@Param("idcard") String idcard,
			@Param("education") String education,
			@Param("address1") String address1,
			@Param("address2") String address2, 
			@Param("address3") String address3,
			@Param("headimageurl") String headimageurl,
			@Param("servicestate") String servicestate,
			@Param("sex") String sex,
			@Param("addresslabel") String addresslabel,
			@Param("roomarea") String roomarea,
			@Param("courtarea") String courtarea,
			@Param("bedroom") String bedroom,
			@Param("restaurant") String restaurant,
			@Param("washroom") String washroom,
			@Param("peoplecount") String peoplecount,
			@Param("oldcount") String oldcount,
			@Param("childcount") String childcount,
			@Param("describes") String describes
			);
	// 后台删除雇主人员信息
	int deleteEmployer(
			@Param("username") String username,
			@Param("state") String state
			);	
	// 取消收藏
	int deleteEmployerStar(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);	
	// 添加收藏
	int insertEmployerStar(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);
	// 添加签约
	int insertSign(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);	
	// 添加联系
	int insertContract(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);	
	// 更新联系
	int updateContract(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);
	
	int addViewCard(
			@Param("emid") String emid,
			@Param("ccid") String ccid,
			@Param("totalcount") String totalcount,
			@Param("remaincount") String remaincount
			);
	int updateViewCard(
			@Param("emid") String emid,
			@Param("ccid") String ccid,
			@Param("totalcount") String totalcount
			);
	int updateRemainCount(
			@Param("emid") String emid,
			@Param("ccid") String ccid
			);
	
}
