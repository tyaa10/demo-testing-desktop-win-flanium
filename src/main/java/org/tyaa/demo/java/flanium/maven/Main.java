/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.demo.java.flanium.maven;

import FlaNium.WinAPI.elements.Window;
import FlaNium.WinAPI.webdriver.DesktopOptions;
import FlaNium.WinAPI.webdriver.FlaNiumDriver;
import FlaNium.WinAPI.webdriver.FlaNiumDriverService;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Map;

/**
 * @author Yurii
 */
public class Main {

    private static FlaNiumDriver driver;
    private static FlaNiumDriverService service;

    private enum RESOURCE_OPTIONS {
        NOTEPAD_APP, AVALONIA_UI_2022_APP, C_ENERGO
    }
    private final static Map<RESOURCE_OPTIONS, Map<String, String>> RESOURCES = Map.of(
            RESOURCE_OPTIONS.NOTEPAD_APP, Map.of(
                    "DRIVER_PATH", "src/main/resources/driver/FlaNium.Desktop.Driver-v1.6.0/FlaNium.Driver.exe",
                    "APP_PATH", "C:\\Windows\\system32\\notepad.exe",
                    "BUTTON_XPATH_SELECTOR", "//*[(@ControlType = 'MenuItem') and (@Name = 'Файл')]"
            ),
            RESOURCE_OPTIONS.AVALONIA_UI_2022_APP, Map.of(
                    "DRIVER_PATH", "src/main/resources/driver/FlaNium.Desktop.Driver-v1.6.0/FlaNium.Driver.exe",
                    "APP_PATH", "C:\\Users\\ofixm\\OneDrive\\Документы\\artifacts\\avaloniaui2022-net6.0-winx64\\avalonia-ui-2022.exe",
                    "BUTTON_XPATH_SELECTOR", "//*[(@ControlType = 'Button') and (@Name = 'Add an item')]"
            ),
            RESOURCE_OPTIONS.C_ENERGO, Map.of(
                    "DRIVER_PATH", "src/main/resources/driver/FlaNium.Desktop.Driver-v1.6.0/FlaNium.Driver.exe",
                    "APP_PATH", "C:\\Program Files\\Energomera\\cEnergo\\ceShell.View.Win.exe",
                    "BUTTON_XPATH_SELECTOR", "//*[(@ControlType = 'TabItem') and (@Name = 'Вид')]"
            )
    );

    private final static RESOURCE_OPTIONS CURRENT_RESOURCE = RESOURCE_OPTIONS.AVALONIA_UI_2022_APP;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello Flanium!");
        try {
            demoNotepadTest(getDriver());
        } finally {
            if (driver != null) {
                driver.close();
            }
            if (service != null && service.isRunning()) {
                service.stop();
            }
        }
    }

    private static FlaNiumDriver getDriver() {
        // Получение экземпляра драйвера приложения
        if(driver == null) {
            int driverPort = 9996;
            // Инициализация драйвера:
            service = new FlaNiumDriverService.Builder()
                    // Указание пути до драйвера
                    .usingDriverExecutable(new File(RESOURCES.get(CURRENT_RESOURCE).get("DRIVER_PATH")).getAbsoluteFile())
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
            options.setApplicationPath(RESOURCES.get(CURRENT_RESOURCE).get("APP_PATH"));
            // Задержка после запуска приложения (сек)
            options.setLaunchDelay(5);
            // Подключение к ранее запущенному экземпляру приложения
            options.setDebugConnectToRunningApp(false);
            driver = new FlaNiumDriver(service, options);
        }
        return driver;
    }

    public static void demoNotepadTest(FlaNiumDriver driver) throws InterruptedException {
        /* Пробное взаимодействие с окном приложения */
        Window attentionDialogWindow = new Window(driver.getActiveWindow());
        System.out.println("Window Name: " + attentionDialogWindow.getName());
        WebElement element =
                driver.findElement(By.xpath(RESOURCES.get(CURRENT_RESOURCE).get("BUTTON_XPATH_SELECTOR")));
        element.click();
        Thread.sleep(5000);
    }
}
