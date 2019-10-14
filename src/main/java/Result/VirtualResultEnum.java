
package Result;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:panghaichen
 * @Date: 2019/10/12 0012 14:35
 */
public enum VirtualResultEnum {

    SUCCESS("success", "成功"),
    FAIL("fail", "失败"),
    ERROR("error", "异常");

    private String code;
    private String msg;

    VirtualResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

