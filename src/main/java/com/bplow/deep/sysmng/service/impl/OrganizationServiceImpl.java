/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysOrganization;
import com.bplow.deep.sysmng.domain.SysOrganizationUser;
import com.bplow.deep.sysmng.mapper.SysOrganizationMapper;
import com.bplow.deep.sysmng.mapper.SysOrganizationUserMapper;
import com.bplow.deep.sysmng.service.OrganizationService;

/**
 * @desc 机构管理
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:30:35
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysOrganizationMapper sysOrganizationMapper;
	
	@Autowired
	SysOrganizationUserMapper sysOrganizationUserMapper;
	
	@Override
	public Page<SysOrganization> queryOrganizationForPage(SysOrganization organization) {

		Page<SysOrganization> page = sysOrganizationMapper.queryForPage(organization);
		
		return page;
	}

	@Override
	public SysOrganization queryOrganization(SysOrganization organization) {

		SysOrganization sysOrganization = sysOrganizationMapper.selectByPrimaryKey(organization.getOrganizationId());
		
		return sysOrganization;
	}

	@Override
	public SysOrganization addOrganization(SysOrganization organization) {

		sysOrganizationMapper.insert(organization);
		
		return null;
	}

	@Override
	public void deleteRole(SysOrganization organization) {
		sysOrganizationMapper.delete(organization.getOrganizationId());

	}

	@Override
	public void updateRole(SysOrganization organization) {
		sysOrganizationMapper.update(organization);

	}

    @Override
    public void addOrganizationUser(SysOrganizationUser orgUser, String addUserIds, String delUserIds) {
        
        String[] userArray = addUserIds.split(",");

        String[] userIds = delUserIds.split(",");
        List<String> delList = Arrays.asList(userIds);

        if (delList.size() > 0) {
            SysOrganizationUser record = new SysOrganizationUser();
            record.setOrganizationId(orgUser.getOrganizationId());
            record.setUserIds(delList);

            sysOrganizationUserMapper.delete(record);
        }
        for (String userId : userArray) {
            if (StringUtils.isBlank(userId)) {
                continue;
            }
            SysOrganizationUser orgUserTmp = new SysOrganizationUser();
            
            orgUserTmp.setOrganizationId(orgUser.getOrganizationId());
            orgUserTmp.setUserId(userId);

            sysOrganizationUserMapper.insert(orgUserTmp);
        }
    }

    @Override
    public List<SysOrganization> queryOrgList(SysOrganization organization) {
        
        List<SysOrganization> list = sysOrganizationMapper.queryOrgList(organization);
        
        return list;
    }

}
