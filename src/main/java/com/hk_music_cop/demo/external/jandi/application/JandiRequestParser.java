package com.hk_music_cop.demo.external.jandi.application;

import static com.hk_music_cop.demo.external.jandi.domain.JandiRequestData.*;

public interface JandiRequestParser {
	Params getParameter(String requestData);
}
