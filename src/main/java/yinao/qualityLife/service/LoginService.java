package yinao.qualityLife.service;

import yinao.qualityLife.model.LoginDetail;
import yinao.qualityLife.model.TokenDetail;

public interface LoginService {

    LoginDetail getLoginDetail(String username , String usertype); 

    String generateToken(TokenDetail tokenDetail ,  String usertype);
    
}
