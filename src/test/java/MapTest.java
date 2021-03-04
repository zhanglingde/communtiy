import com.ling.other.common.utils.DateUtils;
import com.ling.other.modules.user.dto.User;
import org.joda.time.DateTime;
import org.junit.Test;

import java.nio.channels.SocketChannel;
import java.time.temporal.ValueRange;
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
        // 创建并赋值 HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java");
        map.put(2, "JDK");
        map.put(3, "Spring Framework");
        map.put(4, "MyBatis framework");
        map.put(5, "Java中文社群");
        
        // 遍历
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getKey() == 1) {
                map.remove(entry.getKey());
            }
        }
        //map.forEach((key, value)->{
        //    System.out.print(key);
        //    System.out.println(value);
        //});
    }
}
