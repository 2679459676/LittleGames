package com.yuwu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 小蜜蜂
 */
public class Bee extends FlyingObject implements EnemyAward {
    private int xSpeed;// x轴方向的速度
    private int ySpeed;// y轴方向的速度
    private int awardType;//奖励类型

    public Bee() {
        super(68,68);
        xSpeed=1;
        ySpeed=2;
        Random rand=new Random();
        awardType=rand.nextInt(2);
    }
    private int index=1;
    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return Images.bees[0];
        } else if (isDead()) {
            BufferedImage img =  Images.bees[index++];
            if (index >= Images.bees.length) {//若爆破图下标大于数组长度，则将状态改为删除
                state = REMOVE;
            }
            return img;
        }
        return null;//删除状态时，不返回图片
    }
    @Override
    public void step() {
        x+=xSpeed;
        y+=ySpeed;
        if (x>=World.WIDTH-width||x<=0) {//边界检测
            xSpeed=-xSpeed;//改变x轴方向的速度
        }
    }
    @Override
    public int getAwardType() {
        return awardType;//奖励类型
    }
}
