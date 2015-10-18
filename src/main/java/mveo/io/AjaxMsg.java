package mveo.io;


public class AjaxMsg {

	private MsgType type;
	private String text;

	public MsgType getType() {
		return this.type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String msg) {
		this.text = msg;
	}

}
