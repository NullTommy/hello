package Page;

import java.io.Serializable;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: wangxiangwei
 * Date: 17-07-28
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public final class PageList<T> implements Serializable {

    private static final long serialVersionUID = 7636400405542623379L;

    private List<T> datas;

    private Paginator paginator;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
}
