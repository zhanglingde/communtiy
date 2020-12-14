package com.hutool.excel.write;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @since 2020/12/14 10:16
 */
public class WriteTest {

    static List<List<Employee>> init() {
        List<Employee> employees1 = new ArrayList<>();
        employees1.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).build());

        List<List<Employee>> lists = new ArrayList<>();
        lists.add(employees1);

        List<Employee> employees2 = new ArrayList<>();
        employees2.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).build());
        lists.add(employees1);

        List<Employee> employees3 = new ArrayList<>();
        employees3.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).build());
        lists.add(employees3);

        return lists;
    }

    /**
     * 写list数据到Excel
     */
    @Test
    public void writeList() {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        List<List<Employee>> init = init();

        //// 保存地址
        ExcelWriter writer = ExcelUtil.getWriter(WriteTest.class.getResource("/").getPath() + "测试" + System.currentTimeMillis() + ".xlsx");
        //writer.passCurrentRow();
        //writer.merge(row1.size()-1,"测试标题");

        writer.write(init, true);
        writer.close();

    }

    /**
     * map的写
     */
    public void writeMap() {

    }

    /**
     * 将对象封装成List写出数据
     */
    @Test
    public void writeBean() {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).build());
        employees.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).build());
        employees.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).build());

        // 保存地址
        ExcelWriter writer = ExcelUtil.getWriter(WriteTest.class.getResource("/").getPath() + "测试" + System.currentTimeMillis() + ".xlsx");


        writer.addHeaderAlias("employeeId", "员工Id");
        writer.addHeaderAlias("employeeName", "员工姓名");
        writer.addHeaderAlias("birthday", "生日");
        writer.addHeaderAlias("departmentId", "部门Id");
        writer.addHeaderAlias("departmentName", "部门名称");

        //writer.merge(0,0,0,4,"测试",false);
        writer.merge(0, 2, 0, 0, "测试", true);
        //writer.merge(4,"员工信息");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(employees, true);
        writer.write(employees);
        writer.merge(4, "Excel结束");
        writer.close();
    }


    static List<Employee> mergeEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().employeeId(1).employeeName("张三").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        employees.add(Employee.builder().employeeId(2).employeeName("李四").birthday(new Date()).departmentId(1).departmentName("技术部").build());
        employees.add(Employee.builder().employeeId(3).employeeName("mark").birthday(new Date()).departmentId(2).departmentName("运营部").build());
        employees.add(Employee.builder().employeeId(4).employeeName("jack").birthday(new Date()).departmentId(3).departmentName("财务部").build());
        employees.add(Employee.builder().employeeId(5).employeeName("tom").birthday(new Date()).departmentId(3).departmentName("财务部").build());
        employees.add(Employee.builder().employeeId(6).employeeName("bai").birthday(new Date()).departmentId(4).departmentName("HR").build());
        employees.add(Employee.builder().employeeId(7).employeeName("jerry").birthday(new Date()).departmentId(4).departmentName("HR").build());
        employees.add(Employee.builder().employeeId(8).employeeName("ter").birthday(new Date()).departmentId(4).departmentName("HR").build());
        return employees;
    }

    /**
     * 合并单元格写数据
     */
    @Test
    public void mergeWrite() {
        List<Employee> employees = mergeEmployees();

        Map<Integer, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDepartmentId));

        // 保存地址
        ExcelWriter writer = ExcelUtil.getWriter(WriteTest.class.getResource("/").getPath() + "测试" + System.currentTimeMillis() + ".xlsx");

        writer.addHeaderAlias("employeeId", "员工Id");
        writer.addHeaderAlias("employeeName", "员工姓名");
        writer.addHeaderAlias("birthday", "生日");
        writer.addHeaderAlias("departmentId", "部门Id");
        writer.addHeaderAlias("departmentName", "部门名称");

        int i = 1;
        for (Map.Entry<Integer, List<Employee>> entry : collect.entrySet()) {
            for (int j = 3; j < 5; j++) {
                if (entry.getValue().size() > 1) {
                    writer.merge(i, i + entry.getValue().size() - 1, j, j, null, true);
                }
            }
            i = i + entry.getValue().size();
        }

        writer.write(employees);
        writer.close();
    }
}
