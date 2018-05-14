package org.cynic.megaapplication.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.cynic.megaapplication.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
    private static SecureRandom secureRandom;

    @Autowired
    private SampleService sampleService;

    @GetMapping("/echo/{text}")
    public int echo(@PathVariable String text) {
        LOGGER.info("response", StringEscapeUtils.escapeJava(text));

        String result = text.equals("banana") ? sampleService.process(text) : "cacosa";
        LOGGER.info("result is " + StringEscapeUtils.escapeJava(result));

        return result.length();
    }

    @GetMapping("/sample")
    public String sample(HttpSession httpSession) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("application.properties");
        httpSession.setAttribute("file", url.toString());

        return "OK";
    }

//    @GetMapping("/randomizer")
//    public Long randomizer(HttpServletRequest httpServletRequest) {
//        String query = httpServletRequest.getQueryString();
//        LOGGER.info("processed Query: " + query);
//        return new Random().nextLong();
//    }
    @GetMapping("/randomizer")
    public Long randomizer(HttpServletRequest httpServletRequest) {
        String query = httpServletRequest.getQueryString();
        LOGGER.info("processed Query: " + StringEscapeUtils.escapeJava(query));
        return secureRandom.nextLong();
    }

    @GetMapping("/echo-dynamic/{fileName}")
    public String echoDynamic(@PathVariable String fileName) throws IOException, URISyntaxException {

        return sampleService.processFile(fileName);
    }


    @GetMapping("/hash/{text}")
    public String hashString(@PathVariable("text") String text) {

        return new String(DigestUtils.getSha256Digest().digest(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    @GetMapping("/validate")
    public boolean valdiate(@RequestParam String text) {
        Pattern pattern = Pattern.compile("^(a+)+$");

        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    @GetMapping("/forward/{url}")
    public String router(@PathVariable("url") String url) {
        if (url.equals("sample")) {
            return "redirect:" + "sample";
        } else if (url.equals("echo")) {
            return "redirect:" + "echo";
        } else {
            return "redirect:" + "sample";
        }
    }

    @GetMapping("/serve-file/{file}")
    public File serveFile(@PathVariable("file") String file) {
        switch (file) {
            case "./sample":
                return new File("./sample");
            case "./someOtherSample":
                return new File("./someOtherSample");
            default:
                return new File("./default");
        }
    }
}
