package com.qinglan.ai.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qinglan.ai.auth.entity.SysUser;

/**
 * 系统用户数据访问接口。
 * <p>
 * 负责 sys_user 表的数据访问，
 * 提供用户信息的新增、查询、更新及删除等基础操作。
 *
 * @author Kaifan Yang
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
}
