package com.pastebinLite.pastebin.services;

import org.springframework.stereotype.Service;

import com.pastebinLite.pastebin.dto.FetchResponse;
import com.pastebinLite.pastebin.dto.PasteBinPojo;

@Service
public interface PasteBinService {

  public PasteBinPojo insertingPaste(PasteBinPojo data);

  public FetchResponse fetchPaste(String id);

  public  String getContent(String id);

}
