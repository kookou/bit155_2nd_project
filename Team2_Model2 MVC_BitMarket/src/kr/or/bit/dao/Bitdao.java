package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.bit.dto.Admin;
import kr.or.bit.dto.Board;
import kr.or.bit.dto.BoardCt_Join;
import kr.or.bit.dto.BoardUser_Join;
import kr.or.bit.dto.Category;
import kr.or.bit.dto.Notice;
import kr.or.bit.dto.Qna;
import kr.or.bit.dto.QnaNick;
import kr.or.bit.dto.QnaReply;
import kr.or.bit.dto.Reply;
import kr.or.bit.dto.ReplyUser_Join;
import kr.or.bit.dto.User;
import kr.or.bit.utils.ConnectionHelper;
import kr.or.bit.utils.DB_Close;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Bitdao {

	// 회원가입
	// 사원 등록
	// Parameter (id,ename,cotent)
	// 권장: public int insertMemo(memo m){} >> FrameWork 자동화..

	public int joinUser(User user) {
		Connection conn = null;// 추가
		int resultrow = 0;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");// 추가

			String sql = "insert into bitUSER(profile, id, pwd, nick , loc, lat, lon, rtime) values(?,?,?,?,?,?,?, TO_CHAR(SYSDATE,'yyyy/mm/dd hh24:mi:ss'))";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getProfile());
			pstmt.setString(2, user.getId());
			pstmt.setString(3, user.getPwd());
			pstmt.setString(4, user.getNick());
			pstmt.setString(5, user.getLoc());
			pstmt.setString(6, user.getLat());
			pstmt.setString(7, user.getLon());

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultrow;

	}

	// 유저 로그인
	public User getUser(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		User user = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT ID, PWD, NICK FROM BITUSER WHERE ID=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getString(1));
				user.setPwd(rs.getString(2));
				user.setNick(rs.getString(3));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	// 글쓰기
	public int boardWrite(Board board) {

		int result = 0;
		String sql = "";

		ResultSet rs = null;
		Connection conn = null;// 추가
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			sql = "insert into board (bdindex,title,price,content,RTIME,trstate,delstate,count,id,ctcode,img)"
					+ "values(board_bdindex.nextval,?,?,?,sysdate,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, board.getTitle());
			pstmt.setInt(2, board.getPrice());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, "N");
			pstmt.setString(5, "N");
			pstmt.setInt(6, 0);
			pstmt.setString(7, board.getId());
			pstmt.setString(8, board.getCtcode());
			pstmt.setString(9, board.getImg());

			result = pstmt.executeUpdate();
			System.out.println(result);

		} catch (Exception e) {
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 게시글 조회하기 (삭제 유무 추가한 것 아래 있음)
	/*
	 * public List<Board> getBoardList(int cpage, int pagesize) { Connection conn =
	 * null; PreparedStatement pstmt = null; ResultSet rs = null; List<Board>
	 * boardlist = null;
	 * 
	 * try { conn = ConnectionHelper.getConnection("oracle"); String sql =
	 * "select * from " +
	 * "(select rownum rn,bdindex,title,price,content,rtime,trstate,delstate,count "
	 * + ",id,ctcode" + " from (SELECT * FROM board ORDER BY bdindex desc) " +
	 * " where rownum <= ?" + // endrow ") where rn >= ?"; pstmt =
	 * conn.prepareStatement(sql);
	 * 
	 * int start = cpage * pagesize - (pagesize - 1); int end = cpage * pagesize;
	 * 
	 * pstmt.setInt(1, end); pstmt.setInt(2, start);
	 * 
	 * rs = pstmt.executeQuery(); boardlist = new ArrayList<Board>(); while
	 * (rs.next()) { Board board = new Board();
	 * board.setBdindex(rs.getInt("bdindex"));
	 * board.setTitle(rs.getString("title")); board.setPrice(rs.getInt("price"));
	 * board.setContent(rs.getString("content"));
	 * board.setRtime(rs.getString("rtime"));
	 * board.setTrstate(rs.getString("trstate")); boardlist.add(board); }
	 * System.out.println(boardlist.toString()); } catch (Exception e) {
	 * System.out.println(e.getMessage()); } finally { DB_Close.close(rs);
	 * DB_Close.close(pstmt); try { conn.close(); } catch (Exception e2) {
	 * System.out.println(e2.getMessage()); } } return boardlist; }
	 */
	// 게시글 총 건수 구하기
	public int getTotalBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalcount;
	}

	// 관리자 로그인
	public Admin getAdmin(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Admin admin = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT ID, PWD FROM ADMIN WHERE ID=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				admin = new Admin();
				admin.setId(rs.getString(1));
				admin.setPwd(rs.getString(2));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return admin;
	}

	// 공지사항 게시글 목록 조회
	public List<Notice> getNoticeList(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Notice> noticelist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from " + "(select rownum rn, n.* from notice n order by ncindex desc)"
					+ "where rn between ? and ?";
			pstmt = conn.prepareStatement(sql);

			int start = cpage * pagesize - (pagesize - 1);
			int end = cpage * pagesize;

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();
			noticelist = new ArrayList<Notice>();
			while (rs.next()) {
				Notice notice = new Notice();
				notice.setNcindex(rs.getInt("ncindex"));
				notice.setTitle(rs.getString("title"));
				notice.setNccontent(rs.getString("nccontent"));
				notice.setRtime(rs.getString("rtime"));
				notice.setNcstate(rs.getString("ncstate"));
				notice.setAdminid(rs.getString("adminid"));

				noticelist.add(notice);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());

			}
		}
		return noticelist;
	}

	// 공지사항 총 건수 구하기
	public int getTotalNoticeCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from notice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return totalcount;
	}

	// 공지사항 글쓰기
	public int noticeWrite(Notice notice) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "insert into notice(ncindex, title, nccontent, rtime, ncstate,adminid)"
					+ "values(notice_ncindex.nextval,?,?,sysdate,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getNccontent());
			pstmt.setString(3, notice.getNcstate());
			pstmt.setString(4, notice.getAdminid());

			resultrow = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// 공지사항 상세 보기
	public Notice getNoticeByIdx(int ncindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice notice = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from notice where ncindex=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ncindex);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				notice = new Notice();
				notice.setNcindex(rs.getInt("ncindex"));
				notice.setTitle(rs.getString("title"));
				notice.setNccontent(rs.getString("nccontent"));
				notice.setRtime(rs.getString("rtime"));
				notice.setNcstate(rs.getString("ncstate"));
				notice.setAdminid(rs.getString("adminid"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return notice;
	}

	// 카테고리 이름 불러오기
	public List<String> getCategory() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> clist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select ctname from category";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			clist = new ArrayList<String>();

			while (rs.next()) {
				clist.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return clist;
	}

	// 카테고리 번호 가져오기
	public String getCtcode(String ctname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ctcode = "";

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select ctcode from category where ctname=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctname);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				ctcode = rs.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return ctcode;
	}

	// 카테고리 전체 가져오기
	public List<Category> getCategoryList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Category> ctlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select*from category";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ctlist = new ArrayList<Category>();

			while (rs.next()) {
				Category category = new Category();
				category.setCtcode(rs.getString("ctcode"));
				category.setCtname(rs.getString("ctname"));

				ctlist.add(category);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return ctlist;
	}

	// 전체 보드 리스트
	public List<Board> getBoardListAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select bdindex,title,price,content,rtime,trstate,delstate,count,id,ctcode,img from (SELECT * FROM board ORDER BY bdindex desc)";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			boardlist = new ArrayList<Board>();

			while (rs.next()) {

				if (rs.getString("delstate").equals("N")) {
					Board board = new Board();
					board.setBdindex(rs.getInt("bdindex"));
					board.setId(rs.getString("id"));
					board.setRtime(rs.getString("rtime"));
					board.setPrice(rs.getInt("price"));
					board.setTitle(rs.getString("title"));
					board.setCtcode(rs.getString("ctcode"));
					board.setImg(rs.getString("img"));
					board.setTrstate(rs.getString("trstate"));
					board.setCount(rs.getInt("count"));
					boardlist.add(board);

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boardlist;

	}

	    //board 리스트 페이징 
		public List<Board> getBoardList(int cpage, int pagesize){
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Board> boardlist = null;
			
			
			try {
				conn = ConnectionHelper.getConnection("oracle");
				String sql = "select * from " +
	    				"(select rownum rn,bdindex,title,price,content,rtime,trstate,delstate,count " +
	    				",id,ctcode,img" +
	    				" from (SELECT * FROM board ORDER BY bdindex desc) "+
	    				" where rownum <= ?" + 
	    	") where rn >= ?";
				
				pstmt = conn.prepareStatement(sql);
				// 공식같은 로직
				int start = cpage * pagesize - (pagesize - 1); 
				int end = cpage * pagesize;
				
				pstmt.setInt(1, end);
				pstmt.setInt(2, start);
				
				rs = pstmt.executeQuery();
				boardlist = new ArrayList<Board>();
				
				
				while (rs.next()) {
					
					if(rs.getString("delstate").equals("N")) {
						Board board = new Board();
						board.setBdindex(rs.getInt("bdindex"));
						board.setId(rs.getString("id"));
						board.setRtime(rs.getString("rtime"));
						board.setPrice(rs.getInt("price"));
						board.setTitle(rs.getString("title"));
						board.setCtcode(rs.getString("ctcode"));
						board.setImg(rs.getString("img"));
						boardlist.add(board);
					
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				DB_Close.close(rs);
				DB_Close.close(pstmt);
				try {
					conn.close(); // 받환하기
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return boardlist;
			
		}

	// 게시글 가져오기 다양한 방식으로 전체 이미지 게시판 리스트 가져오기
	public List<BoardCt_Join> getBoardSearchList(int cpage, int pagesize, String searchContent, String ctname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardCt_Join> boardlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT * from "
					+ " (select rownum rn, bdindex, title,price,content,rtime,trstate,delstate,count,id,img"
					+ " ,ctname,ctcode FROM (SELECT b.bdindex,b.title,b.price,b.content,b.rtime,b.trstate,b.delstate,b.count,b.id,b.ctcode,c.ctname,b.img FROM board b JOIN category c ON b.ctcode = c.ctcode order by b.bdindex desc)"
					+ " where rownum <=?" + " ) where rn >= ?";

			String sql_search = "SELECT * from "
					+ " (select rownum rn, bdindex, title,price,content,rtime,trstate,delstate,count,id,img"
					+ " ,ctname,ctcode FROM (SELECT b.bdindex,b.title,b.price,b.content,b.rtime,b.trstate,b.delstate,b.count,b.id,b.ctcode,c.ctname,b.img "
					+ "FROM board b JOIN category c ON b.ctcode = c.ctcode where title like '%' || ? || '%' or content like '%' || ? || '%' order by b.bdindex desc)"
					+ "  where rownum <=?" + "  ) where rn >= ?";

			String sql_category_select = "SELECT * from "
					+ " (select rownum rn, bdindex, title,price,content,rtime,trstate,delstate,count,id,img "
					+ " ,ctname,ctcode FROM (SELECT b.bdindex,b.title,b.price,b.content,b.rtime,b.trstate,b.delstate,b.count,b.id,b.ctcode,c.ctname,b.img FROM board b JOIN category c ON b.ctcode = c.ctcode order by b.bdindex desc)"
					+ " where rownum <=? and ctname = ?" + " ) where rn >= ?";

			int start = cpage * pagesize - (pagesize - 1);
			int end = cpage * pagesize;

			if (searchContent != null && !(searchContent.equals(""))) {
				pstmt = conn.prepareStatement(sql_search);
				pstmt.setString(1, searchContent);
				pstmt.setString(2, searchContent);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);

			} else if (ctname != null && !(ctname.equals("")) && !(ctname.equals("선택없음"))) {
				pstmt = conn.prepareStatement(sql_category_select);
				pstmt.setInt(1, end);
				pstmt.setString(2, ctname);
				pstmt.setInt(3, start);

			} else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, end);
				pstmt.setInt(2, start);
			}

			rs = pstmt.executeQuery();
			boardlist = new ArrayList<BoardCt_Join>();
			while (rs.next()) {
				
				if(rs.getString("delstate").equals("N")) {
				
					BoardCt_Join board = new BoardCt_Join();
					board.setBdindex(rs.getInt("bdindex"));
					board.setTitle(rs.getString("title"));
					board.setPrice(rs.getInt("price"));
					board.setContent(rs.getString("content"));
					board.setRtime(rs.getString("rtime"));
					board.setTrstate(rs.getString("trstate"));
					board.setDelstate(rs.getString("delstate"));
					board.setId(rs.getString("id"));
					board.setCount(rs.getInt("count"));
					board.setCtcode(rs.getString("ctcode"));
					board.setImg(rs.getString("img"));
					board.setId(rs.getString("id"));
					board.setCtname(rs.getString("ctname"));
					boardlist.add(board);
				}
			}
			System.out.println("ct탄 리스트 : " + boardlist.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return boardlist;
	}
	
	// 게시글 거리순으로 정렬해서 가져오기
	public List<BoardUser_Join> getBoardDistList(int cpage, int pagesize, String lat, String lon) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardUser_Join> boardlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT * from "
					+ " (select rownum rn, bdindex, title,price,content,rtime,trstate,delstate,count,id,img, ctcode, dist"
					+ " FROM (SELECT b.*, round((google_distance(lat,lon, ?, ?)),0) dist FROM board b JOIN bituser u ON b.id = u.id order by dist)"
					+ " where rownum <=? ) where rn >= ?";


			int start = cpage * pagesize - (pagesize - 1);
			int end = cpage * pagesize;


				pstmt = conn.prepareStatement(sql);
				pstmt.setDouble(1, Double.parseDouble(lat));
				pstmt.setDouble(2, Double.parseDouble(lon));
				pstmt.setInt(3, 50);   //잠시 테스트 위해 수정 원래 end
				pstmt.setInt(4, 1);  //잠시 테스트 위해 수정 원래 start


			rs = pstmt.executeQuery();
			boardlist = new ArrayList<BoardUser_Join>();
			while (rs.next()) {
				
				if(rs.getString("delstate").equals("N")) {
				
					BoardUser_Join board = new BoardUser_Join();
					board.setBdindex(rs.getInt("bdindex"));
					board.setTitle(rs.getString("title"));
					board.setPrice(rs.getInt("price"));
					board.setContent(rs.getString("content"));
					board.setRtime(rs.getString("rtime"));
					board.setTrstate(rs.getString("trstate"));
					board.setDelstate(rs.getString("delstate"));
					board.setId(rs.getString("id"));
					board.setCount(rs.getInt("count"));
					board.setCtcode(rs.getString("ctcode"));
					board.setImg(rs.getString("img"));
					board.setId(rs.getString("id"));
					board.setDist(rs.getString("dist"));
					boardlist.add(board);
					
					System.out.println("구한 거리=" +rs.getString("dist"));
				}
			}
			System.out.println("boardUserList탄 리스트 : " + boardlist.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return boardlist;
	}
	
	//리스트 최신순으로 가져오기
			public List<Board> getBoardRtimeList(int cpage, int pagesize){
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<Board> boardlist = null;
				
				
				try {
					conn = ConnectionHelper.getConnection("oracle");
					String sql = "select * from " +
		    				"(select rownum rn,bdindex,title,price,content,rtime,trstate,delstate,count " +
		    				",id,ctcode,img" +
		    				" from (SELECT * FROM board ORDER BY rtime desc) "+
		    				" where rownum <= ?" + 
		    				") where rn >= ?";
					
					pstmt = conn.prepareStatement(sql);
					// 공식같은 로직
					int start = cpage * pagesize - (pagesize - 1); 
					int end = cpage * pagesize;
					
					pstmt.setInt(1, 30);  //테스트 위해 잠시 수정 원래 end
					pstmt.setInt(2, start);
					
					rs = pstmt.executeQuery();
					boardlist = new ArrayList<Board>();
					
					
					while (rs.next()) {
						
						if(rs.getString("delstate").equals("N")) {
							Board board = new Board();
							board.setBdindex(rs.getInt("bdindex"));
							board.setId(rs.getString("id"));
							board.setRtime(rs.getString("rtime"));
							board.setPrice(rs.getInt("price"));
							board.setTitle(rs.getString("title"));
							board.setCtcode(rs.getString("ctcode"));
							board.setImg(rs.getString("img"));
							boardlist.add(board);
						
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}finally {
					DB_Close.close(rs);
					DB_Close.close(pstmt);
					try {
						conn.close(); // 받환하기
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return boardlist;
				
			}
	
	

	// 회원 리스트 조회 (회원의 위치 조회용?)
	public List<User> getUserList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userlist = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from bituser";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			userlist = new ArrayList<User>();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getString("id"));
				
				//동이나 로까지만 주소 받아오기
				String str = rs.getString("loc");
				String[] array = str.split(" ");
				str = array[0]+" "+array[1]+" "+array[2];
				user.setLoc(str);
				
				user.setNick(rs.getString("nick"));
				user.setProfile(rs.getString("profile"));
			

				userlist.add(user);
			}
		} catch (Exception e) {
			System.out.println("오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userlist;
	}

	// board list count 삭제유무 N인것만
	public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int boardcount = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from board where delstate ='N'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardcount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return boardcount;
	}

	// 상세페이지
	public Board getBoardByIdx(int bdindex) throws SQLException {
		Connection conn = ConnectionHelper.getConnection("oracle");

		PreparedStatement pstmt = null;
		String sql = "select * from board where bdindex=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bdindex);
		ResultSet rs = pstmt.executeQuery();
		Board board = new Board();

		if (rs.next()) {
			board.setBdindex(rs.getInt("bdindex"));
			board.setCtcode(rs.getString("ctcode"));
			board.setTitle(rs.getString("title"));
			board.setPrice(rs.getInt("price"));
			board.setContent(rs.getString("content"));
			board.setRtime(rs.getString("rtime"));
			board.setTrstate(rs.getString("trstate"));
			board.setDelstate(rs.getString("delstate"));
			board.setCount(rs.getInt("count"));
			board.setId(rs.getString("id"));
			board.setImg(rs.getString("img"));

		}
		DB_Close.close(rs);
		DB_Close.close(pstmt);
		conn.close(); // 반환하기

		return board;
	}
	//상세 페이지 게시글 조회수 증가
	public boolean getBoardReadNum(int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "update board set count = count + 1 where bdindex= ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bdindex);

			int resultrow = pstmt.executeUpdate();
			if (resultrow > 0) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 게시물 수정
	public int updateBoard(Board board) {
		Connection conn = null;
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			System.out.println("board.getImg=" +board.getImg());
			if(board.getImg().equals("")) {
				String sql = "update board set title=?,price=?,content=?,rtime=sysdate,trstate=?,delstate=?,id=?,ctcode=?where bdindex=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setInt(2, board.getPrice());
				pstmt.setString(3, board.getContent());
				pstmt.setString(4, "N");
				pstmt.setString(5, "N");
				pstmt.setString(6, board.getId());
				pstmt.setString(7, board.getCtcode());
				pstmt.setInt(8, board.getBdindex());
			}else {
				String sql = "update board set title=?,price=?,content=?,rtime=sysdate,trstate=?,delstate=?,id=?,ctcode=?,img=? where bdindex=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setInt(2, board.getPrice());
				pstmt.setString(3, board.getContent());
				pstmt.setString(4, "N");
				pstmt.setString(5, "N");
				pstmt.setString(6, board.getId());
				pstmt.setString(7, board.getCtcode());
				pstmt.setString(8, board.getImg());
				pstmt.setInt(9, board.getBdindex());
			}

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 게시글 삭제 유무 변경
	public int getDeleteByIdx(int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "update board set DELSTATE=? where bdindex=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "Y");
			pstmt.setInt(2, bdindex);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return result;
	}

	// 회원 정보 수정
	public int updateUser(User user) {
		Connection conn = null;
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "update bituser set pwd=?,loc=?,nick=?,profile=? where id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getLoc());
			pstmt.setString(3, user.getNick());
			pstmt.setString(4, user.getProfile());
			pstmt.setString(5, user.getId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// id로 작성한 게시글 불러오기
	public List<Board> getBoardById(String id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> idboardlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from board where id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			idboardlist = new ArrayList<Board>();

			while (rs.next()) {
				if (rs.getString("delstate").equals("N")) {
					Board board = new Board();
					board.setBdindex(rs.getInt("bdindex"));
					board.setTitle(rs.getString("title"));
					board.setPrice(rs.getInt("price"));
					board.setContent(rs.getString("content"));
					board.setRtime(rs.getString("rtime"));
					board.setTrstate(rs.getString("trstate"));
					board.setDelstate(rs.getString("delstate"));
					board.setCount(rs.getInt("count"));
					board.setId(rs.getString("id"));
					board.setCtcode(rs.getString("ctcode"));
					board.setImg(rs.getString("img"));

					idboardlist.add(board);

				}
			}
		} catch (Exception e) {
			System.out.println("오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return idboardlist;
	}

	// id로 작성한 댓글 불러오기
	public List<Reply> getReplyById(String id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reply> idreplylist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from Reply where id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			idreplylist = new ArrayList<Reply>();
			System.out.println("id 있니?" + id);

			while (rs.next()) {
				if (rs.getString("delstate").equals("N")) {
					Reply reply = new Reply();
					reply.setRpindex(rs.getInt("rpindex"));
					reply.setContent(rs.getString("content"));
					reply.setScstate(rs.getString("scstate"));
					reply.setDelstate(rs.getString("delstate"));
					reply.setTrstate(rs.getString("trstate"));
					reply.setRtime(rs.getString("rtime"));
					reply.setRefer(rs.getInt("refer"));
					reply.setDepth(rs.getInt("depth"));
					reply.setStep(rs.getInt("step"));
					reply.setId(rs.getString("id"));
					reply.setBdindex(rs.getInt("bdindex"));

					idreplylist.add(reply);

				}
			}

		} catch (Exception e) {
			System.out.println("오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("아 젠장" + idreplylist);
		return idreplylist;
	}

	// 글번호로 댓글 유저 id 불러오기
	public List<Reply> getReplyByBdindex(int bdindex) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reply> idbdindexlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select id from Reply where bdindex=?";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			idbdindexlist = new ArrayList<Reply>();

			while (rs.next()) {
				if (rs.getString("delstate").equals("N")) {
					Reply reply = new Reply();
					reply.setRpindex(rs.getInt("id"));
					reply.setTrstate(rs.getString("trstate"));
					reply.setBdindex(rs.getInt("dbindex"));
					idbdindexlist.add(reply);
				}
			}
		} catch (Exception e) {
			System.out.println("오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idbdindexlist;
	}

	// 카테고리별로 셀렉트
	public List<Board> getBoardListByCategory(String ctcode) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlistbycategory = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT * from board where ctcode =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctcode);

			rs = pstmt.executeQuery();
			boardlistbycategory = new ArrayList<Board>();

			while (rs.next()) {
				Board board = new Board();
				board.setBdindex(rs.getInt("bdindex"));
				board.setId(rs.getString("id"));
				board.setRtime(rs.getString("rtime"));
				board.setPrice(rs.getInt("price"));
				board.setTitle(rs.getString("title"));
				board.setCtcode(rs.getString("ctcode"));
				board.setImg(rs.getString("img"));

				boardlistbycategory.add(board);
			}

		} catch (Exception e) {
			System.out.println("오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boardlistbycategory;
	}

	// 댓글 쓰기
	public int writeReply(Reply reply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "insert into reply(rpindex,content,scstate,delstate,trstate,rtime,depth,id,bdindex,refer,step) " + 
							"values(reply_rpindex.nextval,?,?,'N','N',sysdate,0,?,?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getContent());
			pstmt.setString(2, reply.getScstate());
			pstmt.setString(3, reply.getId());
			pstmt.setInt(4, reply.getBdindex());

			int refer = getReplyMaxRefer();
			pstmt.setInt(5, refer+1);

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// max step 구하기
	public int maxStepByRefer(int refer, int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int step = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select nvl(max(step),0) from reply where refer=? and bdindex=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, refer);
			pstmt.setInt(2, bdindex);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				step = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		DB_Close.close(rs);
		DB_Close.close(pstmt);
		try {
			conn.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}

		return step;
	}

	// 대댓글 쓰기
	public int reWriteReply(Reply reply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultrow = 0;
		try {

			int bdindex = reply.getBdindex();
			int rpindex = reply.getRpindex();
			System.out.println("대댓bd" + bdindex);
			System.out.println("대댓rp" + rpindex);
			conn = ConnectionHelper.getConnection("oracle");

			// refer, depth, step 조회
			String sql_refer_depth_step = "select refer, depth from reply where bdindex = ? and rpindex = ?";

			// rewrite
			String sql_rewrite = "insert into reply(rpindex,content,scstate,delstate,trstate,rtime,id,bdindex,depth,refer,step)"
					+ "values(reply_rpindex.nextval,?,?,'N','N',sysdate,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql_refer_depth_step);
			pstmt.setInt(1, bdindex);
			pstmt.setInt(2, rpindex);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int refer = rs.getInt("refer");
				int depth = rs.getInt("depth");
				int step = maxStepByRefer(refer, bdindex);

				if (depth < 1) {
					depth++;
				}

				// 닫아주고 실행 (rewrite)
				DB_Close.close(pstmt);
				pstmt = conn.prepareStatement(sql_rewrite);
				pstmt.setString(1, reply.getContent());
				pstmt.setString(2, reply.getScstate());
				pstmt.setString(3, reply.getId());
				pstmt.setInt(4, bdindex);

				pstmt.setInt(5, depth);

				pstmt.setInt(6, refer);
				pstmt.setInt(7, step + 1);

				int row = pstmt.executeUpdate();

				if (row > 0) {
					resultrow = row;
				} else {
					resultrow = -1;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;

	}

	// 댓글 조회하기
	public List<ReplyUser_Join> getReplyList(int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReplyUser_Join> replylist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT * FROM (SELECT r.*, u.nick FROM reply r JOIN bituser u ON r.id = u.id order by refer asc, step asc)  where bdindex=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bdindex);

			rs = pstmt.executeQuery();

			replylist = new ArrayList<ReplyUser_Join>();

			while (rs.next()) {
				ReplyUser_Join reply = new ReplyUser_Join();
				reply.setRpindex(rs.getInt("rpindex"));
				reply.setId(rs.getString("id"));
				reply.setContent(rs.getString("content"));
				reply.setRtime(rs.getString("rtime"));
				reply.setScstate(rs.getString("scstate"));
				reply.setDelstate(rs.getString("delstate"));
				reply.setBdindex(rs.getInt("bdindex"));
				reply.setDepth(rs.getInt("depth"));
				reply.setRefer(rs.getInt("refer"));
				reply.setStep(rs.getInt("step"));
				reply.setTrstate(rs.getString("trstate"));
				reply.setNick(rs.getString("nick"));
				replylist.add(reply);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return replylist;
	}

	// 댓글 삭제하기 (delstate를 y로 바꿈)
	public int deleteReply(int rpindex, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "update reply set delstate='Y' where rpindex=? and id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rpindex);
			pstmt.setString(2, id);

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// 댓글 수정하기
	public int updateReply(int rpindex, String id, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "update reply set content=? where rpindex=? and id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, rpindex);
			pstmt.setString(3, id);

			resultrow = pstmt.executeUpdate();
			System.out.println(resultrow);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// 댓글 refer 구하기
	public int getReplyMaxRefer() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int refer_max = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select nvl(max(refer),0) from reply";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				refer_max = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return refer_max;
	}

	// 구매 완료 체크 위해서 댓글 닉네임 가져오기
	public List<ReplyUser_Join> getNickList(int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReplyUser_Join> nickIdTrlist = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "SELECT distinct nick,r.id,trstate FROM reply r JOIN bituser u ON r.id = u.id  where bdindex = ? order by u.nick asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bdindex);
			rs = pstmt.executeQuery();
			nickIdTrlist = new ArrayList<ReplyUser_Join>();
			while (rs.next()) {
				ReplyUser_Join nick = new ReplyUser_Join();
				nick.setNick(rs.getString("nick"));
				nick.setId(rs.getString(2));
				nick.setTrstate(rs.getString(3));
				nickIdTrlist.add(nick);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return nickIdTrlist;
	}

	// 댓글 거래 상태 업데이트
	public int updateTrstate(String boardId, String oldRepId, String newRepId, int bdindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql_update_board = "update board set trstate='Y' where bdindex= ? and id = ?";

			String sql_update_reply = "update reply set trstate ='Y' where bdindex=? and id = ?";

			String sql_update_reply_oldnew = "UPDATE reply " + "SET trstate =" + "CASE " + "WHEN id = ? THEN 'N' "
					+ "WHEN id = ? THEN 'Y' " + "ELSE 'N' " + "END where bdindex = ?";

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql_update_board);
			pstmt.setInt(1, bdindex);
			pstmt.setString(2, boardId);
			pstmt.executeUpdate();
			DB_Close.close(pstmt);

			if (oldRepId == null) {
				pstmt = conn.prepareStatement(sql_update_reply);
				pstmt.setInt(1, bdindex);
				pstmt.setString(2, newRepId);
			} else {
				pstmt = conn.prepareStatement(sql_update_reply_oldnew);
				pstmt.setString(1, oldRepId);
				pstmt.setString(2, newRepId);
				pstmt.setInt(3, bdindex);
			}

			resultrow = pstmt.executeUpdate();
			if (resultrow > 0) {
				conn.commit();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}

		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// 공지사항 수정
	public int updateNotice(Notice notice) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		int resultrow = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "update notice set ncstate= ?, title= ?, nccontent= ? where ncindex= ? and adminid= ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice.getNcstate());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getNccontent());
			pstmt.setInt(4, notice.getNcindex());
			pstmt.setString(5, notice.getAdminid());

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return resultrow;
	}

	// 공지사항 삭제
	public int deleteNotice(int ncindex, String adminid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultrow = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			// ID검증
			String sql_adminid = "select adminid from notice where ncindex= ? and adminid= ?";

			// 게시글 삭제
			String sql_notice = "delete from notice where ncindex= ?";

			pstmt = conn.prepareStatement(sql_adminid);
			pstmt.setInt(1, ncindex);
			pstmt.setString(2, adminid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (adminid.equals(rs.getString(1))) {
					conn.setAutoCommit(false);

					/*
					 * DB_Close.close(pstmt); pstmt = conn.prepareStatement(sql_reply);
					 * pstmt.setInt(1, idx); pstmt.executeUpdate();
					 */

					// 게시글 삭제
					DB_Close.close(pstmt);
					pstmt = conn.prepareStatement(sql_notice);
					pstmt.setInt(1, ncindex);
					resultrow = pstmt.executeUpdate();
					if (resultrow > 0) {
						conn.commit();
					}

				} else { // ID 일치하지 않는 경우
					resultrow = -1;
				}
			}
		} catch (Exception e) {
			// 예외 발생시 rollback
			try {
				conn.rollback();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			try {
				conn.close();
			} catch (Exception e3) {
				System.out.println(e3.getMessage());
			}
		}
		return resultrow;
	}

/////////////////////////////////////////////++++++
//QnA Nick 리스트 (전체데이터 read)
	public List<QnaNick> getQnaNickList(int cpage, int pagesize, String id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaNick> qnaNickList = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

// 공식같은 로직
			int start = cpage * pagesize - (pagesize - 1); // 1 * 5 - (5 - 1) >> 1
			int end = cpage * pagesize; // 1 * 5 >> 5

			String sql_admin = "select * from "
					+ "(select rownum rn, qaindex, title, qatime, count, scstate, content, filename, id, awstate, nick from ("
					+ "SELECT q.*, u.nick FROM userqna q JOIN bituser u ON q.id = u.id order by q.qaindex desc" + // endrow
					") where rownum <=?) where rn >= ?"; // startrow

			String sql = "select * from (select rownum rn, qaindex, title, qatime, count, scstate, content, filename, id, awstate, nick"
					+ " from (SELECT q.*, u.nick FROM userqna q JOIN bituser u ON q.id = u.id where u.id=? or scstate='n' order by q.qaindex desc)"
					+ " where rownum <=?) where rn >= ?"; // startrow

			if (id.equals("admin")) {

				pstmt = conn.prepareStatement(sql_admin);

				pstmt.setInt(1, end);
				pstmt.setInt(2, start);

			} else {

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			}

			rs = pstmt.executeQuery();
			qnaNickList = new ArrayList<QnaNick>();
			System.out.println(qnaNickList + "값 가져오기전-dao");

			while (rs.next()) {
				QnaNick qnaNick = new QnaNick();
				qnaNick.setQaindex(rs.getInt("qaindex"));
				qnaNick.setTitle(rs.getString("title"));
				qnaNick.setQatime(rs.getString("qatime"));
				qnaNick.setCount(rs.getInt("count"));
				qnaNick.setScstate(rs.getString("scstate"));
				qnaNick.setContent(rs.getString("content"));
				qnaNick.setFilename(rs.getString("filename"));
				qnaNick.setId(rs.getString("id"));
				qnaNick.setAwstate(rs.getString("awstate"));
				qnaNick.setNick(rs.getString("nick"));

				qnaNickList.add(qnaNick);
			}

			System.out.println(qnaNickList + "값 가져옴-dao");

		} catch (Exception e) {
			System.out.println("dao 오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return qnaNickList;
	}

/////////////////////////////////////////////
//QnA Bituser (Nick 읽기)	
	public List<User> getUserNickList() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> usernicklist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "select id, nick from bituser";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			usernicklist = new ArrayList<User>();

			System.out.println(usernicklist + "값 가져오기전-dao");

			while (rs.next()) {
				User user = new User();
				user.setNick(rs.getString("nick"));
				user.setId(rs.getString("id"));
				usernicklist.add(user);
			}

			System.out.println(usernicklist + "값 가져옴-dao");

		} catch (Exception e) {
			System.out.println("dao 오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return usernicklist;
	}

/////////////////////////////////////////////
//QnA 상세보기(1건 읽기)
	public Qna getDetail(int qaindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Qna qna = new Qna();
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from userqna where qaindex=?"; // *하지말자
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qaindex);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				qna.setQaindex(rs.getInt("qaindex"));
				qna.setTitle(rs.getString("title"));
				qna.setQatime(rs.getString("qatime"));
				qna.setCount(rs.getInt("count"));
				qna.setScstate(rs.getString("scstate"));
				qna.setContent(rs.getString("content"));
				qna.setFilename(rs.getString("filename"));
				qna.setId(rs.getString("id"));
				qna.setAwstate(rs.getString("awstate"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return qna;
	}

/////////////////////////////////////////////
//QnA 글쓰기
	public int QnAWrite(Qna qna) {
		Connection conn = null;// 추가
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");// 추가
			String sql = "insert into userqna(qaindex,title,qatime,count,scstate,content,filename,id,awstate) values(userqna_qaindex.nextval,?,sysdate,0,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, qna.getTitle());
			pstmt.setString(2, qna.getScstate());
			pstmt.setString(3, qna.getContent());
			pstmt.setString(4, qna.getFilename());
			pstmt.setString(5, qna.getId());
			pstmt.setString(6, qna.getAwstate());

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultrow;
	}

/////////////////////////////////////////////
//QnA 글쓰기 refer값 생성
	private int getMaxRefer() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int refer_max = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "select nvl(max(refer),0) from empboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				refer_max = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close(); // 반납이요 ^^
			} catch (Exception e) {

			}
		}

		return refer_max;
	}

/////////////////////////////////////////////
//QnA 게시글 수정
	public int editQna(Qna qna) {
		Connection conn = null;// 추가
		int resultrow = 0;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");// 추가
			String sql = "update userqna set title=?, scstate=?, content=?, filename=?, id=?, awstate=? where qnindex=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, qna.getTitle());
			pstmt.setString(2, qna.getScstate());
			pstmt.setString(3, qna.getContent());
			pstmt.setString(4, qna.getFilename());
			pstmt.setString(5, qna.getId());
			pstmt.setString(6, qna.getAwstate());

			pstmt.setInt(7, qna.getQaindex());

			resultrow = pstmt.executeUpdate();

			System.out.println("Dao 수정 :" + qna.getQaindex());

		} catch (Exception e) {
			System.out.println("Update : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultrow;
	}

/////////////////////////////////////////////
// QnA 게시글 삭제
	public int deleteQna(int qaindex, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			System.out.println("2");

// ID검증
			String sql_id = "select id from userqna where qaindex=? and id=?";
// 댓글 삭제
			String sql_qnareply = "delete from qnareply where qaindex=?";
// 게시글 삭제
			String sql_userqna = "delete from userqna where qaindex=?";

			System.out.println("2");
// 연결
			pstmt = conn.prepareStatement(sql_id);
			pstmt.setInt(1, qaindex);
			pstmt.setString(2, id);

			System.out.println("3");
// 쿼리실행
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (id.equals(rs.getString(1))) {
					System.out.println("4");

					conn.setAutoCommit(false); // 오토커밋 해제

					System.out.println("sql_id:  " + pstmt);

					DB_Close.close(pstmt); // pstmt 닫고

					System.out.println("5");
// 재연결
					pstmt = conn.prepareStatement(sql_qnareply);
					pstmt.setInt(1, qaindex);

// 쿼리실행
					pstmt.executeUpdate();

					System.out.println("6");
					System.out.println("sql_qnareply : " + pstmt);

					DB_Close.close(pstmt); // pstmt 닫고

					System.out.println("7");
// 게시글 삭제
					pstmt = conn.prepareStatement(sql_userqna);
					pstmt.setInt(1, qaindex);

					System.out.println(" sql_userqna: " + pstmt);
					System.out.println("8");

					resultrow = pstmt.executeUpdate();
					System.out.println("resultrow :" + resultrow);

					if (resultrow > 0) {
						System.out.println("9");
						conn.commit(); // 커밋
					}

				} else { // ID 일치하지 않는 경우
					resultrow = -1;
					System.out.println("10");
				}
			}

		} catch (Exception e) {
			System.out.println("11");

// 예외 발생시 rollback
			try {
				conn.rollback();
				System.out.println("12");

			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				System.out.println("13");
			}
			System.out.println(e.getMessage());
			System.out.println("14");

		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			System.out.println("15");

			try {
				conn.close();
				System.out.println("16");
			} catch (Exception e3) {
				System.out.println(e3.getMessage());
				System.out.println("17");
			}
		}
		System.out.println("18");
		return resultrow;
	}

/////////////////////////////////////////////
//QnA 게시글 조회수 증가
	public boolean getReadNum(int qaindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "update userqna set count = count + 1 where qaindex=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, qaindex);

			int resultrow = pstmt.executeUpdate();
			if (resultrow > 0) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

/////////////////////////////////////////////
//총 QnA 게시글 세기
	public int getQnACount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int qnacount = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from userqna";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				qnacount = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return qnacount;
	}

/////////////////////////////////////////////
//댓글(admin 답변)쓰기
	public int QnAReplyWrite(QnaReply qnaReply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "insert into qnareply(title, id, content, qaindex, qartime) " + " values(?,?,?,?,sysdate)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, qnaReply.getTitle());
			pstmt.setString(2, qnaReply.getId());
			pstmt.setString(3, qnaReply.getContent());
			pstmt.setInt(4, qnaReply.getQaindex());

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 받환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultrow;
	}

/////////////////////////////////////////////
//댓글(admin 답변)여부 체크
	public int QnaReplyCheck(int qaindex) {
		Connection conn = null;// 추가
		int resultrow = 0;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");// 추가
			String sql = "update userqna set awstate=? where qaindex=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "1");
			pstmt.setInt(2, qaindex);

			resultrow = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Update 댓글여부체크: " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultrow;
	}

/////////////////////////////////////////////
//댓글(admin 답변) 수정
	public int QnAReplyUpdate(QnaReply qnaReply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "update qnareply set title=?, qartime=sysdate, content=? where qaindex=? and id=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, qnaReply.getTitle());
			pstmt.setString(2, qnaReply.getContent());
			pstmt.setInt(3, qnaReply.getQaindex());
			pstmt.setString(4, qnaReply.getId());

			resultrow = pstmt.executeUpdate();
			System.out.println(resultrow);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return resultrow;
	}

//////////////////////////////////////////////////////////////////////////////
//댓글(admin 답변) 삭제
	public int QnAReplyDelete(int qaindex, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultrow = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");

// ID검증
			String sql_id = "select id from admin where id=?";
// 댓글 삭제
			String sql_qnareply = "delete from qnareply where qaindex=?";
// 게시글 awstate 업뎃
			String sql_userqna = "update userqna set awstate='0' where qaindex=?";

// 연결
			pstmt = conn.prepareStatement(sql_id);
			pstmt.setString(1, id);

// 쿼리실행
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (id.equals(rs.getString(1))) {
					conn.setAutoCommit(false); // 오토커밋 해제

					DB_Close.close(pstmt); // pstmt 닫고

// 재연결
					pstmt = conn.prepareStatement(sql_qnareply);
					pstmt.setInt(1, qaindex);

// 쿼리실행
					pstmt.executeUpdate();
					DB_Close.close(pstmt); // pstmt 닫고

// 게시글 삭제
					pstmt = conn.prepareStatement(sql_userqna);
					pstmt.setInt(1, qaindex);

					resultrow = pstmt.executeUpdate();

					if (resultrow > 0) {
						conn.commit(); // 커밋
					}

				} else { // ID 일치하지 않는 경우
					resultrow = -1;
				}

			}

		} catch (Exception e) {

// 예외 발생시 rollback
			try {
				conn.rollback();

			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
			System.out.println(e.getMessage());

		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);

			try {
				conn.close();
			} catch (Exception e3) {
				System.out.println(e3.getMessage());
			}
		}

		return resultrow;
	}

//////////////////////////////////////////////////////////////////////////////
//댓글(admin 답변) 수 세기
	public int getQnAReplyCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int replycount = 0;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from qnareply";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				replycount = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return replycount;
	}

//////////////////////////////////////////////////////////////////////////////
//댓글(admin 답변) 조회하기
	public List<QnaReply> QnAReplyAll(int qaindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaReply> qnaReplylist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select title, id, content, qaindex ,qartime from qnareply where qaindex=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qaindex);
			rs = pstmt.executeQuery();

			qnaReplylist = new ArrayList<QnaReply>();

			System.out.println("dao 댓글조회 가져오기전: " + qnaReplylist);

			while (rs.next()) {
				QnaReply qnaReply = new QnaReply();

				qnaReply.setTitle(rs.getString("title"));
				qnaReply.setId(rs.getString("id"));
				qnaReply.setContent(rs.getString("content"));
				qnaReply.setQaindex(rs.getInt("qaindex"));
				qnaReply.setQartime(rs.getString("qartime"));

				qnaReplylist.add(qnaReply);
			}

			System.out.println("dao 댓글조회 가져옴: " + qnaReplylist);

		} catch (Exception e) {
			System.out.println("dao 오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return qnaReplylist;
	}

//////////////////////////////////////////////////////////////////////////////
//댓글(admin 답변) 상세보기
	public QnaReply QnAReplyDetail(int qaindex) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		QnaReply qnaReply = new QnaReply();

		try {
			conn = ConnectionHelper.getConnection("oracle");

//String sql = "select title from qnareply where qaindex=?";    => ^-^....ㅋ
			String sql = "select * from qnareply where qaindex=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qaindex);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				qnaReply.setTitle(rs.getString("title"));
				qnaReply.setId(rs.getString("id"));
				qnaReply.setContent(rs.getString("content"));
				qnaReply.setQaindex(rs.getInt("qaindex"));
				qnaReply.setQartime(rs.getString("qartime"));
			}

		} catch (Exception e) {
			System.out.println("dao 오류 :" + e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();// 반환하기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return qnaReply;
	}

	// 회원관리 목록 조회
	public List<User> getUserList(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userlist = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from" + "(select rownum rn, n.* from bituser n order by rtime desc)"
					+ " where rn between ? and ?";
			pstmt = conn.prepareStatement(sql);

			int start = cpage * pagesize - (pagesize - 1);
			int end = cpage * pagesize;

			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();
			userlist = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setPwd(rs.getString("pwd"));
				user.setNick(rs.getString("nick"));
				user.setLoc(rs.getString("loc"));
				user.setProfile(rs.getString("profile"));
				user.setRtime(rs.getString("rtime"));

				userlist.add(user);
			}
			System.out.println(userlist.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return userlist;
	}

	// 회원 총 수 구하기
	public int getTotalUserCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select count(*) from bituser";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return totalcount;
	}

	// 회원관리 상세 보기
	public User getUserById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "select * from bituser where id= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getString("id"));
				user.setPwd(rs.getString("pwd"));
				user.setNick(rs.getString("nick"));
				user.setLoc(rs.getString("loc"));
				user.setProfile(rs.getString("profile"));
				user.setRtime(rs.getString("rtime"));
				user.setLat(rs.getString("lat"));
				user.setLon(rs.getString("lon"));

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return user;
	}

	// 회원 정보 삭제
	public int deleteUser(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultrow = 0;
		try {
			conn = ConnectionHelper.getConnection("oracle");
			// ID검증
			String sql_id = "select id from bituser where id= ?";

			// 게시글 삭제
			String sql_user = "delete from bituser where id= ? ";

			pstmt = conn.prepareStatement(sql_id);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			System.out.println("테스트");
			if (rs.next()) {
				if (id.equals(rs.getString(1))) {
					conn.setAutoCommit(false);

					/*
					 * DB_Close.close(pstmt); pstmt = conn.prepareStatement(sql_reply);
					 * pstmt.setInt(1, idx); pstmt.executeUpdate();
					 */

					// 게시글 삭제
					DB_Close.close(pstmt);
					pstmt = conn.prepareStatement(sql_user);
					pstmt.setString(1, id);
					resultrow = pstmt.executeUpdate();
					if (resultrow > 0) {
						conn.commit();

					}

				} else { // ID 일치하지 않는 경우
					resultrow = -1;
				}
			}
		} catch (Exception e) {
			// 예외 발생시 rollback
			try {
				conn.rollback();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
			System.out.println(e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			try {
				conn.close();
			} catch (Exception e3) {
				System.out.println(e3.getMessage());
			}
		}
		return resultrow;
	}
	// 차트 데이터
		public JSONArray getCtTranChart() {

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			try {
				conn = ConnectionHelper.getConnection("oracle");
				String sql = " select ctname, count(b.trstate) tran from board b join category c on b.ctcode = c.ctcode " +
						" where b.trstate = 'Y' group by ctname ";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {

					jsonObject.put("ctname", rs.getString(1));
					jsonObject.put("trCount", rs.getInt(2));
					jsonArray.add(jsonObject);
				}

			} catch (Exception e) {
				System.out.println("오류 :" + e.getMessage());
			} finally {
				DB_Close.close(rs);
				DB_Close.close(pstmt);
				try {
					conn.close(); // 받환하기
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return jsonArray;
		}
}
