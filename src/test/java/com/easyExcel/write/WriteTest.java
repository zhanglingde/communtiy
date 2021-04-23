package com.easyExcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.easyExcel.interceptor.DropWriteHandler;
import com.easyExcel.interceptor.DropWriteHandler2;
import com.ling.other.CommunityApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 写数据成Excel，导出数据为Excel
 *
 * @author zhangling
 * @since 2020/12/11 15:17
 */
public class WriteTest {

    static List<Employee> init() {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).build());
        employees.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).build());
        employees.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).build());
        return employees;
    }

    List<Department> mergeDepartment() {
        List<Department> departments = new ArrayList<>();

        // 技术部
        List<Employee> list1 = new ArrayList<>();
        list1.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        list1.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        Department jishu = Department.builder().departmentId(1).departmentName("技术部").build();
        jishu.setEmployees(list1);
        departments.add(jishu);

        // 运营部
        List<Employee> list2 = new ArrayList<>();
        list2.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).departmentId(2).departmentName("运营部").build());
        Department yunying = Department.builder().departmentId(2).departmentName("运营部").build();
        yunying.setEmployees(list2);
        departments.add(yunying);

        // 财务部
        List<Employee> list3 = new ArrayList<>();
        list3.add(Employee.builder().employeeId(4).employeeName("jack").birthday(new Date()).departmentId(3).departmentName("财务部").build());
        list3.add(Employee.builder().employeeId(5).employeeName("tom").birthday(new Date()).departmentId(3).departmentName("财务部").build());

        Department caiwu = Department.builder().departmentId(3).departmentName("财务部").build();
        caiwu.setEmployees(list3);
        departments.add(caiwu);
        return departments;
    }

    static List<Employee> mergeEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        employees.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        employees.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).departmentId(2).departmentName("运营部").build());
        employees.add(Employee.builder().employeeId(4).employeeName("jack").birthday(new Date()).departmentId(3).departmentName("财务部").build());
        employees.add(Employee.builder().employeeId(5).employeeName("tom").birthday(new Date()).departmentId(3).departmentName("财务部").build());
        return employees;
    }

    /**
     * 简单的写Excel
     */
    @Test
    public void simpleWrite() {

        List<Employee> list = init();
        // 导出路径 /D:/AAAShuju/IntelliJIDEA/communtiy/target/test-classes/订单1607672223048.xlsx
        String fileName = WriteTest.class.getResource("/").getPath() + "订单" + System.currentTimeMillis() + ".xlsx";

        // 写法一：将Employee模型的数据导出到Excel的第一个sheel，sheet名称为模板
        // excelType：使用03版的xls文件
        EasyExcel.write(fileName, Employee.class).excelType(ExcelTypeEnum.XLS).sheet("模板").doWrite(list);

        // 写法二：同一个excelWriter对象 写入的就是一个Excel文件里
        // 同一个 WriteSheet 对象就是写入同一个sheet中
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, Employee.class).build();

            for (int i = 0; i < 5; i++) {
                // 写入到5个sheet中
                WriteSheet writeSheet = EasyExcel.writerSheet("模板" + i).build();
                excelWriter.write(list, writeSheet);
            }
        } finally {
            // finish方法会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 合并单元格写
     * <p>
     * 使用了策略模式，可以自己实现合并策略，实现合并单元格的方式
     */
    @Test
    public void mergeWrite() {
        List<Employee> employees = mergeEmployees();
        List<Department> departments = mergeDepartment();

        // 导出路径 /D:/AAAShuju/IntelliJIDEA/communtiy/target/test-classes/订单1607672223048.xlsx
        String fileName = WriteTest.class.getResource("/").getPath() + "订单" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            // 合并开始的行
            int i = 1;
            // 要合并的列的索引
            int[] arr = {3, 4};

            // 使用合并策略
            ExcelMergeStrategy strategy = new ExcelMergeStrategy(i, arr);
            excelWriter = EasyExcel.write(fileName, Employee.class).registerWriteHandler(strategy).build();

            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            for (Department department : departments) {
                // 模拟数据库查询部门下的员工
                List<Employee> temp = department.getEmployees();

                // 修改合并策略：每一合并单元格开始合并前修改开始合并的行数为当前数据所在的行
                strategy.setMergeRowIndex(i);

                // 写入Excel
                excelWriter.write(temp, writeSheet);

                i = i + temp.size();
            }
        } finally {
            // finish方法会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    /**
     * 下拉列表
     */
    @Test
    public void dropWriter() {
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        List<String> classes = new ArrayList<>();
        classes.add("班级1");
        classes.add("班级2");
        classes.add("班级3");
        //request.getSession().setAttribute("classGradeList", classes);

        List<Employee> list = init();
        // 导出路径 /D:/AAAShuju/IntelliJIDEA/communtiy/target/test-classes/订单1607672223048.xlsx
        String fileName = WriteTest.class.getResource("/").getPath() + "订单" + System.currentTimeMillis() + ".xlsx";


        // 写法二：同一个excelWriter对象 写入的就是一个Excel文件里
        // 同一个 WriteSheet 对象就是写入同一个sheet中
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, Employee.class).registerWriteHandler(new DropWriteHandler2()).build();


            // 写入到5个sheet中
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(list, writeSheet);
        } finally {
            // finish方法会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }


}
