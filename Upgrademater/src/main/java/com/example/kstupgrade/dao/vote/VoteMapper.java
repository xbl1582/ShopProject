package com.example.kstupgrade.dao.vote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteMapper  extends BaseMapper<VotePoJo> {
    List<VotePoJo> findVoteByName(FindVoteDto findVotedto);

    long addVote(VoteDto voteDto);

    long updateVote(VoteDto voteDto);

    long deleteVote(String uuid);

    long getcount(FindVoteDto findVotedto);

    List<VotePoJo> findVotes(FindVoteDto findVotedto);


    VotePoJo findByuuid(String uuid);

    long updateini();

    int getMaxPriority();
}
