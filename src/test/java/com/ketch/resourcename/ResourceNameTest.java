package com.ketch.resourcename;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ResourceNameTest {

    @Test
    @DisplayName("Encode tests for ResourceName")
    public void testEncoide() {
        for (Map m : TestExamples.EXAMPLES) {
            String encodedResourceName = (String)m.get("s");
            ResourceName resourceName = (ResourceName)m.get("n");
            assertEquals(
                    encodedResourceName,
                    resourceName.encode(),
                    "encoded resources should be equal"
            );
        }
    }

    @Test
    @DisplayName("Decode tests for ResourceName")
    public void testDecode() {
        for (Map m : TestExamples.EXAMPLES) {
            String encodedResourceName = (String)m.get("s");
            ResourceName resourceName = (ResourceName)m.get("n");
            assertEquals(
                    resourceName,
                    ResourceName.decode(encodedResourceName),
                    "decoded resources should be equal"
            );
        }
    }
}
