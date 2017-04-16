/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.stock.service.SystemUserService;
import com.bplow.deep.sysmng.domain.SysUser;
import com.bplow.deep.sysmng.mapper.SysUserMapper;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月25日 下午4:56:31
 */
@Service
public class SystemUserServiceImpl implements SystemUserService{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public SysUser queryUser(SysUser user) {
		
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(user.getUserId());
		
		return sysUser;
	}

}
