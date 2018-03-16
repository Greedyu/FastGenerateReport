package fp.bd.frame.common;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by kongryan on 16/7/28.
 */
@Configuration // 该注解类似于spring配置文件
//@MapperScan(basePackages = "fp.bd.frame.**.presto",sqlSessionFactoryRef = "prestoSqlSessionFactory")
public class PrestoConfig {

    @Autowired
    private Environment env;

    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean(name = "prestoDataSource")
    public DataSource prestoDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc.presto.driverClassName"));
        props.put("url", env.getProperty("jdbc.presto.url"));
        props.put("username", env.getProperty("jdbc.presto.username"));
        props.put("password", env.getProperty("jdbc.presto.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name = "prestoSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("prestoDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        return fb.getObject();
    }

}
