/**
 * www.bplow.com
 */
package com.bplow.deep.bpm.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefined;
import com.bplow.deep.bpm.domain.BpmProcessDefinedSet;
import com.bplow.deep.bpm.mapper.BpmProcessDefinedMapper;
import com.bplow.deep.bpm.mapper.BpmProcessDefinedSetMapper;
import com.bplow.deep.bpm.service.ProcessDefinedService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.BMMimeMultipart;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年7月9日 下午9:26:37
 */
@Service
@Transactional
public class ProcessDefinedServiceImpl implements ProcessDefinedService {

    @Autowired
    private BpmProcessDefinedMapper    bpmProcessDefinedMapper;

    @Autowired
    private BpmProcessDefinedSetMapper bpmProcessDefinedSetMapper;

    @Override
    public Page<BpmProcessDefined> queryProcessDefineForPage(BpmProcessDefined processDefined) {

        Page<BpmProcessDefined> page = bpmProcessDefinedMapper.queryForPage(processDefined);

        return page;
    }

    @Override
    public BpmProcessDefined queryProcessDefined(BpmProcessDefined processDefined) {

        BpmProcessDefined bpmProcessDefined = bpmProcessDefinedMapper
            .queryBpmProcessDefined(processDefined);

        return bpmProcessDefined;
    }

    @Override
    public String deployProcess(String name, InputStream in) {

        return null;
    }

    @Override
    public void updateProcessDefined(BpmProcessDefined processDefined) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addProcessDefined(BpmProcessDefined bpmProcessDefined) {

        BpmProcessDefined tmpBpmProcessDef = bpmProcessDefinedMapper
            .queryBpmProcessDefined(bpmProcessDefined);

        if (tmpBpmProcessDef != null) {

            tmpBpmProcessDef.setFormId(bpmProcessDefined.getFormId());
            bpmProcessDefinedMapper.update(tmpBpmProcessDef);
        } else {
            bpmProcessDefinedMapper.insert(bpmProcessDefined);
        }

    }

    @Override
    public void addProcessDefinitionSet(BpmProcessDefinedSet bpmProcessDefinedSet) {

        BpmProcessDefinedSet tmpBpmProcessDefinedSet = bpmProcessDefinedSetMapper
            .queryProcessDefSet(bpmProcessDefinedSet);

        if (tmpBpmProcessDefinedSet != null) {
            tmpBpmProcessDefinedSet.setFormId(bpmProcessDefinedSet.getFormId());
            tmpBpmProcessDefinedSet.setUserId(bpmProcessDefinedSet.getUserId());

            bpmProcessDefinedSetMapper.update(tmpBpmProcessDefinedSet);
        } else {
            bpmProcessDefinedSetMapper.insert(bpmProcessDefinedSet);
        }

    }

    @Override
    public void updateProcessDefinitionSet(BpmProcessDefinedSet bpmProcessDefinedSet) {

        bpmProcessDefinedSetMapper.update(bpmProcessDefinedSet);

    }

}
