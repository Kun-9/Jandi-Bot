package com.hk_music_cop.demo.jandi.application;

import static com.hk_music_cop.demo.jandi.domain.JandiRequestData.*;

public interface JandiRequestParser {
	Params getParameter(String requestData);
}
