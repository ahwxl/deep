/**
 * www.bplow.com
 */
package com.bplow.deep.sysmng.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.utils.DateUtils;
import com.bplow.deep.sysmng.domain.SysOrganization;
import com.bplow.deep.sysmng.domain.SysPermission;
import com.bplow.deep.sysmng.domain.SysPermissionResource;
import com.bplow.deep.sysmng.domain.SysResource;
import com.bplow.deep.sysmng.domain.SysRole;
import com.bplow.deep.sysmng.domain.SysRolePermission;
import com.bplow.deep.sysmng.service.OrganizationService;
import com.bplow.deep.sysmng.service.PermissionService;
import com.bplow.deep.sysmng.service.ResourceService;
import com.bplow.deep.sysmng.service.RoleService;

/**
 * @desc 系统管理
 * @author wangxiaolei
 * @date 2017年3月26日 下午4:31:13
 */
@Controller
@RequestMapping("/sysmng")
public class SysmngController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResourceService resourceService;

	@Autowired
	PermissionService permissionService;

	@Autowired
	RoleService roleService;

	@Autowired
	OrganizationService organizationService;
	
	@RequestMapping(value = "/usersPage")
	public String userListPage(HttpServletRequest httpRequest, Model view){
		
		logger.info("");
		
		return "sys/user";
	}

	// 资源
	@RequestMapping(value = "/resourcePage")
	public String resourceListPage(HttpServletRequest httpRequest, Model view) {

		logger.info("查看资源");
		Subject subject =SecurityUtils.getSubject();
        Session shiroSession = subject.getSession(false);
        
        Date date = shiroSession.getStartTimestamp();
        logger.info("sessionDate:>>>>>>>[{}][{}][{}]",shiroSession.getId(),DateUtils.formatDate(date),shiroSession.getTimeout());
        

		return "sys/resource";
	}

	@RequestMapping(value = "/resourceList")
	@ResponseBody
	public Page<SysResource> resourceList(HttpServletRequest httpRequest,
			Model view, SysResource resource) {

		Page<SysResource> page = resourceService.queryResourceForPage(resource);

		return page;
	}

	@RequestMapping(value = "/addRes")
	@ResponseBody
	public String addRes(HttpServletRequest httpRequest, Model view,
			SysResource resource) {

		resource.setResourceId(UUID.randomUUID().toString().replace("-", ""));
		resourceService.addResoucre(resource);

		return "ok";
	}

	@RequestMapping(value = "/delRes")
	@ResponseBody
	public String delRes(HttpServletRequest httpRequest, Model view,
			SysResource resource) {

		resourceService.deleteResource(resource);

		return "ok";
	}

	@RequestMapping(value = "/updateRes")
	@ResponseBody
	public String upateRes(HttpServletRequest httpRequest, Model view,
			SysResource resource) {

		resourceService.updateResource(resource);

		return "ok";
	}

	@RequestMapping(value = "/queryRes")
	@ResponseBody
	public SysResource queryRes(HttpServletRequest httpRequest, Model view,
			SysResource resource) {

		SysResource res = resourceService.queryResouce(resource);

		return res;
	}
	
	@RequestMapping(value = "/queryResForTree")
    @ResponseBody
    public List<SysResource> queryResListForTree(HttpServletRequest httpRequest, Model view,
            SysResource resource) {

	    if(StringUtils.isBlank(resource.getParentResourceId())){
	        resource.setParentResourceId("0");
	    }
        List<SysResource> res = resourceService.queryResource(resource);

        return res;
    }

	// 权限
	@RequestMapping(value = "/permissionPage")
	public String permissionListPage(HttpServletRequest httpRequest, Model view) {

		logger.info("");

		return "sys/permission";
	}
	
	@RequestMapping(value = "/permissionResPage")
    public String permissionResListPage(HttpServletRequest httpRequest, Model view) {

        logger.info("权限资源关系");

        return "sys/permission-res";
    }

	@RequestMapping(value = "/permissionList")
	@ResponseBody
	public Page<SysPermission> permissionList(HttpServletRequest httpRequest,
			Model view, SysPermission permission) {

		Page<SysPermission> page = permissionService
				.queryPermissionForPage(permission);

		return page;
	}
	
	@RequestMapping(value = "/addPerm")
	@ResponseBody
	public String addPerm(HttpServletRequest httpRequest, Model view,
			SysPermission permission) {

		permissionService.addPermission(permission);

		return "ok";
	}

	@RequestMapping(value = "/delPerm")
	@ResponseBody
	public String delPerm(HttpServletRequest httpRequest, Model view,
			SysPermission permission) {

		permissionService.deletePermission(permission);

		return "ok";
	}

	@RequestMapping(value = "/updatePerm")
	@ResponseBody
	public String upatePerm(HttpServletRequest httpRequest, Model view,
			SysPermission permission) {

		permissionService.updatePermission(permission);

		return "ok";
	}

	@RequestMapping(value = "/queryPerm")
	@ResponseBody
	public SysPermission queryPerm(HttpServletRequest httpRequest, Model view,
			SysPermission permission) {

		SysPermission perm = permissionService.queryPermission(permission);

		return perm;
	}

	// 添加权限对应的资源 一对一 关系
	@RequestMapping(value = "/addPermRes")
	@ResponseBody
	public String addPermRes(HttpServletRequest httpRequest, Model view,
			SysPermission permission,@RequestParam("resourceIds")String resourceIds) {

		permissionService.addPermRes(permission.getPermissionId(), resourceIds);

		return "success";
	}

	// 查询权限与资源对应关系
	@RequestMapping(value = "/queryPermRes")
	@ResponseBody
	public List<SysPermissionResource> queryPermRes(
			HttpServletRequest httpRequest, Model view, SysPermission permission) {

		List<SysPermissionResource> permRes = permissionService
				.queryPermRes(permission.getPermissionId());

		return permRes;
	}

	// 删除权限对应的资源
	@RequestMapping(value = "/delPermRes")
	@ResponseBody
	public String delPermRes(HttpServletRequest httpRequest, Model view,
			SysPermission permission) {

		permissionService.delPermRes(permission.getPermissionId(), "");

		return "success";
	}

	// 角色
	@RequestMapping(value = "/rolesPage")
	public String roleListPage(HttpServletRequest httpRequest, Model view) {

		logger.info("");

		return "sys/roles";
	}

	@RequestMapping(value = "/roleList")
	@ResponseBody
	public Page<SysRole> roleList(HttpServletRequest httpRequest, Model view,
			SysRole role) {

		Page<SysRole> page = roleService.queryRoleForPage(role);

		return page;
	}

	@RequestMapping(value = "/addRole")
	@ResponseBody
	public String addRole(HttpServletRequest httpRequest, Model view,
			SysRole role) {

		roleService.addRole(role);

		return "ok";
	}

	@RequestMapping(value = "/delRole")
	@ResponseBody
	public String delRole(HttpServletRequest httpRequest, Model view,
			SysRole role) {

		roleService.deleteRole(role);

		return "ok";
	}

	@RequestMapping(value = "/updateRole")
	@ResponseBody
	public String upateRole(HttpServletRequest httpRequest, Model view,
			SysRole role) {

		roleService.updateRole(role);

		return "ok";
	}

	@RequestMapping(value = "/queryRole")
	@ResponseBody
	public SysRole queryRole(HttpServletRequest httpRequest, Model view,
			SysRole role) {

		SysRole r = roleService.queryRole(role);

		return r;
	}

	// 添加权限对应的角色 多对多 关系
	@RequestMapping(value = "/addRolePerm")
	@ResponseBody
	public String addRolePerm(HttpServletRequest httpRequest, Model view,
			SysRole role, String permIds) {

		roleService.addRolePerm(role.getRoleId(), permIds);

		return "success";
	}

	// 查询权限与资源对应关系
	@RequestMapping(value = "/queryRolePerm")
	@ResponseBody
	public List<SysRolePermission> queryRolePerm(
			HttpServletRequest httpRequest, Model view, SysRole role) {

		List<SysRolePermission> permRes = roleService.queryRolePerms(role.getRoleId());

		return permRes;
	}

	// 删除权限对应的资源
	@RequestMapping(value = "/delRolePerm")
	@ResponseBody
	public String delPermRes(HttpServletRequest httpRequest, Model view,
			SysRolePermission rolePerm) {

		roleService.delRolePerm(rolePerm);

		return "success";
	}

	// 机构
	@RequestMapping(value = "/organizationPage")
	public String organizationListPage(HttpServletRequest httpRequest,
			Model view) {

		logger.info("");

		return "sys/organization";
	}

	@RequestMapping(value = "/organizationList")
	@ResponseBody
	public Page<SysOrganization> organizationList(
			HttpServletRequest httpRequest, Model view,
			SysOrganization organization) {

		Page<SysOrganization> page = organizationService
				.queryOrganizationForPage(organization);

		return page;
	}

	@RequestMapping(value = "/addOrg")
	@ResponseBody
	public String addOrg(HttpServletRequest httpRequest, Model view,
			SysOrganization organization) {

		organizationService.addOrganization(organization);

		return "ok";
	}

	@RequestMapping(value = "/delOrg")
	@ResponseBody
	public String delOrg(HttpServletRequest httpRequest, Model view,
			SysOrganization organization) {

		organizationService.deleteRole(organization);

		return "ok";
	}

	@RequestMapping(value = "/updateOrg")
	@ResponseBody
	public String upateOrg(HttpServletRequest httpRequest, Model view,
			SysOrganization organization) {

		organizationService.updateRole(organization);

		return "ok";
	}

	@RequestMapping(value = "/queryOrg")
	@ResponseBody
	public SysOrganization queryOrg(HttpServletRequest httpRequest, Model view,
			SysOrganization organization) {

		SysOrganization r = organizationService.queryOrganization(organization);

		return r;
	}

}
