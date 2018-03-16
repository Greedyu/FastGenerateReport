# FastGenerateReport
快速生成报表系统

系统意在让一个不懂SQL用户，通过提供的业务基础数据（a表），进行运算，筛选，拼接等操作组成新的数据（b表）。
拼接可基于基础表（a），也可以利用组合后中间表（b）。最终得到一份完整的表格（c）。之后在每一次导出（c）都会自动加载之前逻辑更新基础数据。
从而长期

本系统使用了Presto进行数据的etl，没有安装的朋友需要在官网下载安装 https://prestodb.io/docs/current/installation/deployment.html

单元测试 ：
这里提供三个基础表。（也可以通过 PrestoUtil 先扫描presto中的表，将字段，表名加入mysql。只是这样就需要在创建表的时候先按规则备注中文，不然用户难以使用）
日销售相关表 cdm_nsr.store_daily_sales_analyze ;

            Column            |     Type      | Extra | Comment 
------------------------------+---------------+-------+---------
 store_code                   | varchar       |       | 门店编号        
 sales_daily                  | date          |       | 销售日期        
 order_quantity               | bigint        |       | 单数        
 real_sales_amount            | decimal(38,2) |       |         
 sub_coupon_sales_amount      | decimal(38,2) |       | 扣券销售        
 retail_sales_amount          | decimal(38,2) |       |         
 discount_amount              | decimal(38,2) |       |         
 item_quantity                | bigint        |       |         
 vip_real_sales_amount        | decimal(38,2) |       |         
 common_vip_real_sales_amount | decimal(38,2) |       |         
 return_item_quantity         | bigint        |       |         
 return_order_quantity        | bigint        |       |         
 return_amount                | decimal(38,2) |       |     
 
 月销售目标相关表 cdm_nsr.store_monthly_analyze ;
     
  Column             |     Type      | Extra | Comment 
 --------------------------------+---------------+-------+---------
  store_code                     | varchar       |       | 门店编号        
  year                           | bigint        |       | 销售目标年份       
  month                          | bigint        |       | 销售目标月份        
  order_quantity                 | decimal(38,3) |       |         
  real_sales_amount              | decimal(38,3) |       |         
  sub_coupon_sales_amount        | decimal(38,3) |       |         
  retail_sales_amount            | decimal(38,3) |       |         
  discount_amount                | decimal(38,3) |       |         
  item_quantity                  | decimal(38,3) |       |                
  store_monthly_sales_analyze_no | varchar(60)   |       |         
  monthly_year_goal_money        | decimal(38,3) |       |         
  monthly_init_target_money      | decimal(38,3) |       | 门店初始目标        
  monthly_cumulate_target_money  | decimal(38,3) |       | 门店累计目标 
  
  
  
  门店属性相关表 cdm_nsr.store_summary
    Column  |     Type     | Extra | Comment 
  ----------+--------------+-------+---------
   门店编号 | varchar(20)  |       |         
   店名     | varchar      |       |         
   销售渠道 | varchar(255) |       |         
   店铺形式 | varchar(255) |       |         
   区域     | varchar(255) |       |         
   
   
   通过cdm_nsr.store_daily_sales_analyze  过滤得到
   今年本月 （I_A_2000011016.mtd_sales） ，和去年本月的销售数据 （I_A_2000011016.last_mtd_sales） 。
   
   关联I_A_2000011016.mtd_sales 和cdm_nsr.store_monthly_analyze 得到销售达成相关数据
   I_A_2000011016.mtd_target_accomplish 
    mtd达成%     | decimal(38,3）         
    mtd相对达成% | decimal(38,3)       
    门店编码     | varchar      
    
   关联I_A_2000011016.mtd_sales ，I_A_2000011016.mtd_target_accomplish ，I_A_2000011016.mtd_sales），I_A_2000011016.last_mtd_sales
   得到 报表.jpg 中门店部分数据。（总计数据可以通过类似的方式先聚合，然后通过union纵向关联）
   生成最终表后中间sql的创建记录都在mysql中保存。
   
    
   