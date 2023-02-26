
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@EnableAutoConfiguration
public class TransformationApplication {

    @PostMapping(value = "/transformation", consumes = "text/plain")
    String transformation(@RequestBody String message) {
        String output = "";
        String regexPattern = "Tour id: \"(.*)\"\r\nLocation: \"(.*)\"\r\nPerson: \"(.*) (.*)\"";
        Pattern p = Pattern.compile(regexPattern);

        Matcher m = p.matcher(message);
        if ( m.find() )
            output += ("{\n" + "  \"id\": \"" + m.group(1) + "\",\n"
                    + "  \"location\": \"" + m.group(2) + "\",\n"
                    + "  \"person\": {\n" + ""
                    + "    \"name\": \"" + m.group(3) + "\",\n"
                    + "    \"surname\": \"" + m.group(4) + "\"\n" + "  }\n" + "}\n");

        return output;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransformationApplication.class, args);
    }

}