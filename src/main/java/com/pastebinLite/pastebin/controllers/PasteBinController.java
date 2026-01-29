package com.pastebinLite.pastebin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastebinLite.pastebin.dto.FetchResponse;
import com.pastebinLite.pastebin.dto.PasteBinPojo;
import com.pastebinLite.pastebin.dto.PasteUrlResponse;
import com.pastebinLite.pastebin.dto.Responses;
import com.pastebinLite.pastebin.services.PasteBinService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PasteBinController {
	
	@Autowired
	private PasteBinService serv;
	
	@GetMapping("/healthz")
	public ResponseEntity<Responses> healthCheck(){
		Responses res = new Responses(true);
		return new ResponseEntity<Responses>(res,HttpStatus.OK);
	}
	
	@PostMapping("/pastes")
	public ResponseEntity<PasteUrlResponse> insertingPaste(@RequestBody PasteBinPojo data){
		ResponseEntity<PasteUrlResponse> res = null;
		PasteUrlResponse responseData = null;
		try {
			PasteBinPojo saved = serv.insertingPaste(data);
			if (saved == null) {
				responseData = new PasteUrlResponse();
				res = new ResponseEntity<PasteUrlResponse>(responseData, HttpStatus.BAD_REQUEST);
			} else {
				responseData = new PasteUrlResponse(saved);
				res = new ResponseEntity<PasteUrlResponse>(responseData, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			responseData = new PasteUrlResponse();
			res = new ResponseEntity<PasteUrlResponse>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@GetMapping("/pastes/{id}")
	public ResponseEntity<FetchResponse> fetchPaste(@PathVariable("id") String id){
		ResponseEntity<FetchResponse> res = null;
		FetchResponse data = serv.fetchPaste(id);
		try {
			res = new ResponseEntity<FetchResponse>(data,HttpStatus.OK);
		}catch(Exception e) {
			res = new ResponseEntity<FetchResponse>(data,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@GetMapping("/p/{id}")
	public ResponseEntity<String> getContent(@PathVariable("id") String id){
		ResponseEntity<String> res = null;
		try {
			res = new ResponseEntity<String>(serv.getContent(id),HttpStatus.OK);
		}catch(Exception e) {
			res = new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
}
