package Page;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class TestPaginator{
    public static void main(String args[]){

        PageList<String> stringPageList= new PageList<String>();

        List<String> stringList = new ArrayList<>();
        stringList.add("test1");
        stringList.add("test2");

        stringPageList.setDatas(stringList);

        Paginator paginator = new Paginator();
        paginator.setItemsPerPage(20);
        paginator.setPage(1);

        stringPageList.setPaginator(paginator);

        System.out.println(JSON.toJSONString(stringPageList));
    }

}


