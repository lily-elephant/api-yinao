package yinao.qualityLife.model;


import java.util.HashMap;

@SuppressWarnings("serial")
public class ResultMap extends HashMap<String, Object> {


	public ResultMap() {

	}

	public ResultMap success() {
		this.put("code", "200");
		return this;
	}

	public ResultMap fail(String code) {
		this.put("code", code);
		return this;
	}

	public ResultMap message(String message) {
		this.put("message", message);
		return this;
	}

	public ResultMap data(Object obj) {
		this.put("data", obj);
		return this;
	}

	public ResultMap token(String token) {
		this.put("X-Auth-Token", token);
		return this;
	}
	
	public ResultMap count(int count) {
		this.put("count", count);
		return this;
	}
	
	public ResultMap ortherdata(Object obj) {
		this.put("ortherdata", obj);
		return this;
	}
	
	public ResultMap parentauth(Object obj) {
		this.put("parentauth", obj);
		return this;
	}
	public ResultMap childauth(Object obj) {
		this.put("childauth", obj);
		return this;
	}
	
}
