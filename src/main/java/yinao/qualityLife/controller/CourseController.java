package yinao.qualityLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yinao.qualityLife.dao.CourseMapper;
import yinao.qualityLife.dao.InterviewMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.Course;
import yinao.qualityLife.model.domain.Interview;
import yinao.qualityLife.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class CourseController {

    private final TokenUtils tokenUtils;
    private final CourseMapper courseMapper;

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    public CourseController(TokenUtils tokenUtils, CourseMapper courseMapper) {
        this.tokenUtils = tokenUtils;
        this.courseMapper = courseMapper;
    }

    @RequestMapping(value = "addcourse", method = RequestMethod.POST)
    public ResultMap addcourse(@Valid Course params, BindingResult bindingResult) {

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()) {
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对").data("");
        }
        try {
            int count = this.courseMapper.addcourse(params.getHkid() ,params.getCid() , params.getDuration() ) ;
            if (count == 1) {
                return new ResultMap().success().message("插入成功");
            } else {
                return new ResultMap().fail("435").message("插入失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("插入失败").data(e);
        }
    }

    @RequestMapping(value = "editcourse", method = RequestMethod.POST)
    public ResultMap editcourse(@Valid Course params, BindingResult bindingResult) {

        // 检查用户输入的参数是否正确
        if (bindingResult.hasErrors()) {
            return new ResultMap().fail("400").message("缺少参数或者参数格式不对");
        }
        //检查参数种是否存在ocid
        if (params.getOcid() == null) {
            return new ResultMap().fail("400").message("缺少ocid");
        }
        try {
            int count = this.courseMapper.editcourse(params.getOcid() , params.getHkid() , params.getCid() , params.getDuration()) ;
            if (count == 1) {
                return new ResultMap().success().message("修改成功");
            } else {
                return new ResultMap().fail("435").message("修改失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("修改失败").data(e);
        }
    }

    @RequestMapping(value = "deletecourse", method = RequestMethod.POST)
    public ResultMap deletecourse(HttpServletRequest request) {
        String ocid = request.getParameter("ocid");
        if (ocid == null) {
            return new ResultMap().fail("400").message("请添加ocid");
        }
        try {
            int count = this.courseMapper.deletecourse(ocid);
            if (count == 1) {
                return new ResultMap().success().message("删除成功");
            } else {
                return new ResultMap().fail("435").message("删除失败");
            }
        } catch (Exception e) {
            return new ResultMap().fail("436").message("删除失败").data(e.toString());
        }
    }
}
