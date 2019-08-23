package com.example.springboot.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@EnableAutoConfiguration
public class BrandController {
    @RequestMapping("/258")
    public static void brand() throws IOException {
        //利用Selenium爬取js渲染之后的网页；其他的方法有htmlunit
        //获取品牌
        String url="https://search.jd.com/Search?keyword=%E7%99%BD%E6%9D%BF&enc=utf-8&wq=%E7%99%BD%E6%9D%BF&pvid=df0918133e1b4de78c5031f34ddd6bed";
        BufferedWriter writer=new BufferedWriter(new FileWriter("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\brand"));
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.navigate().to(url);
        webDriver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        webDriver.get(url);
        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"J_selector\"]/div[1]/div/div[3]/a[1]"));
        webElement.click();
        webDriver.getPageSource();
        List<WebElement> webElement1 = webDriver.findElements(By.xpath("//*[@id=\"J_selector\"]/div[1]/div/div[2]/div[2]/ul/li/a"));
        for (WebElement webs:webElement1
                ) {
            System.out.println(webs.getAttribute("title"));
            writer.write(webs.getAttribute("title")+"\r\n");
        }
        writer.close();
        System.out.println(webElement1.size());
        webDriver.close();

    }
}
