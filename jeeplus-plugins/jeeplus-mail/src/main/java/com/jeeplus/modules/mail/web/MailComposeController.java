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
import com.jeeplus.modules.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 发件箱Controller
 *
 * @author Jin
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mailCompose")
public class MailComposeController extends BaseController {

    @Autowired
    private MailComposeService mailComposeService;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private MailService mailService;

    public SystemInfoSocketHandler systemInfoSocketHandler() {
        return new SystemInfoSocketHandler();
    }

    @ModelAttribute
    public MailCompose get(@RequestParam(required = false) String id) {
        MailCompose entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailComposeService.get(id);
        }
        if (entity == null) {
            entity = new MailCompose();
        }
        return entity;
    }


    /*
     * 写站内信
     */
    @RequestMapping(value = {"sendLetter"})
    public String sendLetter(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        user = systemService.getUser(user.getId());
        model.addAttribute("receiver", user);

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

        return "modules/mail/mail_send";
    }

    /*
     * 回复站内信
     */
    @RequestMapping(value = {"replyLetter"})
    public String replyLetter(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("mailBox", mailBoxService.get(mailBox.getId()));

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

        return "modules/mail/mail_reply";
    }

    @RequestMapping(value = {"list", ""})
    public String list(MailCompose mailCompose, HttpServletRequest request, HttpServletResponse response, Model model) {


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


        if (mailCompose.getStatus() == null || mailCompose.getStatus().equals("0")) {
            return "modules/mail/mailDraftList";//草稿箱
        }
        return "modules/mail/mailComposeList";//已发送
    }

    @ResponseBody
    @RequestMapping(value = "data")
    public Map<String, Object> data(MailCompose mailCompose, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MailCompose> page = mailComposeService.findPage(new MailPage<MailCompose>(request, response), mailCompose);
        return getBootstrapData(page);
    }

    @RequestMapping(value = "detail")//打开已发送信件
    public String detail(MailCompose mailCompose, Model model) {
        model.addAttribute("mailCompose", mailCompose);


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

        return "modules/mail/mailComposeDetail";
    }

    @RequestMapping(value = "draftDetail")//打开草稿
    public String draftDetail(MailCompose mailCompose, Model model) {
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

        mailCompose = mailComposeService.get(mailCompose.getId());
        model.addAttribute("mailCompose", mailCompose);
        return "modules/mail/mailDraftDetail";
    }

    @RequestMapping(value = "save")
    public String save(MailCompose mailCompose, Model model, HttpServletRequest request, HttpServletResponse response) {
        mailService.saveOnlyMain(mailCompose.getMail());
        Date date = new Date(System.currentTimeMillis());
        mailCompose.setSender(UserUtils.getUser());
        mailCompose.setSendtime(date);

        //保存草稿
        if (mailCompose.getStatus().equals("0"))//已发送，同时保存到收信人的收件箱
        {
            for (User receiver : mailCompose.getReceiverList()) {

                mailCompose.setReceiver(receiver);
            }
            mailComposeService.save(mailCompose);//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。

        }

        //发送邮件
        for (User receiver : mailCompose.getReceiverList()) {


            if (mailCompose.getStatus().equals("1"))//已发送，同时保存到收信人的收件箱
            {
                mailCompose.setReceiver(receiver);
                //	mailCompose.setId(null);//标记为新纪录，每次往发件箱插入一条记录
                mailComposeService.save(mailCompose);//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。

                MailBox mailBox = new MailBox();
                mailBox.setReadstatus("0");
                mailBox.setReceiver(receiver);
                mailBox.setSender(UserUtils.getUser());
                mailBox.setMail(mailCompose.getMail());
                mailBox.setSendtime(date);
                mailBoxService.save(mailBox);
                //发送通知到客户端
                systemInfoSocketHandler().sendMessageToUser(UserUtils.get(receiver.getId()).getLoginName(), "收到一封新邮件");
            }
        }

        request.setAttribute("mailCompose", mailCompose);
        return "modules/mail/mail_compose_success";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxJson delete(MailCompose mailCompose, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        mailComposeService.delete(mailCompose);
        j.setMsg("删除站内信成功");
        return j;
    }

    /**
     * 批量删除已发送
     */
    @ResponseBody
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAllCompose(String ids, Model model) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            mailComposeService.delete(mailComposeService.get(id));
        }
        AjaxJson j = new AjaxJson();
        j.setMsg("删除邮件成功");
        return j;

    }


}