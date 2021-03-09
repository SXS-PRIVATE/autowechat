package cn.shu.wechat.enums;

/**
 * 确认添加好友Enum
 * 
 * @author SXS
 * @date 创建时间：2017年6月29日 下午9:47:14
 * @version 1.1
 *
 */
public enum VerifyFriendEnum {

	ADD(2, "添加"),
	ACCEPT(3, "接受");

	private int code;
	private String desc;

	private VerifyFriendEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

}
