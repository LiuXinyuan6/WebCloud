package cn.cloud.controller;

import cn.cloud.domain.CloudChart;
import cn.cloud.domain.CloudResourceFile;
import cn.cloud.domain.CloudUser;
import cn.cloud.domain.Msg;
import cn.cloud.service.CloudSignService;
import cn.cloud.service.EditUserService;
import cn.cloud.service.LoginService;
import cn.cloud.service.FileResourcesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * json数据管理
 */
@Controller
@RequestMapping(value = "/json")
@ResponseBody
public class JsonController {
    @Autowired
    FileResourcesService fileResourcesService;
    private final LoginService loginService;
    @Autowired
    CloudSignService cloudSignService;
    @Autowired
    FileResourcesService srcfService;
    @Autowired
    EditUserService editUserService;
    @Autowired
    public JsonController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/register", produces = "application/json; charset=utf-8" )
    public Map<String,Object> finUser(String user_name, HttpServletRequest request){
        //检验传入参数是否为空
        if(StringUtils.isEmpty(user_name)){
            request.setAttribute("error","用户名不能为空");
            System.out.println("error");
        }
        Map<String,Object> resultMap = new HashMap<String, Object>();
        boolean tof=loginService.findUser(user_name);
        if(tof==true){
            resultMap.put("result","success");
            return resultMap;
        }else{
            resultMap.put("result","notsuccess");
            return resultMap;
        }
    }
    /**
     * 显示用户签到情况
     * 通过user_id,c从数据库进行遍历查找
     */
    @RequestMapping("/show")
    public List signShow(String user_id, HttpSession session) {
        user_id= (String) session.getAttribute("user_id");
        //System.out.println(user_id);
        List list = new ArrayList();
        list = cloudSignService.findMonthSign(user_id);
        System.out.println(list);
        return list;
    }
    /**
     * 用于导出报表的json
     * 集合数据格式list:
     * user_name,status  ---------->  目标数据
     * name1,10          ---------->  user_name:name1
     * name1,11          ---------->  status1:10
     * name1,20          ---------->  status2:11
     * name2,14          ---------->  ......
     * name3,15
     *
     * @return  返回值为List集合，list中包含map集合
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public List<Map> exportSign(@Param("month") int month) throws ParseException {
        //解析日期
        Calendar calendar=Calendar.getInstance();//获得日历
        String firstTime=calendar.get(Calendar.YEAR)+"-"+month+"-01 00:00:00";//设置查询的月份
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化日期
        Date date=simpleDateFormat.parse(firstTime);  //将设置的字符串日期转化成date类型
        calendar.setTime(date);  //将设置的日期传入日历中
        calendar.add(Calendar.MONTH,+1);//将月份加一
        String lastTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-01 00:00:00";//设置下个月的日期
        //处理数据
        List<Map<String,Object>> list=cloudSignService.exportSign(firstTime,lastTime);
        // list:[{user_name=小黄人, sign_status=19}, {user_name=小黄人, sign_status=21}, {user_name=小绿人},{user_name=小红人}]
        List<Map> mapList=new ArrayList<Map>();  //返回值，存放Map集合
        Map<String,String> objectMap=new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        }); //存放对象信息的map集合
        int i=31;     //为了实现排序，无实际意义，可忽略
        Object [] objects=  list.toArray(); //将对象转化为数组，目的是为了获得数组大小控制循环次数
        int count=objects.length;//用来记录数组长度
        for (int index=0;index<count;index++) {//map:{user_name=小黄人, sign_status=19}
            HashMap map= (HashMap) objects[index]; //将object数组的元素强制转化为map对象
            //获得user_name
            String user_name= (String)(map.get("user_name"));//小黄人
            //获得status
            String status= map.get("sign_status")==null?"":""+map.get("sign_status");//转换成3-10的格式或者为""
            //将数据放入到map集合中，一个Map集合放一个对象的信息
            if(!objectMap.containsKey("user_name")){// 判断是否有相同的键，如果没有则添加键以及第一个日期
                objectMap.put("user_name",user_name);
                if(!"".equals(status)){  //如果日期不为空则进行字符串拼接
                    objectMap.put("status"+i,month+"-"+status);
                }else{    //如果日期为空则直接传入其空值
                    objectMap.put("status"+i,status);
                }
                i--;
            }else if(objectMap.get("user_name").equals(user_name)){//如果用户名相同，则只添加日期
                if(!"".equals(status)){  //如果日期不为空则进行字符串拼接
                    objectMap.put("status"+i,month+"-"+status);
                }else{    //如果日期为空则直接传入其空值
                    objectMap.put("status"+i,status);
                }
                i--;
            }else{      //如果出现了不同的user_name，则将map集合存入List集合中，然后清空，存放下一个对象的信息
                if(!objectMap.isEmpty()){//如果不为空则将数据进行导入
                    //新建Map集合存放对象信息，且实现倒序排放。  为什么要新建呢？好好想想吧！                                           //提示：跟map.clear()方法有关
                    Map<String,String> newMap=new TreeMap<String,String>(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o2.compareTo(o1);
                        }
                    });
                    newMap.putAll(objectMap);//将集合中的所有数据全部放入新建的map中进行排序，然后添加进List中
                    mapList.add(newMap);
                    objectMap.clear();  //清空集合中的信息，准备放入下一个对象信息
                    i=31;   //初始化i值
                    index--;//进行数据合并的时候需要将循环次数加1
                    //放入下一个对象的信息
                    objectMap.put("user_name",user_name);
                    if(!"".equals(status)){//如果日期不为空，则进行字符串拼接
                        objectMap.put("status"+i,month+"-"+status);
                    }else{      //日期为空，将空字符串写入
                        objectMap.put("status"+i,status);
                    }
                    if(count-2==index){//如果是最后一次循环则将数据放入list集合中
                        //新创建一个集合存放最后一个数据。
                        Map<String,String> finalMap=new TreeMap<String,String>(new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return o2.compareTo(o1);
                            }
                        });
                        finalMap.putAll(objectMap);//将最后一个对象的信息放入finalMap中，然后添加进集合里
                        objectMap.clear();
                        mapList.add(finalMap);
                        break;
                    }
                }
            }
        }
        if(objectMap.size()!=0){//当只有一个用户的情况
            mapList.add(objectMap);
        }
        return mapList;
    }

    /**
     * 使用分页插件完成
     * @param pn
     * @return
     */
    @RequestMapping(value = "/rsf/page",produces = "application/json; charset=utf-8")
    public Msg getInformationWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){

        //在查询前只需要调用，传入页码以及每页显示多少条数据
        PageHelper.startPage(pn,12);
        //startPage后面跟的查询就是一个分页查询
        List<CloudResourceFile> informationList = srcfService.getAll();
        System.out.println(informationList);
        //使用pageInfo包装查询后的结果,将pageInfo交个页面即可
        PageInfo page = new PageInfo(informationList,5);//包含了详细的分页信息，包括查询出来的数据(emps),连续显示的页数(5)
        return Msg.success().add("pageInfo",page);
    }


    /**
     * 异步上传测试版
     */
    @RequestMapping(value = "/upload/file" ,method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object uploadPic(HttpServletRequest request, MultipartFile[] files,String describe) {
        //设置路径
        if (files.length==0){
            return "redirect:/rsf/upload";
        }
        CloudUser user = (CloudUser) request.getSession(false).getAttribute("UserLogin");
        if (user == null) {
            return "redirect:/login/user/toLogin";
        }
        System.out.println(describe);
        for (MultipartFile file : files) {
            Boolean judge = fileResourcesService.encapsulationDataOfFileMSG(request, file, user);
            if (judge) {
                continue;
            } else {
                return (Object)"error";
            }
        }
//        String json = toJson();
        return (Object)"true";//没有页面
    }

    /**
     * 修改密码 判断原密码是否正确
     * @param user_name
     * @param user_pwd
     * @param session
     * @return
     */
    @RequestMapping(value = "/findPass", produces = "application/json; charset=utf-8" )
    public Map<String,Object> findPass(String user_name,String user_pwd,HttpSession session){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        boolean to=editUserService.findPass(user_name,user_pwd);//用户密码和数据库对比
        if(to==false){
            resultMap.put("result","notsuccess");
        }else{
            resultMap.put("result",session.getAttribute("user_name"));//session.getAttribute("user_name")
        }
        return resultMap;
    }
    /**
     *
     *
     *
     *
     *
     * @param month 按照月份进行查询
     * @return 返回CloudChart对象，包含签到的具体信息
     */
    @RequestMapping(value = "/admin/monthChart",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CloudChart showMonthChart(@Param("month") int month) throws ParseException {
        //解析日期
        Calendar calendar=Calendar.getInstance();//获得日历
        String firstTime=calendar.get(Calendar.YEAR)+"-"+month+"-01 00:00:00";//设置查询的月份
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化日期
        Date date=simpleDateFormat.parse(firstTime);  //将设置的字符串日期转化成date类型
        calendar.setTime(date);  //将设置的日期传入日历中
        calendar.add(Calendar.MONTH,+1);//将月份加一
        String lastTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-01 00:00:00";//设置下个月的日期
        //查询数据
        List<Map<String,Object>> list=cloudSignService.showMonthChart(firstTime,lastTime);
        //数据转化
        List<Integer> first=new ArrayList<Integer>();   //存放大一每天签到总数
        List<Integer> second=new ArrayList<Integer>();  //存放大二每天签到总数
        List<Integer> sum=new ArrayList<Integer>();           //存放每天签到总人数
        List<String> datelist=new ArrayList<String>();       //存放一个月的日期
        Map<Integer,Object> firstMap=new TreeMap<Integer,Object>();
        Map<Integer,Object> secondMap=new TreeMap<Integer,Object>();
        boolean up= month >= 9 && month <= 12;//判断是否是上学期
        for (Map<String, Object> map : list) {//将大一大二进行分类并统计每天签到的总人数
            Integer key=Integer.parseInt(String.valueOf(map.get("sign_status")));
            String value=String.valueOf(map.get("user_real_name"));
            String num = String.valueOf(map.get("user_stu_id")) ;//获得学号
            List<String> tempList=new ArrayList<String>();
            List nameList;
            //通过学号判断年级
            if ( ( up && num.startsWith(String.valueOf(calendar.get(Calendar.YEAR)).substring(2)))
                    || (!up && num.startsWith(String.valueOf(calendar.get(Calendar.YEAR)-1).substring(2))) ) {//大一
                if (!firstMap.containsKey(key)) {//如果为空则添加日期和姓名
                    tempList.add(value);//将姓名放入集合中
                    firstMap.put(key, tempList);//key为日期，value为姓名的集合
                } else {//如果为空则只添加姓名
                    nameList = (List) firstMap.get(key);//获得当天签到的姓名集合
                    nameList.add(value);
                    firstMap.replace(key,nameList);
                }
            }
            else if ( (up && num.startsWith(String.valueOf(calendar.get(Calendar.YEAR) - 1).substring(2)))
                    || (!up && num.startsWith(String.valueOf(calendar.get(Calendar.YEAR) - 2).substring(2))) ) {//大二
                if (!secondMap.containsKey(key)) {//如果为空则添加日期和姓名
                    tempList.add(value);//将姓名放入集合中
                    secondMap.put(key, tempList);//key为日期，value为姓名的集合
                } else {//如果为空则只添加姓名
                    nameList = (List) secondMap.get(key);//获得当天签到的姓名集合
                    nameList.add(value);
                    secondMap.replace(key,nameList);
                }
            }
        }
        //判断月份有几天
        String[] day31={"1","3","5","7","8","10","12"};
        String[] day30={"4","6","9","11"};
        int day;
        if(Arrays.asList(day31).contains(String.valueOf(month))){//31天
            day=31;
        }else if(Arrays.asList(day30).contains(String.valueOf(month))){//30天
            day=30;
        }else if( calendar.get(Calendar.YEAR)%4==0 && calendar.get(Calendar.YEAR)%100!=0 || calendar.get(Calendar.YEAR)%400==0){//29天
            day=29;
        }else {//28天
            day=28;
        }
        for(int i=1;i<=day;i++){//从第一天开始计算签到的人数,如果没有则为0
            datelist.add(i-1,month+"."+i);//index:0,element:3.1
            List firstList= (List) firstMap.get(i);
            List secondList= (List) secondMap.get(i);
            if(firstList!=null){
                first.add(firstList.size());
            }else{
                firstMap.put(i,"");
                first.add(0);
            }
            if(secondList!=null){
                second.add(secondList.size());
            }else{
                secondMap.put(i,"");
                second.add(0);
            }

            sum.add(first.get(i-1)+second.get(i-1));
        }

        return new CloudChart(first,second,sum,datelist,firstMap,secondMap);
    }
}
