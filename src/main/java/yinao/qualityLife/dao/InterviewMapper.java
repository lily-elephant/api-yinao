package yinao.qualityLife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.InterviewList;


@Mapper
@Component
public interface InterviewMapper {

    //新增面试
    int addinterview(
            @Param("emid") String emid,
            @Param("hkid") String hkid,
            @Param("interviewtime") String interviewtime,
            @Param("place") String place,
            @Param("manager") String manager,
            @Param("result") String result,
            @Param("remark") String remark );

    //修改面试
    int editinterview(
            @Param("interviewid") String interviewid,
            @Param("emid") String emid,
            @Param("hkid") String hkid,
            @Param("interviewtime") String interviewtime,
            @Param("place") String place,
            @Param("manager") String manager,
            @Param("result") String result,
            @Param("remark") String remark
    );

    //删除面试
    int deleteinterview(
            @Param("interviewid") String interviewid
    );
    
    //获取面试列表
    List<InterviewList> interviewlist(
    		@Param("emphone") String emphone,
            @Param("hkphone") String hkphone,
            @Param("emname") String emname,
            @Param("hkname") String hkname
    		 
    );
    
  //获取面试詳情
    List<InterviewList> detailsinterview(
    		@Param("interviewid") String interviewid
    );
    
    int cancelInterview(
    		 @Param("interviewid") String interviewid,
             @Param("emid") String emid
    );
    
    int sureInterview(
   		 @Param("interviewid") String interviewid,
         @Param("emid") String emid
   );
    
    int changeInterviewState(@Param("interviewid") String interviewid) ;
    
    int cancelInterviewTwo(
    		@Param("interviewid") String interviewid,
            @Param("hkid") String hkid
    );
    
    int admincancelInterview(
    		@Param("interviewid") String interviewid,
            @Param("result") String result
    );

}
