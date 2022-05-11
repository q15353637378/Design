package Mediator;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 05/05/2022 08:11
 */
public class client {
    public static void main(String[] args) {
        // create Mediator
        MotherBoard motherBoard = new MotherBoard();

        //create every Colleague  every port all know who are their MotherBoard
        CDDriver cdDriver = new CDDriver(motherBoard);
        cpu cpu = new cpu(motherBoard);
        soundCard soundCard = new soundCard(motherBoard);
        graphicsCard graphicsCard = new graphicsCard(motherBoard);

        motherBoard.setCdDriver(cdDriver);
        motherBoard.setCpu(cpu);
        motherBoard.setSoundCard(soundCard);
        motherBoard.setGraphicsCard(graphicsCard);

        cdDriver.readCD();
    }
}
//各模块儿遵守的抽象类
abstract class Colleague{
    Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return mediator;
    }
}

interface Mediator{
    void change(Colleague colleague);
}

class CDDriver extends Colleague {

    private String date = "设计大法好啊，好好学习";

    public CDDriver(Mediator mediator) {
        super(mediator);
    }

    public String getDate() {
        return date;
    }

    void readCD() {
        this.date = "设计模式牛逼，好好学习吧";
        //Call his Mediator data is ready
        this.getMediator().change(this);
    }
}
class cpu extends Colleague{
    public cpu(Mediator mediator) {
        super(mediator);
    }
    String videoData="";
    String soundData="";

    public String getVideoData() {
        return videoData;
    }

    public String getSoundData() {
        return soundData;
    }

    void executeDate(String date) {
        String[] ss = date.split("，");
        this.videoData = ss[0];
        this.soundData = ss[1];
        this.getMediator().change(this);
    }
}
class graphicsCard extends Colleague{
    public graphicsCard(Mediator mediator) {
        super(mediator);
    }
    void showDate(String date) {
        System.out.println("您正在观看的是："+date);
    }
}
class soundCard extends Colleague{

    public soundCard(Mediator mediator) {
        super(mediator);
    }
    void showDate(String date) {
        System.out.println("画外音："+date);
    }
}

class MotherBoard implements Mediator{
    CDDriver cdDriver = null;
    cpu cpu = null;
    soundCard soundCard = null;
    graphicsCard graphicsCard = null;

    public MotherBoard(CDDriver cdDriver, cpu cpu, soundCard soundCard, graphicsCard graphicsCard) {
        this.cdDriver = cdDriver;
        this.cpu = cpu;
        this.soundCard = soundCard;
        this.graphicsCard = graphicsCard;
    }

    public MotherBoard() {
    }

    public void setCdDriver(CDDriver cdDriver) {
        this.cdDriver = cdDriver;
    }

    public void setCpu(cpu cpu) {
        this.cpu = cpu;
    }

    public void setSoundCard(soundCard soundCard) {
        this.soundCard = soundCard;
    }

    public void setGraphicsCard(graphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    @Override
    public void change(Colleague colleague) {
        if (colleague == cdDriver) {
            this.opeCDDriverReadData((CDDriver)colleague);
        } else if (colleague == cpu) {
            this.opeCPU((cpu)colleague);
        }
    }

    private void opeCPU(cpu cpu) {
        String videoData = cpu.getVideoData();
        String soundData = cpu.getSoundData();
        this.soundCard.showDate(soundData);
        this.graphicsCard.showDate(videoData);
    }

    private void opeCDDriverReadData(CDDriver colleague) {
        String date = colleague.getDate();
        this.cpu.executeDate(date);
    }
}
