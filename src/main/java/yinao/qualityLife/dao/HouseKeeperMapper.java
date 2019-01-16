package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.EmployerComment;
import yinao.qualityLife.model.domain.EmployerHouseKeeper;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.HousekeeperCertificate;
import yinao.qualityLife.model.domain.LikeBean;
import yinao.qualityLife.model.vo.DataCount;


@Mapper
@Component
public interface HouseKeeperMapper {

	//获取家政列表总记录数
	DataCount dataCount(
			@Param("username") String username,
			@Param("name") String name,
			@Param("state") String state,
			@Param("servicestate") String servicestate,
			@Param("sage") String sage,
			@Param("eage") String eage,
			@Param("nativeplace") String nativeplace,
			@Param("education") String education);

	//获取家政端列表  详情页同上 
	List<HouseKeeper> houseKeeperList(
			@Param("username") String username,
			@Param("name") String name,
			@Param("state") String state
			);
	//获取联系家政人员列表
	List<HouseKeeper> houseKeeperContractList(
			@Param("emid") String emid,
			@Param("hkid") String hkid,
			@Param("currpage") int currpage,
			@Param("pagesize") int pagesize
			);
	
	//获取雇主联系家政人员所有信息列表
	List<EmployerHouseKeeper> emhouseKeeperContractList(
			@Param("emname") String emname,
			@Param("hkname") String hkname,
			@Param("emphone") String emphone,
			@Param("hkphone") String hkphone,
			@Param("pageindex") int pageindex,
			@Param("pagecount") int pagecount
			);
	//获取雇主签约家政人员所有信息列表
	List<EmployerHouseKeeper> emhouseKeeperSignList(
			@Param("emname") String emname,
			@Param("hkname") String hkname,
			@Param("emphone") String emphone,
			@Param("hkphone") String hkphone,
			@Param("pageindex") int pageindex,
			@Param("pagecount") int pagecount
			);
	//获取雇主签约家政人员详细
	EmployerHouseKeeper emhouseKeeperSignDetail(
			@Param("primaryid") String primaryid
			);
	
	//获取家政端列表  详情页同上 
	List<HouseKeeper> houseKeeperStarList(
			@Param("emid") String emid,
			@Param("hkid") String hkid
			);
	
	List<HouseKeeper> adminhouseKeeperList(
			@Param("currpage") int currpage,
			@Param("pagesize") int pagesize,
			@Param("username") String username,
			@Param("name") String name,
			@Param("state") String state,
			@Param("servicestate") String servicestate,
			@Param("sage") String sage,
			@Param("eage") String eage,
			@Param("nativeplace") String nativeplace,
			@Param("education") String education);
	
	//修改家政信息接口
	int updateEmployerInfo(
			@Param("servicestate") String servicestate,
			@Param("username") String username,
			@Param("name") String name,
			@Param("headimageurl") String headimageurl,
			@Param("headvideourl") String headvideourl,
			@Param("idcard") String idcard,
			@Param("education") String education,
			@Param("address1") String address1,
			@Param("address2") String address2,
			@Param("address3") String address3,
			@Param("sex") String sex,
			@Param("role_id") String role_id,
			@Param("brief") String brief,
			@Param("salary") String salary,
			@Param("workdate") String workdate,
			@Param("marry") String marry,
			@Param("isdrive") String isdrive,
			@Param("describes") String describes,
			@Param("nativeplace") String nativeplace,
			@Param("nowsalary") String nowsalary,
			@Param("frompart") String frompart);
			//@Param("state") String state); 
	//删除家政信息接口
	int deletehousekeeper(
			@Param("username") String username);
	
	// 前台显示家政人员信息
	int showhousekeeper(
			@Param("username") String username,
			@Param("state") String state
			);	
	
	//新增家政人员信息接口
	int addhousekeeper(HouseKeeper housekeeper);
	
	 List<LikeBean> houseKeeperLikeCount(
			 @Param("hkid") String hkid
	);
	 
	//获取雇主订单信息接口
	 List<EmployerComment> housekeepercomment(
			 @Param("hkid") String hkid,@Param("emid") String emid
	);
	 
	 //获取家政人员证书信息
	 List<HousekeeperCertificate> certificatelist(
			 @Param("hkid") String hkid
	) ;
	 //添加家政人員證書
	 int addCertificate(
			 @Param("hkid") String hkid,
			 @Param("cid") String cid,
			 @Param("picture") String picture
	);
	 //刪除家政人员证书
	int deleteCertificate(
			@Param("hkid") String hkid,
			@Param("cid") String cid
	);
	//更新签约信息
	int updateemhouseKeeperSignDetail(EmployerHouseKeeper params);		
   

}
