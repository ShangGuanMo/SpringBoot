package com.example.yin.controller;

import com.example.yin.common.ErrorMessage;
import com.example.yin.common.SuccessMessage;
import com.example.yin.common.WarningMessage;
import com.example.yin.domain.Collect;
import com.example.yin.service.impl.CollectServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

@RestController
public class CollectController {

    @Autowired
    private CollectServiceImpl collectService;

    // 添加收藏的歌曲
    @ResponseBody
    @RequestMapping(value = "/collection/add", method = RequestMethod.POST)
    public Object addCollection(HttpServletRequest req) {
        String user_id = req.getParameter("userId");
        String type = req.getParameter("type");
        String song_id = req.getParameter("songId");
        String song_list_id = req.getParameter("songListId");

        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(user_id));
        collect.setType(new Byte(type));
        if (new Byte(type) == 0) {
            collect.setSongId(Integer.parseInt(song_id));
        } else if (new Byte(type) == 1) {
            collect.setSongListId(Integer.parseInt(song_list_id));
        }
        collect.setCreateTime(new Date());

        boolean res = collectService.addCollection(collect);
        if (res) {
            return new SuccessMessage<Boolean>("收藏成功", true).getMessage();
        } else {
            return new ErrorMessage("收藏失败").getMessage();
        }
    }

    // 取消收藏的歌曲
    @RequestMapping(value = "/collection/delete", method = RequestMethod.GET)
    public Object deleteCollection(HttpServletRequest req) {
        String user_id = req.getParameter("userId").trim();
        String song_id = req.getParameter("songId").trim();

        boolean res = collectService.deleteCollect(Integer.parseInt(user_id), Integer.parseInt(song_id));
        if (res) {
            return new SuccessMessage<Boolean>("取消收藏成功", false).getMessage();
        } else {
            return new ErrorMessage("取消收藏失败").getMessage();
        }
    }

    // 是否收藏歌曲
    @RequestMapping(value = "/collection/status", method = RequestMethod.POST)
    public Object isCollection(HttpServletRequest req) {
        System.out.println("收藏状态");
        String user_id = req.getParameter("userId").trim();
        String song_id = req.getParameter("songId").trim();

        boolean res = collectService.existSongId(Integer.parseInt(user_id), Integer.parseInt(song_id));
        if (res) {
            System.out.println("歌曲已收藏");
            return new WarningMessage("已收藏").getMessage();
        } else {
            System.out.println("歌曲未收藏");
            return new SuccessMessage<Boolean>("未收藏", false).getMessage();
        }
    }

    // 返回的指定用户 ID 收藏的列表
    @RequestMapping(value = "/collection/detail", method = RequestMethod.GET)
    public Object collectionOfUser(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        System.out.println("用户" + userId + "收藏的列表");
        return new SuccessMessage<List<Collect>>("用户"+userId+"的收藏列表", collectService.collectionOfUser(Integer.parseInt(userId))).getMessage();
    }

    // 返回所有用户收藏列表
    @RequestMapping(value = "/collection", method = RequestMethod.GET)
    public Object allCollection(){
        System.out.println("所有用户收藏列表");
        return collectService.allCollect();
    }
}
