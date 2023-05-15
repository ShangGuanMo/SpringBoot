package com.example.yin.dao;

import com.example.yin.domain.RankList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RankListMapper {

    /**
     * 插入评分记录
     * @param record
     * @return
     */
    int insert(RankList record);

    /**
     * 插入评分记录（不为空）
     * @param record
     * @return
     */
    int insertSelective(RankList record);

    /**
     * 查总分
     * @param songListId
     * @return
     */
    int selectScoreSum(Long songListId);

    /**
     * 查总评分人数
     * @param songListId
     * @return
     */
    int selectRankNum(Long songListId);

    /**
     * 查制定用户评分
     * @param consumerId
     * @param songListId
     * @return
     */
    int selectUserRank(@Param("consumerId") Long consumerId, @Param("songListId")  Long songListId);

    /**
     * 查询所有评分记录
     * @return
     */
    List<RankList> selectRanks();
}
