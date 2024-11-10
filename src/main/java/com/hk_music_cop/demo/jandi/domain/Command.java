package com.hk_music_cop.demo.jandi.domain;

import com.hk_music_cop.demo.global.error.common.CustomUndefinedCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Command {
	DRAW_REGISTER("추첨 등록", new ParameterRule(1, 2)),
	DRAW_MODIFY("추첨 수정", new ParameterRule(2, Arrays.asList(1, 2))),
	DRAW_DELETE("추첨 삭제", new ParameterRule(1, 1)),
	DRAW_VIEW("추첨", new ParameterRule(0, 0)),
	DRAW_LIST("추첨 리스트 조회", new ParameterRule(0, 0)),
	DAILY_SCHEDULE("일단위 일정 조회", new ParameterRule(1, 3)),
	WEEKLY_SCHEDULE("주단위 일정 조회", new ParameterRule(1, 3));

	private final String commandText;
	private final ParameterRule parameterRule;

	Command(String commandText, ParameterRule parameterRule) {
		this.commandText = commandText;
		this.parameterRule = parameterRule;
	}

	public static Command fromString(String text) {
		return Arrays.stream(values())
				.filter(command -> command.commandText.equals(text))
				.findFirst()
				.orElseThrow(() -> new CustomUndefinedCommand("존재하지 않는 명령어입니다."));
	}

	@Getter
	public static class ParameterRule {
		private final int requiredListCount;
		private final List<Integer> parameterSizes;

		public ParameterRule(int requiredListCount, int parameterSize) {
			this.requiredListCount = requiredListCount;
			this.parameterSizes = Collections.singletonList(parameterSize);
		}

		public ParameterRule(int requiredListCount, List<Integer> parameterSizes) {
			this.requiredListCount = requiredListCount;
			this.parameterSizes = Collections.unmodifiableList(new ArrayList<>(parameterSizes));
		}

		// 파라미터 유효성 검증
		public boolean isValid(List<List<String>> parameters) {
			if (parameters.size() != requiredListCount) {
				return false;
			}

			// 각 파라미터 리스트의 크기 검증
			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i).size() != parameterSizes.get(i)) {
					return false;
				}
			}

			return true;
		}
	}
}

