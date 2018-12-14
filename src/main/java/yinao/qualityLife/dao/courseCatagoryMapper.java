package yinao.qualityLife.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;

import yinao.qualityLife.model.domain.Courses;
import yinao.qualityLife.model.domain.GetHkid;
import yinao.qualityLife.model.domain.HouseKeeper;
import yinao.qualityLife.model.domain.MyCourse;
import yinao.qualityLife.model.domain.CourseCatagory;
import yinao.qualityLife.model.domain.CourseCatagoryCount;

@Mapper
@Component

public interface courseCatagoryMapper {

	//获取课程模块信息
	public List<CourseCatagory> getCourseCatagoryList(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取课程信息
	public List<Courses> getCourseList(@Param("ccid") String ccid,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//添加课程模块
	public int addCourseCatagory(@Param("name") String name,
								 @Param("sort") String sort,
								 @Param("description") String description,
								 @Param("ismust") String ismust,
								 @Param("create_at") String create_at,
								 @Param("picture") String picture,
								 @Param("discount") String discount,
								 @Param("aid") String aid
				
			);
	//编辑课程模块
	public int editCourseCatagory(
			 @Param("ccid") String ccid,
			 @Param("name") String name,
			 @Param("sort") String sort,
			 @Param("description") String description,
			 @Param("ismust") String ismust,
			 @Param("update_at") String update_at,
			 @Param("picture") String picture,
			 @Param("discount") String discount,
			 @Param("aid") String aid
			);
	//删除课程模块
	public int deleteCourseCatagory( 
			 @Param("ccid") String ccid,
			 @Param("update_at") String update_at
			 );
	
	//添加课程
	public int addCourse(Courses courses);
	
	//编辑课程
	public int editCourse(Courses courses);
	//删除课程
	public int deleteCourse(@Param("cid") String cid,@Param("update_at") String update_at);
	//获取课程模块总得纪录条数
	public List<CourseCatagoryCount> getCourseCatagoryCount();
	//获取课程的纪录条数
	public List<CourseCatagoryCount> getCourseCount(@Param("ccid") String ccid);
	//获取我的课程记录条数
	public List<CourseCatagoryCount> getMyCourseCount(@Param("ccid") String  ccid,@Param("hkid") String hkid);
	//我的课程列表
	public List<MyCourse> getMyCourseList(@Param("ccid") String  ccid,@Param("hkid") String hkid,@Param("offset") int offset,@Param("pageSize") int pageSize);
	//获取家政hkid
	public List<GetHkid> getHkid(@Param("username") String username);
	//判断家政是否购买了该课程
	public List<CourseCatagoryCount> getOrderCourseCount(@Param("cid") String cid,@Param("hkid") String hkid);
	//获取课程模块的详情内容
	public List<CourseCatagory> getCourseCatagoryDetail(@Param("ccid") String ccid);
	//获取课程详情内容
	public List<Courses> getCourseDetail(@Param("cid") String cid);
	//课程详情页浏览递增
	public int addViewCount(@Param("cid") String cid);
	//课程模块考试单选总数
	public List<CourseCatagoryCount> getSingleCount(@Param("ccid") String ccid);
	//课程模块考试多选总数
	public List<CourseCatagoryCount> getMultipleCount(@Param("ccid") String ccid);
	//查找购买课程所属模块
	public List<CourseCatagory> gettestCourseCatagory(@Param("hkid") String hkid);
	//查找已购买模块下的课程总数
	public List<CourseCatagoryCount> getOrderCount(@Param("hkid") String hkid,@Param("ccid") String ccid);
	//查找模块下课程的总数
	public List<CourseCatagoryCount> getCatagoryCourseCount(@Param("ccid") String ccid);
	//获取需要考试的模块
	public List<CourseCatagory> gettestCatagory(@Param("ccidArray") ArrayList<String> ccidArray);
	//获取模块下试题的总数
	public List<CourseCatagoryCount> getExamCount(@Param("ccid") String ccid);
	//核对模块下是否有试题
	public List<CourseCatagoryCount> getUseCcidCount(@Param("ccid") String ccid);
	//课程模块下拉框
	public List<CourseCatagory> selectCourseCatagory();
	//课程模块搜索列表
	public List<CourseCatagory> serachCourseCatagoryList(@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("name") String name);
	public List<CourseCatagoryCount> seachCourseCatagoryCount(@Param("name") String name);
	//后台课程列表
	public List<Courses> getAdminCourseList(@Param("ccid") String ccid,@Param("name") String name,@Param("offset") int offset,@Param("pageSize") int pageSize);
	public List<CourseCatagoryCount> getAdminCourseCount(@Param("ccid") String ccid,@Param("name") String name);
	//课程详情
	public List<Courses> getAdminCourseDetail(@Param("cid") String cid);
	//获取雇主id
	public List<GetHkid> getEmid(@Param("username") String username);
	//获取课程模块下的课程id
	public List<GetHkid> getCid(@Param("ccid") String ccid);
	//向employ订单表里插入订单
	public int addEmployOrder(@Param("emid") String emid,
				@Param("orderNo") String orderNo,
				@Param("money") String money,
				@Param("transactionid") String transactionid,
				@Param("businesstype") String businesstype,
				@Param("osid") String osid
			);
	//向hoursekeep订单表里插入订单
	public int addHouseKeepOrder(@Param("hkid") String hkid, 
			@Param("orderNo") String orderNo,
			@Param("money") String money,
			@Param("transactionid") String transactionid,
			@Param("businesstype") String businesstype
		);
	//向coursed订单表里插入订单
	public int addOrder(@Param("hkid") String hkid,@Param("cid") String cid);
	//查询家政是否存在针对某个课程的订单
	public List<CourseCatagoryCount> getOrderCourse(@Param("hkid") String hkid,@Param("cid") String cid);
	//删除家政针对某个课程的重复订单
	public int deteleOrderCourse(@Param("hkid") String hkid,@Param("cid") String cid);
	//支付回调bill表插入数据
	public int billOrder(@Param("money") String money,
			@Param("orderNum") String orderNum,
			@Param("threeNum") String threeNum,
			@Param("businesstype") String businesstype,
			@Param("hkid") String hkid,
			@Param("transactionid") String transactionid
			);
	//获取家政余额
	public List<CourseCatagoryCount> getBalance(@Param("hkid") String hkid);
	//更改orderCourse订单状态
	public int updateOrderState(@Param("cid") String cid,@Param("hkid") String hkid);
	//更改家政余额
	public int updateHouseBalance(@Param("hkid") String hkid,@Param("balance") int balance);
	//雇主billemployer表插入支付信息
	public int billEmployer(@Param("emid") String emid,
			@Param("orderNum") String orderNum,
			@Param("threeNum") String threeNum,
			@Param("businesstype") String businesstype,
			@Param("money") String money,
			@Param("osid") String osid
			);
	//家政购买时billhousekeeper里边插入数据
	public int addHouseKeep(@Param("hkid") String hkid,
			@Param("orderNo") String orderNo,
			@Param("money") String money,
			@Param("transactionid") String transactionid,
			@Param("businesstype") String businesstype
			);
	//生成预支付时更改雇主支付薪水订单数据
	public int updateEmployer(@Param("billid") String billid,@Param("orderNo") String orderNo);
	//匹配家政人员列表
	public List<HouseKeeper> getHouseKeeper(@Param("username") String username,@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("matchcount") String matchcount);
	//匹配家政人员总数
	public List<CourseCatagoryCount> getHouseKeeperCount(@Param("username") String username,@Param("matchcount") String matchcount);
	//匹配家政人员的点赞数
	public List<CourseCatagoryCount> getLikeCount(@Param("hkid") String hkid);
	//雇主是否为匹配的家政人员点赞
	public List<CourseCatagoryCount> getIsLike(@Param("hkid") String hkid,@Param("username") String username);
	//匹配个数为0的家政数据列表
	public List<HouseKeeper> getHouseKeeperList(@Param("offset") int offset,@Param("pageSize") int pageSize);
	//匹配个数为0的家政数据个数
	public List<CourseCatagoryCount> getHouseKeeperListCount();
	//课程购买次数
	public List<CourseCatagoryCount> getcourseOrderCount(@Param("cid") String cid);
	//修改课程学习状态
	public int changeLearn(@Param("cid") String cid,@Param("hkid") String hkid);
	//获取客户针对某个课程是否已学
	public List<CourseCatagoryCount> getCourseLearn(@Param("cid") String cid,@Param("hkid") String hkid);
	//增加已学记录
	public int addCourseLearn(@Param("cid") String cid,@Param("hkid") String hkid);
	//获取雇主针对课程模块已学的课程总数
	public List<CourseCatagoryCount> getOrderLearnCount(@Param("hkid") String hkid,@Param("ccid") String ccid);
	//判断家政是否已学该课程
	public List<CourseCatagoryCount> getOrderLearnCourseCount(@Param("cid") String cid,@Param("hkid") String hkid);
}
