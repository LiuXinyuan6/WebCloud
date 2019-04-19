package cn.cloud.util;

import java.util.Map;

public class TimerTaskTest extends  java.util.TimerTask{

    private String name;
    private Map map;

    public TimerTaskTest(String name, Map map) {
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "TimerTaskTest{" +
                "name='" + name + '\'' +
                ", map=" + map +
                '}';
    }

    @Override
    public void run() {


            map.remove(name);
            System.out.println("run@@@@@@@@@@@@@run"+name);




    }
}
