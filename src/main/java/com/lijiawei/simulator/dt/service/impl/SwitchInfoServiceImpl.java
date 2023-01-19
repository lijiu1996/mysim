package com.lijiawei.simulator.dt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijiawei.simulator.dt.bean.entity.PortInfoEntity;
import com.lijiawei.simulator.dt.bean.entity.SwitchInfoEntity;
import com.lijiawei.simulator.dt.bean.pojo.PortInfo;
import com.lijiawei.simulator.dt.bean.pojo.SwitchInfo;
import com.lijiawei.simulator.dt.mapper.SwitchInfoMapper;
import com.lijiawei.simulator.dt.service.SwitchInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li JiaWei
 * @ClassName: SwitchInfoServiceImpl
 * @Description:
 * @Date: 2023/1/4 11:48
 * @Version: 1.0
 */
@Service
public class SwitchInfoServiceImpl extends ServiceImpl<SwitchInfoMapper, SwitchInfoEntity> implements SwitchInfoService {

    private final PortInfoServiceImpl portInfoService;

    public SwitchInfoServiceImpl(PortInfoServiceImpl portInfoService) {
        this.portInfoService = portInfoService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSwitchInfo(List<SwitchInfo> switchInfoList) {
        List<SwitchInfoEntity> switchInfoEntityList = new ArrayList<>();
        for (SwitchInfo switchInfo : switchInfoList) {
            SwitchInfoEntity switchInfoEntity = new SwitchInfoEntity();
            BeanUtils.copyProperties(switchInfo,switchInfoEntity);
            switchInfoEntityList.add(switchInfoEntity);
            List<PortInfoEntity> portInfoEntityList = new ArrayList<>();
            for (PortInfo portInfo : switchInfo.getPortInfoList()) {
                PortInfoEntity portInfoEntity = new PortInfoEntity();
                BeanUtils.copyProperties(portInfo,portInfoEntity);
                portInfoEntity.setSwitchId(switchInfoEntity.getSwitchId());
                portInfoEntityList.add(portInfoEntity);
            }
            portInfoService.saveBatch(portInfoEntityList);
            if (switchInfo.getSwitchId() == 1) {
                throw new IllegalArgumentException("非法的switchId :" + 1);
            }
        }
        this.saveBatch(switchInfoEntityList);
    }

    public List<SwitchInfo> getSwitchInfo() {
        List<SwitchInfoEntity> switchInfoEntityList = this.list();
        List<SwitchInfo> switchInfoList = new ArrayList<>();
        for (SwitchInfoEntity switchInfoEntity : switchInfoEntityList) {
            SwitchInfo switchInfo = new SwitchInfo();
            BeanUtils.copyProperties(switchInfoEntity,switchInfo);
            List<PortInfoEntity> portInfoEntityList = portInfoService.getBySwitchId(switchInfo.getSwitchId());
            List<PortInfo> portInfoList = portInfoEntityList.stream().map(e -> {
                PortInfo portInfo = new PortInfo();
                BeanUtils.copyProperties(e, portInfo);
                return portInfo;
            }).collect(Collectors.toList());
            switchInfo.setPortNum(portInfoEntityList.size());
            switchInfo.setPortInfoList(portInfoList);
            switchInfoList.add(switchInfo);
        }
        return switchInfoList;
    }
}
