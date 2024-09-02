package com.yuwu.shoot;
import java.awt.image.BufferedImage;

/**
 * 天空
 */
public class Sky extends FlyingObject {
    private int speed;
    private int y1;//第二个天空的Y坐标

    public Sky() {
        super(World.WIDTH,World.HEIGHT,0,0);
        speed = 1;
        y1 = -744;
    }
    //重写获取图片的方法
    @Override
    public BufferedImage getImage() {
        return Images.sky;//返回天空的图片
    }
    public int getY1() {
        return y1;//返回第二个天空的Y坐标
    }
    public void step() {
        y += speed;//天空向下移动
        y1 += speed;//第二个天空向下移动
        if (y > World.HEIGHT) {//天空移出屏幕
            y = -World.HEIGHT;//天空回到初始位置
        }
        if (y1 > World.HEIGHT) {//第二个天空移出屏幕
            y1 = -World.HEIGHT;//第二个天空回到初始位置
        }
    }
}
