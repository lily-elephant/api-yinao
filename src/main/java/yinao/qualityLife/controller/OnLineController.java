package yinao.qualityLife.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.VersionInfo;

@RestController
public class OnLineController {

	public OnLineController() {
		super();
		// TODO Auto-generated constructor stub
	}
		//返回上线状态
		@RequestMapping(value="/version110",method=RequestMethod.POST)
		public ResultMap onlineState(HttpServletRequest request) {			
			VersionInfo entity = new VersionInfo() ;
			entity.setOnline(true);
			return new ResultMap().success().message("success").data(entity);
			
		}
		//返回上线状态
		@RequestMapping(value="/version109",method=RequestMethod.POST)
		public ResultMap onlineStateben(HttpServletRequest request) {			
			VersionInfo entity = new VersionInfo() ;
			entity.setOnline(true);
			return new ResultMap().success().message("success").data(entity);
			
		}
}
 