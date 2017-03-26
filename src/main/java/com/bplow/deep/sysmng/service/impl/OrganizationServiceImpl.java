/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysOrganization;
import com.bplow.deep.sysmng.mapper.SysOrganizationMapper;
import com.bplow.deep.sysmng.service.OrganizationService;

/**
 * @desc 机构管理
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:30:35
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysOrganizationMapper sysOrganizationMapper;
	
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

}
