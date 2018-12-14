package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.Certificate;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.SystemMessage;
@Mapper
@Component
public interface SystemMesMapper {
	//获取系统消息列表
	List<SystemMessage> getSystemMessage(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取系统消息总数
	List<CourseCatagoryCount> getSystemMesCount();
	//添加系统消息
	int addSysMessage(SystemMessage systemMessage);
	//系统消息详情
	List<SystemMessage> detailSysMessage(@Param("smid") String  smid);
	//编辑系统消息
	int updateSysMessage(SystemMessage systemMessage);
	//系统证书列表
	List<Certificate> getCertificate(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//证书列表个数
	List<CourseCatagoryCount> getCertificateCount();
	//添加证书
	int addCertificate(@Param("name") String name,
			@Param("picture") String picture,
			@Param("brief") String brief,
			@Param("sort") String sort
			);
	//证书详情
	List<Certificate> getCertificateDetail(@Param("cid") String cid);
	//编辑证书
	int updateCertificate(@Param("name") String name,
			@Param("picture") String picture,
			@Param("brief") String brief,
			@Param("sort") String sort,
			@Param("cid") String cid
			
			);
	//删除证书
		int deleteCertificate(@Param("cid") String cid);
}
