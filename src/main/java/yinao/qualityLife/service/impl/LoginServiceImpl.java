package yinao.qualityLife.service.impl;

import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.LoginDetail;
import yinao.qualityLife.model.TokenDetail;
import yinao.qualityLife.service.LoginService;
import yinao.qualityLife.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final TokenUtils tokenUtils;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public String generateToken(TokenDetail tokenDetail , String usertype) {
        return tokenUtils.generateToken(tokenDetail , usertype);
    }

	@Override
	public LoginDetail getLoginDetail(String username, String usertype) {
    	if(usertype.equals("EMPLOYER")) {
    		//System.out.println("1111111111111111111111");
    		return userMapper.loginEmployer(username);
    		
    	}else if(usertype.equals("ADMIN")){
    		//System.out.println("2222222222222222222222");
    		return userMapper.loginAdmin(username) ; 
    		
    	}else {
    		//System.out.println("333333333333333333333333");
    		return userMapper.loginHousekeeper(username) ; 
    		
    	}
	}

}
