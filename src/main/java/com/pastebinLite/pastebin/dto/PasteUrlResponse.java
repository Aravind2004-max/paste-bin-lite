package com.pastebinLite.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteUrlResponse {
	private String id;
	private String url;
	
 public PasteUrlResponse(PasteBinPojo data) {
	if (data != null && data.getId() != null) {
		id = data.getId();
		url = "http://localhost:8080/api/p/" + id;
	} else {
		id = null;
		url = null;
	}
 }
}
