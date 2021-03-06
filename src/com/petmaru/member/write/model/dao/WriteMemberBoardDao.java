package com.petmaru.member.write.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.petmaru.common.DBCPTemplate;
import com.petmaru.member.write.model.vo.WriteMemberBoardVo;

public class WriteMemberBoardDao {

	// 글 쓰기
	public int boardwrite(Connection conn, WriteMemberBoardVo vo) {
		int result = -1;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "INSERT INTO BOARD" + "(board_no, board_title, board_content, board_writer)"
				+ "VALUES (seq_board.NEXTVAL, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getBoard_title());
			pstmt.setString(2, vo.getBoard_content());
			pstmt.setString(3, vo.getBoard_writer());
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = 1; // 로그인 성공 : 1
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 글 목록
	public WriteMemberBoardVo getBoard(Connection conn, int board_no) {
		WriteMemberBoardVo vo = null;

		String sql = "SELECT board_no, board_title, board_content, board_date, board_writer"
				+ "  FROM board WHERE board_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				vo = new WriteMemberBoardVo();
				vo.setBoard_no(rset.getInt("BOARD_NO"));
				vo.setBoard_title(rset.getString("BOARD_TITLE"));
				vo.setBoard_content(rset.getString("BOARD_CONTENT"));
				vo.setBoard_date(rset.getDate("BOARD_DATE"));
				vo.setBoard_writer(rset.getString("BOARD_WRITER"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(rset);
			DBCPTemplate.close(pstmt);
		}
		return vo;
	}

	public int getBoardCount(Connection conn) {
		int result = 0;
		String sql = "SELECT COUNT(board_no) FROM board";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(rset);
			DBCPTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<WriteMemberBoardVo> selectBoardList(Connection conn, int start, int end) {
		ArrayList<WriteMemberBoardVo> volist = null;

		String sql = "SELECT * from "
				+ " (select rownum rnum, t1.* from (SELECT * FROM board ORDER BY board_no DESC) t1 ) t2"
				+ " WHERE rnum BETWEEN ? AND ?";

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			volist = new ArrayList<WriteMemberBoardVo>();
			if (rset.next()) {
				do {
					WriteMemberBoardVo vo = new WriteMemberBoardVo();
					vo.setBoard_no(rset.getInt("BOARD_NO"));
					vo.setBoard_title(rset.getString("BOARD_TITLE"));
					vo.setBoard_content(rset.getString("BOARD_CONTENT"));
					vo.setBoard_date(rset.getDate("BOARD_DATE"));
					vo.setBoard_writer(rset.getString("BOARD_WRITER"));
					volist.add(vo);
				} while (rset.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(rset);
			DBCPTemplate.close(pstmt);
		}
		return volist;

	}
	
	public int deleteBoard(Connection conn, int board_no) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "DELETE FROM board WHERE board_no = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(rset);
			DBCPTemplate.close(pstmt);
		}
		return result;
	}
	
	public int reWriteBoard(Connection conn, WriteMemberBoardVo vo) {
		int result =0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "UPDATE board SET board_title = ?, board_content = ? where board_no = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getBoard_title());
			pstmt.setString(2, vo.getBoard_content());
			pstmt.setInt(3, vo.getBoard_no());
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = 1; // 성공
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}