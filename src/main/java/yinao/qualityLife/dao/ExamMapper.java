package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.AnsewerCompare;
import yinao.qualityLife.model.domain.Ccid;
import yinao.qualityLife.model.domain.CourseCatagoryCount;
import yinao.qualityLife.model.domain.Eid;
import yinao.qualityLife.model.domain.Exam;
import yinao.qualityLife.model.domain.ExamAdd;
import yinao.qualityLife.model.domain.ExamBen;
import yinao.qualityLife.model.domain.ExamOption;

@Mapper
@Component
public interface ExamMapper {
	
	
	//获取小程序考试列表
    List<Exam> getexamlist(
            @Param("ccid") String ccid,
            @Param("name") String name,
            @Param("ismust") String ismust,
            @Param("eid") String eid
    );

    //获取试题列表
    List<Exam> examlist(
            @Param("ccid") String ccid,
            @Param("name") String name,
            @Param("ismust") String ismust,
            @Param("eid") String eid
    );
    
  //获取试题列表
    List<Exam> baseExamlist();
    
    //根据试题id获取答案集
    List<ExamOption> examOptionlist(
    		@Param("eid") String eid
    );
    
    //根据试题id获取真实答案集
    List<ExamOption> examAnswer(
    		@Param("eid") String eid
    );
    
  //根据试题id获取真实答案集
    List<ExamBen> examAnswerben(
    		@Param("eid") String eid
    );
    
    //根据oid 获取name 
    List<ExamOption> getNameByOid(
    		@Param("oid") String oid
    );
    

    //新增考试结果
    int addresult(
    		@Param("oid") String oid ,
    		@Param("eid") String eid ,
    		@Param("usertype") String usertype,
    		@Param("username") String username   
    );
    
    //修改考试结果
    int editresult(
    		@Param("id") String id ,
    		@Param("oid") String oid ,
    		@Param("eid") String eid ,
    		@Param("usertype") String usertype,
    		@Param("username") String username   
    );
    
    //删除考试结果
    int deleteresult(
    		@Param("id") String id ,
    		@Param("usertype") String usertype
    );
    
    //用户删除考试结果
    int deleteresultUser(
    		@Param("eid") String eid ,
    		@Param("usertype") String usertype,
    		@Param("username") String username
    );
    
    //新增试题   t_test_exam
    int addexamcommon(ExamAdd examadd);
    //新增试题addexamoption
    int addexamoption(ExamOption examOption) ;
    //新增试题addexamanswer
    int addexamanswer(
    		@Param("eid") int eid ,
    		@Param("oid") String oid
    ) ;
    
    //编辑试题
    int editexamcommon(ExamAdd examadd);
    //删除试题表和答案表
    int deleteoption(
    		@Param("eid") int eid 
    );
  //删除试题表和答案表
    int deleteanswer(
    		@Param("eid") int eid 
    );
    
    //删除基础表
    int deleteexam(
    		@Param("eid") int eid 
    );
    
    //获取考试表内   可用的ccid
    List<CourseCatagoryCount> getUseCcidCount(@Param("ccid") String ccid );
    
    //根据ccid获取eid
    List<Eid> getEidByCcid(@Param("ccid") String ccid );
    
    //根据eid获取正确答案和用户答案
    List<AnsewerCompare> getAnsewerCompareList(@Param("eid") String eid , @Param("username") String username);
    
    //通过emid获取当前用户答题记录
    List<ExamBen> getRecordByemid(@Param("eid") String eid , @Param("username") String username) ;
    //通过hkid获取当前用户答题记录
    List<ExamBen> getRecordByhkid(@Param("eid") String eid , @Param("username") String username) ;
    
    //获取小程序考试列表
    List<Exam> getexamlist(
            @Param("ccid") String ccid,
            @Param("name") String name,
            @Param("ismust") String ismust,
            @Param("eid") String eid,
            @Param("label") String label,
            @Param("examtype") String examtype
    );
    //获取模块考试题的类型
    List<Exam> getLabelList( @Param("ccid") String ccid);

    
   
}
