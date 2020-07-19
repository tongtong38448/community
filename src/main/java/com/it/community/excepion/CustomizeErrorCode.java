package com.it.community.excepion;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"问题不存在，换个问题试试"),
    TARGET_PARENT_NOT_FOUND(2002,"未选中任何评论或回复"),
    NO_LOGIN(2003,"当前操作需要登陆，请登录后重试"),
    SYS_ERROR(2004,"服务器错误，稍等试试"),
    TYPE_PARAM_WRONG(2005,"评论错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    READ_NOTIFICATION_FAIL(2007,"读别人信息呢？"),
    COMMENT_IS_EMPTY(2008,"评论不能为空"),
    NOTIFICATION_NOT_FOUND(2009,"消息不存在，飞走了"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败")
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


}
