/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.project.smartcharge.mapper.CarinfoMapper;
import com.project.smartcharge.pojo.Carinfo;
import com.project.smartcharge.pojo.CarinfoExample;
import com.project.smartcharge.service.CarService;
import com.project.smartcharge.system.respon.CarInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * carService接口的具体实现类
 */
@Service("carService")
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    CarinfoMapper carinfoMapper;


    private static String carDeleteResult(boolean delete) {
        CarInfoResponse.updateCarTableResp carRegister = new CarInfoResponse.updateCarTableResp();
        //如果删除成功
        if (delete) {
            carRegister.setCode(200);
            carRegister.setMessage("汽车删除成功");
        }
        //如果删除失败
        else {
            //检查是不是在充电中
            carRegister.setCode(500);
            carRegister.setMessage("汽车删除失败，可能在充电或者等待中");
        }
        return JSON.toJSONString(carRegister);
    }

    /**
     * 管理员查看对应充电桩id的服务车辆信息（包括充电中和等待充电中）
     *
     * @param deviceID 要查看的deviceID
     * @return 车辆信息的JSON字符串
     */
    @Override
    public List<Carinfo> selectServingCarByDeviceID(int deviceID) {
        CarinfoExample carinfoExample = new CarinfoExample();
        CarinfoExample.Criteria criteria = carinfoExample.createCriteria();
        //找到满足当前的deviceid的汽车并且是要在服务中
        criteria.andDeviceIDEqualTo(deviceID).andWaitingStatuGreaterThan((byte) -1);
        return carinfoMapper.selectByExample(carinfoExample);
    }

    /**
     * 在充电区取消充电请求后对carinfo的更新
     *
     * @param carinfo 传入的carinfo对象
     * @return 更新成功返回true
     */
    @Override
    public boolean cancelChargingRequestInCharging(Carinfo carinfo) {
        //对汽车的更新(主要是更新车辆等待状态和汽车排名)
        carinfo.setWaitingStatu((byte) -1);
        //更新之后的汽车排名汽车排名
        updateRankOfMode(carinfo.getCarRank(), carinfo.getChargeMode());

        carinfo.setCarRank(-1);
        carinfo.setDeviceID(null);
        carinfo.setChargeMode(null);

        return carinfoMapper.updateByPrimaryKey(carinfo) == 1;
    }

    /**
     * 在等待区取消充电请求后对carinfo的更新
     *
     * @param carinfo 传入的carinfo对象
     * @return 更新成功返回true
     */
    @Override
    public boolean cancelChargingRequestInWaiting(Carinfo carinfo) {
        Boolean chargeMode = carinfo.getChargeMode();
        //更新汽车
        Integer carRank = carinfo.getCarRank();
        carinfo.setCarRank(-1);
        carinfo.setWaitingStatu((byte) -1);

        //更新后面汽车的排位(可能不会有多的汽车，所以可能不会更新）
        updateRankOfMode(carRank, chargeMode);
        return carinfoMapper.updateByPrimaryKeySelective(carinfo) == 1;
    }

    /**
     * 更新具有订单的该汽车之后的汽车排名
     *
     * @param carRank    userid
     * @param chargeMode 汽车当前的预充电或充电状态
     */
    @Override
    public void updateRankOfMode(Integer carRank, boolean chargeMode) {

        //使用自定义sql来进行车辆排队序列的更新
        int i = carinfoMapper.updateCarRankByChargeModeAndCarRank(carRank, chargeMode);
    }

    /**
     * 用户删除自己对应的车辆信息
     *
     * @param userID 用户的id，
     * @return true表示车辆信息更新成功，反之返回false
     */
    @Override
    public boolean carDeleteByUserID(int userID) {


        CarinfoExample carinfoExample = new CarinfoExample();
        carinfoExample.createCriteria().andUserIDEqualTo(userID);

        //找到车列表中，理论上不需要检测用户权限
        List<Carinfo> carinfos = carinfoMapper.selectByExample(carinfoExample);

        //如果根本就没这辆车
        if (carinfos.size() == 0)
            return false;
        else {
            //拿到车辆信息
            Carinfo carinfo = carinfos.get(0);
            //拿到充电信息
            Byte waitingStatu = carinfo.getWaitingStatu();
            //如果该车在充电中
            if (waitingStatu != -1)
                return false;
        }
        //否则根据是不是删除成功去删除
        return carinfoMapper.deleteByExample(carinfoExample) == 1;

    }

    /**
     * 用户填写对应的车辆信息
     *
     * @param carinfo 车子的信息，刚进入时某些项为空
     * @return true表示车辆信息更新成功，反之返回false
     */
    @Override
    public boolean carRegister(Carinfo carinfo) {
        Integer userID = carinfo.getUserID();
        Carinfo carinfo1 = selectCarByUserID(userID);

        //如果用户之前没有注册汽车，则进行注册
        if (carinfo1 == null) {
            return carinfoMapper.insert(carinfo) == 1;
        } else
            return false;
    }

    /**
     * 更新车况信息
     *
     * @param carinfo 预更新的车况信息
     * @return 更新后的json数组, 如果更新失败则返回空串
     */
    @Override
    public boolean updateByPrimaryKeySelective(Carinfo carinfo) {
        int update = carinfoMapper.updateByPrimaryKeySelective(carinfo);
        return update == 1;
    }

    /**
     * 根据用户id查看本车的排队号码
     *
     * @param userID 传入的userid
     * @return 用户汽车的排队号码, 如果没有该汽车就返回-1
     */
    @Override
    public int selectCarRankByUserID(int userID) {
        CarinfoExample carinfoExample = new CarinfoExample();
        CarinfoExample.Criteria criteria = carinfoExample.createCriteria();
        criteria.andUserIDEqualTo(userID);

        List<Carinfo> carinfos = carinfoMapper.selectByExample(carinfoExample);
        if (carinfos != null)
            return carinfos.get(0).getCarRank();
        else
            return -1;

    }

    /**
     * 查看本车在该充店模式下的排队号码
     *
     * @param chargeMod 查询的充电模式
     * @return 排队编号, 若当前充电模式下没有等待或者是充电中的车辆则返回-1
     */
    @Override
    public int selectMaxCarRankByMode(boolean chargeMod) {
        CarinfoExample carinfoExample = new CarinfoExample();
        CarinfoExample.Criteria criteria = carinfoExample.createCriteria();
        //这里是要求找到当前充电模式下等待中或者是进行中的车辆
        criteria.andChargeModeEqualTo(chargeMod).andWaitingStatuGreaterThan((byte) -1).andCarRankGreaterThan(0);
        carinfoExample.setOrderByClause("carRank DESC");

        List<Carinfo> carinfos = carinfoMapper.selectByExample(carinfoExample);
        if (!carinfos.isEmpty()) {
            return carinfos.get(0).getCarRank();
        } else
            return 0;
    }

    /**
     * 查找用户对应的车辆
     *
     * @param userID 用户id
     * @return 返回该车的详细信息
     */
    @Override
    public Carinfo selectCarByUserID(int userID) {
        CarinfoExample carinfoExample = new CarinfoExample();
        CarinfoExample.Criteria criteria = carinfoExample.createCriteria();
        criteria.andUserIDEqualTo(userID);
        List<Carinfo> carinfos = carinfoMapper.selectByExample(carinfoExample);
        if (carinfos.isEmpty())
            return null;
        else
            return carinfos.get(0);
    }

}
