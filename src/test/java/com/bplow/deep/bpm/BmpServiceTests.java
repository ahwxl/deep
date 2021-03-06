/**
 * www.bplow.com
 */
package com.bplow.deep.bpm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;
import com.bplow.deep.bpm.service.impl.BmpServiceImpl;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2016年7月9日 上午10:53:58
 */
@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-myclient.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class BmpServiceTests {

    private Logger         log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BmpServiceImpl bpmService;
    
    @Autowired
    RepositoryService        repositoryService;

    @Test
    public void deployTest() {
        bpmService.deploy("bpm/countersign.bpmn20.xml");

    }

    @Test
    public void testDeployByInputstream() {
        InputStream is = this.getClass().getResourceAsStream("/bpm/leave.bpmn20.xml");
        bpmService.deploy("leave.bpmn20.xml", is);
    }

    @Test
    public void startPrcocessByKeyTest() {

        String key = "vacationRequest";

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("_name", "请假流程-汪小磊");
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        bpmService.startProcessByKey(key, variables);
    }

    @Test
    public void startProcessTest() {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        List<String> countersigns = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            //CounterSign countersign = new CounterSign();
            //countersign.setAssignee("user"+i);
            //countersigns.add(countersign);
            countersigns.add("user" + i);
        }

        variables.put("assigneeList", countersigns);

        //bpmService.startProcessById("vacationRequest:1:2503",variables);
        bpmService.startProcessById("countersign:5:52504", variables);
    }

    @Test
    public void completeTaskTest() {
        String processId = "";
        String taskId = "5008";
        taskId = "100019";
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");

        taskVariables.put("resendRequest", "true");

        bpmService.completeTask(processId, taskId, taskVariables);
    }

    @Test
    public void getImageInputStreamTest() throws FileNotFoundException, IOException {

        InputStream in = bpmService.getImageInputStream("vacationRequest:1:2503");
        File file = new File("c:/log/bpm.jpg");
        IOUtils.copy(in, new FileOutputStream(file));
        in.close();
    }

    @Test
    public void getImageInputStreamByIdTest() throws FileNotFoundException, IOException {

        InputStream in = bpmService.getImageInputStreamById(null);//countersign:2:25004
        File file = new File("d:/logs/sign2.jpg");
        IOUtils.copy(in, new FileOutputStream(file));
        in.close();
    }

    @Test
    public void testGetprocessDef() {

        List list = bpmService.getProcesDef("");
        log.info("{}", list);
    }

    @Test
    public void testGetProcessList() {

        ProcessInstanceInfo processInfo = new ProcessInstanceInfo();
        processInfo.setPageNo(0);
        processInfo.setPageSize(10);

        Page<ProcessInstanceInfo> list = bpmService.queryProcessInstanceItem(processInfo);

        log.info("{}", list);
    }

    @Test
    public void testGetProcess() {
        ProcessInstanceInfo processInfo = new ProcessInstanceInfo();
        ProcessInstanceInfo obj = bpmService.queryProcessInstance(processInfo);

        log.info("{}", obj);
    }

    @Test
    public void testQueryProcessStartForm() {

        ProcessInstanceInfo processInfo = new ProcessInstanceInfo();
        processInfo.setKey("countersign");
        //processInfo.setProcessDefineId("countersign:1:87504");

        String formkey = bpmService.queryProcessStartForm(processInfo);

        log.info("{}", formkey);
    }

    @Test
    public void testProcessSet() {

        /*ProcessDefinitionEntity pde = Context.getCommandContext()
            .getProcessDefinitionEntityManager().findProcessDefinitionById("leave:1:97557");
        log.info("{}", pde);*/
        String procDefId = "leave:1:97557";
        ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) repositoryService
                .getProcessDefinition(procDefId);
        
        List<ActivityImpl> activities = processDefinitionImpl.getActivities();
        List<ActivityImpl> userTaskActs = new ArrayList<ActivityImpl>();
        
        for(ActivityImpl act : activities){
           if(act.getActivityBehavior() instanceof UserTaskActivityBehavior || act.getActivityBehavior() instanceof NoneStartEventActivityBehavior){
               userTaskActs.add(act);
           }
        }
        
        System.out.println("");
    }
    
    @Test
    public void testGetHistoryTaskInst(){
        
        List list = bpmService.getHistoryProcess("112501");
        
        log.info("{}", list);
    }
    

}
