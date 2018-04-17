package com.xuanwu.competency_model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {

	public static void main(String[] args) {
		Map<String,String> map = new ConcurrentHashMap<>();
		map.put("12", "22");
		map.get("12");
	}

}
