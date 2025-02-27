package com.example.demo3.console.controller;

import com.example.demo3.console.domain.GunInfoVo;
import com.example.demo3.console.domain.GunListCellVo;
import com.example.demo3.console.domain.GunListVo;
import com.example.demo3.module.entity.Gun;
import com.example.demo3.module.service.GunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GunController {

    @Autowired
    private GunService service;

    @RequestMapping("/gun/create")
    public String gunCreate(@RequestParam(name = "title")String title,
                            @RequestParam(name = "author")String author,
                            @RequestParam(name = "images")String images,
                            @RequestParam(name = "content")String content) {
        int result = service.createGun(title.trim(),author.trim(),images,content);
        return 1 == result ? "成功":"失败";
    }

    @RequestMapping("/gun/update")
    public String gunUpdate(@RequestParam(name = "gunId")BigInteger gunId,
                            @RequestParam(name = "title")String title,
                            @RequestParam(name = "author")String author,
                            @RequestParam(name = "images") String images,
                            @RequestParam(name = "content")String content) {
        int result = service.updateGun(gunId,title.trim(),author.trim(),images,content);
        return 1 == result ? "成功":"失败";
    }
    @RequestMapping("/gun/delete")
    public String gunDelete(@RequestParam(name = "gunId")BigInteger gunId) {
        int result = service.deleteGun(gunId);
        return 1 == result ? "成功":"失败";
    }

    @RequestMapping("/gun/list")
    public GunListVo gunAllList(@RequestParam(name = "page")Integer page,
                                @RequestParam(name ="gunName",required = false )String gunName) {
        Integer pageSize = 4;
        List<Gun> guns =service.getInfoPage(page,pageSize,gunName);
        List<GunListCellVo> gunListCellVo = new ArrayList<>();
        for (Gun gun : guns) {
            GunListCellVo vo = new GunListCellVo();
            vo.setGunId(gun.getId());
            vo.setTitle(gun.getTitle());
            vo.setCreateTime(service.timeText(gun.getCreateTime()));
            vo.setImage(gun.getImages().split("\\$")[0]);
            gunListCellVo.add(vo);
        }
        GunListVo gunListVo = new GunListVo();
        gunListVo.setList(gunListCellVo);
        gunListVo.setTotal(service.getTotal());
        gunListVo.setPageSize(pageSize);

        return gunListVo ;

    }

    @RequestMapping("/gun/info")
    public GunInfoVo gunInfo(@RequestParam(name = "gunId") BigInteger gunId) {
        GunInfoVo gunInfoVo = new GunInfoVo();
        Gun gun = service.getById(gunId);
        gunInfoVo.setTitle(gun.getTitle());
        gunInfoVo.setAuthor(gun.getAuthor());
        gunInfoVo.setContent(gun.getContent());
        gunInfoVo.setCreateTime(service.timeText(gun.getCreateTime()));
        gunInfoVo.setUpdateTime(service.timeText(gun.getUpdateTime()));
        gunInfoVo.setImages(Arrays.asList(gun.getImages().split("\\$")));
        return gunInfoVo;
    }






}
