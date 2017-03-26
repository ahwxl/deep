/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysOrganization;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年3月26日 下午3:29:01
 */
public interface OrganizationService {
	
	public Page<SysOrganization> queryOrganizationForPage(SysOrganization organization);

	public SysOrganization queryOrganization(SysOrganization organization);

	public SysOrganization addOrganization(SysOrganization organization);

	public void deleteRole(SysOrganization organization);

	public void updateRole(SysOrganization organization);

}
