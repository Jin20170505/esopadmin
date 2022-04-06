/**
 *
 */
package com.jeeplus.modules.sys.web;

import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.core.security.shiro.session.SessionDAO;
import com.jeeplus.core.servlet.ValidateCodeServlet;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.security.FormAuthenticationFilter;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.utils.ConfigUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.security.auth.login.Configuration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 登录Controller
 * @author Jin
 * @version 2016-5-31
 */
@Api(value = "LoginController", description = "登录控制器")
@Controller
public class LoginController extends BaseController
{
    
    @Autowired
    private SessionDAO sessionDAO;
    
    @Autowired
    JeePlusProperites jeePlusProperites;

    /**
     * 管理登录
     * @throws IOException 
     */
    @ApiOperation(notes = "login", httpMethod = "POST", value = "用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "mobileLogin", value = "接口标志", required = true, paramType = "query", dataType = "string")})
    @RequestMapping(value = {"${adminPath}/login", ""})
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Principal principal = UserUtils.getPrincipal();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        
        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin())
        {
            return "redirect:" + adminPath;
        }
        
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
        // 如果是手机没有登录跳转到到login，则返回JSON字符串
        if (savedRequest != null)
        {
            String queryStr = savedRequest.getQueryString();
            if (queryStr != null && (queryStr.contains("__ajax") || queryStr.contains("mobileLogin")))
            {
                AjaxJson j = new AjaxJson();
                j.setSuccess(false);
                j.setErrorCode("0");
                j.setMsg("没有登录!");
                return renderString(response, j);
            }
        }

        return "modules/sys/login/sysLogin";
    }
    
    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Principal principal = UserUtils.getPrincipal();
        
        // 如果已经登录，则跳转到管理首页
        if (principal != null)
        {
            return "redirect:" + adminPath;
        }
        
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        
        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null"))
        {
            message = "用户或密码错误, 请重试.";
        }
        
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}", sessionDAO.getActiveSessions(false).size(), message, exception);
        }
        
        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception))
        {
            model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
        }
        
        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
        
        // 如果是手机登录，则返回JSON字符串
        if (mobile)
        {
            AjaxJson j = new AjaxJson();
            j.setSuccess(false);
            j.setMsg(message);
            j.put("username", username);
            j.put("name", "");
            j.put("mobileLogin", mobile);
            j.put("JSESSIONID", "");
            return renderString(response, j.getJsonStr());
        }
        
        return "modules/sys/login/sysLogin";
    }
    
    /**
     * 管理登录
     * @throws IOException 
     */
    @RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        Principal principal = UserUtils.getPrincipal();
        // 如果已经登录，则跳转到管理首页
        if (principal != null)
        {
            UserUtils.getSubject().logout();
            CacheUtils.remove(principal.getLoginName());
            
        }
        // 如果是手机客户端退出跳转到login，则返回JSON字符串
        String ajax = request.getParameter("__ajax");
        if (ajax != null)
        {
            model.addAttribute("success", "1");
            model.addAttribute("msg", "退出成功");
            return renderString(response, model);
        }
        return "redirect:" + adminPath + "/login";
    }



    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Principal principal = UserUtils.getPrincipal();
        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
        

        
        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin())
        {
                return renderString(response, principal);
        }



        if (UserUtils.getMenuList().size() == 0)
        {
            return "modules/sys/login/noAuth";
        }
        else
        {
            Date createDate  = UserUtils.getUser().getCreateDate();
            Date now = new Date();
            try {
                int days = daysBetween(createDate,now);
                if(days>30){
                    model.addAttribute("pswMsg","密码长时间没修改，为保证账号安全，请及时修改。");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "modules/sys/login/sysIndex";

        }
        
    }

    private static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 是否是验证码登录
     * @param useruame 用户名
     * @param isFail 计数加1
     * @param clean 计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean)
    {
        Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
        if (loginFailMap == null)
        {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null)
        {
            loginFailNum = 0;
        }
        if (isFail)
        {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean)
        {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }
    
    /**
     * 首页
     * @throws IOException 
     */
    @RequestMapping(value = "${adminPath}/home")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        
        return "modules/sys/login/sysHome";
        
    }
}
