   package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.AdminUser;
import yinao.qualityLife.model.domain.AppointmentMoney;
import yinao.qualityLife.model.domain.AuthData;
import yinao.qualityLife.model.domain.Balance;
import yinao.qualityLife.model.domain.Ben;
import yinao.qualityLife.model.domain.Bill;
import yinao.qualityLife.model.domain.Certificate;
import yinao.qualityLife.model.domain.ChargePropertices;
import yinao.qualityLife.model.domain.Comment;
import yinao.qualityLife.model.domain.Emid;
import yinao.qualityLife.model.domain.EmployerRecord;
import yinao.qualityLife.model.domain.Feedback;
import yinao.qualityLife.model.domain.Hkid;
import yinao.qualityLife.model.domain.HousekeeperCertificate;
import yinao.qualityLife.model.domain.OfflineTraining;
import yinao.qualityLife.model.domain.OfflineTrainingNotes;
import yinao.qualityLife.model.domain.OfflineTrainingRecord;
import yinao.qualityLife.model.domain.Order;
import yinao.qualityLife.model.domain.PersonalityNine;
import yinao.qualityLife.model.domain.PersonalityTest;
import yinao.qualityLife.model.domain.PersonalityTestDes;
import yinao.qualityLife.model.domain.ServiceRecord;
import yinao.qualityLife.model.domain.SixteenOption;
import yinao.qualityLife.model.domain.SixteenTest;
import yinao.qualityLife.model.domain.StudyOrder;
import yinao.qualityLife.model.domain.SysInfo;
import yinao.qualityLife.model.domain.User;

@Mapper
@Component
public interface UserMapper {

	//获取后台登录信息
	List<AdminUser> getAdminUser(@Param("phone") String phone);	
	//后台登录接口
    User loginAdmin(@Param("username") String username);
	//雇主登录接口
    User loginEmployer(@Param("username") String username);
    //家政登录接口
    User loginHousekeeper(@Param("username") String username);
    //雇主注册接口
    void registerEmployer(@Param("username") String username , @Param("password") String password , @Param("name") String name);
    //家政注册接口
    void registerHousekeeper(@Param("username") String username , @Param("password") String password , @Param("name") String name);
    //雇主找回密码接口
    void updateEmployer(@Param("username") String username , @Param("password") String password);
    //家政找回密码接口
    void updateHousekeeper(@Param("username") String username , @Param("password") String password);
    //雇主注册接口
    void registerLoginEmployer(@Param("username") String username,@Param("password") String password);
    //家政注册接口
    void registerLoginHousekeeper(@Param("username") String username,@Param("password") String password);
    //通过username获取emid
    Emid getEmidByUsername(@Param("username") String username) ;
    //通过username获取hkid
    Hkid getHkidByUsername(@Param("username") String username) ;
    //提交意见反馈接口
    int submitFeedback(@Param("userid") String userid , @Param("clienttype") String clienttype , @Param("comment") String comment);
    //客服回复意见返回
    int userFeedback(@Param("cid") String cid,@Param("reply") String reply);
    //我的意见反馈
    List<Feedback> myFeedback(@Param("userid") String userid , @Param("clienttype") String clienttype ) ;
    //后台获取意见反馈列表
    List<Feedback> adminFeedback(@Param("isanswer") String isanswer) ;
    //获取系统消息列表
    List<SysInfo> getSysInfoList( @Param("clienttype") String clienttype );
    //获取用户个人消息列表
    List<SysInfo> getNoticeInfoList(@Param("userid") String userid , @Param("clienttype") String clienttype);
    //通过id和userid判断数据是否为空
    List<SysInfo> getSysCount(@Param("id") String id , @Param("userid") String userid );
    //我的消息设置成已读
    int setSysInfoIsRead(@Param("userid") String userid , @Param("id") String id);
    int setUserInfoIsRead(@Param("userid") String userid , @Param("clienttype") String clienttype , @Param("id") String id);
    //获取订单列表
    List<Order> getOrderList(@Param("emid") String emid , @Param("payflag") String payflag) ;
    //获取雇主记录
    List<EmployerRecord> getEmployerRecord(@Param("emid") String emid ,@Param("hkid") String hkid) ;
    
    //获取雇主记录
    List<ServiceRecord> getServiceRecord(@Param("emid") String emid ,@Param("hkid") String hkid) ;
    
