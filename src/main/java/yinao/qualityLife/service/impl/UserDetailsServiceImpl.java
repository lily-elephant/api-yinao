package yinao.qualityLife.service.impl;

import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.SecurityModelFactory;
import yinao.qualityLife.model.domain.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description Spring Security 用于将 数据库中的用户信息转换成 userDetail 对象的服务类的实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    

    /**
     * 获取 userDetail
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
		return null;

//        User user = this.userMapper.loginEmployer(username) ;
//        //User user = this.userMapper.loginHousekeeper(username);  
//       
//
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//        } else {
//            return SecurityModelFactory.create(user);
//        }
    }
    
    
   public UserDetails judgeType(String username , String usertype ) {
	   User user ; 
	   if(usertype.equals("EMPLOYER")) {
		   user = this.userMapper.loginEmployer(username) ;
	   }else if(usertype.equals("ADMIN")){
		   user = this.userMapper.loginAdmin(username) ;
       }else{
		   user = this.userMapper.loginHousekeeper(username);  
	   }
       if (user == null) {
           throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
       } else {
           return SecurityModelFactory.create(user);
       }
	
}


}
