package io.brooklyn.camp.brooklyn;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import brooklyn.entity.Entity;
import brooklyn.entity.basic.Entities;
import brooklyn.policy.Policy;
import brooklyn.test.entity.TestEntity;
import brooklyn.test.policy.TestPolicy;
import com.google.common.collect.ImmutableMap;

@Test
public class PoliciesYamlTest extends AbstractYamlTest {
    static final Logger log = LoggerFactory.getLogger(PoliciesYamlTest.class);

    @Test
    public void testWithAppPolicy() throws Exception {
        Entity app = createAndStartApplication("test-app-with-policy.yaml");
        waitForApplicationTasks(app);
        Assert.assertEquals(app.getDisplayName(), "test-app-with-policy");

        log.info("App started:");
        Entities.dumpInfo(app);

        Assert.assertEquals(app.getPolicies().size(), 1);
        Policy policy = app.getPolicies().iterator().next();
        Assert.assertTrue(policy instanceof TestPolicy);
        Assert.assertEquals(policy.getConfig(TestPolicy.CONF_NAME), "Name from YAML");
        Assert.assertEquals(policy.getConfig(TestPolicy.CONF_FROM_FUNCTION), "$brooklyn: is a fun place");
        Map<?, ?> leftoverProperties = ((TestPolicy) policy).getLeftoverProperties();
        Assert.assertEquals(leftoverProperties.get("policyLiteralValue1"), "Hello");
        Assert.assertEquals(leftoverProperties.get("policyLiteralValue2"), "World");
        Assert.assertEquals(leftoverProperties.size(), 2);
    }
    
    @Test
    public void testWithEntityPolicy() throws Exception {
        Entity app = createAndStartApplication("test-entity-with-policy.yaml");
        waitForApplicationTasks(app);
        Assert.assertEquals(app.getDisplayName(), "test-entity-with-policy");

        log.info("App started:");
        Entities.dumpInfo(app);

        Assert.assertEquals(app.getPolicies().size(), 0);
        Assert.assertEquals(app.getChildren().size(), 1);
        Entity child = app.getChildren().iterator().next();
        Assert.assertEquals(child.getPolicies().size(), 1);
        Policy policy = child.getPolicies().iterator().next();
        Assert.assertNotNull(policy);
        Assert.assertTrue(policy instanceof TestPolicy, "policy=" + policy + "; type=" + policy.getClass());
        Assert.assertEquals(policy.getConfig(TestPolicy.CONF_NAME), "Name from YAML");
        Assert.assertEquals(policy.getConfig(TestPolicy.CONF_FROM_FUNCTION), "$brooklyn: is a fun place");
        Assert.assertEquals(((TestPolicy) policy).getLeftoverProperties(),
                ImmutableMap.of("policyLiteralValue1", "Hello", "policyLiteralValue2", "World"));
        Assert.assertEquals(policy.getConfig(TestPolicy.TEST_ATTRIBUTE_SENSOR), TestEntity.NAME);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
    
}