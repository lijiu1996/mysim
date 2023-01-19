package com.lijiawei.simulator.dt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijiawei.simulator.dt.bean.entity.PortInfoEntity;
import com.lijiawei.simulator.dt.mapper.PortInfoMapper;
import com.lijiawei.simulator.dt.service.PortInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JiaWei
 * @ClassName: PortInfoServiceImpl
 * @Description:
 * @Date: 2023/1/4 11:48
 * @Version: 1.0
 */
@Service
public class PortInfoServiceImpl extends ServiceImpl<PortInfoMapper, PortInfoEntity> implements PortInfoService {

    public List<PortInfoEntity> getBySwitchId(Integer switchId) {
        LambdaQueryWrapper<PortInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PortInfoEntity::getSwitchId,switchId);
        return list(lqw);
    }
}
