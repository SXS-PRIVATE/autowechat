package cn.shu.wechat.enums;

/**
 * APP消息的子类型
 *
 * @author ShuXinSheng
 * @version 1.1
 * @date 创建时间：2017年5月13日 下午11:53:00
 */
public enum WXReceiveMsgCodeOfAppEnum {
    UNKNOWN(0, "未知消息"),
    FAVOURITE(5, "链接消息"),
    PROGRAM(33, "小程序消息"),
    MUSIC(3, "分享的音乐"),
    FILE(6, "文件消息");
    private int type;
    private String code;

    WXReceiveMsgCodeOfAppEnum(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public static WXReceiveMsgCodeOfAppEnum getByCode(int type) {
        for (WXReceiveMsgCodeOfAppEnum value : WXReceiveMsgCodeOfAppEnum.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

}
