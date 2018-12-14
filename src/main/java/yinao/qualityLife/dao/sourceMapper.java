package yinao.qualityLife.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.Banner;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Like;
import yinao.qualityLife.model.domain.SourceArticle;
import yinao.qualityLife.model.domain.SourceCatagory;
import yinao.qualityLife.model.domain.SourcePicture;
import yinao.qualityLife.model.domain.SourceVideo;

@Mapper
@Component
public interface sourceMapper {
	//添加资源目录
	public int addCatagory(SourceCatagory sourceCatagory);
	//资源目录详情
	public List<SourceCatagory> getDetailCatagory(@Param("scid") String scid);
	//编辑资源目录
	public int editCatagory(@Param("scid") String scid,@Param("update_at") String update_at,@Param("name") String name,@Param("sort") String sort,@Param("parentid") String parentid);
	//删除资源目录
	public int deleteCatagory(@Param("scid") String scid,@Param("update_at") String update_at);
	//添加图文资源
	public int addArticle(SourceArticle sourceArticle);
	//图文资源详情
	public List<SourceArticle> getDetailArticle(@Param("aid") String aid);
	//编辑图文资源
	public int editArticle(SourceArticle sourceArticle);
	//删除图文资源
	public int deleteArticle(@Param("aid") String aid,@Param("update_at") String update_at);
	//添加banner
	public int addBanner(Banner banner);
	//编辑banner
	public int editBanner(Banner banner);
	//删除banner
	public int deleteBanner(@Param("bannerid") String bannerid,@Param("update_at") String update_at);
	//添加图片资源
	public int addPicture(SourcePicture sourcePicture);
	//编辑图片资源
	public int editPicture(SourcePicture sourcePicture);
	//删除图片资源
	public int deletePicture(@Param("pid") String pid,@Param("update_at") String update_at);
	//添加视频资源
	public int addVideo(SourceVideo sourceVideo);
	//编辑视频资源
	public int editVideo(SourceVideo sourceVideo);
	//删除图片资源
	public int deleteVideo(@Param("vid") String vid,@Param("update_at") String update_at);
	//获取图文资源列表
	public List<SourceArticle> getArticleList(@Param("aid") String aid,@Param("name") String name,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取图文总数
	public List<CourseCatagoryCount> getArticleCount(@Param("aid") String aid,@Param("name") String name);
	//获取banner列表
	public List<Banner> getBannerList(@Param("bid") String aid,@Param("name") String name,@Param("product") String product,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取后台banner搜索列表
	public List<Banner> getBannerLists(@Param("bid") String aid,@Param("name") String name,@Param("product") String product,@Param("state") int state,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取banner列表总数
	public List<CourseCatagoryCount> getBannerCount(@Param("bid") String aid,@Param("name") String name,@Param("product") String product,@Param("state") int state);
	//获取资源目录列表
	public List<SourceCatagory> getCatagoryList(@Param("sourcetype") String sourcetype,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取资源总数
	public List<CourseCatagoryCount> getSourcesCount(@Param("sourcetype") String sourcetype);
	//获取资源目录子级列表
	public List<SourceCatagory> getChildCatagoryList(@Param("sourcetype") String sourcetype);
	//资源目录三级列表
	public List<SourceCatagory> getThreeCatagoryList(@Param("sourcetype") String sourcetype);
	//获取图片列表
	public List<SourcePicture> getPictureList(@Param("scid") String scid,@Param("name") String name,@Param("catagoryname") String catagoryname,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//图片个数
	public List<CourseCatagoryCount> getPictureCount(@Param("scid") String scid,@Param("name") String name,@Param("catagoryname") String catagoryname);
	//获取视频列表
	public List<SourceVideo> getVideoList(@Param("scid") String scid,@Param("name") String name,@Param("catagoryname") String catagoryname,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取视频总数
	public List<CourseCatagoryCount> getVideoCount(@Param("scid") String scid,@Param("name") String name,@Param("catagoryname") String catagoryname);
	//雇主对家政人员点赞
	public int  getLike(@Param("hkid") String hkid,@Param("username") String username,@Param("usertype") String usertype);
	//获取家政人员一共获得的点赞数
	public List<Like> getLikeCount(@Param("hkid") String hkid,@Param("username") String username,@Param("usertype") String usertype);
	//取消对家政人员的点赞
	public int deleteLike(@Param("hkid") String hkid,@Param("username") String username,@Param("usertype") String usertype);
	//banner更换状态
	public int changeBannerState(@Param("bannerId") String bannerId,@Param("state") String state,@Param("update_at") String update_at);
	//获取后台banner列表
	public List<Banner> getSearchBannerLists(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取后台banner列表总数
	public List<CourseCatagoryCount> getSearchBannerCount();
	public List<Banner> getbannerDetail(@Param("bannerid") String bannerid);
	//获取图片最大sort
	public List<SourcePicture> getSorts();
	//获取video最大sort
	public List<SourceVideo> getVideoSorts();
	//资源目录列表
	public List<SourceCatagory> getSourceCatagoryList(@Param("sourcetype") String sourcetype,@Param("pageindex") String pageindex,@Param("pagecount") String pagecount);
	}
