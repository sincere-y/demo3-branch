package com.example.demo3.module.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo3.module.entity.Gun;
import com.example.demo3.module.mapper.GunMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GunService {
    @Resource
    private GunMapper mapper;
    public Gun getById(BigInteger id){
        QueryWrapper<Gun>queryWrapper= new QueryWrapper<Gun>();
        queryWrapper.eq("id", id).eq("isDeleted", 0);
        return mapper.selectOne(queryWrapper);
    }
    public Gun extractById(BigInteger id){
        return mapper.selectById(id);
    };
    public List<Gun> getAllInfo(){
        return mapper.selectList(null);
    }
    public int insert(Gun gun){
        return mapper.insert(gun);
    }
    public int update(Gun gun){
        return mapper.updateById(gun);
    }
    public int delete(Gun gun){
        return mapper.updateById(gun);
    }
    public long getTotal(){
        return mapper.selectCount(null);
    }

    public int createGun(String title,String author,String images,String content){
        int timestamp=(int)(System.currentTimeMillis()/1000);
        Gun gun=new Gun();
        gun.setTitle(title).setAuthor(author).setImages(images).setContent(content).setCreateTime(timestamp).setUpdateTime(timestamp).setIsDeleted(0);

        return insert(gun);
    }

    public int updateGun(BigInteger id,String title,String author,String images,String content){
        int timestamp=(int)(System.currentTimeMillis()/1000);
        Gun gun=new Gun();
       gun.setId(id).setTitle(title).setAuthor(author).setImages(images).setContent(content).setUpdateTime(timestamp);

        return update(gun);
    }

    public int deleteGun(BigInteger gunId){
        int timestamp=(int)(System.currentTimeMillis()/1000);
        Gun gun=new Gun();
        gun.setUpdateTime(timestamp);
        gun.setIsDeleted(0);
        return delete(gun);
    }

    public List<Gun> getInfoPage(Integer page,Integer pageSize,String gunName){

            Integer start = (page - 1) * pageSize;
            return mapper.getInfoPage(start,pageSize,gunName);
    }

    //创建时间格式转换
    public String timeText(Integer createTime){

        long timestamp = (long) createTime * 1000;
        Date date=new Date(timestamp);

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        return format.format(date);
    }


}
