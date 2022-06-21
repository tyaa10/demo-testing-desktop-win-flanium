/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.demo.java.flanium.maven;

import FlaNium.WinAPI.webdriver.DesktopOptions;
import FlaNium.WinAPI.webdriver.FlaNiumDriver;
import FlaNium.WinAPI.webdriver.FlaNiumDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

/**
 * @author Yurii
 */
public class Main {

    private static FlaNiumDriver driver;
    private static FlaNiumDriverService service;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello Flanium!");
        try {
            demoNotepadTest(getDriver());
        } finally {
            if (driver != null) {
                driver.close();
            }
            if (service.isRunning()) {
                service.stop();
            }
        }
    }

    private static FlaNiumDriver getDriver() {
        // Получение экземпляра драйвера приложения
        if(driver == null) {
            String DRIVER_PATH = "src/main/resources/driver/FlaNium.Desktop.Driver-v1.6.0/FlaNium.Driver.exe";
            String APP_PATH = "C:\\Windows\\system32\\notepad.exe";
            int driverPort = 9993;

            // Инициализация драйвера:
            service = new FlaNiumDriverService.Builder()
                    // Указание пути до драйвера
                    .usingDriverExecutable(new File(DRIVER_PATH).getAbsoluteFile())
                    // Установка порта (по умолчанию 9999)
                    .usingPort(driverPort)
                    // Включение режима отладки (вывод логов в консоль)
                    .withVerbose(true)
                    // Отключение логирования
                    .withSilent(false)
                    .buildDesktopService();
            // Инициализация приложения:
            DesktopOptions options = new DesktopOptions();
            // Указание пути до тестируемого приложения
            options.setApplicationPath(APP_PATH);
            // Задержка после запуска приложения (сек)
            options.setLaunchDelay(5);
            // Подключение к ранее запущенному экземпляру приложения
            options.setDebugConnectToRunningApp(false);
            driver = new FlaNiumDriver(service, options);
        }
        return driver;
    }

    public static void demoNotepadTest(FlaNiumDriver driver) throws InterruptedException {
        /* List<WebElement> elements =
                driver.findElements(By.xpath("//*[(@ControlType = 'MenuItem')]"));
        System.out.println("Elements:");
        elements.forEach(webElement -> System.out.println(webElement.getText()));
        System.out.println("***"); */
        WebElement element =
                driver.findElement(By.xpath("//*[(@ControlType = 'MenuItem') and (@Name = 'Файл')]"));
        element.click();
        Thread.sleep(5000);
    }
}
