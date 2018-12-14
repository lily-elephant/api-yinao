package yinao.qualityLife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CourseMapper {

    //新增课程订单
    int addcourse(
            @Param("hkid") String hkid,
            @Param("cid") String cid,
            @Param("duration") String duration
    );

    //修改课程订单
    int editcourse(
            @Param("ocid") String ocid,
            @Param("hkid") String hkid,
            @Param("cid") String cid,
            @Param("duration") String duration
    );

    //删除课程订单
    int deletecourse(
            @Param("ocid") String ocid
    );


}
