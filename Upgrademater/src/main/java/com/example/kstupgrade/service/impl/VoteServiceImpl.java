package com.example.kstupgrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kstupgrade.com.obj.VotePoJo;
import com.example.kstupgrade.com.obj.ZipPoJo;
import com.example.kstupgrade.com.obj.dto.*;
import com.example.kstupgrade.com.util.Formatter;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.com.util.MyThreadDoSql;
import com.example.kstupgrade.com.util.ResponseEnumCode;
import com.example.kstupgrade.dao.vote.VoteMapper;
import com.example.kstupgrade.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class VoteServiceImpl extends ServiceImpl<VoteMapper, VotePoJo> implements VoteService {
    @Value("${filebasepath}")
    private String basefilepath;
    @Autowired
    private VoteMapper voteMapper;
    //多线程查询数据库
    @Autowired
    private MyThreadDoSql<FindVoteDto, Object> myThreadDoSql;
    @Override
    public MyResponseBody deletevote(String uuid) {
        //删除文件
        //查询
        VotePoJo votePoJo= voteMapper.findByuuid(uuid);
        if(votePoJo!=null){
            File dir = new File(basefilepath + "vote/" + votePoJo.getVotename());
            if (dir.exists()) {
                boolean result = dir.delete();
                if (!result) {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }
            }
            long result = voteMapper.deleteVote(uuid);
            if (result >= 1) {
                return MyResponseBody.success();
            } else {
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }
        }else{
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody saveFile(MultipartFile file) {
        try {
            //添加数据库
            VoteDto voteDto = new VoteDto();
            voteDto.setVotename(file.getOriginalFilename());
            //根据文件名称来判断类型
            //判断文件名：
            //获取文件名称
            String filename = file.getOriginalFilename();
//            String[] filenamepath = filename.split(".");
            int lastIndexOf = filename.lastIndexOf(".");
            //获取文件的后缀名
            String suffix = filename.substring(lastIndexOf + 1);
            voteDto.setType(suffix);


            String[] temps = UUID.randomUUID().toString().split("-");
            StringBuffer uuidsb = new StringBuffer();
            for (String temp : temps) {
                uuidsb.append(temp);
            }
            voteDto.setUuid(uuidsb.toString());
            voteDto.setVotename(filename);
            voteDto.setVotesize(Formatter.formatFileSize(file.getSize()));
            voteDto.setUpdatetime(String.valueOf(System.currentTimeMillis()));
            voteDto.setDownloadtime(0);
            String basepath = basefilepath + "vote";
            File temp2 = new File(basepath);
            if (!temp2.exists()) {
                temp2.mkdirs();
            }
            voteDto.setPath("vote");
            long result = 0;
            FindVoteDto findVotedto = new FindVoteDto();
            findVotedto.setVotename(voteDto.getVotename());
            List<VotePoJo> votePoJos =voteMapper.findVoteByName(findVotedto);
            if (votePoJos.isEmpty()) {
                result = voteMapper.addVote(voteDto);
            } else {
                voteDto.setUuid(votePoJos.get(0).getUuid());
                result = voteMapper.updateVote(voteDto);
            }

            //保存数据库

            if (result >= 0) {
                //保存文件
                //保存文件的根地址

                log.info(basepath);
                //创建文件对象

                File dir = new File(basefilepath + "vote");
                //判断根路径是否存在不存在创建
                File dir2 = new File(basefilepath);
                if (!dir2.exists()) {
                    try {
                        dir2.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                }
                //判断根子目录是否存在
                if (!dir.exists()) {
                    try {
                        dir.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                }
                //判断前端传递过来的文件是否为空，为空返回错误
                if (file.isEmpty()) {
                    return MyResponseBody.failure(ResponseEnumCode.CHOOSE_NO_FILE);

                }
                //创建文件对象
                File localFile = new File(basepath + "/" + filename);
                //如果文件存在将原来的文件进行删除，然后覆盖
                boolean flag = true;
                if (localFile.exists()) {
                    flag = localFile.delete();
                }
                log.info(basepath + "/" + filename);
                if (flag) {
                    try {
                        file.transferTo(localFile); //把上传的文件保存至本地

                        log.info(file.getOriginalFilename() + " 上传成功");
                        return MyResponseBody.success("上传成功", null, 1L);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                    }
                } else {
                    return MyResponseBody.failure(ResponseEnumCode.FAILURE);
                }

            } else {
                //数据库保存失败。
                return MyResponseBody.failure(ResponseEnumCode.FAILURE);
            }


        }catch (Exception e) {
            e.printStackTrace();
            return MyResponseBody.failure(ResponseEnumCode.DATA_IS_WRONG);

        }
    }

    @Override
    public List<VotePoJo> findByName(FindVoteDto findVoteDto) {
        return  voteMapper.findVoteByName(findVoteDto);
    }

    @Override
    public MyResponseBody updatevote(VoteDto voteDto) {
        long result = voteMapper.updateVote(voteDto);
        if (result >= 1) {
            return MyResponseBody.success();
        } else {
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody findVotes(FindVoteDto findVoteDto) {
        Function<FindVoteDto, Object> selectfun = zips -> voteMapper.findVotes(findVoteDto);
        Function<FindVoteDto, Object> countfun = zipcounts ->voteMapper.getcount(findVoteDto);
        try {
            Long count = 0L;

            Object o = myThreadDoSql.dosql(findVoteDto, selectfun);
            Object c = myThreadDoSql.dosql(findVoteDto, countfun);
            if (c instanceof Long) {
                count = (long) c;
            }
            return MyResponseBody.success(o, count);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseBody.failure(ResponseEnumCode.FAILURE);
        }
    }

    @Override
    public MyResponseBody setMaxPriority(VoteDto voteDto) {
        long setinipriority=voteMapper.updateini();
        int maxpriority=voteMapper.getMaxPriority();
        System.out.println(maxpriority);
        voteDto.setPriority(maxpriority+1);
       return  updatevote(voteDto);


    }

    @Override
    public MyResponseBody getMaxPriority() {

        int maxpriority=voteMapper.getMaxPriority();
        if(maxpriority>0){
            return  MyResponseBody.success("",Long.valueOf(maxpriority));
        }else{
            return MyResponseBody.success("",-1L);
        }
    }
}
