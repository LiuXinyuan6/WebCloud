package cn.cloud.service.impl;

import cn.cloud.dao.FileResourcesDao;
import cn.cloud.domain.CloudResourceFile;
import cn.cloud.domain.CloudUser;
import cn.cloud.service.FileResourcesService;
import cn.cloud.util.DateUtil;
import cn.cloud.util.FileWayUtil;
import cn.cloud.util.UpLoadUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
@Transactional
public class FileResourcesServiceImpl implements FileResourcesService {
    @Autowired
    FileResourcesDao fileResourcesDao;
    @Override
    public List<CloudResourceFile> getAll() {

        return fileResourcesDao.SelectAllInformation(null);
    }
    @Override
    public void addFile(CloudResourceFile cloudResourceFile) {

        fileResourcesDao.insertOfAllFileMSG(cloudResourceFile);
    }
    @Override
    public Boolean encapsulationDataOfFileMSG(HttpServletRequest request, MultipartFile file, CloudUser user) {
        String path = request.getSession().getServletContext().getRealPath(FileWayUtil.getPathByNowDate("/WEB-INF/save"));
        String filess = file.getOriginalFilename();
        String fileName = FileWayUtil.getFileNameByRandom(file.getOriginalFilename());
        //获取文件后缀名
        String extension = FilenameUtils.getExtension(fileName);
        //判断文件类型并存储
        if(UpLoadUtil.judgeFileType(extension,UpLoadUtil.DEFAULT_TYPES)){
            //String userId = request.getParameter("user_id");
            File dir = new File(path,fileName);
            if(!dir.exists()){
                dir.mkdirs();
            }
            //MultipartFile自带的解析方法
            try {
                file.transferTo(dir);
                CloudResourceFile CRF = new CloudResourceFile();
                CRF.setSrcf_name(filess.substring(0,filess.lastIndexOf(".")));
                CRF.setClouduser(user);
                CRF.setSrcf_date(DateUtil.getNowTime());
                CRF.setSrcf_size((int) file.getSize());
                CRF.setSrcf_type(extension);
                CRF.setSrcf_src(File.separator+"WEB-INF"+path.split("WEB-INF")[1]+File.separator+fileName);
                CRF.setSrcf_describe(request.getParameter("srcf_describe"));
                this.addFile(CRF);
            } catch (IOException e) {
                return false;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public CloudResourceFile findFileById(Integer id) {
        return fileResourcesDao.getFileById(id);
    }
    @Override
    public List<CloudResourceFile> getAllOfInformation() {
        return fileResourcesDao.SelectAllOfInformation(null);
    }

    @Override
    public CloudResourceFile getOneInformation(Integer id) {
        return fileResourcesDao.getOneInformation(id);
    }

    @Override
    public void updateStatus(Integer id) {
        fileResourcesDao.updateStatus(id);
    }

    @Override
    public void deleteOneResource(Integer id) {
        fileResourcesDao.deleteOneResource(id);
    }
}
