package com.ruoyi.help.help.dubbo;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.help.api.RemoteHelpReportService;
import com.ruoyi.help.help.service.IHelpReportService;
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
public class RemoteHelpReportServiceImpl implements RemoteHelpReportService {

    private final IHelpReportService helpReportService;


    @Override
    public R report() {
        return helpReportService.report();
    }
}
