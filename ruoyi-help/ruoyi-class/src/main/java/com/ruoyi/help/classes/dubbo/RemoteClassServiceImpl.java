package com.ruoyi.help.classes.dubbo;


import com.ruoyi.classes.api.RemoteClassService;
import com.ruoyi.classes.api.domain.vo.ClassesVo;
import com.ruoyi.help.classes.service.IClassesService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteClassServiceImpl implements RemoteClassService {

    private final IClassesService classesService;

    @Override
    public ClassesVo queryById(Long id) {
        return classesService.queryById(id);
    }
}
