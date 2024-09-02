package com.yuwu.shoot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 整个游戏窗口
 */
public class World extends JPanel {
    public static final int WIDTH = 500;// 游戏窗口宽度
    public static final int HEIGHT = 744;// 游戏窗口高度
    public static final int START = 0;//启动状态
    public static final int RUNNING = 1;//运行状态
    public static final int PAUSE = 2;//暂停状态
    public static final int GAME_OVER = 3;//结束状态
    private int state = START;//当前状态默认启动

    //表示窗口中所显示的对象
    private Sky sky = new Sky();// 天空对象
    private Hero hero = new Hero();// 英雄机对象
    private FlyingObject[] enemies = {};// 敌人数组
    private Bullet[] bullets = {};// 子弹数组

    //生成敌人对象
    public FlyingObject nextOne() {
        Random random = new Random();// 随机数对象
        int type = random.nextInt(20);// 0-19
        if (type < 3) {// 3/20的概率生成大敌机
            return new BigAirplane();
        } else if (type < 15) {// 12/20的概率生成小敌机
            return new Airplane();
        } else {// 5/20的概率生成小蜜蜂
            return new Bee();
        }
    }

    //g: 画笔 重写paint方法
    public void paint(Graphics g) {
        g.drawImage(sky.getImage(), sky.x, sky.y, null);// 绘制天空
        g.drawImage(sky.getImage(), sky.x, sky.getY1(), null);// 绘制天空2
        g.drawImage(hero.getImage(), hero.x, hero.y, null);// 绘制英雄机
        for (int i = 0; i < enemies.length; i++) {//遍历敌人数组
            FlyingObject e = enemies[i];
            g.drawImage(e.getImage(), e.x, e.y, null);
        }
        for (int i = 0; i < bullets.length; i++) {//遍历子弹数组
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.x, b.y, null);
        }

        g.drawString("SCORE:" + score, 10, 25);
        g.drawString("LIFE:" + hero.getLife(), 10, 50);
        switch (state) {// 根据当前状态绘制界面
            case START:
                g.drawImage(Images.start, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(Images.gameover, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(Images.pause, 0, 0, null);
                break;
        }
    }

    private int enterIndex = 0;//敌人入场计数

    public void enterAction() {
        enterIndex++;//每10ms执行一次
        if (enterIndex % 40 == 0) {//每400ms执行一次
            FlyingObject obj = nextOne();
            enemies = Arrays.copyOf(enemies, enemies.length + 1);//扩容
            enemies[enemies.length - 1] = obj;//添加到数组末尾
        }
    }

    private int shootIndex = 0;//英雄机发射子弹计数

    //英雄机发射子弹
    public void shootAction() {
        shootIndex++;//每10ms执行一次
        if (shootIndex % 30 == 0) {//每300ms执行一次
            Bullet[] bs = hero.shoot();//英雄机发射子弹的数组
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);//扩容
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);//添加到数组末尾
        }
    }

    //移动飞行物
    public void stepAction() {
        sky.step();//天空移动
        for (int i = 0; i < enemies.length; i++) {//遍历敌人数组
            FlyingObject e = enemies[i];
            e.step();//敌人移动
        }
        for (int i = 0; i < bullets.length; i++) {//遍历子弹数组
            Bullet b = bullets[i];
            b.step();//子弹移动
        }
    }

    //删除越界的飞行物
    public void outOfBoundsAction() {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].isOutOfBounds() || enemies[i].isRemove()) {//判断越界
                enemies[i] = enemies[enemies.length - 1];//将越界元素替换为最后一个元素
                enemies = Arrays.copyOf(enemies, enemies.length - 1);//删除越界对象
            }
        }
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isOutOfBounds() || bullets[i].isRemove()) {//判断越界
                bullets[i] = bullets[bullets.length - 1];
                bullets = Arrays.copyOf(bullets, bullets.length - 1);//删除越界对象
            }
        }
    }

    //子弹和敌人碰撞
    private int score = 0;//玩家的得分

    //子弹和敌人碰撞
    public void bulletBangAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            for (int j = 0; j < enemies.length; j++) {
                FlyingObject e = enemies[j];
                if (b.isLive() && e.isLive() && b.isHit(e)) {//子弹和敌人碰撞
                    e.goDead();//敌人死亡
                    b.goDead();//子弹死亡
                    if (e instanceof EnemyScore) {//若被撞对象能够得分
                        EnemyScore es = (EnemyScore) e;//强制类型转换
                        score += es.getScore();//得分
                    }
                    if (e instanceof EnemyAward) {//若被撞对象能够获得奖励
                        EnemyAward ea = (EnemyAward) e;
                        int type = ea.getAwardType();
                        switch (type) {
                            case EnemyAward.LIFE:// 获得生命
                                hero.addLife();
                                break;
                            case EnemyAward.FIRE:// 获得火力
                                hero.addFire();
                                break;
                        }
                    }
                }
            }
        }
    }

    //英雄机与敌人碰撞
    public void heroBangAction() {
        for (int i = 0; i < enemies.length; i++) {
            FlyingObject e = enemies[i];
            if (e.isLive() && hero.isLive() && e.isHit(hero)) {//英雄机与敌人碰撞
                e.goDead();//敌人死亡
                hero.subtractLife();//英雄机减1生命
                hero.clearFire();//英雄机清空火力
            }
        }
    }

    //检测游戏结束
    public void checkGameOverAction() {
        if (hero.getLife() <= 0) {//英雄机生命值小于等于0
            state = GAME_OVER;//游戏结束
        }
    }

    //启动程序的执行
    public void action() {
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    hero.moveTo(e.getX(), e.getY());//英雄机移动
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START:
                        state = RUNNING;
                        break;
                    case GAME_OVER:
                        score = 0;
                        sky = new Sky();
                        hero = new Hero();
                        enemies = new FlyingObject[0];
                        bullets = new Bullet[0];
                        state = START;
                        break;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }
        };// 鼠标移动监听器
        this.addMouseListener(listener);//添加鼠标移动监听器
        this.addMouseMotionListener(listener);//添加鼠标移动监听器
        Timer timer = new Timer();// 定时器对象
        int interval = 10;// 10ms
        timer.schedule(new TimerTask() {
            @Override
            public void run() {// 定时任务, 每隔10ms执行一次
                if (state == RUNNING) {
                    enterAction();//敌人入场
                    shootAction();//英雄机发射子弹
                    stepAction();//飞行物移动
                    outOfBoundsAction();//删除越界的飞行物
                    bulletBangAction();//子弹和敌人碰撞
                    heroBangAction();//英雄机与敌人碰撞
                    checkGameOverAction();//检查游戏是否结束
                }


                repaint();// 重绘, 调用paint方法
            }
        }, interval, interval);//定时计划表
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("飞机大战");
        World world = new World();
        frame.add(world);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        world.action();
    }

}
