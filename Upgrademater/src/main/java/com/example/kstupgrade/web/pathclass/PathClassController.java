package com.example.kstupgrade.web.pathclass;

import com.example.kstupgrade.com.obj.PathClassPoJo;
import com.example.kstupgrade.com.obj.dto.PathClassDto;
import com.example.kstupgrade.com.util.MyResponseBody;
import com.example.kstupgrade.com.util.ResponseEnumCode;
import com.example.kstupgrade.service.PathClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pathclass")
@Slf4j
public class PathClassController {
    @Autowired
    private PathClassService pathClassService;
    @GetMapping
    MyResponseBody selectallpathclass(){
        List<PathClassPoJo> list=pathClassService.selectAllPathClass();
        return MyResponseBody.success(list,Long.valueOf(list.size()));
    }

    @GetMapping("path")
    MyResponseBody selectallpathclass(PathClassDto pathClassDto){
 PathClassPoJo pathClassPoJo=pathClassService.selectPathClassByPath(pathClassDto);
         if(pathClassPoJo!=null){
             return MyResponseBody.success(pathClassPoJo,1L);
         }else{
             return MyResponseBody.failure(ResponseEnumCode.RESULE_DATA_NONE);
         }

    }

    @PutMapping
    MyResponseBody updatepathclass(@RequestBody PathClassDto pathClassDto){
        log.info(pathClassDto.toString());
       long result= pathClassService.updatedPathclass(pathClassDto);
       if(result!=0){
           return MyResponseBody.success("更新成功");
       }
        return  MyResponseBody.failure(ResponseEnumCode.FAILURE);
    }
    @PostMapping
    MyResponseBody addpathclass(PathClassDto pathClassDto){
        PathClassPoJo pathClassPoJo=pathClassService.setectPathclassByPath(pathClassDto.getPath());
        long result=0;
        if(pathClassPoJo==null){
            result=pathClassService.addPathclass(pathClassDto);
        }else {
            result = pathClassService.updatedPathclass(pathClassDto);
        }
        if(result!=0){
            return MyResponseBody.success("添加成功");
        }
        return  MyResponseBody.failure(ResponseEnumCode.FAILURE);
    }


}
