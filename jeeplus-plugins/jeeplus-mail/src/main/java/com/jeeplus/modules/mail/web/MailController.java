/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.mail.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.mail.entity.Mail;
import com.jeeplus.modules.mail.service.MailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;

/**
 * 发件箱Controller
 *
 * @author Jin
 * @version 2015-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/mail")
public class MailController extends BaseController {

    @Autowired
    private MailService mailService;

    @ModelAttribute
    public Mail get(@RequestParam(required = false) String id) {
        Mail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailService.get(id);
        }
        if (entity == null) {
            entity = new Mail();
        }
        return entity;
    }

    @RequiresPermissions("iim:mail:view")
    @RequestMapping(value = {"list", ""})
    public String list(Mail mail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Mail> page = mailService.findPage(new Page<Mail>(request, response), mail);
        model.addAttribute("page", page);
        return "modules/mail/mailList";
    }

    @RequiresPermissions("iim:mail:view")
    @RequestMapping(value = "form")
    public String form(Mail mail, Model model) {
        model.addAttribute("mail", mail);
        return "modules/mail/mailForm";
    }

    @RequestMapping(value = "save")
    public String save(Mail mail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, mail)) {
            return form(mail, model);
        }
        mailService.save(mail);
        addMessage(redirectAttributes, "删除站内信成功");
        return "redirect:" + adminPath + "/mail/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(Mail mail, RedirectAttributes redirectAttributes) {
        mailService.delete(mail);
        addMessage(redirectAttributes, "删除站内信成功");
        return "redirect:" + adminPath + "/mail/?repage";
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            mailService.delete(mailService.get(id));
        }
        addMessage(redirectAttributes, "删除站内信成功");
        return "redirect:" + adminPath + "/mail/?repage";
    }

}