package com.ruoyi.classes.api;


import com.ruoyi.classes.api.domain.vo.ClassesVo;

/**
 * 老师服务
 *
 * @author Lion Li
 */
public interface RemoteClassService {

    ClassesVo queryById(Long id);
}
