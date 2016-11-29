package com.kaffatech.latte.mainframe.blo;

import java.util.Map;

public interface HeaderModelCreater {
	
	String MODEL_KEY = "header";

	Map<String, Object> create();

}
