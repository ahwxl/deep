import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/application-*.xml")
public class ${testClassName}Test {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ${testClassName} ${testClassNamePreSmall};


<#list metheds as method >

<@Field method=method >
${product}
</@Field>

</#list>

}