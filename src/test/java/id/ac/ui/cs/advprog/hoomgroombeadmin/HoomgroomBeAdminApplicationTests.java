package id.ac.ui.cs.advprog.hoomgroombeadmin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.annotation.Resource;
import id.ac.ui.cs.advprog.hoomgroombeadmin.controller.ProductRestController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HoomgroomBeAdminApplicationTests {
	@Resource(name="productController")
	private ProductRestController productController;
//	@Test
//	void contextLoads() {
//		HoomgroomBeAdminApplication.main(new String[] {});
//		assertThat(productController).isNotNull();
//	}

}
