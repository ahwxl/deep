/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.sysmng.domain.SysResource;

/**
 * @desc 资源 操作、菜单
 * @author wangxiaolei
 * @date 2017年3月26日 下午2:57:32
 */
public interface ResourceService {
	
	public Page<SysResource> queryResourceForPage(SysResource resource);
	
	public SysResource queryResouce(SysResource resource);
	
	public SysResource addResoucre(SysResource resource);
	
	public void deleteResource(SysResource resource);
	
	public void updateResource(SysResource resource);

}
