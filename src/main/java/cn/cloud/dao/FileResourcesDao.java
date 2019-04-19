package cn.cloud.dao;

import cn.cloud.domain.CloudResourceFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileResourcesDao {
    /**
     * 获取数据库中所有审核通过的资源文件信息 status 1
     * @param cloudResourceFile
     * @return
     */
    public List<CloudResourceFile> SelectAllInformation(CloudResourceFile cloudResourceFile);
    /**
     * 获取数据库中所有资源文件包括未审核的资源文件 status 0&1
     * @param cloudResourceFile
     * @return
     */
    public List<CloudResourceFile> SelectAllOfInformation(CloudResourceFile cloudResourceFile);
    /**
     * 向数据库输入文件信息
     * @param cloudResourceFile
     */
    @Insert("insert into cloud_resource_file(srcf_name,srcf_type,srcf_src,srcf_size,srcf_date,srcf_describe,user_id) values(#{srcf_name},#{srcf_type},#{srcf_src},#{srcf_size},#{srcf_date},#{srcf_describe},#{clouduser.user_id})")

    public void insertOfAllFileMSG(CloudResourceFile cloudResourceFile);

    /**
     * 根据id查询文件路径
     */
    @Select("select srcf_src from cloud_resource_file where srcf_id=#{id}")
    CloudResourceFile getFileById(Integer id);
    /**
     * 获取要审核的资源文件的信息（页面审核时模态框中的显示的信息）
     * @param id
     * @return
     */
    public CloudResourceFile getOneInformation(int id);

    /**
     * 审核通过时修改数据库中该资源文件的审核状态
     * @param id
     */
    void updateStatus(Integer id);

    /**
     * 删除资源文件
     * @param id
     */
    void deleteOneResource(Integer id);
}
