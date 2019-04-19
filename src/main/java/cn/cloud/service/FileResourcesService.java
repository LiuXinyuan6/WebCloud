package cn.cloud.service;

import cn.cloud.domain.CloudResourceFile;
import cn.cloud.domain.CloudUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileResourcesService {

    List<CloudResourceFile> getAll();


    /**
     * 分析上传文件
     * @param request
     * @param file
     * @param user
     * @return
     */
    Boolean encapsulationDataOfFileMSG(HttpServletRequest request, MultipartFile file, CloudUser user);
    /**
     * 将分析的文件信息存到数据库
     * @param cloudResourceFile
     */
    void addFile(CloudResourceFile cloudResourceFile);

    /**
     * 文件下载
     * @param id
     * @return
     */
    CloudResourceFile findFileById(Integer id);

    /**
     * 获取所有上传的资源文件数据，包含未审核的
     * @return
     */
    List<CloudResourceFile> getAllOfInformation();

    /**
     * 获取要审核的资源文件数据
     * @param id
     * @return
     */
    CloudResourceFile getOneInformation(Integer id);

    /**
     * 更新审核成功的资源文件的状态信息
     */
    void updateStatus(Integer id);

    /**
     * 删除文件
     * @param id
     */
    void deleteOneResource(Integer id);
}
