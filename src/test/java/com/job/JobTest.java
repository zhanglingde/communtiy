package com.job;

import com.ling.other.CommunityApplication;
import com.ling.other.modules.job.NoticeSendJob;
import com.ling.other.modules.job.QuartzManage;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.entity.SupplierUser;
import com.ling.other.modules.user.service.SupplierUserService;
import com.ling.other.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/12/9 10:45
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobTest {

    @Autowired
    QuartzManage quartzManage;

    @Test
    public void addJob() {
        Date now = null;
        try {
            now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-24 22:34:10");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        quartzManage.addJob("测试Job", "2", now, NoticeSendJob.class);
    }


}
