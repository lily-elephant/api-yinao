package yinao.qualityLife.controller;




import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import yinao.qualityLife.model.domain.Banner;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Like;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yinao.qualityLife.dao.sourceMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.SourceArticle;
import yinao.qualityLife.model.domain.SourceCatagory;
import yinao.qualityLife.model.domain.SourcePicture;
import yinao.qualityLife.model.domain.SourceVideo;
import yinao.qualityLife.utils.TokenUtils;


@RestController
public class SourceController {

	private final sourceMapper sourceMapper;
	private final TokenUtils tokenUtils;
	

    @Value("${token.header}")
    private String tokenHeader;

	public SourceController(sourceMapper sourceMapper,TokenUtils tokenUtils) {
		this.sourceMapper = sourceMapper;
		this.tokenUtils= tokenUtils;
	}
	//添加资源目录
	@RequestMapping(value="/source/addcatagory",method=RequestMethod.POST)
	public ResultMap addCatagory(@Valid SourceCatagory sourceCatagory,BindingResult bindingResult, HttpServletRequest request) {
		 if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
		 try {
			 int result=this.sourceMapper.addCatagory(sourceCatagory);
			 if(result==1) {
				 return new ResultMap().success().message("添加成功");
			 }else {
				 return new ResultMap().fail("1").message("添加失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
		
	}
	//资源目录详情
	//获取资源目录列表
	@RequestMapping(value="/source/detailcatagory",method=RequestMethod.POST)
	public ResultMap detailCatagory(HttpServletRequest request) {
		if(request.getParameter("scid")==null) {
			return new ResultMap().fail("400").message("缺少参数").data("");
		}
		List<SourceCatagory> sourceCatagories=this.sourceMapper.getDetailCatagory(request.getParameter("scid"));
		if(sourceCatagories.size() != 0) {
			return new ResultMap().success().message("success").data(sourceCatagories);		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
		
	}
	//编辑资源目录
	@RequestMapping(value="/source/editcatagory",method=RequestMethod.POST)
	public ResultMap editCatagor(HttpServletRequest request) {
		if(request.getParameter("scid")==null || request.getParameter("name")==null|| request.getParameter("parentid")==null ||  request.getParameter("sort")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		String scid=request.getParameter("scid");
		String name=request.getParameter("name");
		String sort=request.getParameter("sort");
		String parentid=request.getParameter("parentid");
		String update_at=request.getParameter("update_at");
		try {
			 int result=this.sourceMapper.editCatagory(scid,update_at,name,sort,parentid);
			 if(result==1) {
				 return new ResultMap().success().message("编辑成功");
			 }else {
				 return new ResultMap().fail("1").message("编辑失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("编辑失败");
		}
		
	}
	//删除资源目录
	@RequestMapping(value="/source/deletecatagory",method=RequestMethod.POST)
	public ResultMap deleteCatagor(HttpServletRequest request) {
		if(request.getParameter("scid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			 int result=this.sourceMapper.deleteCatagory(request.getParameter("scid"),request.getParameter("update_at"));
			 if(result==1) {
				 return new ResultMap().success().message("删除成功");
			 }else {
				 return new ResultMap().fail("1").message("删除失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
		
	}
	
	//添加图文资源
	@RequestMapping(value="/source/addarticle",method=RequestMethod.POST)
	public ResultMap addArticle(@Valid SourceArticle sourceArticle,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		 try {
			 int result=this.sourceMapper.addArticle(sourceArticle);
			 if(result==1) {
				 return new ResultMap().success().message("添加成功");
			 }else {
				 return new ResultMap().fail("1").message("添加失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	//编辑图文资源
	@RequestMapping(value="/source/editarticle",method=RequestMethod.POST)
	public ResultMap editArticle(@Valid SourceArticle sourceArticle,BindingResult bindingResult,HttpServletRequest request) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		if(request.getParameter("aid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			 int result=this.sourceMapper.editArticle(sourceArticle);
			 if(result==1) {
				 return new ResultMap().success().message("编辑成功");
			 }else {
				 return new ResultMap().fail("1").message("编辑失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("编辑失败");
		}
	}
	//删除图文资源
	@RequestMapping(value="/source/deletearticle",method=RequestMethod.POST)
	public ResultMap deleteArticle(HttpServletRequest request) {
		if(request.getParameter("aid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			 int result=this.sourceMapper.deleteArticle(request.getParameter("aid"),request.getParameter("update_at"));
			 if(result==1) {
				 return new ResultMap().success().message("删除成功");
			 }else {
				 return new ResultMap().fail("1").message("删除失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
	}
	//图文详情
	@RequestMapping(value="/source/detailarticle",method=RequestMethod.POST)
	public ResultMap detaileArticle(HttpServletRequest request) {
		if(request.getParameter("aid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		List<SourceArticle> sourceArticles=this.sourceMapper.getDetailArticle(request.getParameter("aid"));
		if(sourceArticles.size() != 0) {
			return new ResultMap().success().message("success").data(sourceArticles );		
		}else {
			return new ResultMap().success().message("没有更多数据");
		}
	}
	//添加banner
	@RequestMapping(value="/source/addbanner",method=RequestMethod.POST)
	public ResultMap addBanner(@Valid Banner banner,BindingResult bindingResult) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		try {
			 int result=this.sourceMapper.addBanner(banner);
			 if(result==1) {
				 return new ResultMap().success().message("添加成功");
			 }else {
				 return new ResultMap().fail("1").message("添加失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	//编辑banner
	@RequestMapping(value="/source/editbanner",method=RequestMethod.POST)
	public ResultMap editBanner(@Valid Banner banner,BindingResult bindingResult,HttpServletRequest request) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		if(request.getParameter("bannerid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			 int result=this.sourceMapper.editBanner(banner);
			 if(result==1) {
				 return new ResultMap().success().message("编辑成功");
			 }else {
				 return new ResultMap().fail("1").message("编辑失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("编辑失败");
		}
	}
	
	//删除banner
	@RequestMapping(value="source/deletebanner",method=RequestMethod.POST)
	public ResultMap deleteBanner(HttpServletRequest request) {
		if(request.getParameter("bannerid")==null) {
			 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
		}
		try {
			 int result=this.sourceMapper.deleteBanner(request.getParameter("bannerid"),request.getParameter("update_at"));
			 if(result==1) {
				 return new ResultMap().success().message("删除成功");
			 }else {
				 return new ResultMap().fail("1").message("删除失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("删除失败");
		}
	}
	
	//新增图片资源
	@RequestMapping(value="source/addpicture",method=RequestMethod.POST)
	public ResultMap addPicture(@Valid SourcePicture sourcePicture,BindingResult bindingResult) {
		if (bindingResult.hasErrors()){
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
		try {
			 int result=this.sourceMapper.addPicture(sourcePicture);
			 if(result==1) {
				 return new ResultMap().success().message("添加成功");
			 }else {
				 return new ResultMap().fail("1").message("添加失败");
			 }
			
		} catch (Exception e) {
			return new ResultMap().fail("1").message("添加失败");
		}
	}
	
	//编辑图片资源
		@RequestMapping(value="/source/editpicture",method=RequestMethod.POST)
		public ResultMap editPicture(@Valid SourcePicture sourcePicture,BindingResult bindingResult,HttpServletRequest request) {
			if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
			if(request.getParameter("pid")==null) {
				 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
			}
			try {
				 int result=this.sourceMapper.editPicture(sourcePicture);
				 if(result==1) {
					 return new ResultMap().success().message("编辑成功");
				 }else {
					 return new ResultMap().fail("1").message("编辑失败");
				 }
				
			} catch (Exception e) {
				return new ResultMap().fail("1").message("编辑失败");
			}
		}
		//删除图片资源
		@RequestMapping(value="/source/deletepicture",method=RequestMethod.POST)
		public ResultMap deletePicture(HttpServletRequest request) {
			if(request.getParameter("pid")==null) {
				 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
			}
			try {
				 int result=this.sourceMapper.deletePicture(request.getParameter("pid"),request.getParameter("update_at"));
				 if(result==1) {
					 return new ResultMap().success().message("删除成功");
				 }else {
					 return new ResultMap().fail("1").message("删除失败");
				 }
				
			} catch (Exception e) {
				return new ResultMap().fail("1").message("删除失败");
			}
		}
		//添加视频资源
		@RequestMapping(value="/source/addvideo",method=RequestMethod.POST)
		public ResultMap addVideo(@Valid SourceVideo sourceVideo,BindingResult bindingResult) {
			if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
			try {
				 int result=this.sourceMapper.addVideo(sourceVideo);
				 if(result==1) {
					 return new ResultMap().success().message("添加成功");
				 }else {
					 return new ResultMap().fail("1").message("添加失败");
				 }
				
			} catch (Exception e) {
				return new ResultMap().fail("1").message("添加失败");
			}
		}
		//编辑视频资源
		@RequestMapping(value="/source/editvideo",method=RequestMethod.POST)
		public ResultMap editVideo(@Valid SourceVideo sourceVideo,BindingResult bindingResult,HttpServletRequest request) {
			if (bindingResult.hasErrors()){
	            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
	        }
			if(request.getParameter("vid")==null) {
				 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
			}
			try {
				 int result=this.sourceMapper.editVideo(sourceVideo);
				 if(result==1) {
					 return new ResultMap().success().message("编辑成功");
				 }else {
					 return new ResultMap().fail("1").message("编辑失败");
				 }
				
			} catch (Exception e) {
				return new ResultMap().fail("1").message("编辑失败");
			}
		}
		//删除视频资源
		@RequestMapping(value="/source/deletevideo",method=RequestMethod.POST)
		public ResultMap deleteVideo(HttpServletRequest request) {
			if(request.getParameter("vid")==null) {
				 return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
			}
			try {
				 int result=this.sourceMapper.deleteVideo(request.getParameter("vid"),request.getParameter("update_at"));
				 if(result==1) {
					 return new ResultMap().success().message("删除成功");
				 }else {
					 return new ResultMap().fail("1").message("删除失败");
				 }
				
			} catch (Exception e) {
				return new ResultMap().fail("1").message("删除失败");
			}
		}
		//获取图文列表
		@RequestMapping(value="/source/articlelist",method=RequestMethod.POST)
		public ResultMap articleList(HttpServletRequest request){
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String aid=request.getParameter("aid");
			String name=request.getParameter("name");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<SourceArticle> articleLists=this.sourceMapper.getArticleList(aid,name,offset,pageSize);
			List<CourseCatagoryCount> articleCounts=this.sourceMapper.getArticleCount(aid,name);
			if(articleLists.size() != 0) {
				return new ResultMap().success().message("success").data(articleLists ).count(articleCounts.get(0).getCount());		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
		}
		//获取banner列表
		@RequestMapping(value="/source/bannerlist",method=RequestMethod.POST)
		public ResultMap BannerList(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String bid=request.getParameter("bid");
			String name=request.getParameter("name");
			String product=request.getParameter("product");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<Banner> banners=this.sourceMapper.getBannerList(bid,name,product,offset,pageSize);
			if(banners.size() != 0) {
				return new ResultMap().success().message("success").data(banners );		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}
		//获取后台banner搜索列表
		@RequestMapping(value="/source/bannerlists",method=RequestMethod.POST)
		public ResultMap BannerLists(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null || request.getParameter("state")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String bid=request.getParameter("bid");
			String name=request.getParameter("name");
			String product=request.getParameter("product");
			int state= Integer.parseInt(request.getParameter("state"));
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<Banner> banners=this.sourceMapper.getBannerLists(bid,name,product,state,offset,pageSize);
			List<CourseCatagoryCount> bannerCounts=this.sourceMapper.getBannerCount(bid,name,product,state);
			if(banners.size() != 0) {
				return new ResultMap().success().message("success").data(banners ).count(bannerCounts.get(0).getCount());		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}
		//获取后台banner详情
		@RequestMapping(value="/source/bannerdetail",method=RequestMethod.POST)
		public ResultMap bannerDetail(HttpServletRequest request) {
			if(request.getParameter("bannerid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			List<Banner> banners=this.sourceMapper.getbannerDetail(request.getParameter("bannerid"));
			if(banners.size() != 0) {
				return new ResultMap().success().message("success").data(banners );		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}
		/*//获取资源目录列表
		@RequestMapping(value="/source/catagorylist",method=RequestMethod.POST)
		public ResultMap CatagoryList(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null || request.getParameter("sourcetype")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String name=request.getParameter("name");
			String sourcetype=request.getParameter("sourcetype");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<SourceCatagory> sourceCatagories=this.sourceMapper.getCatagoryList(name,sourcetype,offset,pageSize);
			List<CourseCatagoryCount> sourceCounts=this.sourceMapper.getSourcesCount(name,sourcetype);
			ArrayList<String> catagoryArray= new ArrayList<String>();
			for(SourceCatagory value:sourceCatagories) {
				value.setList(catagoryArray);
			}
			if(sourceCatagories.size() != 0) {
				return new ResultMap().success().message("success").data(sourceCatagories).count(sourceCounts.get(0).getCount());		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}*/
		/*//获取资源目录子级列表
		@RequestMapping(value="/source/childcatagorylist",method=RequestMethod.POST)
		public ResultMap childCatagoryList(HttpServletRequest request) {
			if(request.getParameter("scid")==null || request.getParameter("sourcetype")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String sourcetype=request.getParameter("sourcetype");
			String scid=request.getParameter("scid");
			List<SourceCatagory> sourceCatagories=this.sourceMapper.getChildCatagoryList(scid,sourcetype);
			
			if(sourceCatagories.size() != 0) {
				return new ResultMap().success().message("success").data(sourceCatagories );		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}*/
		//资源目录列表
		@RequestMapping(value="/source/getcatagorylist",method=RequestMethod.POST)
		public ResultMap getcatagorylist(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null || request.getParameter("sourcetype")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String sourcetype=request.getParameter("sourcetype");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<SourceCatagory> sourceCatagories=this.sourceMapper.getCatagoryList(sourcetype,offset,pageSize);
			List<SourceCatagory> childSourceCatagories=this.sourceMapper.getChildCatagoryList(sourcetype);
			List<SourceCatagory> threeSourceCatagories=this.sourceMapper.getThreeCatagoryList(sourcetype);
			List<CourseCatagoryCount> sourceCounts=this.sourceMapper.getSourcesCount(sourcetype);
			for(SourceCatagory value : sourceCatagories) {
				ArrayList<SourceCatagory> childArray=new ArrayList<SourceCatagory>();
				for(SourceCatagory child : childSourceCatagories) {
					ArrayList<SourceCatagory> threeChildArray=new ArrayList<SourceCatagory>();
					if(child.getParentid().equals(value.getScid())) {
						childArray.add(child);	
					}
					for(SourceCatagory three :threeSourceCatagories) {
						if(three.getParentid().equals(child.getScid())) {
							threeChildArray.add(three);
						}
					}
					child.setList(threeChildArray);
				}
				value.setList(childArray);
			}	
			return new ResultMap().success().message("success").data(sourceCatagories).count(sourceCounts.get(0).getCount());
			
		}
		//获取图片列表
		@RequestMapping(value="/source/picturelist",method=RequestMethod.POST)
		public ResultMap PictureList(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String scid=request.getParameter("scid");
			String name=request.getParameter("name");
			String catagoryname=request.getParameter("catagoryname");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<SourcePicture> sourcePictures=this.sourceMapper.getPictureList(scid,name,catagoryname,offset,pageSize);
			List<CourseCatagoryCount> pictureCounts=this.sourceMapper.getPictureCount(scid,name,catagoryname);
			if(sourcePictures.size() != 0) {
				return new ResultMap().success().message("success").data(sourcePictures ).count(pictureCounts.get(0).getCount());		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}
		
		//获取视频列表
		@RequestMapping(value="/source/videolist",method=RequestMethod.POST)
		public ResultMap VideoList(HttpServletRequest request) {
			if(request.getParameter("pageindex")==null || request.getParameter("pagecount")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String scid=request.getParameter("scid");
			String name=request.getParameter("name");
			String catagoryname=request.getParameter("catagoryname");
			int currPage = Integer.parseInt(request.getParameter("pageindex"));//1 2 
			int pageSize = Integer.parseInt(request.getParameter("pagecount"));//10
			int offset=(currPage-1)*pageSize;
			List<SourceVideo> sourceVideos=this.sourceMapper.getVideoList(scid,name,catagoryname,offset,pageSize);
			List<CourseCatagoryCount> videoCounts=this.sourceMapper.getVideoCount(scid,name,catagoryname);
			if(sourceVideos.size() != 0) {
				return new ResultMap().success().message("success").data(sourceVideos ).count(videoCounts.get(0).getCount());		
			}else {
				return new ResultMap().success().message("没有更多数据");
			}
			
		}
		
		//雇主给家政人员点赞
		@RequestMapping(value="/source/getlike",method=RequestMethod.POST)
		public ResultMap getLike(HttpServletRequest request) {
			if(request.getParameter("hkid")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
	        String token = request.getHeader(this.tokenHeader);
	        String username = this.tokenUtils.getUsernameFromToken(token);
	        String usertype = this.tokenUtils.getUserTypeFromToken(token);
	        List<Like> likeCounts=this.sourceMapper.getLikeCount(request.getParameter("hkid"),username,usertype);
	        if(likeCounts.get(0).getLikeCount()==0) {
	        	try {
					int result=this.sourceMapper.getLike(request.getParameter("hkid"),username,usertype);
					if(result==1) {
						return new ResultMap().success().message("点赞成功");
					}else {
						return new ResultMap().fail("1").message("点赞失败");
					}
					
				} catch (Exception e) {
					return new ResultMap().fail("1").message("点赞失败");
				}
	        }else {
	        	try {
					int result=this.sourceMapper.deleteLike(request.getParameter("hkid"),username,usertype);
					if(result==1) {
						return new ResultMap().success().message("取消点赞成功");
					}else {
						return new ResultMap().fail("1").message("取消点赞失败");
					}
					
				} catch (Exception e) {
					return new ResultMap().fail("1").message("取消点赞失败");
				}
	        }    
	     }
		//更改banner状态
		@RequestMapping(value="/source/changebannerstate",method=RequestMethod.POST)
		public ResultMap changeBannerState(HttpServletRequest request) {
			if(request.getParameter("bannerid")==null || request.getParameter("state")==null) {
				return new ResultMap().fail("400").message("缺少参数").data("");
			}
			String bannerId=request.getParameter("bannerid");
			String state=request.getParameter("state");
			String update_at=request.getParameter("update_at");
			try {
				int result=this.sourceMapper.changeBannerState(bannerId,state,update_at);
				if(result==1) {
					 return new ResultMap().success().message("修改成功");
				 }else {
					 return new ResultMap().fail("1").message("修改失败");
				 }
			} catch (Exception e) {
				 return new ResultMap().fail("1").message("修改失败");
			}
		}
	
	}
