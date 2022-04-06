package com.jeeplus.modules.qiyewx.shenpi.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 	审批申请备注信息，可能有多个备注节点
 */
public class SpComment {
    /** 备注人信息 commentUserInfo.userid */
    private String commentUserInfo;
    /** 备注文本内容 */
    private String commentcontent;
    /** 	备注附件id  逗号隔开 */
    private String media_id;
    /** 备注提交时间戳，Unix时间戳 */
    private Long commenttime;
    /** 备注id */
    private String commentid;

    public String getCommentUserInfo() {
        return commentUserInfo;
    }

    public void setCommentUserInfo(String commentUserInfo) {
        this.commentUserInfo = commentUserInfo;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public Long getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Long commenttime) {
        this.commenttime = commenttime;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }
}
