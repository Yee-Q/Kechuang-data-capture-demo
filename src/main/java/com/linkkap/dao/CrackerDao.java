package com.linkkap.dao;

import com.linkkap.entity.FileData;
import com.linkkap.entity.ProjectDynamicData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/30
 */
@Repository
@Mapper
public interface CrackerDao {

    /**
     * 添加项目动态信息
     * @param dataList 项目动态信息列表
     */
    void createProjectDynamicData(List<ProjectDynamicData> dataList);

    /**
     * 添加文件信息
     * @param fileDataList 文件信息列表
     */
    void createFileData(List<FileData> fileDataList);

    /**
     * 获取项目动态信息
     * @return 项目动态信息列表
     */
    List<ProjectDynamicData> selectProjectDynamicData();

    /**
     * 获取项目招股书和问询报告信息
     * @return 项目动态信息列表
     */
    List<FileData> selectFileData();

    /**
     * 删除文件信息
     */
    void deleteAllFileData();

    /**
     * 删除动态项目信息
     */
    void deleteAllProjectDynamicData();
}
