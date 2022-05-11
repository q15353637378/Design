package Adapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author qinsicheng
 * @Description 内容：实现一个日志功能
 * @Date 30/04/2022 09:02
 */
public class client {
    public static void main(String[] args) {
        logModel logModel = new logModel("2", "2022-04-31", "秦时层", "这只是一次测试日志");
//        list.add(logModel);
//        logFileOperateApi log = new logFileOperate();
//        log.writeLogFile(list);
//        System.out.println("日志更新完毕");
//        List<logModel> logModels = log.readLogFile();
//        System.out.println(logModels.get(0));
//        System.out.println("日志打印完毕");

        logDbOperateApi logDbOperateApi = new Adapter();
        logDbOperateApi.createLog(logModel);
        System.out.println("日志更新完毕");
        List<logModel> allLog = logDbOperateApi.getAllLog();
        for (logModel model : allLog) {
            System.out.println(model);
        }
        System.out.println("日志打印完毕");
    }
}
//日志模板
class logModel implements Serializable{
    //日志ID
    private String id;
    //日志时间  以yyyy-mm-dd HH：mm：ss的格式记录
    private String operateTime;
    //操作人员
    private String operateUser;
    //日志内容
    private String operateContent;

    @Override
    public String toString() {
        return "logModel{" +
                "id='" + id + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", operateUser='" + operateUser + '\'' +
                ", operateContent='" + operateContent + '\'' +
                '}';
    }

    public logModel(String id, String operateTime, String operateUser, String operateContent) {
        this.id = id;
        this.operateTime = operateTime;
        this.operateUser = operateUser;
        this.operateContent = operateContent;
    }

    public logModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }
}

//版本1  日志输出文件
//定义一个操作日志文件的接口
interface logFileOperateApi{
    /**
     * 从文件中读取日志信息
     * @return  返回日志列表
     */
    public List<logModel> readLogFile();

    /**
     * 将日志列表写入文件中
     * @param list 传入的日志列表
     */
    public void writeLogFile(List<logModel> list);
}
//实现日志文件接口
class logFileOperate implements logFileOperateApi{
    private String logFilePathName = "AdapterLog.log";

    public logFileOperate(String logFilePathName) {
        if (logFilePathName!=null&&logFilePathName.trim().length()>0) {
            this.logFilePathName = logFilePathName;
        }
    }

    public logFileOperate() {
    }

    @Override
    public void writeLogFile(List<logModel> list) {
        File f = new File(logFilePathName);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<logModel> readLogFile() {
        List<logModel> list = null;
        ObjectInputStream ois = null;
        try {
            File f = new File(logFilePathName);
            if (f.exists()) {
                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                list = (List<logModel>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois!=null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}

//版本2
interface logDbOperateApi{
    //数据库对日志的增删改查
    void createLog(logModel log);
    void removeLog(logModel log);
    void updateLog(logModel log);
    List<logModel> getAllLog();
}

//问题：  能不能让版本二 同时兼容版本一  使用一个适配器 组合logFileOperate
class Adapter implements logDbOperateApi{
    private logFileOperateApi logFile = new logFileOperate();
    @Override
    public void createLog(logModel log) {
        List<logModel> list = new ArrayList<logModel>();
        list.add(log);
        logFile.writeLogFile(list);
    }

    @Override
    public void removeLog(logModel  log) {

    }

    @Override
    public void updateLog(logModel log) {

    }

    @Override
    public List<logModel> getAllLog() {
        return logFile.readLogFile();
    }
}

//版本3  做一个双向适配器
class AdapterTwoWay implements logDbOperateApi,logFileOperateApi{
    private logFileOperateApi logFile = new logFileOperate();
    private logDbOperateApi logDB = new Adapter();
    @Override
    public List<logModel> readLogFile() {
        return logFile.readLogFile();
    }

    @Override
    public void writeLogFile(List<logModel> list) {
        logFile.writeLogFile(list);
    }

    @Override
    public void createLog(logModel log) {
        logDB.createLog(log);
    }

    @Override
    public void removeLog(logModel log) {
        logDB.removeLog(log);
    }

    @Override
    public void updateLog(logModel log) {
        logDB.updateLog(log);
    }

    @Override
    public List<logModel> getAllLog() {
        return logDB.getAllLog();
    }
}
