package com.example.yin.service;

import com.example.yin.domain.Song;
import com.example.yin.domain.SongList;
import com.example.yin.recommend.dto.ProductDTO;
import com.example.yin.recommend.dto.RelateDTO;

import java.util.List;

/**
 * @author root
 * @Description:
 * @Package com.example.demo.service
 * @date 2021/5/6 17:06
 */
public interface RecommendSongListService {
    /**
     * 获取评分记录
     * @return
     */
    List<RelateDTO> getRankData();

    /**
     * 获取歌单数据
     * @return
     */
    List<ProductDTO> getSongListData();

    /**
     * 根据用户评分推荐歌单
     * @param userId
     * @return
     */
    List<SongList> recommendSongListByRank(Integer userId);

    /**
     * 根据用户收藏进行歌单推荐
     * @param userId
     * @return
     */
    List<SongList> recommendSongListByCollect(Integer userId);

    /**
     * 推荐歌曲
     * @param userId
     * @return
     */
    List<Song> recommendSongs(Integer userId);

    /**
     * 获取歌曲数据   
     * @return
     */
    List<ProductDTO> getSongData();

    /**
     * 获取用户收藏歌曲
     * @return
     */
    List<RelateDTO> getCollectData();

}
