/*
vipin yadav
*/

package com.TestCases;

import com.aventstack.extentreports.Status;
import com.Base.TestBaseClass;
import org.testng.annotations.Test;
import java.util.List;

public class Sample extends TestBaseClass {

    @Test()
    public void filpkart() {
        test = extent.createTest("Verify URL", "Test the flipkart data");
        webDriverUtils.openURL("https://www.flipkart.com/");
        test.log(Status.INFO, "Open URL");
        homePage.closeLoginPopup();
        homePage.clickOnSofasBanner();
        homePage.hoverOnElectronicsLnk();
        List s = homePage.getMobileBrands();
        for (int i = 0; i < s.size(); i++) {
            logger.info(s.get(i));
        }
    }
}
