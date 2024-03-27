package com.example.springboot;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
@MapperScan("com.example.springboot.mapper") // 指定 MyBatis Mapper 的扫描路径
// @ComponentScan(basePackages = {"com.example.springboot.common.AuthAccess"}) // 作用同上面的注解，确保自定义注解 AuthAccess 能被扫描到
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
    
    @Bean
    // public SqlSessionFactory sqlSessionFactory() throws Exception {
    //     SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
    //     // 设置 MyBatis 的配置文件路径
    //     sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
    //     return sessionFactory.getObject();
    // }
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource); // 使用自动配置的数据源
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return sessionFactory.getObject();
    }
    
    // // 手动创建 UserMapper Bean
    // @Bean
    // public UserMapper userMapper() {
    //     return new UserMapper(); // 这里需要根据实际情况创建 UserMapper 的实现类
    // }

}