    //后台获取雇主记录
    List<EmployerRecord> getAdminEmployerRecord(
    		@Param("emphone") String emphone ,
    		@Param("emname") String emname ,
    		@Param("hkphone") String hkphone ,
    		@Param("hkname") String hkname 
    		) ;
    //新增评论
    int insertComment(@Param("emid") String emid , @Param("hkid") String hkid , @Param("score") int score , @Param("content") String content);
    //删除评论
    int deleteComment(@Param("emid") String emid , @Param("hkid") String hkid ) ;
    //获取已有评论
    List<Comment> getCommentList(@Param("emid") String emid , @Param("hkid") String hkid) ;
    //获取家政人员学习订单列表
    List<StudyOrder> getHKOrderList(@Param("hkid") String hkid , @Param("isbuy") String isbuy) ;
    //获取平台所有证书
    List<Certificate> getCertificate() ;
    //获取征信信息
    List<AuthData> getAuthData(@Param("hkid") String hkid) ;
    //获取线下培训列表
    List<OfflineTraining> getOfflineTraining(@Param("otid") String otid) ;
    
    List<OfflineTraining> getadminOfflineTraining(@Param("otid") String otid ,@Param("name") String name) ;
    
    //添加线下课程
     int addOfflineTraining(OfflineTraining params) ;
     //修改线下课程
     int editOfflineTraining(OfflineTraining params) ;
     //刪除线下课程
     int deleteOfflineTraininga(OfflineTraining params) ;
     
    //显现课程用户是否购买
    List<OfflineTraining> getOfflineTrainingIsBuy(@Param("hkid") String hkid , @Param("otid") String otid) ;
    //显现课程多少用戶購買
    List<OfflineTraining> getOfflineTrainingBuyCount(@Param("otid") String otid) ;
    
    //获取预约金 
    List<AppointmentMoney> getAppointMentMoney();
    
    //更改用户的头像
    int changeIcon(@Param("id") String id , @Param("clienttype") String clienttype , @Param("headimageurl") String headimageurl) ;
    //上传图片
    int uploadimage() ;
    
    //获取线下考试 成绩列表
    List<OfflineTrainingRecord> getOfflineTrainingRecord(@Param("hkid") String hkid,@Param("otid") String otid ) ;
    
    //保存线下考试信息
    int savaOfflineTraining(@Param("hkid") String hkid,@Param("otid") String otid , @Param("score") String score );
    //删除线下考试信息
    int deleteOfflineTraining(@Param("hkid") String hkid,@Param("otid") String otid);
    
    //获取线下培训 报名列表  
    List<OfflineTrainingNotes> getOfflineTrainingNotes(@Param("address") String address , @Param("name") String name , @Param("username") String username) ;
    //改变线下培训通知状态
    int changeNoticeState(@Param("hkid") String hkid,@Param("transactionid") String transactionid) ;
    
    List<HousekeeperCertificate> admincertificatelist(
    		@Param("hkid") String hkid,
    		@Param("cid") String cid
    	);
    //获取家政人员余额
    List<Balance> getbalance(@Param("userid") String userid) ;
    
    List<Bill> getbill(@Param("userid") String userid) ;
    
    List<ChargePropertices> getChargePropertices() ;
    
    List<Ben> getEMInfoByUserid(@Param("userid") String userid) ;
    
    List<Ben> getHKInfoByUserid(@Param("userid") String userid) ;
    
    List<PersonalityTest> getPersonalityTest();
    
    List<PersonalityTestDes> getAnswer(@Param("no") String no) ;
    
    //保存九型人格 测试结果
    int savePersonalityAnswer(@Param("userid") String userid , @Param("result") String result ) ;
  //保存16 测试结果
    int savePersonalityAnswerTwo(@Param("userid") String userid , @Param("result") String result ) ;
    
    List<PersonalityNine> getPersonalityAnswer(@Param("hkid") String hkid) ; 
    
    List<PersonalityNine> getPersonalityAnswer2(@Param("hkid") String hkid) ; 
    
    //获取16性格测试列表
    List<SixteenTest> getSixteenTest() ;
    //每个no对应的选项和分数
    List<SixteenOption> getSixteenAnswer(@Param("no") String no) ;
    
    
}
