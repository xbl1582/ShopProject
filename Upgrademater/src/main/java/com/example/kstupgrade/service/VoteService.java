package com.example.kstupgrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.dto.FindVoteDto;
import com.example.kstupgrade.com.obj.dto.InfoDto;
import com.example.kstupgrade.com.obj.dto.VoteDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VoteService  extends IService<VotePoJo> {
    MyResponseBody deletevote(String uuid);

    MyResponseBody saveFile(MultipartFile file);

    List<VotePoJo> findByName(FindVoteDto findVoteDto);

    MyResponseBody updatevote(VoteDto voteDto);

    MyResponseBody findVotes(FindVoteDto findVoteDto);

    MyResponseBody setMaxPriority(VoteDto voteDto);

    MyResponseBody getMaxPriority();
}
