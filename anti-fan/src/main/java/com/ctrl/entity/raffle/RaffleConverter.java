package com.ctrl.entity.raffle;

import com.ctrl.utils.JsonUtils;

/**
 * The type Raffle converter.
 */
public class RaffleConverter {
    /**
     * Convert to vo raffle vo.
     *
     * @param userDO the user do
     * @return the raffle vo
     */
    public static RaffleVO convertToVO(RaffleDO userDO) {
        RaffleVO userVO = new RaffleVO();
        userVO.setCardInfo(userDO.getId().toString());
        userVO.setCharacterName(userDO.getCharacterName());
        userVO.setHeadImg(userDO.getPostImg());
        userVO.setDetails(JsonUtils.toJsonString(userDO));
        switch (userDO.getLevel()) {
            case 1:
                userVO.setLevel("普通");
                break;
            case 2:
                userVO.setLevel("精英");
                break;
            case 3:
                userVO.setLevel("稀有");
                break;
            default:
                userVO.setLevel("至尊");
        }
        return userVO;
    }
}
