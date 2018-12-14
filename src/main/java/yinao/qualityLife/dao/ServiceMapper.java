package yinao.qualityLife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import yinao.qualityLife.model.domain.Service;

@Mapper
@Component
public interface ServiceMapper {

    //新增服务订单
    int addservice(
    		Service params
    );

    //修改服务订单
    int editservice(
            @Param("osid") int osid,
            @Param("emid") String emid,
            @Param("hkid") String hkid,
            @Param("starttime") String starttime ,
            @Param("endtime") String endtime ,
            @Param("salary") String salary ,
            @Param("managefee") String managefee ,
            @Param("remark") String remark
    );

    //删除服务订单
    int deleteservice(
            @Param("osid") String osid
    );
    
    //bill 表添加数据
    int addBillData(
    		@Param("osid") int osid,
    		@Param("emid") String emid,
    		@Param("money") int money
    );
    //添加雇佣记录
    int addServiceRecord(
    		@Param("emid") String emid,
    		@Param("hkid") String hkid,
    		@Param("starttime") String starttime,
    		@Param("endtime") String endtime,
    		@Param("interviewid") String interviewid,
    		@Param("salary") String salary
    		);


}
