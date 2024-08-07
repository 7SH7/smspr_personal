package org.example.smspr.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration  //  이 클래스가 Spring의 설정 클래스임을 의미
// MapperScan :MyBatis의 mapper 인터페이스 위치를 지정. 지정된 패키지에서 선언해준 함수(매퍼)를 스캔하고, spring의 bean 등록
// basePackages: 매퍼 인터페이스가 위치한 패키지를 지정
// sqlSessionFactoryRef: 매퍼가 사용할 SqlSessionFactory의 이름을 지정
@MapperScan(basePackages = {"org.example.smspr.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean(); // bean 주입.
		// bean 주입 이유 : Bean 주입의 이유: 기존에 만들어진 객체를 커스터마이즈하여 사용하고 싶을 때 사용
		sessionFactoryBean.setDataSource(dataSource);   // DB 연결 담당
		sessionFactoryBean.setTypeAliasesPackage("org.example.smspr.dto"); // MyBatis에서 사용할 type 별칭의 패키지 설정
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")); // mapper xml 파일 위치 설정
		return sessionFactoryBean.getObject();
	}

}

// SqlSession은 데이터베이스와 SQL 쿼리를 연결하고, SqlSessionFactory는 이 SqlSession을 생성하고 관리합니다.
// 여러 개의 SqlSessionFactory가 필요할 경우 이름 설정이 필요하며, 이를 위해 sqlSessionFactoryRef를 사용합니다.
// 이름 설정이 필요 없는 경우, @Bean만 적어주면 됩니다.

// 패키지 지정: basePackages = {"org.example.smspr.mapper"}는 MyBatis 매퍼 인터페이스들이 위치한 패키지를 지정합니다. 이 패키지 내의 모든 인터페이스를 찾아서 매퍼로 인식합니다.
// SqlSessionFactory 연결: sqlSessionFactoryRef = "sqlSessionFactory"는 스캔한 매퍼 인터페이스들이 사용할 SqlSessionFactory를 지정합니다.
// 이 설정을 통해, MyBatis는 데이터베이스와의 연결 및 SQL 쿼리 실행을 위해 지정된 SqlSessionFactory를 사용하게 됩니다.

// 그니까, sqlSessionFactory는 xml과 interface를 연결해주는 역할을 한다.