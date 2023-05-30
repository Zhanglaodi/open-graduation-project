package com.ctrl.service.impl;

import com.ctrl.dao.RaffleMapper;
import com.ctrl.entity.CommonResult;
import com.ctrl.entity.CountAndData;
import com.ctrl.entity.DrawCards;
import com.ctrl.entity.raffle.RaffleConverter;
import com.ctrl.entity.raffle.RaffleDO;
import com.ctrl.entity.raffle.RaffleVO;
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
    public CommonResult<List<RaffleVO>> raffle() throws JsonProcessingException {

        String token = request.getParameter("token");
        int GUARANTEED_DRAW_COUNT = 10;
        //所有数据
        List<RaffleDO> raffleD0s = raffleMapper.selectList(null);
        //获取用户数据
        String userInfo = redisUtils.get("anti_fan:" + token, 8);
        UsersDO jsonToBean = JsonUtils.fromJsonString(userInfo, UsersDO.class);
        //获取当前抽卡的次数
        DrawCards drawCards = getAlreadyDrawCardsTime(jsonToBean);
        //抽卡工具类
        DrawCardsUtils cardsUtils = new DrawCardsUtils();
        //最终返回的结果
        List<RaffleVO> demo = new ArrayList<>();
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
        return CommonResult.ok("成功", new CountAndData<>(null, demo));
    }

    /**
     * Gets random cards.获取随即卡片
     *
     * @param i    the
     * @param list the list
     * @return the random cards
     */
    private RaffleVO getRandomCards(int i, List<RaffleDO> list, Random random) {
        List<RaffleDO> commonCards = list.stream().filter(raffle -> raffle.getLevel() == i).collect(Collectors.toList());
        //随机获取一张卡片 根据卡片的等级
        RaffleDO raffleD0 = commonCards.get(random.nextInt(commonCards.size()));
        return RaffleConverter.convertToVO(raffleD0);
    }


    /**
     * Gets already draw cards time.
     *
     * @param usersDO the users do
     * @return the already draw cards time
     * @throws JsonProcessingException the json processing exception
     */
    private DrawCards getAlreadyDrawCardsTime(UsersDO usersDO) throws JsonProcessingException {
        String s = redisUtils.get("anti_fan:draw:" + usersDO.getPhone(), 8);
        DrawCards drawCards = new DrawCards(0, 0);
        if (Objects.isNull(s)) {
            redisUtils.set("anti_fan:draw:" + usersDO.getPhone(), drawCards, 8);
        }
        if (s != null) {
            return JsonUtils.fromJsonString(s, DrawCards.class);
        }
        return drawCards;
    }

    /**
     * Set user draw cards 修改用户抽奖表
     *
     * @param usersDO the users do
     * @param list    the RaffleVO list
     * @return int
     */
    private int setUserDrawCards(UsersDO usersDO, List<RaffleVO> list) {
        return 0;
    }
}
