package com.hk_music_cop.demo.lottery.dto.request;

import lombok.*;

@Getter
public record LotteryReigstRequest(String lotteryName, String position) {}
