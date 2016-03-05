package base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml",
    "classpath:/spring/springmvc-servlet.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests {

}
