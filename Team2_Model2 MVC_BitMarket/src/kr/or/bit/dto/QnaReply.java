package kr.or.bit.dto;

public class QnaReply {
	private String title;
	private String id;
	private String content;
	private int qaindex;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getQaindex() {
		return qaindex;
	}
	public void setQaindex(int qaindex) {
		this.qaindex = qaindex;
	}
	@Override
	public String toString() {
		return "QnaReply [title=" + title + ", id=" + id + ", content=" + content + ", qaindex=" + qaindex + "]";
	}
	
	
}
