package com.hk_music_cop.demo.jandi.common;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomUndefinedCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum JandiCommand {
	DRAW_REGISTER("추첨 등록", new ParameterRule(1, 2)),
	DRAW_MODIFY("추첨 수정", new ParameterRule(2, Arrays.asList(1, 2))),
	DRAW_DELETE("추첨 삭제", new ParameterRule(1, 1)),
	DRAW_VIEW("추첨", new ParameterRule(0, 0)),
	DRAW_LIST("추첨 리스트 조회", new ParameterRule(0, 0)),
	DAILY_SCHEDULE("일단위 일정 조회", new ParameterRule(1, 3)),
	WEEKLY_SCHEDULE("주단위 일정 조회", new ParameterRule(1, 3));

	private String commandText;
	private ParameterRule parameterRule;

	public static JandiCommand fromString(String text) {
		return Arrays.stream(values())
				.filter(command -> command.commandText.equals(text))
				.findFirst()
				.orElseThrow(() -> new CustomUndefinedCommand("존재하지 않는 명령어입니다."));
	}

	JandiCommand(String commandText, ParameterRule parameterRule) {
		this.commandText = commandText;
		this.parameterRule = parameterRule;
	}


	public static class ParameterRule {
		private final int requiredListCount;
		private final List<Integer> parameterSizes;

		public ParameterRule(int requiredListCount, int parameterSize) {
			this.requiredListCount = requiredListCount;
			this.parameterSizes = Collections.singletonList(parameterSize);
		}

		public ParameterRule(int requiredListCount, List<Integer> parameterSizes) {
			this.requiredListCount = requiredListCount;
			this.parameterSizes = List.copyOf(parameterSizes);
		}
	}
}
