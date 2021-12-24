package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

@Value("${file_path}")
private String file_path;

        @GetMapping("/test")
        public String test() throws IOException {
            return "Тест прошёл успешно. Путь до файлов : " + file_path;
        }
    }
