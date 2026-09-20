package com.company.mobile.tests.hybrid;

import com.company.mobile.base.BaseTest;
import com.company.mobile.pages.DualWebviewScreen;
import com.company.mobile.pages.HomeScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("Hybrid App Automation")
@Feature("Context Switching & WebView")
public class DualWebviewTest extends BaseTest {

    @Test(description = "Native moddan WebView moduna geçiş ve HTML element doğrulama")
    @Story("Dual Webview Context Switch")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Uygulama içinde gömülü webview açıldığında NATIVE_APP context'inden WEBVIEW context'ine geçiş yapılabilmelidir.")
    public void testDualWebviewContextSwitching() {
        HomeScreen homeScreen = new HomeScreen();
        DualWebviewScreen webviewScreen = homeScreen.navigateToDualWebview();

        // 1. İlk olarak NATIVE_APP modunda olduğumuzu doğrula
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Başlangıçta NATIVE_APP context'inde olunmalıdır.")
                .isEqualTo("NATIVE_APP");

        // 2. WEBVIEW context'ine geçiş yap
        webviewScreen.switchToWebViewContext();

        // 3. Artık WEBVIEW modunda olduğumuzu doğrula
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Context başarıyla WEBVIEW olarak değişmiş olmalıdır.")
                .containsIgnoringCase("WEBVIEW");

        // 4. Web sayfasının yüklendiğini ve DOM içeriğini oku
        String webContent = webviewScreen.getWebPageBodyText();
        Assertions.assertThat(webContent)
                .as("WebView içindeki web sayfası içeriği boş olmamalıdır.")
                .isNotEmpty();

        // 5. Tekrar yerel NATIVE_APP moduna geri dön
        webviewScreen.switchToNativeContext();
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Test bitiminde tekrar NATIVE_APP context'ine dönülmüş olmalıdır.")
                .isEqualTo("NATIVE_APP");
    }
}
