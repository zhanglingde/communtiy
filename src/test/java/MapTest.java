import com.ling.other.common.utils.DateUtils;
import com.ling.other.modules.user.dto.User;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangling
 * @since 2020/12/10 14:40
 */
public class MapTest {

    @Test
    public void test() {

        List<User> list = new ArrayList<>();
        list.add(User.builder().id(1).username("zhangsan").gender("男").build());
        list.add(User.builder().id(2).username("lisi").gender("男").build());
        list.add(User.builder().id(3).username("maike").gender("女").build());
        list.add(User.builder().id(3).username("mali").gender("女").build());
        list.add(User.builder().id(5).username("jack").gender("其他").build());

        for(int i = 0 ; i<list.size();i++){
            list.get(i);
            System.out.println(list.get(i));
        }

        Date date = new Date();
        System.out.println(DateUtils.getYearByDate(date));
        System.out.println(DateUtils.getMonthByDate(date));
        System.out.println(DateUtils.getDayByMonth(date));


    }
}
