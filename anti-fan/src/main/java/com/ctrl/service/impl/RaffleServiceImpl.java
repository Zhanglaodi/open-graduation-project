package com.ctrl.service.impl;

import com.ctrl.entity.CommonResult;
import com.ctrl.entity.DrawCards;
import com.ctrl.entity.raffle.RaffleD0;
import com.ctrl.entity.user.UsersDO;
import com.ctrl.service.RaffleService;
import com.ctrl.utils.DrawCardsUtils;
import com.ctrl.utils.JsonUtils;
import com.ctrl.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Raffle service.
 */
@Service
public class RaffleServiceImpl implements RaffleService {
    /**
     * The Raffle mapper.
     */
    @Resource
    RaffleMapper raffleMapper;

    /**
     * The Redis utils.
     */
    @Resource
    RedisUtils redisUtils;

    /**
     * The Request.
     */
    @Resource
    HttpServletRequest request;

    /**
     * Raffle common result.
     *
     * @return the common result
     */
    @Override
    public CommonResult<List<RaffleD0>> raffle() throws JsonProcessingException {

        String token = request.getParameter("token");
        int GUARANTEED_DRAW_COUNT = 10;
        //所有数据
        List<RaffleD0> raffleD0s = raffleMapper.selectList(null);
        //获取用户数据
        String userInfo = redisUtils.get("anti_fan:" + token, 8);
        UsersDO jsonToBean = JsonUtils.getJsonToBean(userInfo, UsersDO.class);
        //获取当前抽卡的次数
        DrawCards drawCards = getAlreadyDrawCardsTime(jsonToBean);
        //抽卡工具类
        DrawCardsUtils cardsUtils = new DrawCardsUtils();
        //最终返回的结果
        List<RaffleD0> demo = new ArrayList<>();
        //合理的方法是从内存中读取用户已经抽奖的次数
        //抽奖等级
        int level;
        //随机
        Random random = new Random();
        //抽奖十次
        int total = drawCards.getTotal();
        int current = drawCards.getCount();
        for (int i = 0; i < GUARANTEED_DRAW_COUNT; i++) {
            level = cardsUtils.drawCard();
            if (level != 0) {
                if (current + 1 >= 30) {
                    level = 3;
                }
                //返回到后端
                demo.add(getRandomCards(level, raffleD0s, random));
                drawCards.setTotal(total + 1);
                drawCards.setCount(current + 1);
                current = current + 1;
                if (level == 3) {
                    //抽到奖励 这里就要重新开始计算保底
                    current = 0;
                    drawCards.setCount(0);
                    //redisUtils.set("anti_fan:draw:" + jsonToBean.getPhone(), drawCards, 8);
                }
            }
        }
        redisUtils.set("anti_fan:draw:" + jsonToBean.getPhone(), drawCards, 8);
        return CommonResult.ok("成功", demo);
    }

    /**
     * Gets random cards.获取随即卡片
     *
     * @param i    the
     * @param list the list
     * @return the random cards
     */
    public RaffleD0 getRandomCards(int i, List<RaffleD0> list, Random random) {
        List<RaffleD0> commonCards = list.stream().filter(raffle -> raffle.getLevel() == i).collect(Collectors.toList());
        return commonCards.get(random.nextInt(commonCards.size()));
    }


    /**
     * Gets already draw cards time.
     *
     * @param usersDO the users do
     * @return the already draw cards time
     * @throws JsonProcessingException the json processing exception
     */
    public DrawCards getAlreadyDrawCardsTime(UsersDO usersDO) throws JsonProcessingException {
        String s = redisUtils.get("anti_fan:draw:" + usersDO.getPhone(), 8);
        DrawCards drawCards = new DrawCards(0, 0);
        if (Objects.isNull(s)) {
            redisUtils.set("anti_fan:draw:" + usersDO.getPhone(), drawCards, 8);
        }
        if (s != null) {
            return JsonUtils.getJsonToBean(s, DrawCards.class);
        }
        return drawCards;
    }
}
