package cn.cloud.service;

import cn.cloud.domain.CloudResourceLink;

import java.util.List;

public interface CloudResourceLinkService {
    //添加资源
    public void addLink(CloudResourceLink link);
    //查看个人资源分享
    public List<CloudResourceLink> show_per_link(Integer user_id);
    //查询全部（通过的）
    public List<CloudResourceLink> searchLinkAll();
    //排除，防止重复上传
    public CloudResourceLink searchLinkByLink(String link);
    //删除链接
    public void deleteLink(String srcl_id);
    //查询未通过审核的资源
    public List<CloudResourceLink> searchLink();
    //通过审核
    public void updateLink(Integer srcl_id);
}
