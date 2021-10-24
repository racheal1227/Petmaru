package com.petmaru.member.model.dao;

import static com.petmaru.common.DBCPTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.petmaru.admin.model.vo.AdminVo;
import com.petmaru.common.DBCPTemplate;
import com.petmaru.member.model.vo.MemberVo;

public class MemberDao {

	public MemberDao() {

	}

	public ArrayList<MemberVo> selectList(Connection conn, int start, int end) {

		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		MemberVo m = null;
		String sql = "select * from (select Rownum r, t1.* from (select * from member order by member_id desc) t1 ) where r between ? and ?";
//		String sql = "select member_id,member_name,member_gender,member_phone,member_regdate,member_point from member";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				m = new MemberVo();
				m.setMember_id(rset.getString("member_id")); 
				m.setMember_name(rset.getString("member_name"));
				m.setMember_gender(rset.getString("member_gender"));
				m.setMember_phone(rset.getString("member_phone"));
				m.setMember_point(rset.getInt("member_point"));				
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return list;
	}
	public int getBoardCount(Connection conn) {
		int result = 0;
		String sql = "select count(member_id) from member";
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
	//관리자가 회원전체 출력시 아이디로 검색
		public ArrayList<MemberVo> searchKeywordId(Connection conn, String keyword) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "select * from member where member_id=?";
			ArrayList<MemberVo> list = new ArrayList<MemberVo>();
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, keyword);
				rset = pstmt.executeQuery();
				while (rset.next()) {
					MemberVo m = new MemberVo();
					m.setMember_id(rset.getString("member_id")); 
					m.setMember_name(rset.getString("member_name"));
					m.setMember_gender(rset.getString("member_gender"));
					m.setMember_phone(rset.getString("member_phone"));
					m.setMember_point(rset.getInt("member_point"));		
					list.add(m);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(pstmt);
				close(rset);
			}
			return list;
		}

		//관리자가 회원전체 출력시 이름으로 검색
		public ArrayList<MemberVo> searchKeywordName(Connection conn, String keyword) {

			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String sql = "select * from member where member_name like ?"; // ( ) 없어도 상관없음!!
			ArrayList<MemberVo> list = new ArrayList<MemberVo>();
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				rset = pstmt.executeQuery();
				while (rset.next()) {
					MemberVo m = new MemberVo();
					
					m.setMember_name(rset.getString("member_name")); 

					
					list.add(m);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(pstmt);
				close(rset);
			}
			return list;
		}

		// 회원정보수정
		public int updateMember(Connection conn,String id,String name, String pwd, String phone,String address,String gender,int point,String email) {
			System.out.println("받아온 dao값: " + id+ name + pwd+ phone+  address+ gender+ point+email);
			PreparedStatement pstmt = null;
			String query = "update member set member_name=?,member_pw=?,  member_email=?, member_phone=?, MEMBER_ADDRESS=? where member_id=?";
			int result = -1;
			try {
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1,name);
				pstmt.setString(2,pwd);
				pstmt.setString(3,email);
				pstmt.setString(4,phone);
				pstmt.setString(5,address);
				pstmt.setString(6,id);
				
				result = pstmt.executeUpdate();
				System.out.println("변경된 데이터: " + result);
				System.out.println("변경된 데이터: " + id+ name + pwd+ phone+  address+ gender+ point+email);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBCPTemplate.close(pstmt);
			}
			return result;

		}

	public int deleteMember(Connection conn, String id) {

		int result = 0;
		PreparedStatement pstmt = null;
		String query = "delete from member where member_id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(pstmt);
		}
		return result;
	}

	public int insertMember(Connection conn, MemberVo vo) { // 회원가입
		int result = -1;
		String sqlInsert = "INSERT INTO" + " MEMBER" + " VALUES (?, ?, ?, ?, ?, sysdate, ?, ?, ?)";
		PreparedStatement pstmt = null;
		System.out.println("dao: " + vo);
		try {

			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, vo.getMember_id());
			pstmt.setString(2, vo.getMember_name());
			pstmt.setString(3, vo.getMember_pwd());
			pstmt.setString(4, vo.getMember_phone());
			pstmt.setString(5, vo.getMember_address());
			pstmt.setString(6, vo.getMember_gender());
			pstmt.setInt(7, vo.getMember_point());
			pstmt.setString(8, vo.getMember_email());

			result = pstmt.executeUpdate();
			DBCPTemplate.commit(conn);
			System.out.println(result);
			System.out.println("입력 성공: " + vo);
		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
			DBCPTemplate.close(pstmt);
		}
		return result;
	}

	public int loginmember(Connection conn, String id, String pwd) { // 로그인
		int result = -1;
		String sql = "select MEMBER_PW from MEMBER where MEMBER_ID=? and MEMBER_PW =?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rset = pstmt.executeQuery();
			System.out.println("dao id : " + id);
			System.out.println("dao pwd : " + pwd);

			if (rset.next()) {
				System.out.println("정보 가져오기 성공");
				String dbPwd = rset.getString(1);
				if (pwd.equals(dbPwd)) {
					System.out.println("로그인 성공");
					result = 1;
				} else { // pwd 가 틀림
					System.out.println("로그인 실패");
					result = 0;
				}
			} else {
				System.out.println("해당하는 id가 없다.");
				result = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(pstmt);
		}
		return result;
	}

	
	public int loginadmin(Connection conn, String id, String pwd) { //관리자 로그인
		int result = -1;
		String sql = "select admin_PWD from admin_member where admin_ID=? and admin_PWD =?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rset = pstmt.executeQuery();
			System.out.println("dao id : " + id);
			System.out.println("dao pwd : " + pwd);

			if (rset.next()) {
				System.out.println("정보 가져오기 성공");
				String dbPwd = rset.getString(1);
				if (pwd.equals(dbPwd)) {
					System.out.println("로그인 성공");
					result = 1;
				} else { // pwd 가 틀림
					System.out.println("로그인 실패");
					result = 0;
				}
			} else {
				System.out.println("해당하는 id가 없다.");
				result = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(pstmt);
		}
		return result;
	}
	// 중복확인
	public int checkDuplicatedMember(Connection conn, MemberVo vo) {
		int result = -1;
		String sql = "select member_id 	from member where member_id=?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMember_id());
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = 2; // 기존회원이 있으면
			} else {
				result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
			DBCPTemplate.close(rset);
			DBCPTemplate.close(pstmt);
		}
		return result;
	}

	public String findId(Connection conn, String name, String email) { // 아이디찾기
		int result = -1;
		String id = null;
		String sql = "select MEMBER_ID from MEMBER where MEMBER_NAME=? and MEMBER_Email =?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			rset = pstmt.executeQuery();
			System.out.println("dao name : " + name);
			System.out.println("dao : " + email);

			if (rset.next()) {
				System.out.println("id찾음: " + rset.getString("member_id"));
				id = rset.getString("member_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(pstmt);
			DBCPTemplate.close(rset);
		}
		return id;
	}
	
	public String findPwd(Connection conn, String id, String email) { //비밀번호찾기
		int result = -1;
		String pwd = null;
		String sql = "select MEMBER_Pw from MEMBER where MEMBER_ID=? and MEMBER_Email =?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			rset = pstmt.executeQuery();
			System.out.println("dao name : " + id);
			System.out.println("dao : " + email);

			if (rset.next()) {
				System.out.println("비번찾음: " + rset.getString("member_Pw"));
				pwd = rset.getString("member_Pw");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBCPTemplate.close(pstmt);
			DBCPTemplate.close(rset);
		}
		return pwd;
	}
	//===============================================================================================	
		// 로그인 성공시 회원 정보 전체 session 저장
		// @WebServlet("/login.do")
		public MemberVo loginmember(Connection conn, String id) {
			MemberVo memberVo = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from member where MEMBER_ID = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					memberVo = new MemberVo();
//				    MEMBER_ID VARCHAR2(500) CONSTRAINT MEMBER_ID_PK PRIMARY KEY,
//				    MEMBER_NAME CHAR(20) CONSTRAINT MEMBER_NAME_NN NOT NULL,
//				    MEMBER_PW VARCHAR2(500) CONSTRAINT MEMBER_PW_NN NOT NULL,
//				    MEMBER_PHONE CHAR(15) CONSTRAINT MEMBER_PHONE_NN NOT NULL,
//				    MEMBER_ADDRESS VARCHAR2(500) CONSTRAINT MEMBER_ADDRESS_NN NOT NULL,
//				    MEMBER_EMAIL VARCHAR2(300) CONSTRAINT MEMBER_EMAIL_UK UNIQUE,
//				    MEMBER_GENDER CHAR(1) CONSTRAINT MEMBER_GENDER_CK CHECK(MEMBER_GENDER IN ('M','F')),
//				    MEMBER_POINT NUMBER DEFAULT 0,
//				    MEMBER_REGDATE DATE DEFAULT SYSDATE
					memberVo.setMember_id(rs.getString("MEMBER_ID"));
					memberVo.setMember_name(rs.getString("MEMBER_NAME"));
					memberVo.setMember_pwd(rs.getString("MEMBER_PW"));
					memberVo.setMember_phone(rs.getString("MEMBER_PHONE"));
					memberVo.setMember_address(rs.getString("MEMBER_ADDRESS"));
					memberVo.setMember_email(rs.getString("MEMBER_EMAIL"));
					memberVo.setMember_gender(rs.getString("MEMBER_GENDER"));
					memberVo.setMember_point(rs.getInt("MEMBER_POINT"));
					memberVo.setMember_regdate(rs.getDate("MEMBER_REGDATE"));
					System.out.println("멤버 정보 가져오기 & 담기 성공");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			return memberVo;
		}
		
		public AdminVo loginadmin(Connection conn, String id) {
			AdminVo adminVo = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from admin_member where admin_ID = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					adminVo = new AdminVo();
					adminVo.setAdmin_ID(rs.getString("admin_ID"));
					adminVo.setAdmin_pwd(rs.getString("admin_PWD"));
					adminVo.setAdmin_name(rs.getString("admin_NAME"));
					adminVo.setAdmin_Email(rs.getString("admin_EMAIL"));
					adminVo.setAdmin_phone(rs.getString("admin_PHONE"));
					System.out.println("관리자 정보 가져오기 & 담기 성공");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			return adminVo;
		}
	//======
		
		public MemberVo getMember(Connection conn, String id) { //수정된 회원정보 불러오기
			MemberVo member = new MemberVo();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from member where MEMBER_ID = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member.setMember_id(rs.getString("MEMBER_ID"));
					member.setMember_name(rs.getString("MEMBER_NAME"));
					member.setMember_pwd(rs.getString("MEMBER_PW"));
					member.setMember_phone(rs.getString("MEMBER_PHONE"));
					member.setMember_address(rs.getString("MEMBER_ADDRESS"));
					member.setMember_email(rs.getString("MEMBER_EMAIL"));
					member.setMember_gender(rs.getString("MEMBER_GENDER"));
					member.setMember_point(rs.getInt("MEMBER_POINT"));
					member.setMember_regdate(rs.getDate("MEMBER_REGDATE"));
					System.out.println("변경한dao get:"+ member);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			return member;
		}
}
