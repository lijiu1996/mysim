package com.lijiawei.simulator.dt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijiawei.simulator.dt.bean.entity.AlarmInfoEntity;
import com.lijiawei.simulator.dt.bean.pojo.AlarmInfo;
import com.lijiawei.simulator.dt.mapper.AlarmInfoMapper;
import com.lijiawei.simulator.dt.service.AlarmInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li JiaWei
 * @ClassName: AlarmInfoServiceImpl
 * @Description:
 * @Date: 2023/1/5 15:07
 * @Version: 1.0
 */
@Service
public class AlarmInfoServiceImpl extends ServiceImpl<AlarmInfoMapper, AlarmInfoEntity> implements AlarmInfoService {

    @Transactional(rollbackFor = Exception.class)
    public void saveAlarmInfo(List<AlarmInfo> alarmInfoList) {
        List<AlarmInfoEntity> alarmInfoEntityList = alarmInfoList.stream().
                map(alarmInfo -> {
                    AlarmInfoEntity alarmInfoEntity = new AlarmInfoEntity();
                    BeanUtils.copyProperties(alarmInfo, alarmInfoEntity);
                    return alarmInfoEntity;
                }).collect(Collectors.toList());
        this.saveBatch(alarmInfoEntityList);
    }

    public List<AlarmInfo> getAlarmInfo() {
//        return this.list().stream()
//                .map(alarmInfoEntity -> {
//
//                })
        return null;
    }


}
