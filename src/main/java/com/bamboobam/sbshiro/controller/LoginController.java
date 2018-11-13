package com.bamboobam.sbshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping({"/", "/index"})
    public String indexTemplate() {
        return "/index";
    }

    @GetMapping("/reg")
    public String regTemplate(){
        return "reg";
    }


    @GetMapping("/403")
    public String unauthorizedTemplate(){
        return "403";
    }

    @GetMapping(value = "/login")
    public String loginTemplate(Model model) {
        //如果已经认证通过，直接跳转到首页
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        model.addAttribute("msg", "请登录");
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logoutTemplate() {
        return "redirect:/login";
    }


    /**
     * 登录接口
     *
     * @return
     */
    @PostMapping(value = "/login")
    public String login(String username, String password, Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        } else {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(usernamePasswordToken);//登录
                return "redirect:/index";
            } catch (UnknownAccountException uae) {
                model.addAttribute("msg", "用户帐号或密码不正确");
            } catch (IncorrectCredentialsException ice) {
                model.addAttribute("msg", "用户帐号或密码不正确");
            } catch (LockedAccountException lae) {
                model.addAttribute("msg", "用户帐号被锁定不可用");
            } catch (AuthenticationException ae) {
                model.addAttribute("msg", "登录失败：" + ae.getMessage());
            }
            return "/login";
        }
    }


}
