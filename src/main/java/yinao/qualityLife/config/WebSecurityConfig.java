package yinao.qualityLife.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import yinao.qualityLife.filter.AuthenticationTokenFilter;

@Configuration      // 声明为配置类
@EnableWebSecurity      // 启用 Spring Security web 安全的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注册 401 处理器
     */
    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    /**
     * 注册 403 处理器
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    /**
     * 注册 token 转换拦截器为 bean
     * 如果客户端传来了 token ，那么通过拦截器解析 token 赋予用户权限
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //不需要拦截接口add
                .antMatchers("/user/loginbypassword").permitAll()
                .antMatchers("/user/loginbyverifycode").permitAll()  
                .antMatchers("/user/register").permitAll() 
                .antMatchers("/user/getverifycode").permitAll() 
                .antMatchers("/housekeeper/housekeeperlist").permitAll() 
                .antMatchers("/employerList").permitAll()
                .antMatchers("/source/articlelist").permitAll()
                .antMatchers("/source/bannerlist").permitAll()
                .antMatchers("/source/catagorylist").permitAll()
                .antMatchers("/source/picturelist").permitAll()
                .antMatchers("/source/videolist").permitAll()
                .antMatchers("/exam/examlist").permitAll()
                .antMatchers("/housekeeper/comment").permitAll()
                .antMatchers("/housekeeper/certificatelist").permitAll()
                .antMatchers("/course/courselist").permitAll()
                .antMatchers("/course/coursecatagorylist").permitAll()
                .antMatchers("/source/addcatagory").permitAll() 
                .antMatchers("/source/editcatagory").permitAll()
                .antMatchers("/source/deletecatagory").permitAll()
                .antMatchers("/source/addarticle").permitAll()
                .antMatchers("/source/editarticle").permitAll()
                .antMatchers("/source/deletearticle").permitAll()
                .antMatchers("/source/addbanner").permitAll()
                .antMatchers("/source/editbanner").permitAll()
                .antMatchers("/source/deletebannert").permitAll()
                .antMatchers("/source/bannerdetail").permitAll()
                .antMatchers("/source/addpicture").permitAll()
                .antMatchers("/source/editpicture").permitAll()
                .antMatchers("/source/deletepicture").permitAll()
                .antMatchers("/source/addvideo").permitAll()
                .antMatchers("/source/editvideo").permitAll()
                .antMatchers("/source/deletevideo").permitAll()
                .antMatchers("/getCertificate").permitAll()
                .antMatchers("/getOfflineTraining").permitAll()
                .antMatchers("/getDetailsOfflineTraining").permitAll()
                .antMatchers("/getAppointMentMoney").permitAll()
                .antMatchers("/admin/housekeeper/housekeeperlist").permitAll()
                .antMatchers("/course/getcoursedetail").permitAll()
                .antMatchers("/getAuthData").permitAll()
                .antMatchers("/admin/getPersonalityAnswer").permitAll()
                .antMatchers("/admin/getPersonalityAnswerTwo").permitAll()

                // add by zihai
                .antMatchers("/exam/baseExamCatagory").permitAll()
                //  daodao add 
                .antMatchers("/admin/userlist").permitAll()
                .antMatchers("/admin/adduser").permitAll()
                .antMatchers("/admin/updateuser").permitAll()
                .antMatchers("/admin/saveupdateuser","/admin/deleteuser","/admin/login").permitAll()
                .antMatchers("/admin/departmentlist","/admin/adddepartment","/admin/updatepartment","/admin/saveupdatepartment","/admin/deleteparament").permitAll()                
                .antMatchers("/admin/selectdepartment","/admin/selectrole","/course/selectcoursecatagory","/admin/rolelist","/admin/addrole","/admin/updaterole","/admin/saveupdaterole","/admin/deleterole","/admin/roleauth","/admin/saveroleauth").permitAll()
                .antMatchers("/source/bannerlists").permitAll()
                .antMatchers("/source/changebannerstatet","/source/childcatagorylist").permitAll()
                
                .antMatchers("/source/detailcatagory").permitAll()
                

                .antMatchers("/source/detailarticle").permitAll()
                .antMatchers("/admin/uploadImage").permitAll()
                .antMatchers("/wxNotify").permitAll()
                .antMatchers("/getChargePropertices").permitAll()
                .antMatchers("/course/serachcoursecatagorylist").permitAll()
                .antMatchers("/exam/baseExamlist").permitAll()
                .antMatchers("/getPersonalityTest").permitAll()
                //online
                .antMatchers("/version104").permitAll()
                .antMatchers("/version105").permitAll()
                
                
                

                //ADMIN
                //.antMatchers("/admin/exam/getEaxmRecord").permitAll()
                //.antMatchers("/admin/showHousekeeper").permitAll()
                
                
                
                
                
                
                

                //Course
                //.antMatchers("/aaa").hasAuthority("admin")   // 需拥有 admin 这个权限
                //.antMatchers("/aaa").hasRole("ADMIN")     // 需拥有 ADMIN 这个身份
                .anyRequest().authenticated()  // 需携带有效 token     
                .and()
                // 配置被拦截时的处理
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)   // 添加 token 无效或者没有携带 token 时的处理
                .accessDeniedHandler(this.accessDeniedHandler)      //添加无权限时的处理
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session


        /**
         * 本次 json web token 权限控制的核心配置部分
         * 在 Spring Security 开始判断本次会话是否有权限时的前一瞬间
         * 通过添加过滤器将 token 解析，将用户所有的权限写入本次会话
         */
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
