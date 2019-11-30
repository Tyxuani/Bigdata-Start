package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.Pet;
import springboot.User;

import java.util.Arrays;

@RestController
@SpringBootApplication
public class UserController {
    public static void main(String[] args) {
        SpringApplication.run(UserController.class);
    }

    @RequestMapping("/login")
    public User login(){
        User user = new User(123, "meicai", "123456", Arrays.asList(Pet.B));
        return user;
    }
}



