package kh.ddeonabom.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @Value("${admin.url}")
    private String adminUrl;

    @ModelAttribute("adminUrl")
    public String adminUrl() {
        return adminUrl;
    }
}