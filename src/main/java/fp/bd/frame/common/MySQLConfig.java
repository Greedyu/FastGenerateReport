package fp.bd.frame.common;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.fp.expand.mybatis.interceptor.SortableInterceptor;
import com.fp.expand.mybatis.type.JsonAttrTypeHandler;
import com.fp.expand.mybatis.type.ListAttrTypeHandler;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by kongryan on 16/7/28.
 */
@Configuration // 该注解类似于spring配置文件
@MapperScan(basePackages = "fp.bd.frame.**.dao",sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MySQLConfig {

    @Autowired
    private Environment env;

    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean(name = "mysqlDataSource")
    @Primary
    public DataSource myTestDbDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc.driverClassName"));
        props.put("url", env.getProperty("jdbc.url"));
        props.put("username", env.getProperty("jdbc.username"));
        props.put("password", env.getProperty("jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }


    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        org.apache.ibatis.session.Configuration mybatisConfig =new org.apache.ibatis.session.Configuration();
        mybatisConfig.setMapUnderscoreToCamelCase(true);
        mybatisConfig.setLogImpl(StdOutImpl.class);//打印日志时直接开启

        Interceptor pagePlg = new PageInterceptor();
        Properties props = new Properties();
        props.put("reasonable", "false");
        props.put("supportMethodsArguments", "true");
        props.put("returnPageInfo", "check");
        props.put("params", "count=false");
        props.put("pageSizeZero", "true");//pageSize为0时不分页

        pagePlg.setProperties(props);
//        mybatisConfig.addInterceptor(pagePlg);
        fb.setConfiguration(mybatisConfig);

        SortableInterceptor sortableInterceptor =new SortableInterceptor();
        fb.setPlugins(new Interceptor[]{pagePlg,sortableInterceptor});

        //TODO
//        fb.setTypeHandlers(new TypeHandler<?>[]{new JsonAttrTypeHandler(), new ListAttrTypeHandler(), new JsonArrayAttrTypeHandler()});
        fb.setTypeHandlers(new TypeHandler<?>[]{new JsonAttrTypeHandler(),new ListAttrTypeHandler()});
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }


}
