
package Result;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:panghaichen
 * @Date: 2019/10/12 0012 14:35
 */
@Data
public class VirtualResult<T>{

    //可以替换为继承Serializable接口。另外，这个类里用的是枚举类，也可以考虑常量类。
    private static final long serialVersionUID = -602186320728431833L;

    private String code;
    private String msg;
    private T result;

    public VirtualResult(String errCode, String errMsg) {
        this.code = errCode;
        this.msg = errMsg;
    }

    public static <T> VirtualResult<T> error(String errMsg) {
        return new VirtualResult(VirtualResultEnum.ERROR.getCode(), StringUtils.isBlank(errMsg) ? VirtualResultEnum.ERROR.getMsg() : errMsg);
    }

    public static <T> VirtualResult<T> success() {
        return new VirtualResult(VirtualResultEnum.SUCCESS.getCode(), null);
    }

    public static <T> VirtualResult<T> success(String successMsg) {
        return new VirtualResult(VirtualResultEnum.SUCCESS.getCode(), StringUtils.isBlank(successMsg) ? VirtualResultEnum.ERROR.getMsg() : successMsg);
    }

    public boolean isSuccess() {
        return VirtualResultEnum.SUCCESS.getCode().equals(this.code);
    }

    public String getFullMsg() {
        return "[" + this.code + "]" + this.msg;
    }

}
