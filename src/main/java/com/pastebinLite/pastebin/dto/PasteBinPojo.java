package com.pastebinLite.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteBinPojo {
	private String id;
	private String pasteText;
	private Long createdAt;
	private Long expiresAt;
	private Integer maxViews;
	private Integer viewed = 0;
}
