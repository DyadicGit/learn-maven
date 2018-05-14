package org.cynic.megaapplication.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class SampleService {

  public String process(String text) {
    URL url = getClass().getClassLoader().getResource("application.properties");
    try {
      File file = new File(url.toURI());
       return new StringBuilder(FileUtils.readFileToString(file, StandardCharsets.UTF_8)).reverse().toString() + text;
    } catch (IOException | URISyntaxException e) {
      return null;
    }
  }

  public String processFile(String fileName) throws URISyntaxException, IOException {
    URL url = getClass().getClassLoader().getResource(fileName);
    File file = new File(url.toURI());
//    FileReader fileReader = new FileReader(file);
//    char[] data = new char[1000];
//    fileReader.read(data);
    return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
  }
}
