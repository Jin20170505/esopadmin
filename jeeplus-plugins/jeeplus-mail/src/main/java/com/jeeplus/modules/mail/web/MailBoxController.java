/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.mail.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.mail.entity.MailBox;
import com.jeeplus.modules.mail.entity.MailCompose;
import com.jeeplus.modules.mail.entity.MailPage;
import com.jeeplus.modules.mail.service.MailBoxService;
import com.jeeplus.modules.mail.service.MailComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 收件箱Controller
 *
 * @author Jin
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mailBox")
public class MailBoxController extends BaseController {

    @Autowired
    private MailComposeService mailComposeService;

    @Autowired
    private MailBoxService mailBoxService;

    @ModelAttribute
    public MailBox get(@RequestParam(required = false) String id) {
        MailBox entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailBoxService.get(id);
        }
        if (entity == null) {
            entity = new MailBox();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
        //查询未读的条数
        MailBox serachBox = new MailBox();
        serachBox.setReadstatus("0");
        serachBox.setReceiver(UserUtils.getUser());
        model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));

        //查询总条数
        MailBox serachBox2 = new MailBox();
        serachBox2.setReceiver(UserUtils.getUser());
        model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));

        //查询已发送条数
        MailCompose serachBox3 = new MailCompose();
        serachBox3.setSender(UserUtils.getUser());
        serachBox3.setStatus("1");//已发送
        model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));

        //查询草稿箱条数
        MailCompose serachBox4 = new MailCompose();
        serachBox4.setSender(UserUtils.getUser());
        serachBox4.setStatus("0");//草稿
        model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));

        return "modules/mail/mailBoxList";
    }


    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
        mailBox.setReceiver(UserUtils.getUser());
        Page<MailBox> page = mailBoxService.findPage(new MailPage<MailBox>(request, response), mailBox);
        model.addAttribute("page", page);
        return getBootstrapData(page);
    }

    @RequestMapping(value = "detail")
    public String detail(MailBox mailBox, Model model) {
        if (mailBox.getReadstatus().equals("0")) {//更改未读状态为已读状态
            mailBox.setReadstatus("1");//1表示已读
            mailBoxService.save(mailBox);
        }
        model.addAttribute("mailBox", mailBox);
        //查询未读的条数
        MailBox serachBox = new MailBox();
        serachBox.setReadstatus("0");
        serachBox.setReceiver(UserUtils.getUser());
        model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));

        //查询总条数
        MailBox serachBox2 = new MailBox();
        serachBox2.setReceiver(UserUtils.getUser());
        model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));

        //查询已发送条数
        MailCompose serachBox3 = new MailCompose();
        serachBox3.setSender(UserUtils.getUser());
        serachBox3.setStatus("1");//已发送
        model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));

        //查询草稿箱条数
        MailCompose serachBox4 = new MailCompose();
        serachBox4.setSender(UserUtils.getUser());
        serachBox4.setStatus("0");//草稿
        model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
        return "modules/mail/mailBoxDetail";
    }

    @RequestMapping(value = "save")
    public String save(MailBox mailBox, Model model, RedirectAttributes redirectAttributes) {
        Date date = new Date(System.currentTimeMillis());
        mailBox.setSender(UserUtils.getUser());
        mailBox.setSendtime(date);
        mailBoxService.save(mailBox);
        addMessage(redirectAttributes, "保存站内信成功");
        return "redirect:" + adminPath + "/mailBox/?repage";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxJson delete(MailBox mailBox) {
        AjaxJson j = new AjaxJson();
        mailBoxService.delete(mailBox);
        j.setMsg("删除站内信成功!");
        return j;
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            mailBoxService.delete(mailBoxService.get(id));
        }
        j.setMsg("删除站内信成功!");
        return j;
    }
}