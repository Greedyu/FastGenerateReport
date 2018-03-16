package com;

import fp.bd.frame.FastGenerateReport;
import fp.bd.frame.sql.dto.*;
import fp.bd.frame.sql.po.*;
import fp.bd.frame.sql.service.FieldService;
import fp.bd.frame.sql.service.SQLService;
import fp.bd.frame.sql.service.TableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FastGenerateReport.class)
@WebAppConfiguration
public class TableTest {

    @Autowired
    FieldService fieldService;

    @Autowired
    SQLService sqlService;

    @Autowired
    TableService tableService;

    @Test
    public void runPrestoCMD()throws Exception{
        sqlService.executeSQL("show catalogs");
    }

    @Test
    public void addFieldList(){
        // 中文名称用于用户前端输入模糊匹配
        FieldPo fieldPo6 = new FieldPo("日销售相关表", "cdm_nsr.store_daily_sales_analyze" ,"日扣券销售金额","sub_coupon_sales_amount");
        FieldPo fieldPo1 = new FieldPo("日销售相关表", "cdm_nsr.store_daily_sales_analyze" ,"门店编码","store_code");
        FieldPo fieldPo2 = new FieldPo("日销售相关表", "cdm_nsr.store_daily_sales_analyze" ,"销售日期","sales_daily");
        FieldPo fieldPo3 = new FieldPo("月销售目标相关表", "cdm_nsr.store_monthly_analyze" ,"门店编码","store_code");
        FieldPo fieldPo4 = new FieldPo("月销售目标相关表", "cdm_nsr.store_monthly_analyze" ,"月执行目标","monthly_init_target_money");
        FieldPo fieldPo5 = new FieldPo("月销售目标相关表", "cdm_nsr.store_monthly_analyze" ,"月累计目标","monthly_cumulate_target_money");
        FieldPo fieldPo7 = new FieldPo("月销售目标相关表", "cdm_nsr.store_monthly_analyze" ,"销售目标年份","year");
        FieldPo fieldPo8 = new FieldPo("月销售目标相关表", "cdm_nsr.store_monthly_analyze" ,"销售目标月份","month");
        FieldPo fieldPo9 = new FieldPo("门店属性相关表", "cdm_nsr.store_summary" ,"店名","店名");
        FieldPo fieldPo10 = new FieldPo("门店属性相关表", "cdm_nsr.store_summary" ,"销售渠道","销售渠道");
        FieldPo fieldPo11 = new FieldPo("门店属性相关表", "cdm_nsr.store_summary" ,"区域","区域");
        FieldPo fieldPo12 = new FieldPo("门店属性相关表", "cdm_nsr.store_summary" ,"店铺形式","店铺形式");
        FieldPo fieldPo13 = new FieldPo("门店属性相关表", "cdm_nsr.store_summary" ,"门店编号","门店编号");
        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo6);
        list.add(fieldPo1);
        list.add(fieldPo2);
        list.add(fieldPo3);
        list.add(fieldPo4);
        list.add(fieldPo5);
        list.add(fieldPo7);
        list.add(fieldPo8);
        list.add(fieldPo9);
        list.add(fieldPo10);
        list.add(fieldPo11);
        list.add(fieldPo12);
        list.add(fieldPo13);
        fieldService.addFieldList(list);
        for (FieldPo fieldPo : list){
            TablePo tablePo = new TablePo();
            BeanUtils.copyProperties(fieldPo,tablePo);
            tableService.createTable(tablePo);
        }
    }

    @Test
    public void queryFiled() {
        FieldQuery fieldQuery =new FieldQuery();
        fieldQuery.setFieldNameLike("日");
        List<FieldPo> list = fieldService.queryFieldList(fieldQuery);
        System.out.println(list.size());
    }

    @Test
    public void queryStoreMTDSales() throws Exception{
        Query query = new Query();
        //设置本次相关查询
        query.setTableCodeList(Arrays.asList(new String("cdm_nsr.store_daily_sales_analyze")));
        query.setTableCode("cdm_nsr.store_daily_sales_analyze");
        query.setTableName("日销售相关表");
        FieldPo fieldPo = new FieldPo();
        //求门店mdt销售
        fieldPo.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)");
        fieldPo.setFieldName("门店mtd销售金额");
        FieldPo fieldPo1 = new FieldPo();
        fieldPo1.setFieldCode("cdm_nsr.store_daily_sales_analyze.store_code");
        fieldPo1.setFieldName("门店编码");
        FieldPo fieldPo2 = new FieldPo();
        fieldPo2.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.order_quantity)");
        fieldPo2.setFieldName("门店mtd单数");
        FieldPo fieldPo3 = new FieldPo();
        fieldPo3.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.item_quantity)/sum(cdm_nsr.store_daily_sales_analyze.order_quantity)");
        fieldPo3.setFieldName("门店mtd连带率");
        fieldPo3.setArithmeticFlag(1);
        FieldPo fieldPo4 = new FieldPo();
        fieldPo4.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)/sum(cdm_nsr.store_daily_sales_analyze.item_quantity)");
        fieldPo4.setFieldName("门店mtd件单价");
        fieldPo4.setArithmeticFlag(1);
        FieldPo fieldPo5 = new FieldPo();
        fieldPo5.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.discount_amount)/sum(cdm_nsr.store_daily_sales_analyze.real_sales_amount)");
        fieldPo5.setFieldName("门店mtd折损");
        fieldPo5.setArithmeticFlag(1);
        FieldPo fieldPo6 = new FieldPo();
        fieldPo6.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.vip_real_sales_amount)/sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)");
        fieldPo6.setFieldName("门店vip销售占比");
        fieldPo6.setArithmeticFlag(1);
        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo);
        list.add(fieldPo1);
        list.add(fieldPo2);
        list.add(fieldPo3);
        list.add(fieldPo4);
        list.add(fieldPo5);
        list.add(fieldPo6);
        //设置select部分
        query.setFieldList(list);

        Join join = new Join();
        join.setMainTable("cdm_nsr.store_daily_sales_analyze");
        query.setJoinList(Arrays.asList(join));

        WhereFilter whereFilter1 = new WhereFilter();
        whereFilter1.setTableCode("cdm_nsr.store_daily_sales_analyze");
        whereFilter1.setFieldCode("sales_daily");
        whereFilter1.setFilterWay(" < ");
        whereFilter1.setArithmeticFlag(1);
        //设置月初时间 （月初，昨天等表示形式可以提供表示方法给前端，或者是用户 ）
        whereFilter1.setFirstCondition("date_add('day', -1, CURRENT_DATE)");
        WhereFilter whereFilter2 = new WhereFilter();
        whereFilter2.setTableCode("cdm_nsr.store_daily_sales_analyze");
        whereFilter2.setFieldCode("sales_daily");
        whereFilter2.setFilterWay(" >= ");
        whereFilter2.setArithmeticFlag(1);
        whereFilter2.setFirstCondition("date_parse(date_format(date_add('day', -1, CURRENT_DATE), '%Y-%m-01'), '%Y-%m-%d')");
        List<WhereFilter> whereFilterList = new ArrayList<>();
        whereFilterList.add(whereFilter1);
        whereFilterList.add(whereFilter2);
        //设置查询条件
        query.setWhereFilterList(whereFilterList);

        GroupBy groupBy = new GroupBy();
        groupBy.setFieldCode("store_code");
        groupBy.setTableCode("cdm_nsr.store_daily_sales_analyze");
        //设置查询维度
        query.setGroupByList(Arrays.asList(groupBy));
        //设置新视图表名
        query.setNewTableCode("I_A_2000011016.mtd_sales");
        //设置新视图名称
        query.setNewTableName("I/A类门店MTD销售概况");
        sqlService.runSQL(query);
    }

    @Test
    public void queryLastYearStoreMTDSales() throws Exception{
        Query query = new Query();
        //设置本次相关查询
        query.setTableCodeList(Arrays.asList(new String("cdm_nsr.store_daily_sales_analyze")));
        query.setTableCode("cdm_nsr.store_daily_sales_analyze");
        query.setTableName("日销售相关表");
        FieldPo fieldPo = new FieldPo();
        //求门店mdt销售
        fieldPo.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)");
        fieldPo.setFieldName("门店去年mtd销售金额");
        FieldPo fieldPo1 = new FieldPo();
        fieldPo1.setFieldCode("cdm_nsr.store_daily_sales_analyze.store_code");
        fieldPo1.setFieldName("门店编码");
        FieldPo fieldPo2 = new FieldPo();
        fieldPo2.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.order_quantity)");
        fieldPo2.setFieldName("门店去年mtd单数");
        FieldPo fieldPo3 = new FieldPo();
        fieldPo3.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.item_quantity)/sum(cdm_nsr.store_daily_sales_analyze.order_quantity)");
        fieldPo3.setFieldName("门店去年mtd连带率");
        fieldPo3.setArithmeticFlag(1);
        FieldPo fieldPo4 = new FieldPo();
        fieldPo4.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)/sum(cdm_nsr.store_daily_sales_analyze.item_quantity)");
        fieldPo4.setFieldName("门店去年mtd件单价");
        fieldPo4.setArithmeticFlag(1);
        FieldPo fieldPo5 = new FieldPo();
        fieldPo5.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.discount_amount)/sum(cdm_nsr.store_daily_sales_analyze.real_sales_amount)");
        fieldPo5.setFieldName("门店去年mtd折损");
        fieldPo5.setArithmeticFlag(1);
        FieldPo fieldPo6 = new FieldPo();
        fieldPo6.setFieldCode("sum(cdm_nsr.store_daily_sales_analyze.vip_real_sales_amount)/sum(cdm_nsr.store_daily_sales_analyze.sub_coupon_sales_amount)");
        fieldPo6.setFieldName("门店去年mtd_vip销售占比");
        fieldPo6.setArithmeticFlag(1);
        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo);
        list.add(fieldPo1);
        list.add(fieldPo2);
        list.add(fieldPo3);
        list.add(fieldPo4);
        list.add(fieldPo5);
        list.add(fieldPo6);
        //设置select部分
        query.setFieldList(list);

        Join join = new Join();
        join.setMainTable("cdm_nsr.store_daily_sales_analyze");
        query.setJoinList(Arrays.asList(join));

        WhereFilter whereFilter1 = new WhereFilter();
        whereFilter1.setTableCode("cdm_nsr.store_daily_sales_analyze");
        whereFilter1.setFieldCode("sales_daily");
        whereFilter1.setFilterWay(" < ");
        whereFilter1.setArithmeticFlag(1);
        whereFilter1.setFirstCondition("date_add('year', -1, date_add('day', -1, CURRENT_DATE))");
        WhereFilter whereFilter2 = new WhereFilter();
        whereFilter2.setTableCode("cdm_nsr.store_daily_sales_analyze");
        whereFilter2.setFieldCode("sales_daily");
        whereFilter2.setFilterWay(" >= ");
        whereFilter2.setArithmeticFlag(1);
        whereFilter2.setFirstCondition("date_parse(date_format(date_add('year', -1, date_add('day', -1, CURRENT_DATE)), '%Y-%m-01'), '%Y-%m-%d')");
        List<WhereFilter> whereFilterList = new ArrayList<>();
        whereFilterList.add(whereFilter1);
        whereFilterList.add(whereFilter2);
        //设置查询条件
        query.setWhereFilterList(whereFilterList);

        GroupBy groupBy = new GroupBy();
        groupBy.setFieldCode("store_code");
        groupBy.setTableCode("cdm_nsr.store_daily_sales_analyze");
        //设置查询维度
        query.setGroupByList(Arrays.asList(groupBy));
        //设置新视图表名
        query.setNewTableCode("I_A_2000011016.last_mtd_sales");
        //设置新视图名称
        query.setNewTableName("门店去年MTD销售概况");
        sqlService.runSQL(query);
    }

    @Test
    public void mtdTargetAccomplish() throws Exception{
        Query query = new Query();
        //设置本次相关查询
        query.setTableCodeList(Arrays.asList(new String[]{"cdm_nsr.store_monthly_analyze","I_A_2000011016.mtd_sales"}));
        query.setTableCode("cdm_nsr.store_monthly_analyze");
        query.setTableName("月销售目标相关表");
        FieldPo fieldPo = new FieldPo();
        //求门店mdt销售
        fieldPo.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd销售金额\"/cdm_nsr.store_monthly_analyze.monthly_init_target_money");
        fieldPo.setFieldName("mtd达成%");
        fieldPo.setArithmeticFlag(1);
        FieldPo fieldPo1 = new FieldPo();
        fieldPo1.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd销售金额\"/cdm_nsr.store_monthly_analyze.monthly_cumulate_target_money");
        fieldPo1.setFieldName("mtd相对达成%");
        fieldPo1.setArithmeticFlag(1);

        FieldPo fieldPo2 = new FieldPo();
        fieldPo2.setFieldCode("I_A_2000011016.mtd_sales.\"门店编码\"");
        fieldPo2.setFieldName("门店编码");
        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo);
        list.add(fieldPo1);
        list.add(fieldPo2);
        //设置select部分
        query.setFieldList(list);

        Join join = new Join();
        join.setMainTable("cdm_nsr.store_monthly_analyze");
        Join join1 = new Join();
        join1.setJoinTable("I_A_2000011016.mtd_sales");
        Join.JoinTable joinTable = new Join.JoinTable();
        //设置join字段
        joinTable.setFirstFieldCode("store_code");
        joinTable.setFirstTableCode("cdm_nsr.store_monthly_analyze");
        joinTable.setSecondFieldCode("\"门店编码\"");
        joinTable.setSecondTableCode("I_A_2000011016.mtd_sales");
        join1.setJoinTableList(Arrays.asList(joinTable));
        query.setJoinList(Arrays.asList(new Join[]{join, join1}));
        //设置时间
        WhereFilter whereFilter1 = new WhereFilter();
        whereFilter1.setTableCode("cdm_nsr.store_monthly_analyze");
        whereFilter1.setFieldCode("year");
        whereFilter1.setFilterWay(" = ");
        whereFilter1.setArithmeticFlag(1);
        whereFilter1.setFirstCondition("year(date_add('day', -1, CURRENT_DATE))");
        WhereFilter whereFilter2 = new WhereFilter();
        whereFilter2.setTableCode("cdm_nsr.store_monthly_analyze");
        whereFilter2.setFieldCode("month");
        whereFilter2.setFilterWay(" = ");
        whereFilter2.setArithmeticFlag(1);
        whereFilter2.setFirstCondition("month(date_add('day', -1, CURRENT_DATE)))");
        List<WhereFilter> whereFilterList = new ArrayList<>();
        whereFilterList.add(whereFilter1);
        whereFilterList.add(whereFilter2);
        query.setWhereFilterList(whereFilterList);

        OrderBy orderBy = new OrderBy();
        orderBy.setDescFlag(1);
        orderBy.setFieldCode("\"mtd相对达成%\"");
        orderBy.setTableCode("");
        query.setOrderByList(Arrays.asList(orderBy));
        //设置新视图表名
        query.setNewTableCode("I_A_2000011016.mtd_target_accomplish");
        //设置新视图名称
        query.setNewTableName("门店目标达成概况");
        sqlService.runSQL(query);
    }

    @Test
    public void storeSummary()throws Exception{
        Query query = new Query();
        //设置本次相关查询
        query.setTableCodeList(Arrays.asList(new String[]{"cdm_nsr.store_info","cdm_nsr.store_info","cdm_nsr.store_info","cdm_nsr.store_info"}));
        query.setTableCode("cdm_nsr.store_info");
        query.setTableName("门店信息相关表");
        FieldPo fieldPo = new FieldPo();
        //求门店mdt销售
        fieldPo.setFieldCode("cdm_nsr.store_info.store_code");
        fieldPo.setFieldName("门店编码");
        FieldPo fieldPo1 = new FieldPo();
        fieldPo1.setFieldCode("cdm_nsr.store_info.store_name");
        fieldPo1.setFieldName("mtd相对达成%");
        fieldPo1.setArithmeticFlag(1);

        FieldPo fieldPo2 = new FieldPo();
        fieldPo2.setFieldCode("I_A_2000011016.mtd_sales.\"门店编码\"");
        fieldPo2.setFieldName("门店编码");
        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo);
        list.add(fieldPo1);
        list.add(fieldPo2);
        //设置select部分
        query.setFieldList(list);

        Join join = new Join();
        join.setMainTable("cdm_nsr.store_monthly_analyze");
        Join join1 = new Join();
        join1.setJoinTable("I_A_2000011016.mtd_sales");
        Join.JoinTable joinTable = new Join.JoinTable();
        //设置join字段
        joinTable.setFirstFieldCode("store_code");
        joinTable.setFirstTableCode("cdm_nsr.store_monthly_analyze");
        joinTable.setSecondFieldCode("\"门店编码\"");
        joinTable.setSecondTableCode("I_A_2000011016.mtd_sales");
        join1.setJoinTableList(Arrays.asList(joinTable));
        query.setJoinList(Arrays.asList(new Join[]{join, join1}));
        //设置时间
        WhereFilter whereFilter1 = new WhereFilter();
        whereFilter1.setTableCode("cdm_nsr.store_monthly_analyze");
        whereFilter1.setFieldCode("year");
        whereFilter1.setFilterWay(" = ");
        whereFilter1.setArithmeticFlag(1);
        whereFilter1.setFirstCondition("year(date_add('day', -1, CURRENT_DATE))");
        WhereFilter whereFilter2 = new WhereFilter();
        whereFilter2.setTableCode("cdm_nsr.store_monthly_analyze");
        whereFilter2.setFieldCode("month");
        whereFilter2.setFilterWay(" = ");
        whereFilter2.setArithmeticFlag(1);
        whereFilter2.setFirstCondition("month(date_add('day', -1, CURRENT_DATE)))");
        List<WhereFilter> whereFilterList = new ArrayList<>();
        whereFilterList.add(whereFilter1);
        whereFilterList.add(whereFilter2);
        query.setWhereFilterList(whereFilterList);

        OrderBy orderBy = new OrderBy();
        orderBy.setDescFlag(1);
        orderBy.setFieldCode("\"mtd相对达成%\"");
        orderBy.setTableCode("");
        query.setOrderByList(Arrays.asList(orderBy));
        //设置新视图表名
        query.setNewTableCode("I_A_2000011016.mtd_target_accomplish");
        //设置新视图名称
        query.setNewTableName("门店目标达成概况");
        sqlService.runSQL(query);
    }

    @Test
    public void storeMTDCompareLastYear()throws Exception{
        Query query = new Query();
        //设置本次相关查询
        query.setTableCodeList(Arrays.asList(new String[]{"I_A_2000011016.mtd_sales","I_A_2000011016.last_mtd_sales","I_A_2000011016.mtd_target_accomplish","cdm_nsr.store_summary"}));
        query.setTableCode("I_A_2000011016.mtd_sales");
        FieldPo fieldPo = new FieldPo();
        //求门店mdt销售
        fieldPo.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd销售金额\"");
        fieldPo.setFieldName("门店mtd销售金额");
        fieldPo.setArithmeticFlag(1);
        FieldPo fieldPo1 = new FieldPo();
        fieldPo1.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd单数\"");
        fieldPo1.setFieldName("门店mtd单数");
        fieldPo1.setArithmeticFlag(1);
        FieldPo fieldPo2 = new FieldPo();
        fieldPo2.setFieldCode("I_A_2000011016.mtd_sales.\"门店编码\"");
        fieldPo2.setFieldName("门店编码");
        FieldPo fieldPo3 = new FieldPo();
        fieldPo3.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd连带率\"");
        fieldPo3.setFieldName("门店mtd连带率");
        FieldPo fieldPo4 = new FieldPo();
        fieldPo4.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd件单价\"");
        fieldPo4.setFieldName("门店mtd件单价");
        FieldPo fieldPo5 = new FieldPo();
        fieldPo5.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd折损\"");
        fieldPo5.setFieldName("门店mtd折损");
        FieldPo fieldPo6 = new FieldPo();
        fieldPo6.setFieldCode("I_A_2000011016.mtd_sales.\"门店vip销售占比\"");
        fieldPo6.setFieldName("门店vip销售占比");

        FieldPo fieldPo7 = new FieldPo();
        fieldPo7.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd销售金额\"/I_A_2000011016.last_mtd_sales.\"门店去年mtd销售金额\"");
        fieldPo7.setFieldName("门店mtd销售同比");
        fieldPo7.setArithmeticFlag(1);
        FieldPo fieldPo8 = new FieldPo();
        fieldPo8.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd单数\"/I_A_2000011016.last_mtd_sales.\"门店去年mtd单数\"");
        fieldPo8.setFieldName("门店mtd单数同比");
        fieldPo8.setArithmeticFlag(1);
        FieldPo fieldPo9 = new FieldPo();
        fieldPo9.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd连带率\" - I_A_2000011016.last_mtd_sales.\"门店去年mtd连带率\"");
        fieldPo9.setFieldName("门店mtd连带率同比");
        fieldPo9.setArithmeticFlag(1);
        FieldPo fieldPo10 = new FieldPo();
        fieldPo10.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd件单价\"/I_A_2000011016.last_mtd_sales.\"门店去年mtd件单价\"");
        fieldPo10.setFieldName("门店mtd件单价同比");
        fieldPo10.setArithmeticFlag(1);
        FieldPo fieldPo11 = new FieldPo();
        fieldPo11.setFieldCode("I_A_2000011016.mtd_sales.\"门店mtd折损\"/I_A_2000011016.last_mtd_sales.\"门店去年mtd折损\"");
        fieldPo11.setFieldName("门店mtd折损同比");
        fieldPo11.setArithmeticFlag(1);
        FieldPo fieldPo12 = new FieldPo();
        fieldPo12.setFieldCode("I_A_2000011016.mtd_sales.\"门店vip销售占比\"/I_A_2000011016.last_mtd_sales.\"门店去年mtd_vip销售占比\"");
        fieldPo12.setFieldName("vip销售占比同比");
        fieldPo12.setArithmeticFlag(1);

        FieldPo fieldPo13 = new FieldPo();
        fieldPo13.setFieldCode("I_A_2000011016.mtd_target_accomplish.\"mtd达成%\"");
        fieldPo13.setFieldName("mtd达成%");
        FieldPo fieldPo14 = new FieldPo();
        fieldPo14.setFieldCode("I_A_2000011016.mtd_target_accomplish.\"mtd相对达成%\"");
        fieldPo14.setFieldName("mtd相对达成%");
        FieldPo fieldPo15 = new FieldPo();
        fieldPo15.setFieldCode("cdm_nsr.store_summary.\"店名\"");
        fieldPo15.setFieldName("店名");
        FieldPo fieldPo16 = new FieldPo();
        fieldPo16.setFieldCode("cdm_nsr.store_summary.\"销售渠道\"");
        fieldPo16.setFieldName("销售渠道");
        FieldPo fieldPo17 = new FieldPo();
        fieldPo17.setFieldCode("cdm_nsr.store_summary.\"区域\"");
        fieldPo17.setFieldName("区域");
        FieldPo fieldPo18 = new FieldPo();
        fieldPo18.setFieldCode("cdm_nsr.store_summary.\"店铺形式\"");
        fieldPo18.setFieldName("店铺形式");


        List<FieldPo> list = new ArrayList<>();
        list.add(fieldPo15);
        list.add(fieldPo16);
        list.add(fieldPo17);
        list.add(fieldPo18);
        list.add(fieldPo);
        list.add(fieldPo1);
        list.add(fieldPo2);
        list.add(fieldPo3);
        list.add(fieldPo4);
        list.add(fieldPo5);
        list.add(fieldPo6);
        list.add(fieldPo7);
        list.add(fieldPo8);
        list.add(fieldPo9);
        list.add(fieldPo10);
        list.add(fieldPo11);
        list.add(fieldPo12);
        list.add(fieldPo13);
        list.add(fieldPo14);
        //设置select部分
        query.setFieldList(list);

        Join join = new Join();
        join.setMainTable("I_A_2000011016.mtd_sales");
        Join join1 = new Join();
        join1.setJoinTable("I_A_2000011016.last_mtd_sales");
        Join.JoinTable joinTable = new Join.JoinTable();
        //设置join字段
        joinTable.setFirstFieldCode("\"门店编码\"");
        joinTable.setFirstTableCode("I_A_2000011016.last_mtd_sales");
        joinTable.setSecondFieldCode("\"门店编码\"");
        joinTable.setSecondTableCode("I_A_2000011016.mtd_sales");
        join1.setJoinTableList(Arrays.asList(joinTable));

        Join join2 = new Join();
        join2.setJoinTable("I_A_2000011016.mtd_target_accomplish");
        Join.JoinTable joinTable2 = new Join.JoinTable();
        //设置join字段
        joinTable2.setFirstFieldCode("\"门店编码\"");
        joinTable2.setFirstTableCode("I_A_2000011016.mtd_sales");
        joinTable2.setSecondFieldCode("\"门店编码\"");
        joinTable2.setSecondTableCode("I_A_2000011016.mtd_target_accomplish");
        join2.setJoinTableList(Arrays.asList(joinTable2));

        Join join3 = new Join();
        join3.setJoinTable("cdm_nsr.store_summary");
        Join.JoinTable joinTable3 = new Join.JoinTable();
        //设置join字段
        joinTable3.setFirstFieldCode("\"门店编码\"");
        joinTable3.setFirstTableCode("I_A_2000011016.mtd_sales");
        joinTable3.setSecondFieldCode("\"门店编号\"");
        joinTable3.setSecondTableCode("cdm_nsr.store_summary");
        join3.setJoinTableList(Arrays.asList(joinTable3));
        query.setJoinList(Arrays.asList(new Join[]{join,join1,join2,join3}));

        //设置新视图表名
        query.setNewTableCode("I_A_2000011016.store_mtd");
        //设置新视图名称
        query.setNewTableName("门店MTD销售相关");
        sqlService.runSQL(query);
    }


    @Test
    public void reload()throws Exception{
        sqlService.reloadTable("xxx.report_mtd_accomplish");
    }


    @Test
    public void findSameName(){
        List<String> fieldNameList = fieldService.querySameNameField("xxx_report_mtd_sales","xxx.report_mtd_sales");
        System.out.println(fieldNameList.size());
    }
}
