package com.footprintcat.frostiot.core.springboot.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nieqiurong 2023年8月3日 21:12:08
 */
@Configuration
//TODO 如果使用这个方式的话,记得一定要指定sqlSessionTemplateRef或sqlSessionFactoryRef https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start-for-building-native-image#how-to-use-mapperscan
//@MapperScan(basePackages = "com.example.nativedemo", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        //TODO 3.5.3.1 以下插件多的话,可能会报异常,这个在最新版本修改.  https://github.com/baomidou/mybatis-plus/issues/5532
//        plusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
//        plusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
//        plusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return plusInterceptor;
    }

}
