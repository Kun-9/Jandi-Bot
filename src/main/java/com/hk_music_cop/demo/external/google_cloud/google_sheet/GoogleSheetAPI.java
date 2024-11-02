package com.hk_music_cop.demo.external.google_cloud.google_sheet;


import java.io.IOException;
import java.util.List;

public interface GoogleSheetAPI {

	/**
	 * 구글시트에서 가져온 데이터를 파싱
	 * ',' '\n' 공백 칸 등을 삭제
	 * @param sheetName
	 * @param startCode
	 * @param endCode
	 * @param isDay
	 * @return 정제된 날짜별 Todo List
	 */
	List<List<String>> getSheetDataParse(String sheetName, String startCode, String endCode, Boolean isDay);
}
