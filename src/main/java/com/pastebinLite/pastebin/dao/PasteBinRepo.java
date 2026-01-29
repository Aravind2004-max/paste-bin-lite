package com.pastebinLite.pastebin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.pastebinLite.pastebin.dto.FetchResponse;
import com.pastebinLite.pastebin.dto.PasteBinPojo;
import com.pastebinLite.pastebin.jdbc.Jdbc;

@Repository
public class PasteBinRepo {

	public PasteBinPojo insertingPaste(PasteBinPojo data) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		PasteBinPojo res = null;
		try {
			if(con == null) {
				con = Jdbc.getConnection();
			}
			sql = "insert into pastebin values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1,data.getId());
			ps.setString(2, data.getPasteText());
			ps.setTimestamp(3, new Timestamp(data.getCreatedAt()));
			long expiry = data.getCreatedAt() + data.getExpiresAt() * 60 * 1000;
			ps.setTimestamp(4, new Timestamp(expiry));
			ps.setInt(5,data.getMaxViews());
			ps.setInt(6,data.getViewed());
			
			int success = ps.executeUpdate();
			if(success > 0) {
				res = new PasteBinPojo();
				res.setId(data.getId());
			}
		}catch(Exception e) {
			System.out.println("Error on insert: "+e.getMessage());
		}finally {
			if(con != null || ps != null) {
				try {
					con = null;
					ps = null;
				}catch(Exception e) {
					System.out.println("Error on closing connection: "+e.getMessage());
				}
			}
			sql = null;
		}
		return res;
	}

	public FetchResponse fetchPaste(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		FetchResponse res = null;
		try {
			if(con == null) {
				con = Jdbc.getConnection();
			}
			sql = "select content,maxviews,expiry from pastebin where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
			rs = ps.executeQuery();
			if(rs.next()) {
				res = new FetchResponse();
				res.setContent(rs.getString(1));
				res.setMaxViews(rs.getInt(2));
				res.setExpiresAt(rs.getInt(3));
			}
			
		}catch(Exception e) {
			System.out.println("Error on fetching "+e.getMessage());
		}finally {
			if(con != null || ps != null) {
				try {
					con = null;
					ps = null;
				}catch(Exception e) {
					System.out.println("Error on closing fetch connection: "+e.getMessage());
				}
			}
			sql = null;
		}
		return res;
	}

	public PasteBinPojo getContent(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		PasteBinPojo res = null;
		try {
			if(con == null) {
				con = Jdbc.getConnection();
			}
			sql = "select * from pastebin where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				res = new PasteBinPojo();
				res.setId(rs.getString(1));
				res.setPasteText(rs.getString(2));
				Timestamp t = rs.getTimestamp(3);
				Timestamp t1 = rs.getTimestamp(4);
				res.setCreatedAt(t.getTime());
				res.setExpiresAt(t1.getTime());
				res.setMaxViews(rs.getInt(5));
				res.setViewed(rs.getInt(6));
			}
			
		}catch(Exception e) {
			System.out.println("Error on fetching "+e.getMessage());
		}finally {
			if(con != null || ps != null) {
				try {
					con = null;
					ps = null;
				}catch(Exception e) {
					System.out.println("Error on closing fetch connection: "+e.getMessage());
				}
			}
			sql = null;
		}
		return res;
	}
	
	

}
