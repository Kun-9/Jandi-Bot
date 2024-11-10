package com.hk_music_cop.demo.jandi.domain;

import com.hk_music_cop.demo.global.error.common.CustomUndefinedCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JandiRequestData {

	@Getter
	public static class Params {
		private final String command;
		private final List<List<String>> parameters;

		private Params(String command, List<List<String>> parameters) {
			this.command = command;
			this.parameters = parameters;
		}
	}

	public Params validParamCnt(String command, List<List<String>> parameters) {

		boolean isValid = true;
		switch (command) {
			case "추첨 등록" -> isValid = (parameters.size() == 1) && (parameters.get(0).size() == 2);
			case "추첨 수정" -> isValid = (parameters.size() == 2) && (parameters.get(0).size() == 1) && (parameters.get(1).size() == 2);
			case "추첨 삭제" -> isValid = (parameters.size() == 1) && (parameters.get(0).size() == 1);
		    case "일단위 일정 조회" -> isValid = (parameters.size() == 1) && (parameters.get(0).size() == 3);
            case "주단위 일정 조회" -> {
	            int weekNth = Integer.parseInt(parameters.get(0).get(2));
	            isValid = (parameters.size() == 1) && (parameters.get(0).size() == 3) && (weekNth <= 4) && (weekNth >= 0);
            }
			case "추첨", "추첨 리스트 조회" -> isValid = (parameters.isEmpty());
		}

		if (!isValid) {
			throw new CustomUndefinedCommand("잘못된 명령어 입니다.");
		}

		return new Params(command, parameters);
	}

	@Getter
	public static class FormatValidate {
		private final int openCount;
		private final int closeCount;
		private final Params validParamCnt;

		private FormatValidate(int openCount, int closeCount, Params validParamCnt) {
			this.openCount = openCount;
			this.closeCount = closeCount;
			this.validParamCnt = validParamCnt;
		}
	}

	public FormatValidate validate(String requestData) {
		boolean flag = false;
		int openCount = 0;
		int closeCount = 0;
		String command = null;
		List<List<String>> parameters = new ArrayList<>();
		Params validParamCnt;

		int prePos = 0;

		for (int i = 0; i < requestData.length(); i++) {
			char c = requestData.charAt(i);

			if (c == '[') {
				if (flag) {
					throw new CustomUndefinedCommand("중첩된 대괄호는 허용되지 않습니다");
				}
				flag = true;
				openCount++;
				prePos = i;
			} else if (c == ']') {
				if (!flag) {
					throw new CustomUndefinedCommand("닫는 대괄호가 열린 대괄호보다 먼저 나왔습니다");
				}
				flag = false;
				closeCount++;

				String data = requestData.substring(prePos + 1, i);

				if (command == null) {
					command = data;
				} else {
					List<String> list = Arrays.asList(data.split(","));

					parameters.add(list);
				}
			}
		}

		if (flag) {
			throw new CustomUndefinedCommand("닫히지 않은 대괄호가 있습니다");
		}

		if (command == null) throw new CustomUndefinedCommand("명령어가 비어있습니다.");

		validParamCnt = validParamCnt(command, parameters);
		return new FormatValidate(openCount, closeCount, validParamCnt);
	}
}
