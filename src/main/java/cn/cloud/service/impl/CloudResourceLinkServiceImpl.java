package cn.cloud.service.impl;

import cn.cloud.dao.CloudResourceLinkDao;
import cn.cloud.domain.CloudResourceLink;
import cn.cloud.service.CloudResourceLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CloudResourceLinkServiceImpl implements CloudResourceLinkService {

    @Autowired
    CloudResourceLinkDao cloudResourceLinkDao;
    @Override
    public void addLink(CloudResourceLink link) {
        cloudResourceLinkDao.addLink(link);

    }

    @Override
    public List<CloudResourceLink> show_per_link(Integer user_id) {

        List<CloudResourceLink> list=cloudResourceLinkDao.show_per_link(user_id);
        return list;
    }

    @Override
    public List<CloudResourceLink> searchLinkAll() {
        List list = cloudResourceLinkDao.searchLinkAll();
        return list;
    }


    //通过链接名进行判断
    @Override
    public CloudResourceLink searchLinkByLink(String link) {
        CloudResourceLink cloudResourceLink =cloudResourceLinkDao.searchLinkByLink(link);
        return cloudResourceLink ;
    }

    @Override
    public void deleteLink(String srcl_id) {

        cloudResourceLinkDao.deleteLink(srcl_id);
    }

    @Override
    public List<CloudResourceLink> searchLink() {
        return cloudResourceLinkDao.searchLink();

    }

    @Override
    public void updateLink(Integer srcl_id) {
        cloudResourceLinkDao.updateLink(srcl_id);
    }
}
