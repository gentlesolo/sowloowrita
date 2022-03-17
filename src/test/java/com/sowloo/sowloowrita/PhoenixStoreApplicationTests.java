//package com.sowloo.sowloowrita;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.sql.DataSource;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Slf4j
//class PhoenixStoreApplicationTests {
//
//	@Autowired
//	private DataSource dataSource;
//
//	@Value("${test.property.name}")
//	private String testName;
//
//	@Test
//	void valueExists() {
//		assertThat(testName).isEqualTo("phoenix");
//
//		log.info(testName);
//	}
//
//	@Test
//	void applicationCanConnectToDbTest(){
//		assertThat(dataSource).isNotNull();
//		Connection connection;
//
//		try{
//			connection = dataSource.getConnection();
//			assertThat(connection).isNotNull();
//			log.info("Connection-> {}", connection.getSchema());
//		}catch (SQLException e){
//
//			e.printStackTrace();
//		}
//	}
//
//}
