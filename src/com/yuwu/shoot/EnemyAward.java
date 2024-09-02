package com.yuwu.shoot;
// 奖励接口
public interface EnemyAward {
    public int FIRE = 0;// 火力值
    public int LIFE = 1;
    // 获取奖励类型
    public int getAwardType();
}
