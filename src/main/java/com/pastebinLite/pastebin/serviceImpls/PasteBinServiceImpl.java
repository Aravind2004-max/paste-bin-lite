package com.pastebinLite.pastebin.serviceImpls;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pastebinLite.pastebin.dao.PasteBinRepo;
import com.pastebinLite.pastebin.dto.FetchResponse;
import com.pastebinLite.pastebin.dto.PasteBinPojo;
import com.pastebinLite.pastebin.services.PasteBinService;

@Component
public class PasteBinServiceImpl implements PasteBinService {

	@Autowired
	private PasteBinRepo repo;
	
	Logger log = LoggerFactory.getLogger(PasteBinServiceImpl.class);
	
	@Override
	public PasteBinPojo insertingPaste(PasteBinPojo data) {
		
		if (data == null) {
			log.error("insertingPaste: data is null");
			return null;
		}
		String pasteText = data.getPasteText();
		if (pasteText == null || pasteText.trim().isEmpty()) {
			log.error("insertingPaste: pasteText is null or empty");
			return null;
		}
		if (data.getExpiresAt() == null) {
			data.setExpiresAt(1l);
		}
		if (data.getMaxViews() == null) {
			data.setMaxViews(100000);
		}
		String id = UUID.randomUUID().toString().substring(0,8);
		data.setId(id);
		data.setCreatedAt(System.currentTimeMillis());
		data.setViewed(0);
		log.info("insertingPaste: id={}, pasteText length={}, expiresAt={}, maxViews={}", id, pasteText.length(), data.getExpiresAt(), data.getMaxViews());
		PasteBinPojo result = repo.insertingPaste(data);
		if (result == null) {
			log.error("insertingPaste: database insert failed, result is null");
		}
		return result;
	}

	@Override
	public FetchResponse fetchPaste(String id) {
		if (id == null || id.trim().isEmpty()) {
			return new FetchResponse("Id cant be empty!", 0, 0);
		}
		PasteBinPojo viewsChanger = new PasteBinPojo();
		int views = 1;
		viewsChanger.setViewed(views++);
		return repo.fetchPaste(id);
	}

	@Override
	public String getContent(String id) {
		PasteBinPojo fliter = repo.getContent(id);
		if (fliter == null) {
			return "Not Found";
		}
		String res = null;
		Long expiresAt = fliter.getExpiresAt();
		if (expiresAt != null) {
			Instant expiry = Instant.ofEpochMilli(expiresAt);
			log.info("expiry: " + expiry + " currentime: " + LocalDateTime.now());
			if (expiry.isBefore(Instant.now())) {
				return "Link expired!";
			}
		}
		Integer maxViews = fliter.getMaxViews();
		Integer viewed = fliter.getViewed();
		if (maxViews != null && viewed != null && viewed > maxViews) {
			return "Link view limit reached";
		}
		res = fliter.getPasteText();
		return res == null ? "" : res;
	}

}
