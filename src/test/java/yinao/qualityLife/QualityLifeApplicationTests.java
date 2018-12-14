package yinao.qualityLife;

import yinao.qualityLife.dao.UserMapper;
import yinao.qualityLife.model.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QualityLifeApplicationTests {

	@Autowired
    private UserMapper userMapper;

	@Test
    public void testGetUser(){
        //User user = userMapper.loginEmployer("1");
        //System.out.println(user);
    }

}
