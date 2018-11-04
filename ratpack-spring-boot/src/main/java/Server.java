import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ratpack.spring.config.EnableRatpack;

/**
 * Created by Howell on 28/2/17.
 * e-mail:th15817161961@gmail.com
 */
@SpringBootApplication
//@EnableRatpack
@Slf4j
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
