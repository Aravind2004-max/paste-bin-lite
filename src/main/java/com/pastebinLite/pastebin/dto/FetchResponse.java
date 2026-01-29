package com.pastebinLite.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchResponse {

	private String content;
	private int maxViews;
	private int expiresAt;
}
