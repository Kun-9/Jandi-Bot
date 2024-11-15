package com.hk_music_cop.demo.google_cloud.google_sheet.repository;


import java.util.List;

public interface GoogleSheetAPI {

	/**
	 * 구글시트에서 가져온 데이터를 파싱
	 * ',' '\n' 공백 칸 등을 삭제
	 *
	 * @param sheetName     구글시트 이름 (정확히 일치해야 함)
	 * @param startCode     캘린더의 시작일 좌표 (주)
	 * @param endCode       캘린더의 마지막일 좌표 (주)
	 * @param skipNullDay   null인 날짜 스킵 여부
	 * @return 정제된 날짜별 Todo List
	 */
	List<List<String>> getSheetData(String sheetName, String startCode, String endCode, boolean skipNullDay);

}
