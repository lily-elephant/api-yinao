package yinao.qualityLife.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yinao.qualityLife.dao.SystemMesMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Certificate;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.SystemMessage;

@RestController
public class SystemController {
	private  final SystemMesMapper SystemMesMapper ;
	public SystemController( SystemMesMapper SystemMesMapper) {
		super();
		// TODO Auto-generated constructor stub
		this.SystemMesMapper=SystemMesMapper;
	}
	
	//系统消息列表
	@RequestMapping(value="/system/getsystemmessage",method=RequestMethod.POST)
	public ResultMap getSystemMessage(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<SystemMessage> systemMessages=this.SystemMesMapper.getSystemMessage(offset,pageSize);
		List<CourseCatagoryCount> systemMesCount=this.SystemMesMapper.getSystemMesCount();
		if(systemMessages.size()!=0) {
			return new ResultMap().success().message("success").count(systemMesCount.get(0).getCount()).data(systemMessages);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}

	}
	
	//添加系统消息
	@RequestMapping(value="/system/addsysmessage",method=RequestMethod.POST)
	public ResultMap addSysMessage(@Valid SystemMessage systemMessage,BindingResult bindingResult) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		int result=this.SystemMesMapper.addSysMessage(systemMessage);
		if(result==1) {
			return new ResultMap().success().message("添加成功");
		}else {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	//系统消息详情
	@RequestMapping(value="/system/detailsysmessage",method=RequestMethod.POST)
	public ResultMap detailSysMessage(HttpServletRequest request) {
		if(request.getParameter("smid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		List<SystemMessage> systemMessages=this.SystemMesMapper.detailSysMessage(request.getParameter("smid"));
		if(systemMessages.size()!=0) {
			return new ResultMap().success().message("success").data(systemMessages);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	//编辑系统消息
	@RequestMapping(value="/system/updatesysmessage",method=RequestMethod.POST)
	public ResultMap updateSysMessage(@Valid SystemMessage systemMessage,BindingResult bindingResult,HttpServletRequest request) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		if(request.getParameter("smid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		int result=this.SystemMesMapper.updateSysMessage(systemMessage);
		if(result==1) {
			return new ResultMap().success().message("添加成功");
		}else {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	
	//平台证书列表
	@RequestMapping(value="/admin/certificate",method=RequestMethod.POST)
	public ResultMap certificate(HttpServletRequest request) {
		if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
		int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
		int offset=(currPage-1)*pageSize;
		List<Certificate> certificates=this.SystemMesMapper.getCertificate(offset,pageSize);
		List<CourseCatagoryCount> certificateCount=this.SystemMesMapper.getCertificateCount();
		if(certificates.size()!=0) {
			return new ResultMap().success().message("success").count(certificateCount.get(0).getCount()).data(certificates);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	//平台添加证书
	@RequestMapping(value="/admin/addcertificate",method=RequestMethod.POST)
	public ResultMap addcertificate(HttpServletRequest request) {
		String name=request.getParameter("name");
		String picture=request.getParameter("picture");
		String brief=request.getParameter("brief");
		String sort=request.getParameter("sort");
		if(name==null|| picture==null || brief==null || sort==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int result=this.SystemMesMapper.addCertificate(name,picture,brief,sort);
		if(result==1) {
			return new ResultMap().success().message("添加成功").data("");
		}else {
			return new ResultMap().success().message("添加失败");
		}
	}
	//证书详情
	@RequestMapping(value="/admin/getcertificatedetail",method=RequestMethod.POST)
	public ResultMap getCertificateDetail(HttpServletRequest request) {
		if(request.getParameter("cid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<Certificate> certificates=this.SystemMesMapper.getCertificateDetail(request.getParameter("cid"));
		if(certificates.size()!=0) {
			return new ResultMap().success().message("success").data(certificates);
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	//编辑证书
	@RequestMapping(value="/admin/updatecertificate",method=RequestMethod.POST)
	public ResultMap updateCertificate(HttpServletRequest request) {
		String name=request.getParameter("name");
		String picture=request.getParameter("picture");
		String brief=request.getParameter("brief");
		String sort=request.getParameter("sort");
		String cid=request.getParameter("cid");
		if(name==null|| picture==null || brief==null || sort==null || cid==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int result=this.SystemMesMapper.updateCertificate(name,picture,brief,sort,cid);
		if(result==1) {
			return new ResultMap().success().message("编辑成功").data("");
		}else {
			return new ResultMap().success().message("编辑失败");
		}
	}
	//删除证书
	@RequestMapping(value="/admin/deletecertificate",method=RequestMethod.POST)
	public ResultMap deleteCertificate(HttpServletRequest request) {
		String cid=request.getParameter("cid");
		if(cid==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		int result=this.SystemMesMapper.deleteCertificate(cid);
		if(result==1) {
			return new ResultMap().success().message("删除成功").data("");
		}else {
			return new ResultMap().success().message("删除失败");
		}
	}
	
}
