package org.softuni.maintenancemanager.appUtils;


import org.junit.Assert;
import org.junit.Test;
import org.softuni.maintenancemanager.projects.model.dtos.binding.ProjectSystemBindModel;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CharacterEscapesTests {
    private String testString = "<div>I am html</div>";
    private String testString2 = "<div>I am html too</div>";

    @Test
    public void testStringCharacterEscape(){
        String result = CharacterEscapes.escapeString(testString);
        String expectString = "&lt;div&gt;I am html&lt;/div&gt;";

        Assert.assertEquals("String not escaped correctly.", expectString, result);
    }

    @Test
    public void testObjectCharacterEscape() throws IllegalAccessException {
        ProjectSystemBindModel result = new ProjectSystemBindModel();
        result.setInfo(testString);
        result.setName(testString2);

        result = CharacterEscapes.escapeStringFields(result);

        String expectedName = "&lt;div&gt;I am html too&lt;/div&gt;";
        String expectedInfo = "&lt;div&gt;I am html&lt;/div&gt;";

        Assert.assertEquals("Name not escaped correctly.", expectedName, result.getName());
        Assert.assertEquals("Info not escaped correctly.", expectedInfo, result.getInfo());
    }
}
