package com.pastebinLite.pastebin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responses {
	@JsonProperty
	private boolean status;
	@JsonProperty
	private boolean ok;
	@JsonProperty
	private PasteUrlResponse pasteUrlResponse;
	
	public Responses(boolean ok) {
		this.ok = ok;
	}
	public Responses(PasteUrlResponse urlAndId) {
		this.pasteUrlResponse = urlAndId;
	}
}
