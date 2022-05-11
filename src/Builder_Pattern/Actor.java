package Builder_Pattern;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 26/01/2022 10:10
 */
//产品
public class Actor {
    private String type;
    private String sex;
    private String face;
    private String hairstyle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(String hairstyle) {
        this.hairstyle = hairstyle;
    }
}
//抽象建造者
abstract class ActorBuilder{
    protected Actor actor = new Actor();
    public void buildType() {};
    public void buildSex() {};
    public void buildFace() {};
    public void buildHairStyle() {};
    public Actor createActor() {return actor;}
}
//具体建造者
class HeroBuilder extends ActorBuilder{
    @Override
    public void buildType() {
        actor.setType("heroFace");
    }

    @Override
    public void buildSex() {
        actor.setSex("female");
    }

    @Override
    public void buildFace() {
        actor.setFace("beauty");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("Gold");
    }
}
//具体建造者
class AngleBuilder extends ActorBuilder{
    @Override
    public void buildType() {
        actor.setType("AngleFace");
    }

    @Override
    public void buildSex() {
        actor.setSex("male");
    }

    @Override
    public void buildFace() {
        actor.setFace("cute");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("no");
    }
}
//指挥者
class ActorController{
    public Actor construct(ActorBuilder actorBuilder) {
        actorBuilder.buildType();
        actorBuilder.buildSex();
        actorBuilder.buildFace();
        actorBuilder.buildHairStyle();
        return actorBuilder.createActor();
    }
}
//客户端
class Clint{
    public static void main(String[] args) {
        ActorController actorController = new ActorController();
        Actor construct = actorController.construct(new AngleBuilder());
        System.out.println(construct.getType());
    }
}