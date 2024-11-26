package com.hk_music_cop.demo.lottery.dto.request;

import com.hk_music_cop.demo.lottery.common.enums.Position;

public record LotteryCreateRequest(String lotteryName, String position) {}
