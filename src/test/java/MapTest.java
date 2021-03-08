import com.ling.other.common.utils.DateUtils;
import com.ling.other.modules.user.dto.User;
import lombok.Data;
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

    @Data
    public static class User {
        public Integer id;
        public String username;
    }

    @Test
    public void test() {
        User user = new User();
        user.setUsername("vh");


    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println(i);
        }
    }
}
