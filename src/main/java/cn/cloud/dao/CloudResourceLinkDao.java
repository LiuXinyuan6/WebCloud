package cn.cloud.dao;

import cn.cloud.domain.CloudResourceLink;

import java.util.List;

public interface CloudResourceLinkDao {
    //添加资源链
    public void addLink(CloudResourceLink link);
    //查看个人资源分享
    public List<CloudResourceLink> show_per_link(Integer user_id);
    //查询全部链接
    public List<CloudResourceLink> searchLinkAll();
    //添加链接前，排除重复链接
    public CloudResourceLink searchLinkByLink(String link);
    //删除链接
    public void deleteLink(String srcl_id);

    public List<CloudResourceLink> searchLink();
    //通过审核
    public void updateLink(Integer srcl_id);
}
