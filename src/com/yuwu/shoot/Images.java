package com.yuwu.shoot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片工具类
 */
public class Images {
    public static BufferedImage sky;//天空图片
    public static BufferedImage bullet;//子弹图片
    public static BufferedImage[] heros;//英雄机图片
    public static BufferedImage[] airs;//敌机图片
    public static BufferedImage[] bees;//蜜蜂图片
    public static BufferedImage[] bairs;//大型敌机图片
    public static BufferedImage start;//开始界面图片
    public static BufferedImage gameover;//游戏结束界面图片
    public static BufferedImage pause;//暂停界面图片


    static {//图片初始化
        start = readImage("start.png");
        gameover = readImage("gameover.png");
        pause = readImage("pause.png");
        sky = readImage("background.png");
        bullet = readImage("bullet.png");
        heros = new BufferedImage[2];//英雄机图片数组
        heros[0] = readImage("hero0.png");
        heros[1] = readImage("hero1.png");
        airs = new BufferedImage[5];//敌机图片数组
        bairs = new BufferedImage[5];//大型敌机图片数组
        bees = new BufferedImage[5];//蜜蜂图片数组
        airs[0] = readImage("airplane.png");
        bairs[0] = readImage("bigairplane.png");
        bees[0] = readImage("bee.png");
        for (int i = 1; i < airs.length; i++) {
            airs[i] = readImage("bom" + i + ".png");
            bairs[i] = readImage("bom" + i + ".png");
            bees[i] = readImage("bom" + i + ".png");
        }
    }

    public static BufferedImage readImage(String fileName) {
        try {//读取与FlyingObject同一目录下的图片
            return ImageIO.read(FlyingObject.class.getResource(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("图片加载失败");

        }
    }
}
